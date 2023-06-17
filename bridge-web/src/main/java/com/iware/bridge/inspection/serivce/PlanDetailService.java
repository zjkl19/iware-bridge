package com.iware.bridge.inspection.serivce;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.inspection.vo.*;
import com.iware.bridge.model.entity.inspection.MaintainItem;
import com.iware.bridge.model.entity.inspection.PlanDetail;

import java.util.List;

/**
 * @author LBX
 * @date 2021-7-29
 */

public interface PlanDetailService {

    /** 获取巡查计划详情列表 */
    public PageInfo<PlanDetailVO> listPlanDetail(Integer pageNum, Integer pageSize, PlanDetailFilter filter, Boolean isApp);

    /** 添加巡查计划细项 */
    public void addPlanDetail(PlanDetail detail);

    /** 删除巡查计划细项 */
    public void delPlanDetail(Integer planDetailId);

    public void updPlanDetail(PlanDetailVO planDetailVO);

    public PlanDetailVO selPlanDetail(Integer planDetailId);

    /** 获取维养计划详情列表 */
    public PageInfo<MaintainItemVO> listMaintainItem(Integer pageNum, Integer pageSize, PlanDetailFilter filter, Boolean isApp);

    /** 获取巡查记录列表 */
    public List<PlanDetailVO> listInspectionRecord(PlanRecordFilter filter);

    /** 批量导出 */
    public String batchExport(Integer type, List<Integer> ids, PlanRecordFilter filter, Integer exportAll);

    /** 获取维养记录列表 */
    public PageInfo<MaintainItemVO> listMaintainRecord(Integer pageNum, Integer pageSize, PlanRecordFilter filter);

    public MaintainItemVO selMaintainItem(Integer maintainItemId);

    /** 下载维修项简报 */
    public String downloadMaintainItem(Integer maintainItemId);

    /** 下载巡查简报 */
    public String downloadInspectionReport(Integer planDetailId);

    public String downloadReportAsWord(Integer planId, Boolean isInspection);

    public List<InspectionDiseaseInstanceVO> getInspectionDiseaseByStructureId(Integer structureId, String words);

    public String downloadMaintainPlan(Integer planId);

    public List<MaintainItemVO> listMaintainRecordByTime(String date);

    public List<PlanDetailVO> listInspectionRecordByTime(String date);

    public void deleteMaintainItemById(Integer planDetailId);

    public void addMaintainItem(MaintainItem maintainItem);

    /** 导出项目记录-excel */
    public String batchExcelExport(KeywordVo keywordVo);
}
