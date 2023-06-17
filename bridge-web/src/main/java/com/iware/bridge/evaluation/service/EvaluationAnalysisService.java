package com.iware.bridge.evaluation.service;


import com.iware.bridge.evaluation.vo.*;

import java.util.List;

public interface EvaluationAnalysisService {

    /** 获取角色下的项目集合 **/
    List<AppointProjectVO> getProjects();

    /** 获取最新的一次检测记录 **/
    EvaluationAnalysisVO getNewProject(MonitorAnalysisFilterVO monitorAnalysisFilterVO);

    /** 获取页面信息 **/
    MonitorAnalysisResultVO getPageInfo(MonitorAnalysisFilterVO monitorAnalysisFilterVO);

    /** 获取构件病害类型统计 **/
    List<ComponentDiseaseTypeNumber> getComponentDiseaseType(MonitorAnalysisFilterVO monitorAnalysisFilterVO);

    /** 获取BCIBSI评分 **/
    List<ScoreComparison> getBciBsi(MonitorAnalysisFilterVO monitorAnalysisFilterVO);

    /** 获取结构物时间根据结构物id **/
    List<String> selectStructureTimeByStructureId(Integer id,Integer project);
}
