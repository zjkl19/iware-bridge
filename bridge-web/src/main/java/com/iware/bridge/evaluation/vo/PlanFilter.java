package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "PlanFilter",description = "检测计划请求")
public class PlanFilter implements Serializable {

    private static final long serialVersionUID = -1164226111970511032L;

    @ApiModelProperty(value="项目id")
    private Integer projectId;
    @ApiModelProperty(value="结构物id")
    private Integer structureId;
    @ApiModelProperty(value="开始时间")
    private Date startTime;
    @ApiModelProperty(value="结束时间")
    private Date endTime;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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
