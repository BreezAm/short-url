package com.breez.shorturl.mapper;


import com.breez.shorturl.entity.Analysis;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.breez.shorturl.entity.View;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author BreezAm
 * @since 2021-12-19
 */
public interface AnalysisMapper extends BaseMapper<Analysis> {

    Long selectFrequencyByUserId(String userId);

    Analysis selectAnalysisDayByUserId(@Param("userId") String userId, @Param("urlId") String urlId);

    List<View> selectAnalysisByDay(@Param("userId") String userId);
}
