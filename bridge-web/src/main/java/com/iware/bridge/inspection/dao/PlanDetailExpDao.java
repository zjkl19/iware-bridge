package com.iware.bridge.inspection.dao;

import com.iware.bridge.inspection.vo.*;
import com.iware.bridge.model.entity.inspection.PlanDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LBX
 * @date 2021-7-29
 */

@Repository
public interface PlanDetailExpDao {

    public PlanDetailVO selPlanDetail(@Param("planDetailId") Integer planDetailId);

    public List<MaintainItemVO> listMaintainItem(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                               @Param("powerId") Integer powerId, @Param("condition") PlanDetailFilter condition);

    public List<PlanDetailVO> listPlanDetail(@Param("unitId")Integer unitId, @Param("roleId") Integer roleId,
                                             @Param("powerId") Integer powerId, @Param("condition") PlanDetailFilter condition);

    public List<PlanDetailVO> listInspectionRecord(@Param("unitId")Integer unitId, @Param("roleId") Integer roleId,
                                                   @Param("powerId") Integer powerId, @Param("condition") PlanRecordFilter condition);

    public List<PlanDetailVO> listInspectionRecordByStructure(@Param("roleId") Integer roleId,
                                                              @Param("unitId") Integer unitId,
                                                              @Param("powerId") Integer powerId,
                                                              @Param("structureId") Integer structureId);

    public List<PlanDetailVO> listInspectionRecordByTime(@Param("inspectionTime") String inspectionTime,
                                                         @Param("unitId") Integer unitId,
                                                         @Param("roleId") Integer roleId,
                                                         @Param("powerId") Integer powerId);

    public List<MaintainItemVO> listMaintainRecord(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                                   @Param("powerId") Integer powerId, @Param("condition") PlanRecordFilter condition);

    public List<MaintainItemVO> listMaintainRecordByTime(@Param("maintainTime") String maintainTime,
                                                         @Param("unitId") Integer unitId,
                                                         @Param("roleId") Integer roleId,
                                                         @Param("powerId") Integer powerId);

    public MaintainItemVO selMaintainItem(@Param("maintainItemId") Integer maintainItemId);

    public List<MaintainItemVO> listMaintainRecordByIds(@Param("ids") List<Integer> ids);

    public List<PlanDetailVO> listInspectionRecordByIds(@Param("ids") List<Integer> ids);

    public List<InspectionDiseaseInstanceVO> getInspectionDiseaseByStructureId(@Param("structureId") Integer structureId,
                                                                               @Param("words")String words);

    public List<PlanDetail> selectPlanDetailOrderByTime(@Param("planId") Integer planId);

    /** 其他计划细项是否全部完成**/
    public Integer planDetailInPlanInfo(@Param("planDetailId") Integer planDetailId, @Param("planId") Integer planId);

    public PlanDetailVO selectLastInspection(@Param("planDetailId") Integer planDetailId);

    public List<DiseaseInstanceExpVo> selectDiseaseByDetailId(@Param("planDetailId")Integer planDetailId);

    /** 1全部完成 0部分完成 **/
    public Integer planAllComplete(@Param("planId") Integer planId, @Param("planDetailId") Integer planDetailId);

    public void resetPlanDetailStatus(@Param("nowDate") String nowDate);

    public void resetMaintainItemStatus(@Param("nowDate") String nowDate);

    public String selOtherRemark(@Param("planDetailId") Integer planDetailId);

}
