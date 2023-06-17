package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author: wjp
 * @date: 2021年9月27日10:17:13
 * @since 1.0
 */
@ApiModel(value = "SensorCalculateFilter", description = "查询频率条件")
public class SensorCalculateFilter {

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "测点编号id")
    private Integer id;

    @ApiModelProperty(value = "数据编码")
    private String sensorCoding;

    @ApiModelProperty(value = "类型id（1：称重，2：加速度，3：索力，4：相关性，5：箱线）")
    private Integer type;

    @ApiModelProperty(value = "细项id")
    private Integer sensorDetailsId;

    @ApiModelProperty(value = "测点编号")
    private String name;

    public Integer getSensorDetailsId() {
        return sensorDetailsId;
    }

    public void setSensorDetailsId(Integer sensorDetailsId) {
        this.sensorDetailsId = sensorDetailsId;
    }

    public String getSensorCoding() {
        return sensorCoding;
    }

    public void setSensorCoding(String sensorCoding) {
        this.sensorCoding = sensorCoding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

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
}
