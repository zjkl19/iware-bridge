package com.iware.bridge.model.entity.evaluation;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="BridgeTypeComponentRel", description="桥梁类型构件关联表")
public class BridgeTypeComponentRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "桥梁类型id")
    private Integer bridgeTypeId;

    @ApiModelProperty(value = "构件id")
    private Integer componentId;

    @ApiModelProperty(value = "1：桥面系 2：上部结构 3：下部结构")
    private Integer partType;

    @ApiModelProperty(value = "0：桥墩  1：桥台")
    private Integer pierAbutment;

    @ApiModelProperty(value = "原始权重")
    private Double initialWeight;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBridgeTypeId() {
		return bridgeTypeId;
    }

    public void setBridgeTypeId(Integer bridgeTypeId) {
        this.bridgeTypeId = bridgeTypeId;
    }

    public Integer getComponentId() {
		return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
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

    public Double getInitialWeight() {
		return initialWeight;
    }

    public void setInitialWeight(Double initialWeight) {
        this.initialWeight = initialWeight;
    }


    @Override
    public String toString() {
        return "BridgeTypeComponentRel{" +
            "  id=" + id +
            ", bridgeTypeId=" + bridgeTypeId +
            ", componentId=" + componentId +
            ", partType=" + partType +
            ", pierAbutment=" + pierAbutment +
            ", initialWeight=" + initialWeight +
        "}";
    }
}
