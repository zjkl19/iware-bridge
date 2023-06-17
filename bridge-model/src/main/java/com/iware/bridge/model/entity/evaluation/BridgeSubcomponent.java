package com.iware.bridge.model.entity.evaluation;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="BridgeSubcomponent", description="桥梁下部构件实例")
public class BridgeSubcomponent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "桥梁下部结构id")
    private Integer bridgeSubstructureId;

    @ApiModelProperty(value = "构件类型id")
    private Integer componentId;

    @ApiModelProperty(value = "结构编号")
    private String code;

    @ApiModelProperty(value = "原始权重")
    private Double initialWeight;

    @ApiModelProperty(value = "实际权重")
    private Double actualWeight;

    @ApiModelProperty(value = "扣分")
    private Double deduct;

    @ApiModelProperty(value = "盖梁高度")
    private Double capBeamsHeight;

    @ApiModelProperty(value = "盖梁宽度")
    private Double capBeamsWidth;

    @ApiModelProperty(value = "盖梁厚度")
    private Double capBeamsThick;

    @ApiModelProperty(value = "台身高度")
    private Double abutmentHeight;

    @ApiModelProperty(value = "台身宽度")
    private Double abutmentWidth;

    @ApiModelProperty(value = "台身厚度")
    private Double abutmentThick;

    @ApiModelProperty(value = "台帽高度")
    private Double abutmentCapHeight;

    @ApiModelProperty(value = "墩身形状 1:矩形 2:原型")
    private Integer pierShape;

    @ApiModelProperty(value = "墩身高度")
    private Double pierHeight;

    @ApiModelProperty(value = "墩身宽度")
    private Double pierWidth;

    @ApiModelProperty(value = "墩身厚度")
    private Double pierThick;

    @ApiModelProperty(value = "墩身半径")
    private Double pierRadius;

    @ApiModelProperty(value = "有无桥台 1:有 0:没有")
    private Integer isAbutment;

    @ApiModelProperty(value = "墩台id")
    private Integer pierAbutmentId;

    @ApiModelProperty(value = "病害是否带*(0：不带 1：带*)")
    private Integer serious;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "1：正常 0：被删除")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBridgeSubstructureId() {
        return bridgeSubstructureId;
    }

    public void setBridgeSubstructureId(Integer bridgeSubstructureId) {
        this.bridgeSubstructureId = bridgeSubstructureId;
    }

    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getInitialWeight() {
        return initialWeight;
    }

    public void setInitialWeight(Double initialWeight) {
        this.initialWeight = initialWeight;
    }

    public Double getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(Double actualWeight) {
        this.actualWeight = actualWeight;
    }

    public Double getDeduct() {
        return deduct;
    }

    public void setDeduct(Double deduct) {
        this.deduct = deduct;
    }

    public Double getCapBeamsHeight() {
        return capBeamsHeight;
    }

    public void setCapBeamsHeight(Double capBeamsHeight) {
        this.capBeamsHeight = capBeamsHeight;
    }

    public Double getCapBeamsWidth() {
        return capBeamsWidth;
    }

    public void setCapBeamsWidth(Double capBeamsWidth) {
        this.capBeamsWidth = capBeamsWidth;
    }

    public Double getCapBeamsThick() {
        return capBeamsThick;
    }

    public void setCapBeamsThick(Double capBeamsThick) {
        this.capBeamsThick = capBeamsThick;
    }

    public Double getAbutmentHeight() {
        return abutmentHeight;
    }

    public void setAbutmentHeight(Double abutmentHeight) {
        this.abutmentHeight = abutmentHeight;
    }

    public Double getAbutmentWidth() {
        return abutmentWidth;
    }

    public void setAbutmentWidth(Double abutmentWidth) {
        this.abutmentWidth = abutmentWidth;
    }

    public Double getAbutmentThick() {
        return abutmentThick;
    }

    public void setAbutmentThick(Double abutmentThick) {
        this.abutmentThick = abutmentThick;
    }

    public Double getAbutmentCapHeight() {
        return abutmentCapHeight;
    }

    public void setAbutmentCapHeight(Double abutmentCapHeight) {
        this.abutmentCapHeight = abutmentCapHeight;
    }

    public Integer getPierShape() {
        return pierShape;
    }

    public void setPierShape(Integer pierShape) {
        this.pierShape = pierShape;
    }

    public Double getPierHeight() {
        return pierHeight;
    }

    public void setPierHeight(Double pierHeight) {
        this.pierHeight = pierHeight;
    }

    public Double getPierWidth() {
        return pierWidth;
    }

    public void setPierWidth(Double pierWidth) {
        this.pierWidth = pierWidth;
    }

    public Double getPierThick() {
        return pierThick;
    }

    public void setPierThick(Double pierThick) {
        this.pierThick = pierThick;
    }

    public Double getPierRadius() {
        return pierRadius;
    }

    public void setPierRadius(Double pierRadius) {
        this.pierRadius = pierRadius;
    }

    public Integer getIsAbutment() {
        return isAbutment;
    }

    public void setIsAbutment(Integer isAbutment) {
        this.isAbutment = isAbutment;
    }

    public Integer getPierAbutmentId() {
        return pierAbutmentId;
    }

    public void setPierAbutmentId(Integer pierAbutmentId) {
        this.pierAbutmentId = pierAbutmentId;
    }

    public Integer getSerious() {
        return serious;
    }

    public void setSerious(Integer serious) {
        this.serious = serious;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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


    @Override
    public String toString() {
        return "BridgeSubcomponent{" +
                "  id=" + id +
                ", bridgeSubstructureId=" + bridgeSubstructureId +
                ", componentId=" + componentId +
                ", code=" + code +
                ", initialWeight=" + initialWeight +
                ", actualWeight=" + actualWeight +
                ", deduct=" + deduct +
                ", capBeamsHeight=" + capBeamsHeight +
                ", capBeamsWidth=" + capBeamsWidth +
                ", capBeamsThick=" + capBeamsThick +
                ", abutmentHeight=" + abutmentHeight +
                ", abutmentWidth=" + abutmentWidth +
                ", abutmentThick=" + abutmentThick +
                ", abutmentCapHeight=" + abutmentCapHeight +
                ", pierShape=" + pierShape +
                ", pierHeight=" + pierHeight +
                ", pierWidth=" + pierWidth +
                ", pierThick=" + pierThick +
                ", pierRadius=" + pierRadius +
                ", abutment=" + isAbutment +
                ", pierAbutmentId=" + pierAbutmentId +
                ", serious=" + serious +
                ", creator=" + creator +
                ", status=" + status +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                "}";
    }
}
