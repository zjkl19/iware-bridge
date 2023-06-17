package com.iware.bridge.online.vo;

import com.iware.bridge.online.dto.SensorHistoryDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author: wjp
 * @date: 2021年8月11日13:51:03
 * @since 1.0
 */
@ApiModel(value = "SensorHistoryVO", description = "传感器历史值")
public class SensorHistoryVO {

    @ApiModelProperty(value = "传感器id")
    private Integer id;
    @ApiModelProperty(value = "传感器类型id")
    private Integer sensorTypeId;
    @ApiModelProperty(value = "数据集合")
    private List<SensorHistoryDto> list;
    @ApiModelProperty(value = "最大值")
    private Float max;
    @ApiModelProperty(value = "最小值")
    private Float min;
    @ApiModelProperty(value = "平均值")
    private Float avg;
    @ApiModelProperty(value = "方差")
    private Float variance;
    @ApiModelProperty(value = "单位")
    private String unit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSensorTypeId() {
        return sensorTypeId;
    }

    public void setSensorTypeId(Integer sensorTypeId) {
        this.sensorTypeId = sensorTypeId;
    }

    public List<SensorHistoryDto> getList() {
        return list;
    }

    public void setList(List<SensorHistoryDto> list) {
        this.list = list;
    }

    public Float getMax() {
        return max;
    }

    public void setMax(Float max) {
        this.max = max;
    }

    public Float getMin() {
        return min;
    }

    public void setMin(Float min) {
        this.min = min;
    }

    public Float getAvg() {
        return avg;
    }

    public void setAvg(Float avg) {
        this.avg = avg;
    }

    public Float getVariance() {
        return variance;
    }

    public void setVariance(Float variance) {
        this.variance = variance;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
