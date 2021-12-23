package com.breez.shorturl.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breez.shorturl.controller.base.BaseController;
import com.breez.shorturl.entity.ShortInfo;
import com.breez.shorturl.entity.request.ShortInfoForm;
import com.breez.shorturl.entity.request.UrlSearchForm;
import com.breez.shorturl.entity.vo.TempShortUrlVo;
import com.breez.shorturl.service.IShortInfoService;
import com.breez.shorturl.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-13
 */
@Api(tags = "短网址操作接口")
@RestController
@RequestMapping("/api/shorturl/shortInfo")
public class ShortInfoController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(ShortInfoController.class);
    @Autowired
    private IShortInfoService shortInfoService;

    @ApiOperation("生成短网址")
    @PostMapping("addShortInfo")
    public R addShortInfo(@RequestBody @Validated ShortInfoForm shortInfoForm) {
        shortInfoService.addShortInfo(shortInfoForm);
        return R.ok();
    }

    @ApiOperation("更新短网址")
    @PutMapping("updateShortInfo")
    public R updateShortInfo(@RequestBody ShortInfo shortInfo) {
        shortInfoService.updateById(shortInfo);
        return R.ok();
    }

    @ApiOperation("删除短网址")
    @DeleteMapping("delete/{id}")
    public R deleteShortInfo(@PathVariable @NotEmpty(message = "ID不能为空") String id) {
        logger.info("删除短链接ID：{}", id);
        shortInfoService.removeById(id);
        return R.ok();
    }

    @ApiOperation("获取临时短网址信息")
    @PostMapping("getTempShortInfo")
    public R getTempShortInfo(@RequestBody TempShortUrlVo shortUrlVo) {
        TempShortUrlVo vo = shortInfoService.getTempShortInfo(shortUrlVo.getUrl());
        return R.ok(vo);
    }

    @ApiOperation("获取所有的短网址")
    @PostMapping("shortInfoList/{current}/{limit}")
    public R shortShortInfoList(
            @RequestBody(required = false) UrlSearchForm urlSearchForm,
            @PathVariable @NotNull(message = "当前页不能为空") Long current,
            @PathVariable @NotNull(message = "页数不能为空") Long limit) {

        logger.info("短链接搜索参数：{}", urlSearchForm);
        Page<ShortInfo> data = shortInfoService.listByParams(urlSearchForm, current, limit);
        return R.ok(data);
    }

}
