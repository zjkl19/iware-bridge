package com.iware.bridge.info.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "StructureFilter",description = "信息管理请求参数")
public class StructureFilter implements Serializable {

    private static final long serialVersionUID = -1122996100970511032L;

    @ApiModelProperty(value = "所在省")
    private Integer provinceId;
    @ApiModelProperty(value = "所在市")
    private Integer cityId;
    @ApiModelProperty(value = "所在区(县)")
    private Integer countyId;
    @ApiModelProperty(value = "业主id")
    private Integer unitId;
    @ApiModelProperty(value = "项目id")
    private Integer projectId;
    @ApiModelProperty(value = "承接单位id")
    private Integer undertakeId;
    @ApiModelProperty(value = "当前页面所属顶级模块id")
    private Integer powerId;

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUndertakeId() {
        return undertakeId;
    }

    public void setUndertakeId(Integer undertakeId) {
        this.undertakeId = undertakeId;
    }

    public Integer getPowerId() {
        return powerId;
    }

    public void setPowerId(Integer powerId) {
        this.powerId = powerId;
    }
}
