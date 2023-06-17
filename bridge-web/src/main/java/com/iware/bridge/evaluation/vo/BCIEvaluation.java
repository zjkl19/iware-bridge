package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: CWY
 * @Date: 2021/5/10 17:33
 */

@ApiModel(value = "BCIEvaluation", description = "BCI评价")
public class BCIEvaluation implements Serializable {

    @ApiModelProperty(value = "评分ID")
    private Integer id;

    @ApiModelProperty(value = "线路名称")
    private String roadName;

    @ApiModelProperty(value = "线路id")
    private Long bridgeRoadId;

    @ApiModelProperty(value = "桥跨数量")
    private Integer bridgeSpanNum;

    @ApiModelProperty(value = "BCI评分")
    private Double BCIScore;
    @ApiModelProperty(value = "评价时间 ")
    private String evaluationTime;
    @ApiModelProperty(value = "是否更新了病害 true-未更新 false-更新")
    private boolean is;

    public String getEvaluationTime() {
        return evaluationTime;
    }

    public boolean isIs() {
        return is;
    }

    public void setIs(boolean is) {
        this.is = is;
    }

    public void setEvaluationTime(String evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public Integer getBridgeSpanNum() {
        return bridgeSpanNum;
    }

    public void setBridgeSpanNum(Integer bridgeSpanNum) {
        this.bridgeSpanNum = bridgeSpanNum;
    }

    public Double getBCIScore() {
        return BCIScore;
    }

    public void setBCIScore(Double BCIScore) {
        this.BCIScore = BCIScore;
    }

    public Long getBridgeRoadId() {
        return bridgeRoadId;
    }

    public void setBridgeRoadId(Long bridgeRoadId) {
        this.bridgeRoadId = bridgeRoadId;
    }

    @Override
    public String toString() {
        return "BCIEvaluation{" +
                "id=" + id +
                ", roadName='" + roadName + '\'' +
                ", bridgeRoadId=" + bridgeRoadId +
                ", bridgeSpanNum=" + bridgeSpanNum +
                ", BCIScore=" + BCIScore +
                ", evaluationTime='" + evaluationTime + '\'' +
                ", is=" + is +
                '}';
    }
}
