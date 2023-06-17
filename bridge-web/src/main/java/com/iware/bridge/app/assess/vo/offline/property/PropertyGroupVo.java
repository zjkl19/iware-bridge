package com.iware.bridge.app.assess.vo.offline.property;

import com.iware.bridge.model.entity.evaluation.Property;
import com.iware.bridge.model.entity.evaluation.PropertyGroup;
import com.iware.bridge.model.entity.evaluation.PropertyOption;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value="PropertyGroupVo",description="属性组对象")
public class PropertyGroupVo extends PropertyGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="属性列表")
	private List<Property> propertyList;
	@ApiModelProperty(value="属性选项")
	private List<PropertyOption> propertyOptionList;

	public List<Property> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<Property> propertyList) {
		this.propertyList = propertyList;
	}

	public List<PropertyOption> getPropertyOptionList() {
		return propertyOptionList;
	}

	public void setPropertyOptionList(List<PropertyOption> propertyOptionList) {
		this.propertyOptionList = propertyOptionList;
	}
	
	
	
}
