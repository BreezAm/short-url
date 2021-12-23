package com.breez.shorturl.service;

import com.breez.shorturl.entity.Group;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breez.shorturl.entity.vo.GroupAreaVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-13
 */
public interface IGroupService extends IService<Group> {

    boolean saveGroup(Group form);

    boolean updateGroup(Group form);

    //删除组并删除下面对应的短网址信息，如果只有一个分组，那么不能删除
    boolean deleteGroup(String id);
    //获取当前用户的所有组信息
    List<Group> getCurrentUserGroupList();


    List<GroupAreaVo> getGroupArea();

    long getGroupCount();
}
