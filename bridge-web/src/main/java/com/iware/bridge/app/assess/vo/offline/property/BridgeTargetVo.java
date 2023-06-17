package com.iware.bridge.app.assess.vo.offline.property;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value="BridgeTargetVo",description="目标组件对象")
public class BridgeTargetVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键id")
	private Integer id;
	@ApiModelProperty(value="部位类型 1：桥面系 2：上部结构 3：下部结构")
	private Integer partType;
	@ApiModelProperty(value="目标类型 1：部位 2：构件")
	private Integer targetType;
	@ApiModelProperty(value="目标id")
	private Integer targetId;
	@ApiModelProperty(value="属性列表")
	private List<PropertyVo> propertyList;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPartType() {
		return partType;
	}
	public void setPartType(Integer partType) {
		this.partType = partType;
	}
	public Integer getTargetType() {
		return targetType;
	}
	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
	public List<PropertyVo> getPropertyList() {
		return propertyList;
	}
	public void setPropertyList(List<PropertyVo> propertyList) {
		this.propertyList = propertyList;
	}
	
	
	
}
