package com.iware.bridge.evaluation.dao;

import com.iware.bridge.evaluation.vo.AppointProjectVO;
import com.iware.bridge.evaluation.vo.MonitorPlanListVO;
import com.iware.bridge.evaluation.vo.PlanFilter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WJP
 * @date 2021-8-13
 */

@Repository
public interface MonitorPlanExpDao {

    /** 查询检测计划列表  **/
    public List<MonitorPlanListVO> selectPlanList(@Param("unitId")Integer unitId, @Param("roleId")Integer roleId,
                                           @Param("filter")PlanFilter filter, @Param("list") List<AppointProjectVO> list);

    /** 获取结构物时间根据结构物id */
    public List<String> selectStructureTimeByStructureId(@Param("id") Integer id, @Param("unitId") Integer unitId,
                                                  @Param("roleId") Integer roleId,@Param("project") Integer project);

    /** 查询有检测计划的项目数 **/
    public Integer getItemCount(@Param("list") List<AppointProjectVO> list);

    /** 查询有检测计划的桥隧数 **/
    public Integer getStructureCount(@Param("list") List<Integer> list);

    /** 根据线路id修改状态为已完成 **/
    void updateStateByRoadId(@Param("roadId") Long roadId);
}
