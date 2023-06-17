package com.iware.bridge.app.assess.vo.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value="AppMontitorPlanDetailVo",description="检测计划详情实例")
public class AppMontitorPlanDetailVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="监测计划id")
	private Integer id;
	@ApiModelProperty(value="项目id")
	private Integer projectId;
	@ApiModelProperty(value="项目名称")
	private String projectName;
	@ApiModelProperty(value="检测计划创建者id")
	private Integer createUserId;
	@ApiModelProperty(value="项目负责人id")
	private Integer pUserId;
	@ApiModelProperty(value="项目负责人名称")
	private String pUserName;
	@ApiModelProperty(value="项目上一级成员id")
	private Integer parentId;
	@ApiModelProperty(value="项目创建者id")
	private Integer pCreateUserId;
	@ApiModelProperty(value="开始时间")
	private Date startTime;
	@ApiModelProperty(value="结束时间")
	private Date endTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getpUserId() {
		return pUserId;
	}
	public void setpUserId(Integer pUserId) {
		this.pUserId = pUserId;
	}
	public String getpUserName() {
		return pUserName;
	}
	public void setpUserName(String pUserName) {
		this.pUserName = pUserName;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getpCreateUserId() {
		return pCreateUserId;
	}
	public void setpCreateUserId(Integer pCreateUserId) {
		this.pCreateUserId = pCreateUserId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "AppMontitorPlanDetailVo{" +
				"id=" + id +
				", projectId=" + projectId +
				", projectName='" + projectName + '\'' +
				", createUserId=" + createUserId +
				", pUserId=" + pUserId +
				", pUserName='" + pUserName + '\'' +
				", parentId=" + parentId +
				", pCreateUserId=" + pCreateUserId +
				", startTime=" + startTime +
				", endTime=" + endTime +
				'}';
	}
}
