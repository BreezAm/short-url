package com.breez.shorturl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breez.shorturl.common.service.UserSessionService;
import com.breez.shorturl.entity.Group;
import com.breez.shorturl.entity.LoginUser;
import com.breez.shorturl.entity.ShortInfo;
import com.breez.shorturl.entity.vo.GroupAreaVo;
import com.breez.shorturl.enums.ResponseCode;
import com.breez.shorturl.exceptions.ShortUrlException;
import com.breez.shorturl.mapper.GroupMapper;
import com.breez.shorturl.mapper.ShortInfoMapper;
import com.breez.shorturl.service.IGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-13
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {
    private final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
    @Autowired
    private UserSessionService userSessionService;
    @Resource
    private ShortInfoMapper shortInfoMapper;
    @Resource
    private GroupMapper groupMapper;

    @Override
    @Transactional
    public boolean saveGroup(Group form) {
        String name = form.getName().trim();
        if (name.length() > 20) {
            logger.error("组名字长度 {} 超过最大长度 {}", name.length(), 20);
            throw new ShortUrlException(ResponseCode.GROUP_NAME_OVER.getCode(), ResponseCode.GROUP_NAME_OVER.getMsg());
        }
        LoginUser currentUser = userSessionService.getCurrentUser();
        form.setUserId(currentUser.getUserId());
        Group group = getGroupByName(name);
        if (Objects.nonNull(group)) {
            logger.error("组已经存在: {}", name);
            throw new ShortUrlException(ResponseCode.GROUP_EXIST.getCode(), ResponseCode.GROUP_EXIST.getMsg());
        }
        form.setCreateTime(LocalDateTime.now());
        baseMapper.insert(form);
        return true;
    }

    @Override
    @Transactional
    public boolean updateGroup(Group form) {
        String name = form.getName().trim();
        LoginUser currentUser = userSessionService.getCurrentUser();
        form.setUserId(currentUser.getUserId());
        long size = getGroupLengthByName(name);
        if (size >= 1) {
            logger.error("组名称重复存在: {}", name);
            throw new ShortUrlException(ResponseCode.GROUP_EXIST.getCode(), ResponseCode.GROUP_EXIST.getMsg());
        }
        form.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(form);
        return false;
    }

    @Override
    @Transactional
    public boolean deleteGroup(String id) {
        long size = getCurrentUserGroupSize();
        logger.info("{} 用户组的数量：{}", userSessionService.getCurrentUser().getUsername(), size);
        if (size == 1) {
            logger.error("不能删除最后一个分组");
            throw new ShortUrlException(ResponseCode.GROUP_LEAST_ONE.getCode(), ResponseCode.GROUP_LEAST_ONE.getMsg());
        }
        try {
            baseMapper.deleteById(id);
            LambdaQueryWrapper<ShortInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ShortInfo::getGroupId, id);
            shortInfoMapper.delete(wrapper);
        } catch (Exception e) {
            logger.error("删除组失败：{}", id);
            return false;
        }
        return true;
    }

    /**
     * 获取当前登录用户的组的数量
     */
    private long getCurrentUserGroupSize() {
        String uid = userSessionService.getCurrentUser().getUserId();
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Group::getUserId, uid);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public List<Group> getCurrentUserGroupList() {
        LoginUser user = userSessionService.getCurrentUser();
        String userId = user.getUserId();
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Group::getUserId, userId);
        return baseMapper.selectList(wrapper);
    }



    @Override
    public List<GroupAreaVo> getGroupArea() {
        String userId = userSessionService.getCurrentUser().getUserId();
        return groupMapper.getGroupArea(userId);
    }

    @Override
    public long getGroupCount() {
        String userId = userSessionService.getCurrentUser().getUserId();
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Group::getUserId, userId);
        return baseMapper.selectCount(wrapper);
    }


    /**
     * 通过组的名字获取详细信息
     */
    private long getGroupLengthByName(String name) {
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Group::getName, name);
        return baseMapper.selectCount(wrapper);
    }

    private Group getGroupByName(String name) {
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Group::getName, name);
        return baseMapper.selectOne(wrapper);
    }


}
