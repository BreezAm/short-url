package com.breez.shorturl.mapper;

import com.breez.shorturl.entity.Group;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.breez.shorturl.entity.vo.GroupAreaVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-13
 */
public interface GroupMapper extends BaseMapper<Group> {

    List<GroupAreaVo> getGroupArea(String userId);
}
