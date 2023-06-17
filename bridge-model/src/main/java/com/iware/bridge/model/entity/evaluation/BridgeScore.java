package com.iware.bridge.model.entity.evaluation;


import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="BridgeScore", description="桥梁评分表")
public class BridgeScore implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private Date evaluationTime;

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


    @Override
    public String toString() {
        return "BridgeScore{" +
                "  id=" + id +
                ", targetId=" + targetId +
                ", type=" + type +
                ", bridgeConditionIndex=" + bridgeConditionIndex +
                ", bridgeStructureIndex=" + bridgeStructureIndex +
                ", ratingLevel=" + ratingLevel +
                ", evaluationUnit=" + evaluationUnit +
                ", evaluationTime=" + evaluationTime +
                ", evaluatorId=" + evaluatorId +
                ", score=" + score +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", status=" + status +
                "}";
    }
}
