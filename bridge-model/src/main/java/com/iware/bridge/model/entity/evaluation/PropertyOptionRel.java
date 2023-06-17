package com.iware.bridge.model.entity.evaluation;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="PropertyOptionRel", description="属性选项关联表")
public class PropertyOptionRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "属性选项id")
    private Integer propertyOptionsId;

    @ApiModelProperty(value = "属性关联表id")
    private Integer propertyRelId;

    @ApiModelProperty(value = "属性表id")
    private Integer propertyId;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPropertyOptionsId() {
		return propertyOptionsId;
    }

    public void setPropertyOptionsId(Integer propertyOptionsId) {
        this.propertyOptionsId = propertyOptionsId;
    }

    public Integer getPropertyRelId() {
		return propertyRelId;
    }

    public void setPropertyRelId(Integer propertyRelId) {
        this.propertyRelId = propertyRelId;
    }

    public Integer getPropertyId() {
		return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }


    @Override
    public String toString() {
        return "PropertyOptionRel{" +
            "  id=" + id +
            ", propertyOptionsId=" + propertyOptionsId +
            ", propertyRelId=" + propertyRelId +
            ", propertyId=" + propertyId +
        "}";
    }
}
