package com.iware.bridge.online.vo;

import com.iware.bridge.model.entity.online.Sensor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="SensorVO", description="传感器信息")
public class SensorVO extends Sensor implements Serializable {

    private static final long serialVersionUID = 1177966100970511032L;

    @ApiModelProperty(value = "传感器细项名称")
    private String name;
    @ApiModelProperty(value = "产商id")
    private Integer companyId;
    @ApiModelProperty(value = "传感器类型id")
    private Integer sensorTypeId;
    @ApiModelProperty(value = "传感器原理")
    private String sensorPrincipleName;
    @ApiModelProperty(value = "测点名称-传感器名称")
    private String sensorName;
    @ApiModelProperty(value = "采样频率")
    private String samplingFrequency;


    public String getSamplingFrequency() {
        return samplingFrequency;
    }

    public void setSamplingFrequency(String samplingFrequency) {
        this.samplingFrequency = samplingFrequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getSensorTypeId() {
        return sensorTypeId;
    }

    public void setSensorTypeId(Integer sensorTypeId) {
        this.sensorTypeId = sensorTypeId;
    }

    public String getSensorPrincipleName() {
        return sensorPrincipleName;
    }

    public void setSensorPrincipleName(String sensorPrincipleName) {
        this.sensorPrincipleName = sensorPrincipleName;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
}
