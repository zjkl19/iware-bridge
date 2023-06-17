package com.iware.bridge.evaluation.service;

import com.iware.bridge.evaluation.vo.BCIDetailVO;
import com.iware.bridge.evaluation.vo.BCIEvaluationDetailDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WJP
 * @date 2021-8-16
 */

public interface EvaluationRecordBciService {

    /** 查找BCI评价详情 **/
    public BCIEvaluationDetailDTO selBCIEvaluationDetail(BCIDetailVO bciDetailVO);

    /** 根据线路查看是否生成记录 **/
    public Integer existRecord(Long roadId) ;

    /** 计算BCI评分 **/
    public BCIEvaluationDetailDTO countBCI(BCIEvaluationDetailDTO bciEvaluationDetailDTO, HttpServletRequest request);
}
