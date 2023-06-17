package com.iware.bridge.app.assess.vo.offline.property;

import com.iware.bridge.model.entity.evaluation.BridgeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value="BridgeTypeVo",description="桥梁类型对象")
public class BridgeTypeVo extends BridgeType {
	@ApiModelProperty(value="组件目标列表")
	private List<BridgeTargetVo> targetList;
	@ApiModelProperty(value="部件列表")
	private List<ComponentVo> componentList;

	
	public List<ComponentVo> getComponentList() {
		return componentList;
	}

	public void setComponentList(List<ComponentVo> componentList) {
		this.componentList = componentList;
	}

	public List<BridgeTargetVo> getTargetList() {
		return targetList;
	}

	public void setTargetList(List<BridgeTargetVo> targetList) {
		this.targetList = targetList;
	}

	
	
}
