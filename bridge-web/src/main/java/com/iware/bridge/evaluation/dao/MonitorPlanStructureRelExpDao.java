package com.iware.bridge.evaluation.dao;

import com.iware.bridge.evaluation.vo.*;
import com.iware.bridge.model.entity.evaluation.MonitorPlanStructureRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WJP
 * @date 2021-8-16
 */

@Repository
public interface MonitorPlanStructureRelExpDao {
    /** 获取最新的一次检测记录 **/
    public EvaluationAnalysisVO selectNewProject(MonitorAnalysisFilterVO monitorAnalysisFilterVO);

    /** 获取检测次数 **/
    public Integer selectDetectionNumber(MonitorAnalysisFilterVO monitorAnalysisFilterVO);

    /** 查询检测列表 **/
    public List<AssessRecordVO> getRecordList(AssessRecordFilter assessRecordFilter);

    /** 根据结构物id查询最新的计划结构物id **/
    public Integer getMpSrelIdByBid(@Param("bid")Integer bid);

    /** 查看记录是否已经被删除   返回值>0即存在 **/
    public Integer existMonitorStructure(@Param("id") Integer id);

    /** 逻辑删除结构物计划 **/
    public void deleteMonitorStructure(@Param("id") Integer id);

    /** 根据计划id删除结构物计划 **/
    public void deleteStructure(@Param("id") Integer id);

    /** 查询信息（不查询路径） **/
    public List<MonitorPlanStructureRel> selectNoUrl(@Param("planId") Integer planId);

    /** 桥梁详情页获取检测记录 */
    public List<MonitorRecord> getMonitorRecordList(@Param("roleId") Integer roleId,
                                                    @Param("unitId") Integer unitId,
                                                    @Param("powerId") Integer powerId,
                                                    @Param("structureId") Integer structureId);
}
