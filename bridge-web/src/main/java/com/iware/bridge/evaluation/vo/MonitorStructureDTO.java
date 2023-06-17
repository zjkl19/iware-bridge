package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: CWY
 * @Date: 2021/5/28 11:38
 */
@ApiModel(value = "MonitorStructureDTO", description = "监测计划结构物")
public class MonitorStructureDTO implements Serializable {

    @ApiModelProperty(value = "评定等级")
    private String ratingLevel;

    @ApiModelProperty(value = "评分")
    private Double score;

    @ApiModelProperty(value = "线路id")
    private Long roadId;

    @ApiModelProperty(value = "评分id")
    private Long scoreId;

    @ApiModelProperty(value = "监测计划结构物id")
    private Long id;

    public String getRatingLevel() {
        return ratingLevel;
    }

    public void setRatingLevel(String ratingLevel) {
        this.ratingLevel = ratingLevel;
    }

    public Long getRoadId() {
        return roadId;
    }

    public void setRoadId(Long roadId) {
        this.roadId = roadId;
    }

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
