package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author: wjp
 * @date: 2021年8月13日10:06:06
 * @since 1.0
 */
public class BridgeLevelRankVO {

    @ApiModelProperty(value = "项目id")
    private Integer projectId;

    @ApiModelProperty(value = "计划id")
    private Integer monitorStructureId;

    @ApiModelProperty(value = "结构物id")
    private Integer structureId;

    @ApiModelProperty(value = "结构物名称")
    private String structureName;

    @ApiModelProperty(value = "项目结束时间")
    private Date endTime;

    @ApiModelProperty(value = "线路名称")
    private String roadName;

    @ApiModelProperty(value = "检测时间")
    private String evaluationTime;

    @ApiModelProperty(value = "BCI分数")
    private Double bridgeConditionIndex;

    @ApiModelProperty(value = "线路id")
    private Integer roleId;

    @ApiModelProperty(value = "桥梁等级")
    private String ratingLevel;


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getMonitorStructureId() {
        return monitorStructureId;
    }

    public void setMonitorStructureId(Integer monitorStructureId) {
        this.monitorStructureId = monitorStructureId;
    }

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(String evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public Double getBridgeConditionIndex() {
        return bridgeConditionIndex;
    }

    public void setBridgeConditionIndex(Double bridgeConditionIndex) {
        this.bridgeConditionIndex = bridgeConditionIndex;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRatingLevel() {
        return ratingLevel;
    }

    public void setRatingLevel(String ratingLevel) {
        this.ratingLevel = ratingLevel;
    }
}
