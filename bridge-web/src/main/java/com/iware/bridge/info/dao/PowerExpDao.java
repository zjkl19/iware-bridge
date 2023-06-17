package com.iware.bridge.info.dao;

import com.iware.bridge.info.vo.PowerVO;
import com.iware.bridge.inspection.vo.ReceiveTime;
import com.iware.bridge.model.entity.user.Power;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowerExpDao {


    /** 查找所有权限 */
    public List<PowerVO> getPowerList(@Param("id") Integer id);

    /** 获取用户权限列表 */
    public List<Power> getPowerByUser(@Param("roleId") Integer roleId, @Param("userId") Integer userId);

    /** 获取单位权限列表 */
    public List<Power> getPowerByUnit(@Param("unitId") Integer unitId);

    /** 查询角色默认权限 */
    public List<Integer> getRoleDefaultPower(@Param("roleId") Integer roleId);

    /** 删除单位权限关联 */
    public void delPowerByUnitId(@Param("unitId") Integer unitId);

    /** 查询角色指派时长 **/
    public List<ReceiveTime> getAppointTime(@Param("unitId")  Integer unitId, @Param("projectId") Integer projectId, @Param("powerId") Integer powerId);

}
