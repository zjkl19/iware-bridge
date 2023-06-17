package com.iware.bridge.info.service;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.info.vo.UserVO;
import com.iware.bridge.model.entity.user.AppRole;
import com.iware.bridge.model.entity.user.User;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-06-22
 */

public interface UserService {

	/** 添加用户 */
	public void addUser(UserVO userVO);

	/** 编辑用户 */
	public void editUser(UserVO userVO);

	/** 用户禁用/启用 */
	public void updateStatus(User user);

	/** 删除用户 */
	public void delUser(Integer userId);

	/** 获取用户列表 */
	public PageInfo<UserVO> getUserList(Integer pageNum, Integer pageSize, InfoFilter filter);

	/** 修改密码 */
	public void update(User user);

	/** 根据角色id获取用户列表 */
	public List<User> getByRoleId(Integer roleId);

	/** 获取app角色列表 */
	public List<AppRole> getAppRoleList();

	/** 解除设备绑定 */
	public void removeBindDevice(Integer userId);
}
