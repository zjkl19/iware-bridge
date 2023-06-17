package com.iware.bridge.app.assess.vo.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(value="AppMonitorPlanApiFilterVo",description="app端检测计划Api过滤器")
public class AppMonitorPlanApiFilterVo {
	@ApiModelProperty(value="时间段")
	private List<Date> betweenTims;
	@ApiModelProperty(value="项目名称")
	private String projectName;
	@ApiModelProperty(value="业主id")
	private Integer ownerId;
	public List<Date> getBetweenTims() {
		return betweenTims;
	}
	public void setBetweenTims(List<Date> betweenTims) {
		this.betweenTims = betweenTims;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	
	
}
