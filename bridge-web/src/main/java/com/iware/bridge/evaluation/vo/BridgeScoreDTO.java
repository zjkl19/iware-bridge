package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: CWY
 * @Date: 2021/5/20 15:30
 */
public class BridgeScoreDTO implements Serializable {

    @ApiModelProperty(value = "评分id")
    private Long id;

    @ApiModelProperty(value = "关联项id")
    private Long targetId;

    @ApiModelProperty(value = "关联项类型")
    private Integer type;

    @ApiModelProperty(value = "BCI分数")
    private Double bridgeConditionIndex;

    @ApiModelProperty(value = "BSI分数")
    private Double bridgeStructureIndex;

    @ApiModelProperty(value = "评价人员")
    private String evaluator;

    @ApiModelProperty(value = "评价人员id")
    private Integer evaluatorId;

    @ApiModelProperty(value = "评定等级")
    private String ratingLevel;

    @ApiModelProperty(value = "评价单位")
    private String evaluationUnit;

    @ApiModelProperty(value = "评价时间")
    private Date evaluationTime;

    @ApiModelProperty(value = "评分")
    private Double score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getBridgeConditionIndex() {
        return bridgeConditionIndex;
    }

    public void setBridgeConditionIndex(Double bridgeConditionIndex) {
        this.bridgeConditionIndex = bridgeConditionIndex;
    }

    public Double getBridgeStructureIndex() {
        return bridgeStructureIndex;
    }

    public void setBridgeStructureIndex(Double bridgeStructureIndex) {
        this.bridgeStructureIndex = bridgeStructureIndex;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public String getRatingLevel() {
        return ratingLevel;
    }

    public void setRatingLevel(String ratingLevel) {
        this.ratingLevel = ratingLevel;
    }

    public String getEvaluationUnit() {
        return evaluationUnit;
    }

    public void setEvaluationUnit(String evaluationUnit) {
        this.evaluationUnit = evaluationUnit;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(Date evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public Integer getEvaluatorId() {
        return evaluatorId;
    }

    public void setEvaluatorId(Integer evaluatorId) {
        this.evaluatorId = evaluatorId;
    }
}










