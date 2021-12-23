package com.breez.shorturl.controller;


import com.breez.shorturl.controller.base.BaseController;
import com.breez.shorturl.entity.Group;
import com.breez.shorturl.service.IGroupService;
import com.breez.shorturl.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 组控制器
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-13
 */
@Api(tags = "锻炼组接口")
@RestController
@RequestMapping("/api/shorturl/group")
public class GroupController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Autowired
    private IGroupService groupService;


    @ApiOperation("保存短网址分组")
    @PostMapping("addGroup")
    public R addGroup(@RequestBody @Validated Group form) {
        return returnAjax(groupService.saveGroup(form));
    }

    @ApiOperation("更新短网址分组")
    @PutMapping("updateGroup")
    public R updateGroup(@RequestBody @Validated Group form) {
        logger.info("组修改信息：{}", form);
        groupService.updateGroup(form);
        return R.ok();
    }

    @ApiOperation("删除短网址分组")
    @DeleteMapping("deleteGroup/{id}")
    public R deleteGroup(@NotEmpty(message = "ID不能为空") @PathVariable String id) {
        return returnAjax(groupService.deleteGroup(id));
    }

    @ApiOperation("短网址分组列表")
    @GetMapping("groupList")
    public R groupList() {
        return R.ok(groupService.getCurrentUserGroupList());
    }

    @ApiOperation("获取短网址分组信息")
    @GetMapping("getGroup/{id}")
    public R getGroup(@NotEmpty(message = "ID不能为空") @PathVariable String id) {
        return R.ok(groupService.getById(id));
    }
}
