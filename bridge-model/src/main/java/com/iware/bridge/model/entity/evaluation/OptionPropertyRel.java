package com.iware.bridge.model.entity.evaluation;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="OptionPropertyRel", description="选项子属性关联表")
public class OptionPropertyRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "属性选项关联id")
    private Integer propertyOptionRelId;

    @ApiModelProperty(value = "属性id")
    private Integer propertyId;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPropertyOptionRelId() {
		return propertyOptionRelId;
    }

    public void setPropertyOptionRelId(Integer propertyOptionRelId) {
        this.propertyOptionRelId = propertyOptionRelId;
    }

    public Integer getPropertyId() {
		return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }


    @Override
    public String toString() {
        return "OptionPropertyRel{" +
            "  id=" + id +
            ", propertyOptionRelId=" + propertyOptionRelId +
            ", propertyId=" + propertyId +
        "}";
    }
}
