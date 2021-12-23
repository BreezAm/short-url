package com.breez.shorturl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breez.shorturl.common.service.UserSessionService;
import com.breez.shorturl.entity.Analysis;
import com.breez.shorturl.entity.View;
import com.breez.shorturl.entity.vo.ViewVo;
import com.breez.shorturl.mapper.AnalysisMapper;
import com.breez.shorturl.service.IAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-19
 */
@Service
public class AnalysisServiceImpl extends ServiceImpl<AnalysisMapper, Analysis> implements IAnalysisService {
    @Autowired
    private UserSessionService userSessionService;
    @Resource
    private AnalysisMapper analysisMapper;

    @Override
    public long getFrequency() {
        String userId = userSessionService.getCurrentUser().getUserId();
        Long total = analysisMapper.selectFrequencyByUserId(userId);
        return total != null ? total : 0;
    }

    @Override
    public Analysis getAnalysisDayByUserId(String userId, String urlId) {
        return analysisMapper.selectAnalysisDayByUserId(userId, urlId);
    }

    @Override
    public ViewVo getView() {
        String userId = userSessionService.getCurrentUser().getUserId();
        List<View> views = analysisMapper.selectAnalysisByDay(userId);

        List<Integer> count = new ArrayList<>();
        List<String> days = new ArrayList<>();
        for (View view : views) {
            days.add(view.getDays());
            count.add(view.getCount());
        }
        ViewVo viewVo = new ViewVo();
        String[] d = new String[days.size()];
        Integer[] c = new Integer[count.size()];

        for (int i = 0; i < days.size(); i++) {
            d[i] = days.get(i);
        }
        for (int i = 0; i < count.size(); i++) {
            c[i] = count.get(i);
        }
        viewVo.setDays(d);
        viewVo.setCount(c);
        return viewVo;
    }
}
