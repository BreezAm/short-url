package com.breez.shorturl.service;

import com.breez.shorturl.entity.Analysis;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breez.shorturl.entity.vo.ViewVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-19
 */
public interface IAnalysisService extends IService<Analysis> {

    long getFrequency();

    Analysis getAnalysisDayByUserId(String userId,String urlId);

    ViewVo getView();

}
