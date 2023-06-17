package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: HX
 * @date: 2021-6-28
 * @since 1.0
 */
@ApiModel(value = "BridgeSurveySecondTierVO", description = "桥跨信息")
@Data
public class BridgeSurveySecondTierVO implements Serializable {
    private static final long serialVersionUID = -28736481116808846L;

    @ApiModelProperty(value = "桥跨id")
    private Integer id;

    @ApiModelProperty(value = "桥路id")
    private Integer bridgeRoadId;

    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "spanCode")
    private String spanCode;

    @ApiModelProperty(value = "桥梁形状（1=直线桥，2=曲线桥）")
    private Integer bridgeShape;

    @ApiModelProperty(value = "桥跨长度")
    private Double length;

    @ApiModelProperty(value = "外弧长")
    private Double outerArcLength;

    @ApiModelProperty(value = "内弧长")
    private Double innerArcLength;

    @ApiModelProperty(value = "0：凹  1：凸")
    private Integer convex;

    @ApiModelProperty(value = "三级树菜单")
    private List<BridgeSurveyThirdTierVO> children;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBridgeRoadId() {
        return bridgeRoadId;
    }

    public void setBridgeRoadId(Integer bridgeRoadId) {
        this.bridgeRoadId = bridgeRoadId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpanCode() {
        return spanCode;
    }

    public void setSpanCode(String spanCode) {
        this.spanCode = spanCode;
    }

    public Integer getBridgeShape() {
        return bridgeShape;
    }

    public void setBridgeShape(Integer bridgeShape) {
        this.bridgeShape = bridgeShape;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getOuterArcLength() {
        return outerArcLength;
    }

    public void setOuterArcLength(Double outerArcLength) {
        this.outerArcLength = outerArcLength;
    }

    public Double getInnerArcLength() {
        return innerArcLength;
    }

    public void setInnerArcLength(Double innerArcLength) {
        this.innerArcLength = innerArcLength;
    }

    public Integer getConvex() {
        return convex;
    }

    public void setConvex(Integer convex) {
        this.convex = convex;
    }

    public List<BridgeSurveyThirdTierVO> getChildren() {
        return children;
    }

    public void setChildren(List<BridgeSurveyThirdTierVO> children) {
        this.children = children;
    }
}
