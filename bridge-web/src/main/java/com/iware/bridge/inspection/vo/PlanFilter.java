package com.iware.bridge.inspection.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "PlanFilter",description = "计划请求参数")
public class PlanFilter implements Serializable {

    private static final long serialVersionUID = -1264996100970511032L;

    @JSONField(format = "yyyy-MM")
    @ApiModelProperty(value="开始时间")
    private Date beginTime;
    @JSONField(format = "yyyy-MM")
    @ApiModelProperty(value ="结束月份")
    private Date endTime;
    @ApiModelProperty(value="类型 1:巡查 2:维养")
    private Integer type;
    @ApiModelProperty(value="巡查类型")
    private Integer inspectType;
    @ApiModelProperty(value="状态")
    private Integer status;
    @ApiModelProperty(value = "项目")
    private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getInspectType() {
        return inspectType;
    }

    public void setInspectType(Integer inspectType) {
        this.inspectType = inspectType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
