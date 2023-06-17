package com.iware.bridge.app.assess.vo.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="AppMonitorPlanFilterVo",description="app端检测计划过滤器")

public class AppMonitorPlanFilterVo extends AppMonitorPlanApiFilterVo{

	@ApiModelProperty(value = "用户单位id")
	private Integer unitId;

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	@Override
	public String getProjectName() {
		if(super.getProjectName()!=null) {
			return "%"+super.getProjectName()+"%";
		}else {
			return null;
		}
		
	}
	
}
