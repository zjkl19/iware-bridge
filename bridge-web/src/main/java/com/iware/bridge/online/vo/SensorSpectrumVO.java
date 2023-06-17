package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年12月27日10:36:17
 * @since 1.0
 */
@ApiModel(value = "SensorSpectrumVO", description = "频谱图数据")
public class SensorSpectrumVO {

    @ApiModelProperty(value = "频率")
    private Float frequency;

    @ApiModelProperty(value = "幅值")
    private Float amplitude;

    public Float getFrequency() {
        return frequency;
    }

    public void setFrequency(Float frequency) {
        this.frequency = frequency;
    }

    public Float getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(Float amplitude) {
        this.amplitude = amplitude;
    }
}
