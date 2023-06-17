package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "OverviewVO",description = "检测概况")
public class OverviewVO implements Serializable {

    private static final long serialVersionUID = -1164226100970511032L;

    @ApiModelProperty(value="检测单位")
    private String evaluationUnit;
    @ApiModelProperty(value="检测类型")
    private String type;
    @ApiModelProperty(value="检测时间")
    private String evaluationTime;
    @ApiModelProperty(value="评定等级")
    private String ratingLevel;
    @ApiModelProperty(value="BCI评分")
    private Double bridgeConditionIndex;
    @ApiModelProperty(value="桥面系分数")
    private Double deckSystemBSIm;
    @ApiModelProperty(value="上部结构分数")
    private Double upperStructureBSIs;
    @ApiModelProperty(value="下部结构分数")
    private Double lowerStructureBSIx;
    @ApiModelProperty(value="计划结构物id")
    private Integer monitorStructureId;
    @ApiModelProperty(value="计划完成时间")
    private String endTime;
    @ApiModelProperty(value="项目id")
    private Integer projectId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEvaluationUnit() {
        return evaluationUnit;
    }

    public void setEvaluationUnit(String evaluationUnit) {
        this.evaluationUnit = evaluationUnit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(String evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public String getRatingLevel() {
        return ratingLevel;
    }

    public void setRatingLevel(String ratingLevel) {
        this.ratingLevel = ratingLevel;
    }

    public Double getBridgeConditionIndex() {
        return bridgeConditionIndex;
    }

    public void setBridgeConditionIndex(Double bridgeConditionIndex) {
        this.bridgeConditionIndex = bridgeConditionIndex;
    }

    public Double getDeckSystemBSIm() {
        return deckSystemBSIm;
    }

    public void setDeckSystemBSIm(Double deckSystemBSIm) {
        this.deckSystemBSIm = deckSystemBSIm;
    }

    public Double getUpperStructureBSIs() {
        return upperStructureBSIs;
    }

    public void setUpperStructureBSIs(Double upperStructureBSIs) {
        this.upperStructureBSIs = upperStructureBSIs;
    }

    public Double getLowerStructureBSIx() {
        return lowerStructureBSIx;
    }

    public void setLowerStructureBSIx(Double lowerStructureBSIx) {
        this.lowerStructureBSIx = lowerStructureBSIx;
    }

    public Integer getMonitorStructureId() {
        return monitorStructureId;
    }

    public void setMonitorStructureId(Integer monitorStructureId) {
        this.monitorStructureId = monitorStructureId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
