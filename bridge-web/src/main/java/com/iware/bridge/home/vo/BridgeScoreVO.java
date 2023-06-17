package com.iware.bridge.home.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "BridgeScoreVO",description = "桥隧综合评分")
public class BridgeScoreVO implements Serializable {

    private static final long serialVersionUID = -1164226100970511032L;

    @ApiModelProperty(value="结构物id")
    private Integer id;
    @ApiModelProperty(value="结构物名称")
    private String name;
    @ApiModelProperty(value="计划结构物关联id")
    private Integer monitorPlanRelId;
    @ApiModelProperty(value="分值")
    private Double score;
    @ApiModelProperty(value="评分时间")
    private Date evaluationTime;
    @ApiModelProperty(value="评价等级")
    private String ratingLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMonitorPlanRelId() {
        return monitorPlanRelId;
    }

    public void setMonitorPlanRelId(Integer monitorPlanRelId) {
        this.monitorPlanRelId = monitorPlanRelId;
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

    public String getRatingLevel() {
        return ratingLevel;
    }

    public void setRatingLevel(String ratingLevel) {
        this.ratingLevel = ratingLevel;
    }
}
