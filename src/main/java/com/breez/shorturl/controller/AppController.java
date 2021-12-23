package com.breez.shorturl.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.breez.shorturl.annotations.View;
import com.breez.shorturl.common.service.RedisCache;
import com.breez.shorturl.common.service.ShortURLService;
import com.breez.shorturl.config.ShortUrlProperties;
import com.breez.shorturl.constants.CacheConstant;
import com.breez.shorturl.entity.ShortInfo;
import com.breez.shorturl.enums.ResponseCode;
import com.breez.shorturl.exceptions.ShortUrlException;
import com.breez.shorturl.service.IShortInfoService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Api(hidden = true)
@Controller
public class AppController {
    private final Logger logger = LoggerFactory.getLogger(AppController.class);
    @Autowired
    private ShortURLService shortURLService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private IShortInfoService shortInfoService;
    @Autowired
    private ShortUrlProperties shortUrlConfig;

    @GetMapping("/{code}")
    @Transactional
    @View
    public String trigger(@PathVariable String code) {
        logger.info("短网址编码：{}", code);
        if (!StringUtils.hasText(code)) {
            throw new ShortUrlException(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
        if (!shortURLService.contain(code)) {
            throw new ShortUrlException(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
        String originalUrl = redisCache.getCacheMapValue(CacheConstant.SHORT_URL_APP_TRIGGER, code);
        if (StringUtils.hasText(originalUrl)) {
            return "redirect:" + originalUrl;
        } else {
            String url = shortUrlConfig.getDomain() + code;
            LambdaQueryWrapper<ShortInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ShortInfo::getShortUrl, url);

            ShortInfo shortInfo = shortInfoService.getOne(wrapper);
            logger.warn("shortInfo:{}", shortInfo);
            String original = shortInfo.getOriginal();
            redisCache.setCacheMapValue(CacheConstant.SHORT_URL_APP_TRIGGER, code, original);
            originalUrl = original;
        }
        return "redirect:" + originalUrl;
    }
}
