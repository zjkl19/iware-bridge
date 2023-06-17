package com.iware.bridge.info.vo;

import com.iware.bridge.model.entity.user.Unit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "UnitVO",description = "单位信息")
public class UnitVO extends Unit implements Serializable {

    private static final long serialVersionUID = -1164996121970511032L;

    @ApiModelProperty(value="单位类型")
    private String unitType;
    @ApiModelProperty(value="创建者")
    private String creator;
    @ApiModelProperty(value = "所勾选的单位权限ID集")
    private List<Integer> powerIds;

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<Integer> getPowerIds() {
        return powerIds;
    }

    public void setPowerIds(List<Integer> powerIds) {
        this.powerIds = powerIds;
    }
}
