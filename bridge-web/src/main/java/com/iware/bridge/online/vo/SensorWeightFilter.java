package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年8月19日14:31:12
 * @since 1.0
 */
@ApiModel(value="SensorWeightFilter", description="统计数据条件过滤")
public class SensorWeightFilter {

    @ApiModelProperty(value = "结构物id")
    private Integer structureId;

    @ApiModelProperty(value = "测点id")
    private Integer carNo;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "超过多少重量,超和t中间的数字(重车统计)")
    private Integer weight;

    @ApiModelProperty(value = "车轴id（超重车统计）")
    private Integer axleId;

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getAxleId() {
        return axleId;
    }

    public void setAxleId(Integer axleId) {
        this.axleId = axleId;
    }

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public Integer getCarNo() {
        return carNo;
    }

    public void setCarNo(Integer carNo) {
        this.carNo = carNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "SensorWeightFilter{" +
                "structureId=" + structureId +
                ", carNo='" + carNo + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", weight=" + weight +
                ", axleId=" + axleId +
                '}';
    }
}
