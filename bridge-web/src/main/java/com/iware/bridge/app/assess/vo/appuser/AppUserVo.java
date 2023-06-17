package com.iware.bridge.app.assess.vo.appuser;

import com.iware.bridge.model.entity.user.AppRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value="AppUserVo",description="app用户实例")
public class AppUserVo implements Serializable{
	private static final long serialVersionUID = 2153095425411779724L;
	@ApiModelProperty(value="id")
	private Integer id;
	@ApiModelProperty(value="用户名")
	private String username;
	@ApiModelProperty(value="姓名")
	private String realName;
	@ApiModelProperty(value="用户角色")
	private List<AppRole> appRoles;
	@ApiModelProperty(value="所属单位")
	private String createUserName;
	@ApiModelProperty(value="用户所属承接单位")
	private String undertakeUnit;
	@ApiModelProperty(value="创建时间")
	private Date createTime;

	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUndertakeUnit() {
		return undertakeUnit;
	}

	public void setUndertakeUnit(String undertakeUnit) {
		this.undertakeUnit = undertakeUnit;
	}

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


	public List<AppRole> getAppRoles() {
		return appRoles;
	}
	public void setAppRoles(List<AppRole> appRoles) {
		this.appRoles = appRoles;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "AppUserVo:{"
				+ "id:"+id+","
				+ "username:'"+username+"',"
				+ "realName:'"+realName+"',"
				+ "createUserName:'"+createUserName+"',"
				+ "createTime:'"+createTime+"',"
				+ "}";

	}
}
