package com.iware.bridge.inspection.dao;

import com.iware.bridge.inspection.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OverViewExpDao {

    /** 总巡查次数/当月巡查次数(巡查细项数) */
    public Integer countInspectionPlan(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                   @Param("powerId") Integer powerId, @Param("month") String month);

    /** 有巡查计划的桥隧数 */
    public Integer countInspectionStructure(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                            @Param("powerId") Integer powerId);

    /** 最新一次巡查病害数 */
    public Integer countLastInspectionDisease(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                              @Param("powerId") Integer powerId, @Param("status") Integer status);

    public Integer countMaintainStructure(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                            @Param("powerId") Integer powerId);

    public Integer countMaintainDisease(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                          @Param("powerId") Integer powerId);

    public Double sumMaintainAmount(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                        @Param("powerId") Integer powerId);

    /** 获取巡查维养工作记录表 */
    public List<Date> getWorkRecord(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                    @Param("powerId") Integer powerId, @Param("type") Integer type,
                                    @Param("filterTime") String filterTime);

    public List<DiseaseRatioVo> getDiseaseRatio(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                                @Param("powerId") Integer powerId);

    public List<StructureGradeRatioVo> getMaintainGradeRatio(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                                       @Param("powerId") Integer powerId);

    public List<MaintainTypeVo> getTypeRatio(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                             @Param("powerId") Integer powerId);

    public List<InspectionDiseaseDateVo> getDiseaseTrend(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                                         @Param("powerId") Integer powerId, @Param("filterTime") String filterTime);

    public List<MaintainAccountMonthVo> getAccountTrend(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                                        @Param("powerId") Integer powerId, @Param("beginMonth") String beginMonth,
                                                        @Param("endMonth") String endMonth);

}

