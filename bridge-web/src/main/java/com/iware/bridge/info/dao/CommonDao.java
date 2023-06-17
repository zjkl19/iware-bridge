package com.iware.bridge.info.dao;

import com.iware.bridge.info.vo.AppointTime;
import com.iware.bridge.info.vo.AreaVO;
import com.iware.bridge.info.vo.StructureFilter;
import com.iware.bridge.model.entity.global.Project;
import com.iware.bridge.model.entity.global.Structure;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonDao {

    /** 获取省市区 */
    public List<AreaVO> getAreaList(@Param("id") Integer id);

    public List<AreaVO> listAreaByStructure(@Param("ids") String ids);

    /** 获取用户项目 */
    public List<Project> listUserProject(@Param("roleId")Integer roleId,@Param("unitId")Integer unitId,
                                         @Param("type") Integer type, @Param("powerId") Integer powerId);

    /** 获取用户巡查排序后项目 */
    public List<Project> listUserInspectionProject(@Param("roleId")Integer roleId,@Param("unitId")Integer unitId,
                                                   @Param("powerId") Integer powerId);


    /** 获取用户结构物 */
    public List<Structure> listUnitStructure(@Param("roleId")Integer roleId,
                                             @Param("unitId")Integer unitId,
                                             @Param("filter")StructureFilter filter);

    /** 获取用户有传感器结构物 */
    public List<Structure> listStructureByOnline(@Param("roleId")Integer roleId,
                                      @Param("unitId")Integer unitId,
                                      @Param("filter")StructureFilter filter);

    /** 获取用户有巡查/维养计划结构物 */
    public List<Structure> listStructureByInspMain(@Param("roleId")Integer roleId,
                                                   @Param("unitId")Integer unitId,
                                                   @Param("type")Integer type,
                                                   @Param("filter")StructureFilter filter);

    /** 获取用户有检测计划结构物 */
    public List<Structure> listStructureByEvaluation(@Param("roleId")Integer roleId,
                                                     @Param("unitId")Integer unitId,
                                                     @Param("filter")StructureFilter filter);

    /**获取承接单位项目指派时间 */
    public List<AppointTime> getUnitAppointTime(@Param("unitId") Integer unitId,
                                         @Param("projectId") Integer projectId,
                                         @Param("powerId") Integer powerId);
}
