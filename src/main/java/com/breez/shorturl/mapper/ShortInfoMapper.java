package com.breez.shorturl.mapper;

import com.breez.shorturl.entity.ShortInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-13
 */
public interface ShortInfoMapper extends BaseMapper<ShortInfo> {

    long selectShortUrlDayCount(String userId);
}
