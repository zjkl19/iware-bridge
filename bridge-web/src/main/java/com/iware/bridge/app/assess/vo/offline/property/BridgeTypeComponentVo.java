package com.iware.bridge.app.assess.vo.offline.property;

import com.iware.bridge.model.entity.evaluation.BridgeType;
import com.iware.bridge.model.entity.evaluation.BridgeTypeComponentRel;
import com.iware.bridge.model.entity.evaluation.Component;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value="BridgeTypeComponentVo",description="桥梁类型部件关联对象")
public class BridgeTypeComponentVo extends BridgeTypeComponentRel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="桥梁类型列表")
	private List<BridgeType> bridgeTypeList;
	@ApiModelProperty(value="桥梁部件列表")
	private List<Component> componentList;
	public List<BridgeType> getBridgeTypeList() {
		return bridgeTypeList;
	}
	public void setBridgeTypeList(List<BridgeType> bridgeTypeList) {
		this.bridgeTypeList = bridgeTypeList;
	}
	public List<Component> getComponentList() {
		return componentList;
	}
	public void setComponentList(List<Component> componentList) {
		this.componentList = componentList;
	}
	
	
	
	
}
