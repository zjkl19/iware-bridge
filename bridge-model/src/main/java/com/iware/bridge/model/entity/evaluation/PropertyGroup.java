package com.iware.bridge.model.entity.evaluation;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="PropertyGroup", description="属性组表")
public class PropertyGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "属性id")
    private Integer propertyId;

    @ApiModelProperty(value = "组别")
    private Integer groups;

    @ApiModelProperty(value = "扣分依据")
    private Integer accord;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getGroups() {
        return groups;
    }

    public void setGroups(Integer groups) {
        this.groups = groups;
    }

    public Integer getAccord() {
        return accord;
    }

    public void setAccord(Integer accord) {
        this.accord = accord;
    }


    @Override
    public String toString() {
        return "PropertyGroup{" +
                "  id=" + id +
                ", propertyId=" + propertyId +
                ", groups=" + groups +
                ", accord=" + accord +
                "}";
    }
}