package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "DetectionRecord", description = "生成记录")
public class DetectionRecord {

    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "要素名称")
    private String name;
    @ApiModelProperty(value = "病害名称")
    private String diseaseName;
    @ApiModelProperty(value = "跨名称")
    private String spanCode;
    @ApiModelProperty(value = "病害损害程度")
    private String degree;
    @ApiModelProperty(value = "流水号")
    private Integer sort;
    @ApiModelProperty(value = "病害缩写")
    private String code;
    @ApiModelProperty(value = "线路名称")
    private String roadName;
    @ApiModelProperty(value = "病害X坐标")
    private float xAxis;
    @ApiModelProperty(value = "病害Y坐标")
    private float yAxis;
    @ApiModelProperty(value = "线路id")
    private Integer roadId;
    @ApiModelProperty(value = "跨id")
    private Integer spanId;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "构件实例code")
    private String componentCode;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;

    public Integer getRoadId() {
        return roadId;
    }

    public void setRoadId(Integer roadId) {
        this.roadId = roadId;
    }

    public Integer getSpanId() {
        return spanId;
    }

    public void setSpanId(Integer spanId) {
        this.spanId = spanId;
    }

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

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getSpanCode() {
        return spanCode;
    }

    public void setSpanCode(String spanCode) {
        this.spanCode = spanCode;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }


    public float getxAxis() {
        return xAxis;
    }

    public void setxAxis(float xAxis) {
        this.xAxis = xAxis;
    }

    public float getyAxis() {
        return yAxis;
    }

    public void setyAxis(float yAxis) {
        this.yAxis = yAxis;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public void setComponentCode(String componentCode) {
        this.componentCode = componentCode;
    }

    @Override
    public String toString() {
        return "DetectionRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", diseaseName='" + diseaseName + '\'' +
                ", spanCode='" + spanCode + '\'' +
                ", degree='" + degree + '\'' +
                ", sort=" + sort +
                ", code='" + code + '\'' +
                ", roadName='" + roadName + '\'' +
                ", xAxis=" + xAxis +
                ", yAxis=" + yAxis +
                ", roadId=" + roadId +
                ", spanId=" + spanId +
                ", remark='" + remark + '\'' +
                ", componentCode='" + componentCode + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
