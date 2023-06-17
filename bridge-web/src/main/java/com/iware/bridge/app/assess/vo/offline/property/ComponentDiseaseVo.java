package com.iware.bridge.app.assess.vo.offline.property;

import com.iware.bridge.model.entity.evaluation.BridgeDisease;
import com.iware.bridge.model.entity.evaluation.BridgeType;
import com.iware.bridge.model.entity.evaluation.Component;
import com.iware.bridge.model.entity.evaluation.ComponentDiseaseRel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value="ComponentDiseaseVo",description="部件病害关联对象")
public class ComponentDiseaseVo extends ComponentDiseaseRel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="桥梁部件列表")
	private List<Component> componentList;
	@ApiModelProperty(value="桥梁类型列表")
	private List<BridgeType> bridgeTypeList;
	@ApiModelProperty(value="桥梁病害列表")
	private List<BridgeDisease> bridgeDiseaseList;
	@ApiModelProperty(value="属性组列表")
	private List<PropertyGroupVo> propertyGroupList;
	
	public List<Component> getComponentList() {
		return componentList;
	}
	public void setComponentList(List<Component> componentList) {
		this.componentList = componentList;
	}
	public List<BridgeType> getBridgeTypeList() {
		return bridgeTypeList;
	}
	public void setBridgeTypeList(List<BridgeType> bridgeTypeList) {
		this.bridgeTypeList = bridgeTypeList;
	}
	public List<BridgeDisease> getBridgeDiseaseList() {
		return bridgeDiseaseList;
	}
	public void setBridgeDiseaseList(List<BridgeDisease> bridgeDiseaseList) {
		this.bridgeDiseaseList = bridgeDiseaseList;
	}
	public List<PropertyGroupVo> getPropertyGroupList() {
		return propertyGroupList;
	}
	public void setPropertyGroupList(List<PropertyGroupVo> propertyGroupList) {
		this.propertyGroupList = propertyGroupList;
	}
	
	
	
	
}
