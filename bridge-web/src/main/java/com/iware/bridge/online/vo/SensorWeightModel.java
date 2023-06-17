package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年10月22日10:45:15
 * @since 1.0
 */
@ApiModel(value="SensorWeightModel", description="称重传感器每小时统计车轴数量")
public class SensorWeightModel {
    @ApiModelProperty(value = "统计时间")
    private String time;
    @ApiModelProperty(value = "结构物id")
    private Integer structureId;
    @ApiModelProperty(value = "测点id")
    private Integer meansPointId;
    @ApiModelProperty(value = "二轴车数量")
    private Integer twoModel;
    @ApiModelProperty(value = "三轴车数量")
    private Integer threeModel;
    @ApiModelProperty(value = "四轴车数量")
    private Integer fourModel;
    @ApiModelProperty(value = "五轴车数量")
    private Integer fiveModel;
    @ApiModelProperty(value = "六轴车数量")
    private Integer sixModel;
    @ApiModelProperty(value = "六轴以上车数量")
    private Integer sixUpModel;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public Integer getMeansPointId() {
        return meansPointId;
    }

    public void setMeansPointId(Integer meansPointId) {
        this.meansPointId = meansPointId;
    }

    public Integer getTwoModel() {
        return twoModel;
    }

    public void setTwoModel(Integer twoModel) {
        this.twoModel = twoModel;
    }

    public Integer getThreeModel() {
        return threeModel;
    }

    public void setThreeModel(Integer threeModel) {
        this.threeModel = threeModel;
    }

    public Integer getFourModel() {
        return fourModel;
    }

    public void setFourModel(Integer fourModel) {
        this.fourModel = fourModel;
    }

    public Integer getFiveModel() {
        return fiveModel;
    }

    public void setFiveModel(Integer fiveModel) {
        this.fiveModel = fiveModel;
    }

    public Integer getSixModel() {
        return sixModel;
    }

    public void setSixModel(Integer sixModel) {
        this.sixModel = sixModel;
    }

    public Integer getSixUpModel() {
        return sixUpModel;
    }

    public void setSixUpModel(Integer sixUpModel) {
        this.sixUpModel = sixUpModel;
    }
}
