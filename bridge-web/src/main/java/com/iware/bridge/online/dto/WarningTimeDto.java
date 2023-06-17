package com.iware.bridge.online.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年8月5日13:44:35
 * @since 1.0
 */
@ApiModel(value = "WarningTimeDto",description = "预警变化")
public class WarningTimeDto {

    @ApiModelProperty(value = "时间")
    private String sensorTime;
    @ApiModelProperty(value = "一级预警数")
    private Integer warningLevelOne;
    @ApiModelProperty(value = "二级预警数")
    private Integer warningLevelTwo;
    @ApiModelProperty(value = "三级预数")
    private Integer warningLevelThree;

    public String getSensorTime() {
        return sensorTime;
    }

    public void setSensorTime(String sensorTime) {
        this.sensorTime = sensorTime;
    }

    public Integer getWarningLevelOne() {
        return warningLevelOne;
    }

    public void setWarningLevelOne(Integer warningLevelOne) {
        this.warningLevelOne = warningLevelOne;
    }

    public Integer getWarningLevelTwo() {
        return warningLevelTwo;
    }

    public void setWarningLevelTwo(Integer warningLevelTwo) {
        this.warningLevelTwo = warningLevelTwo;
    }

    public Integer getWarningLevelThree() {
        return warningLevelThree;
    }

    public void setWarningLevelThree(Integer warningLevelThree) {
        this.warningLevelThree = warningLevelThree;
    }
}
