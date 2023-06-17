package com.iware.bridge.info.dao;

import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.info.vo.UserVO;
import com.iware.bridge.model.entity.user.Role;
import com.iware.bridge.model.entity.user.Unit;
import com.iware.bridge.model.entity.user.User;
import com.iware.bridge.online.vo.ProjectUserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-23
 */

@Repository
public interface UserExpDao {

    /** 获取用户角色信息 */
    public Role getRoleByUserId(@Param("userId") Integer userId);

    /** 获取指定角色用户 */
    public List<User> getUserListByRole(@Param("roleId") Integer roleId);

    /** 查看用户名是否存在 */
    public User existUser(@Param("username") String username,@Param("userId") Integer userId);

    /** 删除用户角色关联 */
    public void deleteRoleRelByUserId(@Param("userId")Integer userId);

    /** 获取用户列表 */
    public List<UserVO> getUserList(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                    @Param("userId") Integer userId, @Param("filter") InfoFilter filter);

    /** 获取业主搜索栏 */
    public List<Unit> listOwnerByPower(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                       @Param("type") Integer type, @Param("powerId") Integer powerId);

    /** 获取承接单位主用户搜索栏 */
    public List<Unit> listMainByPower(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                      @Param("type") Integer type, @Param("powerId") Integer powerId);

    /** 删除用户app权限 **/
    public void delAppRoleRelByUserId(@Param("userId") Integer userId);

    /** 用户解除设备绑定 */
    public void updateDeviceIdNull(@Param("userId") Integer userId);

    /** 获取结构物下的承接单位列表 */
    public ProjectUserVO getUserListByStructure(@Param("structureId") Integer structureId,
                                                @Param("powerId") Integer powerId);

    /** 修改用户信息 **/
    public void update(User user);
}
