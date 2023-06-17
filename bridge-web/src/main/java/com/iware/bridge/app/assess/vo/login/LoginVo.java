package com.iware.bridge.app.assess.vo.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="LoginVo",description="App登录")
public class LoginVo {
	@ApiModelProperty(value="用户名")
	private String username;
	@ApiModelProperty(value="密码")
	private String password;
	@ApiModelProperty(value="设备编码")
	private String deviceId;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	
	
}
