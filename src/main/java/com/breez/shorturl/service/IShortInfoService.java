package com.breez.shorturl.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breez.shorturl.entity.ShortInfo;
import com.breez.shorturl.entity.request.ShortInfoForm;
import com.breez.shorturl.entity.request.UrlSearchForm;
import com.breez.shorturl.entity.vo.TempShortUrlVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-13
 */
public interface IShortInfoService extends IService<ShortInfo> {

    Page<ShortInfo> listByParams(UrlSearchForm urlSearchForm, Long current, Long limit);

    boolean addShortInfo(ShortInfoForm shortInfoForm);


    long getShortUrlDayCount();

    long getShortUrlCount();

    TempShortUrlVo getTempShortInfo(String url);

}
