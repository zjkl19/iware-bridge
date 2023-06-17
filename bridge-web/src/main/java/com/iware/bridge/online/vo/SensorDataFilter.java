package com.iware.bridge.online.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@ApiModel(value="SensorDataFilter", description="传感器数据过滤")
public class SensorDataFilter implements Serializable {

    private static final long serialVersionUID = 2271966114970511032L;

    @ApiModelProperty(value = "测点编号")
    private String name;
    @ApiModelProperty(value = "传感器编码")
    private String coding;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
    @ApiModelProperty(value = "传感器列表")
    private List<SensorVO> sensorList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
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

    public List<SensorVO> getSensorList() {
        return sensorList;
    }

    public void setSensorList(List<SensorVO> sensorList) {
        this.sensorList = sensorList;
    }
}
