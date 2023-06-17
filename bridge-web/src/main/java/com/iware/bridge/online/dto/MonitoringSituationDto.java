package com.iware.bridge.online.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年8月4日11:07:30
 * @since 1.0
 */
@ApiModel(value = "MonitoringSituationDto",description = "监测概况")
public class MonitoringSituationDto {

    @ApiModelProperty(value = "结构物id")
    private Integer structureId;
    @ApiModelProperty(value = "结构物名称")
    private String structureName;
    @ApiModelProperty(value = "传感器总数")
    private Integer sensorCount;
    @ApiModelProperty(value = "在线传感器数量")
    private Integer onlineSensorCount;
    @ApiModelProperty(value = "故障传感器数量")
    private Integer defaultSensorCount;
    @ApiModelProperty(value = "当日处理预警数")
    private Integer dealWithWarningCount;
    @ApiModelProperty(value = "当日未处理数")
    private Integer untreatedWarningCount;
    @ApiModelProperty(value = "当日总预警数")
    private Integer todayWarningCount;
    @ApiModelProperty(value = "当日处警率")
    private String percentageWarning;

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public Integer getSensorCount() {
        return sensorCount;
    }

    public void setSensorCount(Integer sensorCount) {
        this.sensorCount = sensorCount;
    }

    public Integer getOnlineSensorCount() {
        return onlineSensorCount;
    }

    public void setOnlineSensorCount(Integer onlineSensorCount) {
        this.onlineSensorCount = onlineSensorCount;
    }

    public Integer getDefaultSensorCount() {
        return defaultSensorCount;
    }

    public void setDefaultSensorCount(Integer defaultSensorCount) {
        this.defaultSensorCount = defaultSensorCount;
    }

    public Integer getDealWithWarningCount() {
        return dealWithWarningCount;
    }

    public void setDealWithWarningCount(Integer dealWithWarningCount) {
        this.dealWithWarningCount = dealWithWarningCount;
    }

    public Integer getUntreatedWarningCount() {
        return untreatedWarningCount;
    }

    public void setUntreatedWarningCount(Integer untreatedWarningCount) {
        this.untreatedWarningCount = untreatedWarningCount;
    }

    public Integer getTodayWarningCount() {
        return todayWarningCount;
    }

    public void setTodayWarningCount(Integer todayWarningCount) {
        this.todayWarningCount = todayWarningCount;
    }

    public String getPercentageWarning() {
        return percentageWarning;
    }

    public void setPercentageWarning(String percentageWarning) {
        this.percentageWarning = percentageWarning;
    }

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    @Override
    public String toString() {
        return "MonitoringSituationDto{" +
                "structureName='" + structureName + '\'' +
                ", sensorCount=" + sensorCount +
                ", onlineSensorCount=" + onlineSensorCount +
                ", defaultSensorCount=" + defaultSensorCount +
                ", dealWithWarningCount=" + dealWithWarningCount +
                ", untreatedWarningCount=" + untreatedWarningCount +
                ", todayWarningCount=" + todayWarningCount +
                ", percentageWarning='" + percentageWarning + '\'' +
                '}';
    }
}
