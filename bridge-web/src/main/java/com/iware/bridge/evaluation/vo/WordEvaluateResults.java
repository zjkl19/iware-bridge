package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "WordEvaluateResults", description = "poi文档完好状况评估结果")
public class WordEvaluateResults {

    @ApiModelProperty(value = "评估部位")
    private String evaluationPart;
    @ApiModelProperty(value = "构件跨/台")
    private String spanCode;
    @ApiModelProperty(value = "构件实例")
    private String name;
    @ApiModelProperty(value = "权重")
    private float actualWeight;
    @ApiModelProperty(value = "扣分")
    private float deduct;

    public String getEvaluationPart() {
        return evaluationPart;
    }

    public void setEvaluationPart(String evaluationPart) {
        this.evaluationPart = evaluationPart;
    }

    public String getSpanCode() {
        return spanCode;
    }

    public void setSpanCode(String spanCode) {
        this.spanCode = spanCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(float actualWeight) {
        this.actualWeight = actualWeight;
    }

    public float getDeduct() {
        return deduct;
    }

    public void setDeduct(float deduct) {
        this.deduct = deduct;
    }

    @Override
    public String toString() {
        return "WordEvaluateResults{" +
                "evaluationPart='" + evaluationPart + '\'' +
                ", spanCode=" + spanCode +
                ", name='" + name + '\'' +
                ", actualWeight=" + actualWeight +
                ", deduct=" + deduct +
                '}';
    }
}
