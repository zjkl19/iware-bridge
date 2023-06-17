package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ScoreComparison", description = "bci/bsi评分")
public class ScoreComparison {
    @ApiModelProperty(value = "时间")
    private String time;
    @ApiModelProperty(value = "桥面系BCI分数")
    private float dkScoreBCI;
    @ApiModelProperty(value = "上部结构BCI分数")
    private float supScoreBCI;
    @ApiModelProperty(value = "下部结构BCI分数")
    private float subScoreBCI;
    @ApiModelProperty(value = "桥面系BSI分数")
    private float dkScoreBSI;
    @ApiModelProperty(value = "上部结构分数")
    private float supScoreBSI;
    @ApiModelProperty(value = "下部结构分数")
    private float subScoreBSI;
    @ApiModelProperty(value = "BCI")
    private float bci;
    @ApiModelProperty(value = "评级")
    private String level;

    public float getBci() {
        return bci;
    }

    public void setBci(float bci) {
        this.bci = bci;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getDkScoreBCI() {
        return dkScoreBCI;
    }

    public void setDkScoreBCI(float dkScoreBCI) {
        this.dkScoreBCI = dkScoreBCI;
    }

    public float getSupScoreBCI() {
        return supScoreBCI;
    }

    public void setSupScoreBCI(float supScoreBCI) {
        this.supScoreBCI = supScoreBCI;
    }

    public float getSubScoreBCI() {
        return subScoreBCI;
    }

    public void setSubScoreBCI(float subScoreBCI) {
        this.subScoreBCI = subScoreBCI;
    }

    public float getDkScoreBSI() {
        return dkScoreBSI;
    }

    public void setDkScoreBSI(float dkScoreBSI) {
        this.dkScoreBSI = dkScoreBSI;
    }

    public float getSupScoreBSI() {
        return supScoreBSI;
    }

    public void setSupScoreBSI(float supScoreBSI) {
        this.supScoreBSI = supScoreBSI;
    }

    public float getSubScoreBSI() {
        return subScoreBSI;
    }

    public void setSubScoreBSI(float subScoreBSI) {
        this.subScoreBSI = subScoreBSI;
    }
}
