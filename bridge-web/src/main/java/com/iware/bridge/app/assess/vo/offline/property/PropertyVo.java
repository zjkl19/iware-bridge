package com.iware.bridge.app.assess.vo.offline.property;

import com.iware.bridge.model.entity.evaluation.Property;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value="PropertyVo",description="属性对象")
public class PropertyVo extends Property {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="属性选项")
	private List<PropertyOptionVo> propertyOptionList;

	public List<PropertyOptionVo> getPropertyOptionList() {
		return propertyOptionList;
	}

	public void setPropertyOptionList(List<PropertyOptionVo> propertyOptionList) {
		this.propertyOptionList = propertyOptionList;
	}
	
	
	
}
