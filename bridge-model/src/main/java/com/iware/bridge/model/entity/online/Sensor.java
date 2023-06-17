package com.iware.bridge.model.entity.online;


import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="Sensor", description="传感器细项信息表")
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "测点id")
    private Integer meansPointId;

    @ApiModelProperty(value = "传感器细项id")
    private Integer sensorDetailsId;

    @ApiModelProperty(value = "传感器原理id")
    private Integer sensorPrincipleId;

    @ApiModelProperty(value = "传感器编号(数据读取编码)")
    private String sensorCoding;

    @ApiModelProperty(value = "初始值")
    private Float initialValue;

    @ApiModelProperty(value = "预警间隔")
    private Integer warningInterval;

    @ApiModelProperty(value = "一级预警上限")
    private Float firstWarningUpper;

    @ApiModelProperty(value = "一级预警下限")
    private Float firstWarningLower;

    @ApiModelProperty(value = "二级预警上限")
    private Float secondWarningUpper;

    @ApiModelProperty(value = "二级预警下限")
    private Float secondWarningLower;

    @ApiModelProperty(value = "三级预警上限")
    private Float thirdWarningUpper;

    @ApiModelProperty(value = "三级预警下限")
    private Float thirdWarningLower;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "精度")
    private Float precision;

    @ApiModelProperty(value = "状态")
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

    public Integer getMeansPointId() {
		return meansPointId;
    }

    public void setMeansPointId(Integer meansPointId) {
        this.meansPointId = meansPointId;
    }

    public Integer getSensorDetailsId() {
		return sensorDetailsId;
    }

    public void setSensorDetailsId(Integer sensorDetailsId) {
        this.sensorDetailsId = sensorDetailsId;
    }

    public Integer getSensorPrincipleId() {
		return sensorPrincipleId;
    }

    public void setSensorPrincipleId(Integer sensorPrincipleId) {
        this.sensorPrincipleId = sensorPrincipleId;
    }

    public String getSensorCoding() {
		return sensorCoding;
    }

    public void setSensorCoding(String sensorCoding) {
        this.sensorCoding = sensorCoding;
    }

    public Float getInitialValue() {
		return initialValue;
    }

    public void setInitialValue(Float initialValue) {
        this.initialValue = initialValue;
    }

    public Integer getWarningInterval() {
		return warningInterval;
    }

    public void setWarningInterval(Integer warningInterval) {
        this.warningInterval = warningInterval;
    }

    public Float getFirstWarningUpper() {
		return firstWarningUpper;
    }

    public void setFirstWarningUpper(Float firstWarningUpper) {
        this.firstWarningUpper = firstWarningUpper;
    }

    public Float getFirstWarningLower() {
		return firstWarningLower;
    }

    public void setFirstWarningLower(Float firstWarningLower) {
        this.firstWarningLower = firstWarningLower;
    }

    public Float getSecondWarningUpper() {
		return secondWarningUpper;
    }

    public void setSecondWarningUpper(Float secondWarningUpper) {
        this.secondWarningUpper = secondWarningUpper;
    }

    public Float getSecondWarningLower() {
		return secondWarningLower;
    }

    public void setSecondWarningLower(Float secondWarningLower) {
        this.secondWarningLower = secondWarningLower;
    }

    public Float getThirdWarningUpper() {
		return thirdWarningUpper;
    }

    public void setThirdWarningUpper(Float thirdWarningUpper) {
        this.thirdWarningUpper = thirdWarningUpper;
    }

    public Float getThirdWarningLower() {
		return thirdWarningLower;
    }

    public void setThirdWarningLower(Float thirdWarningLower) {
        this.thirdWarningLower = thirdWarningLower;
    }

    public String getUnit() {
		return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getPrecision() {
		return precision;
    }

    public void setPrecision(Float precision) {
        this.precision = precision;
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
        return "Sensor{" +
            "  id=" + id +
            ", meansPointId=" + meansPointId +
            ", sensorDetailsId=" + sensorDetailsId +
            ", sensorPrincipleId=" + sensorPrincipleId +
            ", sensorCoding=" + sensorCoding +
            ", initialValue=" + initialValue +
            ", warningInterval=" + warningInterval +
            ", firstWarningUpper=" + firstWarningUpper +
            ", firstWarningLower=" + firstWarningLower +
            ", secondWarningUpper=" + secondWarningUpper +
            ", secondWarningLower=" + secondWarningLower +
            ", thirdWarningUpper=" + thirdWarningUpper +
            ", thirdWarningLower=" + thirdWarningLower +
            ", unit=" + unit +
            ", precision=" + precision +
            ", status=" + status +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
