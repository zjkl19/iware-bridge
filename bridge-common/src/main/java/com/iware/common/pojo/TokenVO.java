package com.iware.common.pojo;

import com.iware.bridge.model.entity.user.Power;
import com.iware.bridge.model.entity.user.Role;
import com.iware.bridge.model.entity.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "TokenVO", description = "用户TOKEN信息")
public class TokenVO implements Serializable{

	private static final long serialVersionUID = -30297418139905434L;
	@ApiModelProperty(value="用户标示")
	private String code;
	@ApiModelProperty(value="token信息")
	private String token;
	@ApiModelProperty(value = "用户信息")
	private User userInfo;
	@ApiModelProperty(value = "角色信息")
	private Role roleInfo;
	@ApiModelProperty(value="用户权限列表")
	private List<Power> powerList;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public User getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}

	public Role getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(Role roleInfo) {
		this.roleInfo = roleInfo;
	}

	public List<Power> getPowerList() {
		return powerList;
	}

	public void setPowerList(List<Power> powerList) {
		this.powerList = powerList;
	}
}
