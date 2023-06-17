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

@ApiModel(value="BridgeSpan", description="桥跨表")
public class BridgeSpan implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "线路id")
    private Integer bridgeRoadId;

    @ApiModelProperty(value = "桥梁形状")
    private Integer bridgeShape;

    @ApiModelProperty(value = "桥跨编号")
    private String spanCode;

    @ApiModelProperty(value = "桥跨长度")
    private Double length;

    @ApiModelProperty(value = "外弧长")
    private Double outerArcLength;

    @ApiModelProperty(value = "内弧长")
    private Double innerArcLength;

    @ApiModelProperty(value = "0：凹  1：凸")
    private Integer convex;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "1：正常 0：回收")
    private Integer status;

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

    public Integer getBridgeShape() {
		return bridgeShape;
    }

    public void setBridgeShape(Integer bridgeShape) {
        this.bridgeShape = bridgeShape;
    }

    public String getSpanCode() {
		return spanCode;
    }

    public void setSpanCode(String spanCode) {
        this.spanCode = spanCode;
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

    public String getCreator() {
		return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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
        return "BridgeSpan{" +
            "  id=" + id +
            ", bridgeRoadId=" + bridgeRoadId +
            ", bridgeShape=" + bridgeShape +
            ", spanCode=" + spanCode +
            ", length=" + length +
            ", outerArcLength=" + outerArcLength +
            ", innerArcLength=" + innerArcLength +
            ", convex=" + convex +
            ", creator=" + creator +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
            ", status=" + status +
        "}";
    }
}
