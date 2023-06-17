package com.iware.bridge.info.dao;

import com.iware.bridge.info.vo.ProjectAppointVO;
import com.iware.bridge.model.entity.global.ProjectAppoint;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectAppointExpDao {

    /** 查询指派信息 */
    public List<ProjectAppointVO> getAppointList(@Param("roleId")Integer roleId, @Param("unitId")Integer unitId,
                                                 @Param("projectId") Integer projectId);

    /** 判断业务是否已在该时间内指派 */
    public Integer getDupTimeCount(ProjectAppointVO appointVO);

    /** 判断在该时间内指派的项目中桥梁在其他项目中是否指派相同业务 */
    public Integer getDumpStructureInTimeCount(ProjectAppointVO appointVO);

    /** 获取承接单位对业务最后一次指派 */
    public List<ProjectAppointVO> getMainLastAppoint(@Param("unitId") Integer unitId, @Param("projectId") Integer projectId,
                                                     @Param("list") List<Integer> business);

    /** 承接单位主用户查询未被指派过的记录 */
    public List<ProjectAppointVO> getUnAppointByBusiness(@Param("unitId") Integer unitId, @Param("projectId") Integer projectId,
                                                         @Param("list") List<Integer> business);

    /** 承接单位主用户查看选择时间是否在自身受指派时间 */
    public List<ProjectAppoint> getInLimitTime(ProjectAppointVO appointVO);

    /** 业主删除指派，主用户同步删除该指派的指派 */
    public void delMainUserAppoint(ProjectAppoint projectAppoint);

    /** 业主删除指派，主用户同步删除该指派的指派业务关联 */
    public void delMainUserAppointRel(ProjectAppoint projectAppoint);

    /** 删除指派记录业务关联 */
    public Integer deletePowerRel(@Param("appointId") Integer appointId);

    /** 获取单位被指派记录 */
    public List<ProjectAppoint> getBeAppointedByUnit(@Param("unitId") Integer unitId);
}
