package com.iware.bridge.app.assess.vo.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("AppMonitorPlanVo")
public class MonitorPlanVo {
	@ApiModelProperty(value="项目计划id")
	private Integer id;
	@ApiModelProperty(value="项目计划名称")
	private String projectName;
	@ApiModelProperty(value="业主单位")
	private String ownerUnit;
	@ApiModelProperty(value="计划开始时间")
	private Date startTime;
	@ApiModelProperty(value = "项目id")
	private Integer projectId;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getOwnerUnit() {
		return ownerUnit;
	}
	public void setOwnerUnit(String ownerUnit) {
		this.ownerUnit = ownerUnit;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
}
