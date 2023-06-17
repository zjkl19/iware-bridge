package com.iware.bridge.online.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年8月9日16:25:23
 * @since 1.0
 */
@ApiModel(value = "SensorWeightDto",description = "时程数据")
public class SensorWeightDto {
    @ApiModelProperty(value = "标志")
    private String name;
    @ApiModelProperty(value = "时间")
    private String time;
    @ApiModelProperty(value = "数量")
    private Float count;
    @ApiModelProperty(value = "车型id")
    private Integer axleId;

    public SensorWeightDto() {
    }

    public SensorWeightDto(String name, String time, Float count, Integer axleId) {
        this.name = name;
        this.time = time;
        this.count = count;
        this.axleId = axleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Float getCount() {
        return count;
    }

    public void setCount(Float count) {
        this.count = count;
    }

    public Integer getAxleId() {
        return axleId;
    }

    public void setAxleId(Integer axleId) {
        this.axleId = axleId;
    }
}
