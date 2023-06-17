package com.iware.bridge.inspection.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "PlanDetailFilter",description = "计划详情请求参数")
public class PlanDetailFilter implements Serializable {

    private static final long serialVersionUID = -1264996212970511032L;

    @ApiModelProperty(value="结构物id")
    private Integer structureId;
    @ApiModelProperty(value="开始时间")
    private Date startTime;
    @ApiModelProperty(value="结束时间")
    private Date endTime;
    @ApiModelProperty(value="状态")
    private Integer status;
    @ApiModelProperty(value="对应计划")
    private Integer planId;
    @ApiModelProperty(value = "模糊查询")
    private String words;

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
