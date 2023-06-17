package com.iware.bridge.app.assess.vo.platUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="platUserVo",description="我们平台用户")
public class PlatUserVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="id")
	private Integer id;
	@ApiModelProperty(value="用户名")
	private String  username;
	@ApiModelProperty(value="真实姓名")
	private String realName;
	@ApiModelProperty(value="角色")
	private String role;
	@ApiModelProperty(value="单位名称")
	private String petname;
	@ApiModelProperty(value="创建者用户id")
	private Integer createUserId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPetname() {
		return petname;
	}
	public void setPetname(String petname) {
		this.petname = petname;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	
	
}
