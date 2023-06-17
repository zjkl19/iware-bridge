package com.iware.bridge.home.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "HomeFilter",description = "首页过滤")
public class HomeFilter implements Serializable {

    private static final long serialVersionUID = -1177958100970511032L;

    @ApiModelProperty(value="单位id")
    private Integer unitId;
    @ApiModelProperty(value="用户角色")
    private Integer roleId;
    @ApiModelProperty(value="结构物id")
    private Integer structureId;
    @ApiModelProperty(value="开始时间")
    private Date startTime;
    @ApiModelProperty(value="结束时间")
    private Date endTime;

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
