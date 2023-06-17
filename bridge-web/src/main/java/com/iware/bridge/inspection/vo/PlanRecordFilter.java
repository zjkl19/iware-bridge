package com.iware.bridge.inspection.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "PlanRecordFilter",description = "计划记录请求参数")
public class PlanRecordFilter implements Serializable {

    private static final long serialVersionUID = -1264996212970511032L;

    @ApiModelProperty(value="项目id")
    private Integer projectId;
    @ApiModelProperty(value="巡查/维修类型")
    private Integer type;
    @ApiModelProperty(value="开始时间")
    private Date startTime;
    @ApiModelProperty(value="结束时间")
    private Date endTime;
    @ApiModelProperty(value="巡查/维修人员")
    private String username;
    @ApiModelProperty(value="执行状态")
    private Integer status;
    @ApiModelProperty(value = "结构物id")
    private Integer structureId;

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
