package com.breez.shorturl.core.aop;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.breez.shorturl.config.ShortUrlProperties;
import com.breez.shorturl.entity.Analysis;
import com.breez.shorturl.entity.ShortInfo;
import com.breez.shorturl.service.IAnalysisService;
import com.breez.shorturl.service.IShortInfoService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;


@Component
@Aspect
public class ViewLogAspect {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IShortInfoService shortInfoService;
    @Autowired
    private IAnalysisService analysisService;
    @Autowired
    private ShortUrlProperties shortUrlConfig;

    @Pointcut("@annotation(com.breez.shorturl.annotations.View)")
    public void controllerAspect() {
    }

    @AfterReturning(pointcut = "controllerAspect()", returning = "rvt")
    public void after(JoinPoint joinPoint, Object rvt) {
        recordView(request);
    }

    private void recordView(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        int index = requestURI.lastIndexOf("/");
        String code = requestURI.substring(index + 1);
        String shortUrl = shortUrlConfig.getDomain() + code;

        LambdaQueryWrapper<ShortInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShortInfo::getShortUrl, shortUrl);
        ShortInfo info = shortInfoService.getOne(wrapper);
        String userId = info.getUserId();

        Analysis exits = analysisService.getAnalysisDayByUserId(userId, info.getId());
        if (Objects.nonNull(exits)) {
            Long frequency = exits.getFrequency();
            exits.setFrequency(frequency + 1);
            exits.setUpdateTime(LocalDateTime.now());
            analysisService.updateById(exits);
        }
        if (Objects.isNull(exits)) {
            Analysis analysis = new Analysis();
            analysis.setUserId(userId);
            analysis.setUrlId(info.getId());
            analysis.setFrequency(1L);
            analysis.setCreateTime(LocalDateTime.now());
            analysisService.save(analysis);
        }
    }


}
