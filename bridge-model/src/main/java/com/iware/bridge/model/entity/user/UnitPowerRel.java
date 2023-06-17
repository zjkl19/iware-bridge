package com.iware.bridge.model.entity.user;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="UserPowerRel", description="用户权限关联")
public class UnitPowerRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "单位id")
    private Integer unitId;

    @ApiModelProperty(value = "权限id")
    private Integer powerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getPowerId() {
        return powerId;
    }

    public void setPowerId(Integer powerId) {
        this.powerId = powerId;
    }


    @Override
    public String toString() {
        return "UserPowerRel{" +
                "  id=" + id +
                ", unitId=" + unitId +
                ", powerId=" + powerId +
                "}";
    }
}
