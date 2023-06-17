package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: CWY
 * @Date: 2021/5/11 11:33
 */
@ApiModel(value = "BCIEvaluationResultVO", description = "BCI评价结果")
public class BCIEvaluationResultDTO implements Serializable {

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "跨径数量")
    private Integer spanNumber;

    @ApiModelProperty(value = "业主单位")
    private String ownerUnit;

    @ApiModelProperty(value = "检测时间")
    private String testingTime;

    @ApiModelProperty(value = "评价日期")
    private Date evaluationTime;

    @ApiModelProperty(value = "评价单位")
    private String evaluationUnit;

    @ApiModelProperty(value = "评价人员")
    private String evaluator;

    @ApiModelProperty(value = "评价人员id")
    private Integer evaluatorId;

    @ApiModelProperty(value = "线路名称")
    private String roadName;

    @ApiModelProperty(value = "线路评分")
    private Double roadScore;

    @ApiModelProperty(value = "线路评分Id")
    private Long roadScoreId;

    @ApiModelProperty(value = "评价等级")
    private String ratingLevel;

    @ApiModelProperty(value = "桥面系BCIm")
    private Double bridgeBCIm;

    @ApiModelProperty(value = "桥面系BSIm")
    private Double bridgeBSIm;

    @ApiModelProperty(value = "上部结构BCIs")
    private Double upperStructureBCIs;

    @ApiModelProperty(value = "上部结构BSIs")
    private Double upperStructureBSIs;

    @ApiModelProperty(value = "下部结构BCIx")
    private Double lowerStructureBCIx;

    @ApiModelProperty(value = "下部结构BSIx")
    private Double lowerStructureBSIx;

    @ApiModelProperty(value = "桥梁类型")
    private Integer bridgeTypeId;

    public Integer getSpanNumber() {
        return spanNumber;
    }

    public void setSpanNumber(Integer spanNumber) {
        this.spanNumber = spanNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOwnerUnit() {
        return ownerUnit;
    }

    public void setOwnerUnit(String ownerUnit) {
        this.ownerUnit = ownerUnit;
    }

    public String getTestingTime() {
        return testingTime;
    }

    public void setTestingTime(String testingTime) {
        this.testingTime = testingTime;
    }

    public Date getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(Date evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public String getEvaluationUnit() {
        return evaluationUnit;
    }

    public void setEvaluationUnit(String evaluationUnit) {
        this.evaluationUnit = evaluationUnit;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public Double getRoadScore() {
        return roadScore;
    }

    public void setRoadScore(Double roadScore) {
        this.roadScore = roadScore;
    }

    public String getRatingLevel() {
        return ratingLevel;
    }

    public void setRatingLevel(String ratingLevel) {
        this.ratingLevel = ratingLevel;
    }

    public Double getBridgeBCIm() {
        return bridgeBCIm;
    }

    public void setBridgeBCIm(Double bridgeBCIm) {
        this.bridgeBCIm = bridgeBCIm;
    }

    public Double getBridgeBSIm() {
        return bridgeBSIm;
    }

    public void setBridgeBSIm(Double bridgeBSIm) {
        this.bridgeBSIm = bridgeBSIm;
    }

    public Double getUpperStructureBCIs() {
        return upperStructureBCIs;
    }

    public void setUpperStructureBCIs(Double upperStructureBCIs) {
        this.upperStructureBCIs = upperStructureBCIs;
    }

    public Double getUpperStructureBSIs() {
        return upperStructureBSIs;
    }

    public void setUpperStructureBSIs(Double upperStructureBSIs) {
        this.upperStructureBSIs = upperStructureBSIs;
    }

    public Double getLowerStructureBCIx() {
        return lowerStructureBCIx;
    }

    public void setLowerStructureBCIx(Double lowerStructureBCIx) {
        this.lowerStructureBCIx = lowerStructureBCIx;
    }

    public Double getLowerStructureBSIx() {
        return lowerStructureBSIx;
    }

    public void setLowerStructureBSIx(Double lowerStructureBSIx) {
        this.lowerStructureBSIx = lowerStructureBSIx;
    }

    public Integer getBridgeTypeId() {
        return bridgeTypeId;
    }

    public void setBridgeTypeId(Integer bridgeTypeId) {
        this.bridgeTypeId = bridgeTypeId;
    }

    public Long getRoadScoreId() {
        return roadScoreId;
    }

    public void setRoadScoreId(Long roadScoreId) {
        this.roadScoreId = roadScoreId;
    }

    public Integer getEvaluatorId() {
        return evaluatorId;
    }

    public void setEvaluatorId(Integer evaluatorId) {
        this.evaluatorId = evaluatorId;
    }
}
















