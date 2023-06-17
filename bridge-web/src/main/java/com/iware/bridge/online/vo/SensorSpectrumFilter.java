package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author: wjp
 * @date: 2021年12月27日10:36:17
 * @since 1.0
 */
@ApiModel(value = "SensorSpectrumFilter", description = "频谱图数据条件")
public class SensorSpectrumFilter {


    @ApiModelProperty(value = "时间")
    private Date time;

    @ApiModelProperty(value = "测点id")
    private Integer id;

    @ApiModelProperty(value = "类型id")
    private Integer type;

    @ApiModelProperty(value = "传感器编码")
    private String sensorCoding;

    @ApiModelProperty(value = "传感器细项id")
    private Integer sensorDetailsId;

    @ApiModelProperty(value = "测点编号")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
