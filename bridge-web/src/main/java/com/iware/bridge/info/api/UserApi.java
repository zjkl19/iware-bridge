package com.iware.bridge.info.api;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.service.UserService;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.info.vo.UserVO;
import com.iware.bridge.model.entity.user.AppRole;
import com.iware.bridge.model.entity.user.User;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.common.annotations.CheckRepeat;
import com.iware.common.base.AbstractBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-06-22
 */

@RestController
@Api(value = "用户接口", tags = "用户管理接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/user",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserApi implements AbstractBaseController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "")
	@CheckRepeat
	@Permission(actionType = ActionTypeEnum.ACTION_ADD)
	@ApiOperation(value = "新增用户", notes = "新增用户接口", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userVO", value = "新增的用户信息", dataType = "UserVO", required = true, paramType = "body")
	})
	public void addUser(@RequestBody UserVO userVO) {
		userService.addUser(userVO);
	}

	@PutMapping(value = "/{id}")
	@Permission(actionType = ActionTypeEnum.ACTION_UPD)
	@ApiOperation(value = "修改用户", notes = "修改用户接口", httpMethod = "PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userVO", value = "修改的用户信息", dataType = "UserVO", required = true, paramType = "body")
	})
	public void updateUser(@PathVariable("id") Integer id, @RequestBody UserVO userVO) {
		userVO.setId(id);
		userService.editUser(userVO);
	}

	@PutMapping(value = "/updStatus/{id}/{status}")
	@Permission(actionType = ActionTypeEnum.ACTION_UPD)
	@ApiOperation(value = "修改用户状态", notes = "修改用户状态", httpMethod = "PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userVO", value = "修改的用户信息", dataType = "UserVO", required = true, paramType = "body")
	})
	public void updateStatus(@PathVariable("id") Integer id, @PathVariable("status") Integer status) {
		User user = new User();
		user.setId(id);
		user.setStatus(status);
		userService.updateStatus(user);
	}

	@DeleteMapping(value = "/{userId}")
	@Permission(actionType = ActionTypeEnum.ACTION_DEL)
	@ApiOperation(value = "删除用户", notes = "删除用户", httpMethod = "DELETE")
	@ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer", required = true, paramType = "path")
	public void delUser(@PathVariable("userId") Integer userId) {
		userService.delUser(userId);
	}

	@PostMapping(value = "/list")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(value = "查询用户", notes = "查询用户接口", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", example = "1", defaultValue = "1", required = false),
			@ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", example = "10", defaultValue = "10", required = false),
			@ApiImplicitParam(name = "infoFilter", value = "过滤条件", required = true, paramType = "body", dataType = "InfoFilter")
	})
	public PageInfo<UserVO> getUserList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
									  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
									  @RequestBody InfoFilter filter) {
		return userService.getUserList(pageNum, pageSize, filter);
	}

	@PutMapping(value = "/changePwd/{userId}")
	@Permission(actionType = ActionTypeEnum.ACTION_UPD)
	@ApiOperation(value = "修改密码", notes = "修改密码", httpMethod = "PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer", required = true, paramType = "path"),
			@ApiImplicitParam(name = "user", value = "用户信息", dataType = "User", required = true, paramType = "body")
	})
	public void changePwd(@PathVariable("userId") Integer userId, @RequestBody User user) {
		user.setId(userId);
		userService.update(user);
	}

	@GetMapping(value = "/getByRoleId/{roleId}")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(notes="根据角色获取用户列表",value="根据角色获取用户列表",httpMethod="GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "roleId", value = "角色Id", dataType = "Integer", required = true, paramType = "path"),
	})
	public List<User> getUserByRoleId(@PathVariable("roleId") Integer roleId) {
		return userService.getByRoleId(roleId);
	}

	@GetMapping(value = "/appRole/list")
	@Permission(actionType = ActionTypeEnum.ACTION_QRY)
	@ApiOperation(value = "获取App角色列表 ", notes = "获取App角色列表 ", httpMethod = "GET")
	@ApiImplicitParams({
	})
	public List<AppRole> getAppRoles() {
		return userService.getAppRoleList();
	}

	@PutMapping(value = "/removeBindDevice/{id}")
	@Permission(actionType = ActionTypeEnum.ACTION_UPD)
	@ApiOperation(value = "用户设备绑定解除", notes = "用户设备绑定解除", httpMethod = "PUT")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户id", dataType = "int", paramType = "path")
	})
	public void removeBindDevice(@PathVariable("id") Integer id) {

		userService.removeBindDevice(id);
	}

}
