package com.iware.bridge.model.entity.evaluation;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="PropertyRel", description="属性关联表")
public class PropertyRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "目标id")
    private Integer targetId;

    @ApiModelProperty(value = "属性id")
    private Integer propertyId;

    @ApiModelProperty(value = "1：部位 2：桥梁构件关联 ")
    private Integer targetType;

    @ApiModelProperty(value = "1：桥面系 2：上部结构 3：下部结构")
    private Integer partType;

    @ApiModelProperty(value = "桥梁类型id")
    private Integer bridgeTypeId;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTargetId() {
		return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getPropertyId() {
		return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getTargetType() {
		return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getPartType() {
		return partType;
    }

    public void setPartType(Integer partType) {
        this.partType = partType;
    }

    public Integer getBridgeTypeId() {
		return bridgeTypeId;
    }

    public void setBridgeTypeId(Integer bridgeTypeId) {
        this.bridgeTypeId = bridgeTypeId;
    }


    @Override
    public String toString() {
        return "PropertyRel{" +
            "  id=" + id +
            ", targetId=" + targetId +
            ", propertyId=" + propertyId +
            ", targetType=" + targetType +
            ", partType=" + partType +
            ", bridgeTypeId=" + bridgeTypeId +
        "}";
    }
}
