package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "WarningRecordFilter",description = "预警信息条件")
public class WarningRecordFilter implements Serializable {

    private static final long serialVersionUID = -1177958100970511032L;

    @ApiModelProperty(value="单位id")
    private Integer unitId;
    @ApiModelProperty(value="用户角色")
    private Integer roleId;
    @ApiModelProperty(value = "权限id")
    private Integer powerId;
    @ApiModelProperty(value="结构物id")
    private Integer structureId;
    @ApiModelProperty(value="测点编号")
    private String sensorName;
    @ApiModelProperty(value="预警等级")
    private Integer level;
    @ApiModelProperty(value="开始时间")
    private Date startTime;
    @ApiModelProperty(value="结束时间")
    private Date endTime;
    @ApiModelProperty(value="状态")
    private Integer status;

    public Integer getPowerId() {
        return powerId;
    }

    public void setPowerId(Integer powerId) {
        this.powerId = powerId;
    }

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

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
