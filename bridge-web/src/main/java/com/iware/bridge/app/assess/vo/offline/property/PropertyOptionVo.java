package com.iware.bridge.app.assess.vo.offline.property;

import com.iware.bridge.model.entity.evaluation.Property;
import com.iware.bridge.model.entity.evaluation.PropertyOption;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value="PropertyOptionVo",description="属性选项对象")
public class PropertyOptionVo extends PropertyOption {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="顺序")
	private Integer seqNum;
	@ApiModelProperty(value="选项子属性")
	private List<Property> propertyList;
	
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	public List<Property> getPropertyList() {
		return propertyList;
	}
	public void setPropertyList(List<Property> propertyList) {
		this.propertyList = propertyList;
	}
	
	
	
	
	
	
}
