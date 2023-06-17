package com.iware.bridge.evaluation.service;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.evaluation.vo.MonitorPlanListVO;
import com.iware.bridge.evaluation.vo.MonitorPlanVO;
import com.iware.bridge.evaluation.vo.MonitorStructureRelVO;
import com.iware.bridge.evaluation.vo.PlanFilter;
import com.iware.bridge.inspection.vo.ReceiveTime;
import com.iware.bridge.model.entity.evaluation.MonitorPlanStructureRel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author WJP
 * @date 2021-8-16
 */

public interface EvaluationPlanService {

    /** 获取检测计划列表 */
    public PageInfo<MonitorPlanListVO> listPlan(Integer pageNum, Integer pageSize, PlanFilter filter);

    /** 获取检测计划关联结构物 */
    public List<MonitorPlanStructureRel> getStructureRel(Integer planId);

    /** 添加计划 */
    public void addMonitorPlan(MonitorPlanVO planVO, MultipartFile[] files);

    /** 修改计划 */
    public void updMonitorPlan(MonitorPlanVO planVO, MultipartFile[] files);

    /** 刪除计划 */
    public void delMonitorPlan(Integer planId);

    /** 上传方案 */
    public MonitorPlanStructureRel uploadFiles(MultipartFile file);

    /** 添加计划里的结构物 */
    public void addStructureRel(MonitorStructureRelVO monitorStructureRelVO, MultipartFile file);

    /** 修改计划里的结构物 */
    public void updStructureRel(MonitorStructureRelVO monitorStructureRelVO,MultipartFile file);

    /** 刪除计划里的结构物 */
    public void delStructureRel(Integer structureRelId);

    /** 查询检测计划下载地址 **/
    public MonitorPlanStructureRel selectFilePath(Integer structureRelId);

    /** 获取检测计划的项目指派时间 **/
    public List<ReceiveTime> getAppointTime(Integer projectId);

    /** 每天12点判断是否超时 **/
    public void planTimeout();
}
