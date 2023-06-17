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

@ApiModel(value="SensorMinute", description="传感器1小时内自动生成的数据(每1分)")
public class SensorMinute implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "最大值（每1小时）")
    private Float maxValue;

    @ApiModelProperty(value = "最小值（每1小时）")
    private Float minValue;

    @ApiModelProperty(value = "平均值（每1小时）")
    private Float avgValue;

    @ApiModelProperty(value = "接收数据时间")
    private Date time;

    @ApiModelProperty(value = "传感器编码")
    private String sensorCoding;

    @ApiModelProperty(value = "传感器细项id")
    private Integer sensorDetailsId;

    public Float getMaxValue() {
		return maxValue;
    }

    public void setMaxValue(Float maxValue) {
        this.maxValue = maxValue;
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

    public Date getTime() {
		return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
        return "SensorMinute{" +
            "  maxValue=" + maxValue +
            ", minValue=" + minValue +
            ", avgValue=" + avgValue +
            ", time=" + time +
            ", sensorCoding=" + sensorCoding +
            ", sensorDetailsId=" + sensorDetailsId +
        "}";
    }
}
