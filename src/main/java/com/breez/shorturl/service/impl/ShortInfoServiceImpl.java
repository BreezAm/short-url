package com.breez.shorturl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breez.shorturl.common.service.IFileService;
import com.breez.shorturl.common.service.ShortURLService;
import com.breez.shorturl.common.service.UserSessionService;
import com.breez.shorturl.config.ShortUrlProperties;
import com.breez.shorturl.constants.Constants;
import com.breez.shorturl.entity.ShortInfo;
import com.breez.shorturl.entity.request.ShortInfoForm;
import com.breez.shorturl.entity.request.UrlSearchForm;
import com.breez.shorturl.entity.vo.TempShortUrlVo;
import com.breez.shorturl.mapper.ShortInfoMapper;
import com.breez.shorturl.service.IShortInfoService;
import com.breez.shorturl.util.ShortUrlCodeUtil;
import com.breez.shorturl.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-13
 */
@Service
public class ShortInfoServiceImpl extends ServiceImpl<ShortInfoMapper, ShortInfo> implements IShortInfoService {
    private final Logger logger = LoggerFactory.getLogger(ShortInfoServiceImpl.class);
    @Autowired
    private UserSessionService userSessionService;
    @Autowired
    private ShortURLService shortURLService;
    @Resource
    private ShortInfoMapper shortInfoMapper;

    @Autowired
    @Qualifier(value = "ossServiceImpl")
    private IFileService ossService;

    @Autowired
    @Qualifier(value = "minioServiceImpl")
    private IFileService minioService;

    private Lock lock = new ReentrantLock();
    @Autowired
    private ShortUrlProperties shortUrlConfig;

    @Override
    public Page<ShortInfo> listByParams(UrlSearchForm urlSearchForm, Long current, Long limit) {
        String groupId = urlSearchForm.getGroupId();
        String title = urlSearchForm.getTitle();
        String userId = userSessionService.getCurrentUser().getUserId();
        Page<ShortInfo> page = new Page<>(current, limit);

        LambdaQueryWrapper<ShortInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShortInfo::getUserId, userId);
        if (StringUtils.hasText(groupId)) {
            wrapper.eq(ShortInfo::getGroupId, groupId);
        }
        if (StringUtils.hasText(title)) {
            wrapper.like(ShortInfo::getTitle, title);
        }
        wrapper.orderByDesc(ShortInfo::getCreateTime);

        return baseMapper.selectPage(page, wrapper);
    }


    @Override
    @Transactional
    public boolean addShortInfo(ShortInfoForm shortInfoForm) {
        logger.info("添加的短链信息：{}", shortInfoForm);
        lock.lock();
        try {
            String userId = userSessionService.getCurrentUser().getUserId();
            String url = shortInfoForm.getUrl();
            String title = shortInfoForm.getTitle();
            String groupId = shortInfoForm.getGroupId();

            //先去看布隆过滤器中是否有，如果有生成随机字符串【优化】
            String code = ShortUrlCodeUtil.genShortCode(url);
            if (shortURLService.contain(code)) {
                String uuid = UUID.uuid(8);
                code = ShortUrlCodeUtil.genShortCode(url, uuid);
            }

            String shortUrl = shortUrlConfig.getDomain() + code;
            //生成二维码
            //  String qrCodePath = ossService.uploadQR(shortUrl, code);
            String qrCodePath = minioService.uploadQR(shortUrl, code,"");

            ShortInfo shortInfo = new ShortInfo();
            shortInfo.setUserId(userId);
            shortInfo.setTitle(title);
            shortInfo.setGroupId(groupId);
            shortInfo.setOriginal(url);
            shortInfo.setShortUrl(shortUrl);
            shortInfo.setQrCode(qrCodePath);
            shortInfo.setDomain(shortUrlConfig.getDomain());
            //保存信息到数据库
            shortInfo.setCreateTime(LocalDateTime.now());
            baseMapper.insert(shortInfo);
            shortURLService.put(code);
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long getShortUrlDayCount() {
        String userId = userSessionService.getCurrentUser().getUserId();
        return shortInfoMapper.selectShortUrlDayCount(userId);

    }

    @Override
    public long getShortUrlCount() {
        String userId = userSessionService.getCurrentUser().getUserId();
        LambdaQueryWrapper<ShortInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShortInfo::getUserId, userId);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public TempShortUrlVo getTempShortInfo(String url) {

        lock.lock();
        try {
            String userId ="11111111111111111111111111111111";
            String title = "临时";
            String groupId = "11111111111111111111111111111111";

            //先去看布隆过滤器中是否有，如果有生成随机字符串【优化】
            String code = ShortUrlCodeUtil.genShortCode(url);
            if (shortURLService.contain(code)) {
                String uuid = UUID.uuid(8);
                code = ShortUrlCodeUtil.genShortCode(url, uuid);
            }

            String shortUrl = shortUrlConfig.getDomain() + code;
            String qrCodePath = minioService.uploadQR(shortUrl, code, Constants.USER_TYPE_TEMP);

            ShortInfo shortInfo = new ShortInfo();
            shortInfo.setUserId(userId);
            shortInfo.setTitle(title);
            shortInfo.setGroupId(groupId);
            shortInfo.setOriginal(url);
            shortInfo.setShortUrl(shortUrl);
            shortInfo.setQrCode(qrCodePath);
            shortInfo.setDomain(shortUrlConfig.getDomain());
            //保存信息到数据库
            shortInfo.setCreateTime(LocalDateTime.now());
            baseMapper.insert(shortInfo);
            shortURLService.put(code);
            TempShortUrlVo vo = new TempShortUrlVo();
            vo.setQr(qrCodePath);
            vo.setUrl(shortUrl);
            vo.setExpireTime(60L);
            return vo;
        } finally {
            lock.unlock();
        }
    }
}
