package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年9月27日09:12:03
 * @since 1.0
 */
@ApiModel(value = "SensorCalculateVO", description = "东华索力/加速度传感器频率计算")
public class SensorCalculateVO {

    @ApiModelProperty(value = "计算时间")
    private String calculateTime;

    @ApiModelProperty(value = "频率值")
    private Float frequency;

    @ApiModelProperty(value = "传感器编码")
    private String sensorCoding;

    @ApiModelProperty(value = "传感器细项id")
    private Integer sensorDetailsId;

    @ApiModelProperty(value = "索力")
    private Float cableForce;

    public Float getCableForce() {
        return cableForce;
    }

    public void setCableForce(Float cableForce) {
        this.cableForce = cableForce;
    }

    public String getCalculateTime() {
        return calculateTime;
    }

    public void setCalculateTime(String calculateTime) {
        this.calculateTime = calculateTime;
    }

    public Float getFrequency() {
        return frequency;
    }

    public void setFrequency(Float frequency) {
        this.frequency = frequency;
    }

    public String getSensorCoding() {
        return sensorCoding;
    }

    public void setSensorCoding(String sensorCoding) {
        this.sensorCoding = sensorCoding;
    }

    public Integer getSensorDetailsId() {
        return sensorDetailsId;
    }

    public void setSensorDetailsId(Integer sensorDetailsId) {
        this.sensorDetailsId = sensorDetailsId;
    }
}
