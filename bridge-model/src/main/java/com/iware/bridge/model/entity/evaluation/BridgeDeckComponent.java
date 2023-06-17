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

@ApiModel(value="BridgeDeckComponent", description="桥面系构件实例")
public class BridgeDeckComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "桥梁桥面系id")
    private Integer bridgeDeckSystemId;

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

    public Integer getBridgeDeckSystemId() {
        return bridgeDeckSystemId;
    }

    public void setBridgeDeckSystemId(Integer bridgeDeckSystemId) {
        this.bridgeDeckSystemId = bridgeDeckSystemId;
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
        return "BridgeDeckComponent{" +
                "  id=" + id +
                ", bridgeDeckSystemId=" + bridgeDeckSystemId +
                ", componentId=" + componentId +
                ", code=" + code +
                ", initialWeight=" + initialWeight +
                ", actualWeight=" + actualWeight +
                ", deduct=" + deduct +
                ", serious=" + serious +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", creator=" + creator +
                ", status=" + status +
                "}";
    }
}
