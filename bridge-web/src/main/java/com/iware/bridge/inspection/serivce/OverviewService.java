package com.iware.bridge.inspection.serivce;

import com.iware.bridge.inspection.vo.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LBX
 * @date 2021-7-29
 */

public interface OverviewService {

    /** 查询总巡查次数/当月巡查次数/巡查病害数/巡查桥隧数 */
    public Map<String, Integer> getInspectionCount();

    /** 查询维养桥隧数/待处理病害数/已处理病害数/年度维养总金额 */
    public Map<String, Double> getMaintainCount();

    /** 查询日历工作记录表 */
    public List<Date> getWorkRecord(Integer type, Integer year, Integer month);

    /** 查询主要病害占比 */
    public List<DiseaseRatioVo> getDiseaseRatio();

    /** 查询维修病害数 */
    public Map<String, Integer> getDiseaseCount();

    /** 查询养护等级占比 */
    public List<StructureGradeRatioVo> getMaintainGradeRatio();

    /** 查询维修类型分布 */
    public List<MaintainTypeVo> getTypeRatio();

    /** 查询巡查病害数变化 */
    public List<InspectionDiseaseDateVo> getDiseaseTrend(Integer year, Integer month);

    /** 查询维修病害数变化 */
    public List<MaintainAccountMonthVo> getAccountTrend(Integer year, Integer month);
}
