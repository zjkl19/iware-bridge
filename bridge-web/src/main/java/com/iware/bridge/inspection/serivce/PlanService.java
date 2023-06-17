package com.iware.bridge.inspection.serivce;

import com.iware.bridge.inspection.vo.PlanAmountMap;
import com.iware.bridge.inspection.vo.PlanFilter;
import com.iware.bridge.inspection.vo.PlanStructureVO;
import com.iware.bridge.inspection.vo.PlanVO;

import java.util.Date;
import java.util.List;

/**
 * @author LBX
 * @date 2021-7-29
 */

public interface PlanService {

    /** 计划列表 */
    public List<PlanVO> listPlan(Integer pageNum, Integer pageSize, PlanFilter filter);

    /** 添加巡查计划 */
    public String addInspectionPlan(PlanVO planVO);

    /** 修改巡查计划 */
    public String updInspectionPlan(PlanVO planVO);

    /** 删除巡查计划 */
    public void delInspectionPlan(Integer planId);

    /** 添加维养计划 */
    public void addMaintainPlan(PlanVO planVO);

    /** 修改维养计划 */
    public void updMaintainPlan(PlanVO planVO);

    /** 删除维养计划 */
    public void delMaintainPlan(Integer planId);

    /** 获取巡查计划列表 */
    public List<PlanStructureVO> listPlanStructure(Integer projectId, Integer year);

    public Date selPlanFirstDate(Integer structureId,Integer projectId, Date month);

    /** 判断计划是否超期*/
    public void  resetPlanStatus();

    public void resetPlanDetailStatus();
    public void resetMaintainItemStatus();
    public void resetPlanInfoStatus();

    /** 修改计划金额 */
    public void updatePlanAmount(PlanAmountMap map);
}

