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

@ApiModel(value="BridgeSupcomponent", description="桥梁上部构件实例")
public class BridgeSupcomponent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "桥梁上部结构id")
    private Integer supstructureId;

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

    @ApiModelProperty(value = "腹板高度")
    private Double webPlateHight;

    @ApiModelProperty(value = "翼板宽度")
    private Double wingPlateWidth;

    @ApiModelProperty(value = "底板宽度")
    private Double baseplateWidth;

    @ApiModelProperty(value = "横隔板高度")
    private Double diaphragmsHeight;

    @ApiModelProperty(value = "横隔板道数")
    private Integer diaphragmsNumber;

    @ApiModelProperty(value = "悬臂长度")
    private Double cantileverLenght;

    @ApiModelProperty(value = "挂梁长度")
    private Double hangingBeamLength;

    @ApiModelProperty(value = "病害是否带*(0：不带 1：带*)")
    private Integer serious;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "1：正常 0：回收")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupstructureId() {
        return supstructureId;
    }

    public void setSupstructureId(Integer supstructureId) {
        this.supstructureId = supstructureId;
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

    public Double getWebPlateHight() {
        return webPlateHight;
    }

    public void setWebPlateHight(Double webPlateHight) {
        this.webPlateHight = webPlateHight;
    }

    public Double getWingPlateWidth() {
        return wingPlateWidth;
    }

    public void setWingPlateWidth(Double wingPlateWidth) {
        this.wingPlateWidth = wingPlateWidth;
    }

    public Double getBaseplateWidth() {
        return baseplateWidth;
    }

    public void setBaseplateWidth(Double baseplateWidth) {
        this.baseplateWidth = baseplateWidth;
    }

    public Double getDiaphragmsHeight() {
        return diaphragmsHeight;
    }

    public void setDiaphragmsHeight(Double diaphragmsHeight) {
        this.diaphragmsHeight = diaphragmsHeight;
    }

    public Integer getDiaphragmsNumber() {
        return diaphragmsNumber;
    }

    public void setDiaphragmsNumber(Integer diaphragmsNumber) {
        this.diaphragmsNumber = diaphragmsNumber;
    }

    public Double getCantileverLenght() {
        return cantileverLenght;
    }

    public void setCantileverLenght(Double cantileverLenght) {
        this.cantileverLenght = cantileverLenght;
    }

    public Double getHangingBeamLength() {
        return hangingBeamLength;
    }

    public void setHangingBeamLength(Double hangingBeamLength) {
        this.hangingBeamLength = hangingBeamLength;
    }

    public Integer getSerious() {
        return serious;
    }

    public void setSerious(Integer serious) {
        this.serious = serious;
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


    @Override
    public String toString() {
        return "BridgeSupcomponent{" +
                "  id=" + id +
                ", supstructureId=" + supstructureId +
                ", componentId=" + componentId +
                ", code=" + code +
                ", initialWeight=" + initialWeight +
                ", actualWeight=" + actualWeight +
                ", deduct=" + deduct +
                ", webPlateHight=" + webPlateHight +
                ", wingPlateWidth=" + wingPlateWidth +
                ", baseplateWidth=" + baseplateWidth +
                ", diaphragmsHeight=" + diaphragmsHeight +
                ", diaphragmsNumber=" + diaphragmsNumber +
                ", cantileverLenght=" + cantileverLenght +
                ", hangingBeamLength=" + hangingBeamLength +
                ", serious=" + serious +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", creator=" + creator +
                ", status=" + status +
                "}";
    }
}
