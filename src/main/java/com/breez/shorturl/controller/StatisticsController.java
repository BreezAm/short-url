package com.breez.shorturl.controller;

import com.breez.shorturl.entity.Statistics;
import com.breez.shorturl.entity.vo.GroupAreaVo;
import com.breez.shorturl.entity.vo.ViewVo;
import com.breez.shorturl.service.IAnalysisService;
import com.breez.shorturl.service.IGroupService;
import com.breez.shorturl.service.IShortInfoService;
import com.breez.shorturl.util.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据统计
 *
 * @author BreezAm
 */
@RestController
@RequestMapping("/api/shorturl/statistics")
public class StatisticsController {
    @Autowired
    private IShortInfoService shortInfoService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private IAnalysisService analysisService;

    @GetMapping("getGroupArea")
    public R getGroupArea() {
        List<GroupAreaVo> data = groupService.getGroupArea();
        return R.ok(data);
    }

    @GetMapping("getStatistics")
    public R getStatistics() {
        long shortUrlDayCount = shortInfoService.getShortUrlDayCount();
        long shortUrlCount = shortInfoService.getShortUrlCount();
        long groupCount = groupService.getGroupCount();
        long frequency = analysisService.getFrequency();
        Statistics statistics = new Statistics();
        statistics.setGroupCount(groupCount);
        statistics.setShortUrlCount(shortUrlCount);
        statistics.setShortUrlDayCount(shortUrlDayCount);
        statistics.setTotalViewCount(frequency);
        return R.ok(statistics);
    }

    @GetMapping("getView")
    public R getView() {
      ViewVo viewVo= analysisService.getView();
      return R.ok(viewVo);
    }
}
