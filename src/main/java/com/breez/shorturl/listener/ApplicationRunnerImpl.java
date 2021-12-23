package com.breez.shorturl.listener;

import com.breez.shorturl.common.service.ShortURLService;
import com.breez.shorturl.entity.ShortInfo;
import com.breez.shorturl.service.IShortInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统启动之前需要做的事
 *
 * @author BreezAm
 */
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(ApplicationRunnerImpl.class);
    @Autowired
    private ShortURLService shortURLService;
    @Autowired
    private IShortInfoService shortInfoService;


    @Override
    public void run(ApplicationArguments args) {
        initShortUrlCode();
    }

    private void initShortUrlCode() {
        logger.info("从MySQL初始化短链URL code到布隆过滤器");
        List<String> codes = getShortUrlCode();
        if (!CollectionUtils.isEmpty(codes)) {
            for (String code : codes) {
                int i = code.lastIndexOf("/");
                String c = code.substring(i + 1);
                shortURLService.put(c);
            }
        }
    }

    private List<String> getShortUrlCode() {
        List<ShortInfo> list = shortInfoService.list();
        return list.stream().map(ShortInfo::getShortUrl).collect(Collectors.toList());
    }
}
