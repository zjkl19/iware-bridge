package com.iware.bridge.model.entity.evaluation;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="BridgeWeight", description="桥梁部位权重表")
public class BridgeWeight implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
    private Integer id;

    @ApiModelProperty(value = "桥梁类型id")
    private Integer bridgeTypeId;

    @ApiModelProperty(value = "1：桥面系 2：上部结构 3：下部结构")
    private Integer partType;

    @ApiModelProperty(value = "权重")
    private Double weight;

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

    public Integer getPartType() {
		return partType;
    }

    public void setPartType(Integer partType) {
        this.partType = partType;
    }

    public Double getWeight() {
		return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
        return "BridgeWeight{" +
            "  id=" + id +
            ", bridgeTypeId=" + bridgeTypeId +
            ", partType=" + partType +
            ", weight=" + weight +
        "}";
    }
}
