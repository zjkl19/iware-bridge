package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "病害汇总数据", description = "病害汇总数据")
public class DiseaseSummary {

    @ApiModelProperty(value = "部位")
    private String value1;
    @ApiModelProperty(value = "部件")
    private String value2;
    @ApiModelProperty(value = "病害类型")
    private String value3;
    @ApiModelProperty(value = "累计工程")
    private String value4;
    @ApiModelProperty(value = "面积")
    private float area;
    @ApiModelProperty(value = "个数")
    private float number;
    @ApiModelProperty(value = "深度")
    private float depth;
    @ApiModelProperty(value = "缝长")
    private float seamLength;
    @ApiModelProperty(value = "缝宽")
    private float seamWidth;
    @ApiModelProperty(value = "高度差")
    private float heightDifference;
    @ApiModelProperty(value = "程度")
    private String degree;

    public float getHeightDifference() {
        return heightDifference;
    }

    public void setHeightDifference(float heightDifference) {
        this.heightDifference = heightDifference;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public float getSeamLength() {
        return seamLength;
    }

    public void setSeamLength(float seamLength) {
        this.seamLength = seamLength;
    }

    public float getSeamWidth() {
        return seamWidth;
    }

    public void setSeamWidth(float seamWidth) {
        this.seamWidth = seamWidth;
    }

    public String getValue4() {
        return value4;
    }

    public void setValue4(String value4) {
        this.value4 = value4;
    }

    @Override
    public String toString() {
        return "DiseaseSummary{" +
                "value1='" + value1 + '\'' +
                ", value2='" + value2 + '\'' +
                ", value3='" + value3 + '\'' +
                ", value4='" + value4 + '\'' +
                ", area=" + area +
                ", number=" + number +
                ", depth=" + depth +
                ", seamLength=" + seamLength +
                ", seamWidth=" + seamWidth +
                ", heightDifference=" + heightDifference +
                ", degree='" + degree + '\'' +
                '}';
    }
}
