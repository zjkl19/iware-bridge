package com.iware.bridge.online.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: wjp
 * @date: 2021年8月9日11:20:05
 * @since 1.0
 */
@ApiModel(value = "SensorHistoryDto", description = "传感器历史值")
public class SensorHistoryDto implements Serializable {

    private static final long serialVersionUID = -1161196100970511032L;

    @ApiModelProperty(value = "采样时间")
    private Date samplingTime;
    @ApiModelProperty(value = "实测值")
    private Float value;
    @ApiModelProperty(value = "最小值")
    private Float minValue;
    @ApiModelProperty(value = "平均值")
    private Float avgValue;
    @ApiModelProperty(value = "最大值")
    private Float maxValue;
    @ApiModelProperty(value = "传感器编码")
    private String sensorCoding;
    @ApiModelProperty(value = "车牌号")
    private String licensePlate;
    @ApiModelProperty(value = "车轴数")
    private String singleShaftNuber;
    @ApiModelProperty(value = "车型")
    private String modelName;

    public Date getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(Date samplingTime) {
        this.samplingTime = samplingTime;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getMinValue() {
        return minValue;
    }

    public void setMinValue(Float minValue) {
        this.minValue = minValue;
    }

    public Float getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(Float avgValue) {
        this.avgValue = avgValue;
    }

    public Float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Float maxValue) {
        this.maxValue = maxValue;
    }

    public String getSensorCoding() {
        return sensorCoding;
    }

    public void setSensorCoding(String sensorCoding) {
        this.sensorCoding = sensorCoding;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getSingleShaftNuber() {
        return singleShaftNuber;
    }

    public void setSingleShaftNuber(String singleShaftNuber) {
        this.singleShaftNuber = singleShaftNuber;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
