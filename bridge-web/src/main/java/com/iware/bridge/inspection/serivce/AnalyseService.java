package com.iware.bridge.inspection.serivce;

import com.iware.bridge.inspection.vo.DiseaseRatioVo;
import com.iware.bridge.inspection.vo.DiseaseRepairVo;
import com.iware.bridge.inspection.vo.DiseaseTypeVo;
import com.iware.bridge.inspection.vo.MaintainMethodCycleVo;

import java.util.List;
import java.util.Map;

/**
 * @author LBX
 * @date 2021-7-29
 */

public interface AnalyseService {

    /** 获取病害类型统计 */
    public List<DiseaseTypeVo> getDiseaseType(Integer structureId);

    /** 构件病害数排行榜 */
    public List<DiseaseRatioVo> compDiseaseRank(Integer structureId);

    /** 构件病害月频率 */
    public List<DiseaseRatioVo> diseaseMonthFrequency(Integer structureId, Integer month);

    /** 构件病害报修情况*/
    public List<DiseaseRepairVo> diseaseRepair(Integer structureId);

    /** 近五年构件病害数对比 */
    public Map<String, List<DiseaseRatioVo>> diseaseYearRank(Integer structureId);

    /** 项目近五年维修费用变化趋势 */
    Map<String, Integer> getAccountTrend(Integer structureId);

    /** 近五年维修趋势 */
    Map<String, List<DiseaseRatioVo>> getRepairTrend(Integer structureId);

    /** 重复维修项占比及周期分析 */
    List<MaintainMethodCycleVo> itemRepeatAnalyse(Integer structureId);

    /** 当年维修占比 */
    List<DiseaseRatioVo> maintainRatio(Integer structureId);

    /** 近五年维修项目统计 */
    Map<String, Integer> maintainItemStatistics(Integer structureId);
}
