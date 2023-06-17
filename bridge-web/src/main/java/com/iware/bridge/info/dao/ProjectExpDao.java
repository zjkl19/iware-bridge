package com.iware.bridge.info.dao;

import com.iware.bridge.evaluation.vo.AppointProjectVO;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.info.vo.ProjectVO;
import com.iware.bridge.model.entity.global.Project;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.user.Power;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectExpDao {


    /** 获取项目列表(包含分页，模糊查询)  */
    public List<ProjectVO> listProjectByPage(@Param("roleId") Integer roleId,
                                             @Param("unitId") Integer unitId,
                                             @Param("filter") InfoFilter filter);

    /** 获取单位结构物 */
    public List<Structure> listStructureByUnit(@Param("unitId")Integer unitId);

    /** 根据名称查询项目 */
    public List<Project> getProjectByName(@Param("id") Integer id,
                                          @Param("name") String name);

    /** 根据项目id删除结构物关联 */
    public void delProjectStructureRel(@Param("projectId") Integer projectId);

    /** 修改项目 */
    public void update(Project project);

    /** 获取业务 */
    public List<Power> getBusinessByUnit(@Param("roleId") Integer roleId,
                                         @Param("unitId") Integer unitId,
                                         @Param("projectId") Integer projectId);

    /** 根据用户id,角色id获取项目id列表 */
    public List<Integer> getUnitProjectIds(@Param("roleId") Integer roleId,
                                           @Param("unitId") Integer unitId);

    /** 根据结构物id查询关联项目 */
    public List<Project> getProjectByStructureId(@Param("structureId")Integer structureId);

    /** 获取承接单位用户某个模块的项目*/
    public List<Project> getProjectByPower(@Param("unitId") Integer unitId,
                                           @Param("powerId") Integer powerId,
                                           @Param("onTime") Integer onTime);
    /** 获取承接单位下项目的信息 **/
    public List<AppointProjectVO> selectProjectByUserId(@Param("unitId") Integer unitId, @Param("powerId") Integer powerId);

    /** 获取超级管理员/业主的项目信息 **/
    public List<AppointProjectVO> selectProjectAll(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId);

}
