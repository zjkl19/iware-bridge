package com.iware.bridge.inspection.dao;

import com.iware.bridge.inspection.vo.PlanFilter;
import com.iware.bridge.inspection.vo.PlanStructureVO;
import com.iware.bridge.inspection.vo.PlanVO;
import com.iware.bridge.model.entity.inspection.PlanInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author LBX
 * @date 2021-7-29
 */

@Repository
public interface PlanExpDao {
    public List<PlanInfo> selectPlan(@Param("condition") PlanFilter condition);

    /** 获取计划列表 */
    public List<PlanVO> listPlan(@Param("roleId")Integer roleId,
                                   @Param("unitId")Integer unitId,
                                   @Param("powerId") Integer powerId,
                                   @Param("filter") PlanFilter filter);

    public List<PlanStructureVO> listPlanStructure(@Param("projectId") Integer projectId,
                                                   @Param("year") String year,
                                                   @Param("unitId") Integer unitId,
                                                   @Param("roleId") Integer roleId,
                                                   @Param("powerId") Integer powerId);

    public Date selPlanLastDate(@Param("structureId") Integer structureId, @Param("lastMonth") String lastMonth);

    public void resetPlanInfoStatus();

}
