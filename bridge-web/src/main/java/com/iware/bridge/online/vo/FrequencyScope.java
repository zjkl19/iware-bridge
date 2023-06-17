package com.iware.bridge.online.vo;

/**
 * @author: wjp
 * @date: 2021年10月29日09:23:06
 * @since 1.0
 */
public class FrequencyScope {
    private String name;
    private Double Frequency;
    private Double max;
    private Double min;

    public FrequencyScope(String name, Double frequency, Double max, Double min) {
        this.name = name;
        Frequency = frequency;
        this.max = max;
        this.min = min;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFrequency() {
        return Frequency;
    }

    public void setFrequency(Double frequency) {
        Frequency = frequency;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }
}
