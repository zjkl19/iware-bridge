package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author: wjp
 * @date: 2021年8月20日13:54:31
 * @since 1.0
 */
public class ScoreVO {


    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "目标id")
    private Integer targetId;

    @ApiModelProperty(value = "1：结构物评分 2：线路评分 3：桥面系 4：上部结构 5：下部结构 6：墩台评分 7：要素 8：跨")
    private Integer type;

    @ApiModelProperty(value = "BCI分数")
    private Double bridgeConditionIndex;

    @ApiModelProperty(value = "BSI分数")
    private Double bridgeStructureIndex;

    @ApiModelProperty(value = "评定等级")
    private String ratingLevel;

    @ApiModelProperty(value = "评价单位")
    private String evaluationUnit;

    @ApiModelProperty(value = "评价时间")
    private String evaluationTime;

    @ApiModelProperty(value = "评价人员id（对应用户id）")
    private Integer evaluatorId;

    @ApiModelProperty(value = "评分")
    private Double score;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "状态")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
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

    public String getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(String evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public Integer getEvaluatorId() {
        return evaluatorId;
    }

    public void setEvaluatorId(Integer evaluatorId) {
        this.evaluatorId = evaluatorId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
