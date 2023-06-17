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

@ApiModel(value="SensorConverge", description="传感器汇聚表")
public class SensorConverge implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "结构物id")
    private Integer structureId;

    @ApiModelProperty(value = "传感器id")
    private Integer sensorId;

    @ApiModelProperty(value = "采样时间")
    private Date samplingTime;

    @ApiModelProperty(value = "实测值")
    private Float value;

    @ApiModelProperty(value = "传感器编码")
    private String sensorCoding;

    @ApiModelProperty(value = "传感器细项id")
    private Integer sensorDetailsId;

    public Integer getStructureId() {
		return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public Integer getSensorId() {
		return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

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


    @Override
    public String toString() {
        return "SensorConverge{" +
            "  structureId=" + structureId +
            ", sensorId=" + sensorId +
            ", samplingTime=" + samplingTime +
            ", value=" + value +
            ", sensorCoding=" + sensorCoding +
            ", sensorDetailsId=" + sensorDetailsId +
        "}";
    }
}
