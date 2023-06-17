package com.iware.bridge.app.assess.vo.offline.property;

import com.iware.bridge.model.entity.evaluation.Component;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="ComponentVo",description="部件Vo对象")
public class ComponentVo extends Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="原始权重")
	private Integer initialWeight;
	@ApiModelProperty(value="部位 1：桥面系 2：上部结构 3：下部结构")
	private Integer partType;
	@ApiModelProperty(value="0：桥墩 1：桥台")
	private Integer pierAbutment;
	public Integer getInitialWeight() {
		return initialWeight;
	}
	public void setInitialWeight(Integer initialWeight) {
		this.initialWeight = initialWeight;
	}
	public Integer getPartType() {
		return partType;
	}
	public void setPartType(Integer partType) {
		this.partType = partType;
	}
	public Integer getPierAbutment() {
		return pierAbutment;
	}
	public void setPierAbutment(Integer pierAbutment) {
		this.pierAbutment = pierAbutment;
	}
	
	
}
