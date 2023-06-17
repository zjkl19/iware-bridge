package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年10月22日10:46:31
 * @since 1.0
 */
public class SensorWeightWeight {
    @ApiModelProperty(value = "统计时间")
    private String time;
    @ApiModelProperty(value = "结构物id")
    private Integer structureId;
    @ApiModelProperty(value = "测点id")
    private Integer meansPointId;
    @ApiModelProperty(value = "车道号")
    private String name;
    @ApiModelProperty(value = "最大车重")
    private Float maxWeight;
    @ApiModelProperty(value = "车重0-10数量")
    private Integer tenWeight;
    @ApiModelProperty(value = "车重10-30数量")
    private Integer tenThirtyWeight;
    @ApiModelProperty(value = "车重30-50数量")
    private Integer thirtyFiftyWeight;
    @ApiModelProperty(value = "车重超过50数量")
    private Integer fiftyWeight;
    @ApiModelProperty(value = "车重超过60数量")
    private Integer sixtyWeight;
    @ApiModelProperty(value = "车重超过70数量")
    private Integer seventyWeight;
    @ApiModelProperty(value = "车重超过80数量")
    private Integer eightyWeight;
    @ApiModelProperty(value = "车重超过90数量")
    private Integer ninetyWeight;
    @ApiModelProperty(value = "车重超过100数量")
    private Integer hundredWeight;
    @ApiModelProperty(value = "二轴超重车数量")
    private Integer twoModelWeight;
    @ApiModelProperty(value = "三轴超重车数量")
    private Integer threeModelWeight;
    @ApiModelProperty(value = "四轴超重车数量")
    private Integer fourModelWeight;
    @ApiModelProperty(value = "五轴超重车数量")
    private Integer fiveModelWeight;
    @ApiModelProperty(value = "六轴超重车数量")
    private Integer sixModelWeight;
    @ApiModelProperty(value = "六轴以上超重车数量")
    private Integer sixUpModelWeight;
    @ApiModelProperty(value = "二轴未超重车数量")
    private Integer twoModelWeightNot;
    @ApiModelProperty(value = "三轴未超重车数量")
    private Integer threeModelWeightNot;
    @ApiModelProperty(value = "四轴未超重车数量")
    private Integer fourModelWeightNot;
    @ApiModelProperty(value = "五轴未超重车数量")
    private Integer fiveModelWeightNot;
    @ApiModelProperty(value = "六轴未超重车数量")
    private Integer sixModelWeightNot;
    @ApiModelProperty(value = "六轴以上未超重车数量")
    private Integer sixUpModelWeightNot;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Float maxWeight) {
        this.maxWeight = maxWeight;
    }

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

    public Integer getTenWeight() {
        return tenWeight;
    }

    public void setTenWeight(Integer tenWeight) {
        this.tenWeight = tenWeight;
    }

    public Integer getTenThirtyWeight() {
        return tenThirtyWeight;
    }

    public void setTenThirtyWeight(Integer tenThirtyWeight) {
        this.tenThirtyWeight = tenThirtyWeight;
    }

    public Integer getThirtyFiftyWeight() {
        return thirtyFiftyWeight;
    }

    public void setThirtyFiftyWeight(Integer thirtyFiftyWeight) {
        this.thirtyFiftyWeight = thirtyFiftyWeight;
    }

    public Integer getFiftyWeight() {
        return fiftyWeight;
    }

    public void setFiftyWeight(Integer fiftyWeight) {
        this.fiftyWeight = fiftyWeight;
    }

    public Integer getSixtyWeight() {
        return sixtyWeight;
    }

    public void setSixtyWeight(Integer sixtyWeight) {
        this.sixtyWeight = sixtyWeight;
    }

    public Integer getSeventyWeight() {
        return seventyWeight;
    }

    public void setSeventyWeight(Integer seventyWeight) {
        this.seventyWeight = seventyWeight;
    }

    public Integer getEightyWeight() {
        return eightyWeight;
    }

    public void setEightyWeight(Integer eightyWeight) {
        this.eightyWeight = eightyWeight;
    }

    public Integer getNinetyWeight() {
        return ninetyWeight;
    }

    public void setNinetyWeight(Integer ninetyWeight) {
        this.ninetyWeight = ninetyWeight;
    }

    public Integer getHundredWeight() {
        return hundredWeight;
    }

    public void setHundredWeight(Integer hundredWeight) {
        this.hundredWeight = hundredWeight;
    }

    public Integer getTwoModelWeight() {
        return twoModelWeight;
    }

    public void setTwoModelWeight(Integer twoModelWeight) {
        this.twoModelWeight = twoModelWeight;
    }

    public Integer getThreeModelWeight() {
        return threeModelWeight;
    }

    public void setThreeModelWeight(Integer threeModelWeight) {
        this.threeModelWeight = threeModelWeight;
    }

    public Integer getFourModelWeight() {
        return fourModelWeight;
    }

    public void setFourModelWeight(Integer fourModelWeight) {
        this.fourModelWeight = fourModelWeight;
    }

    public Integer getFiveModelWeight() {
        return fiveModelWeight;
    }

    public void setFiveModelWeight(Integer fiveModelWeight) {
        this.fiveModelWeight = fiveModelWeight;
    }

    public Integer getSixModelWeight() {
        return sixModelWeight;
    }

    public void setSixModelWeight(Integer sixModelWeight) {
        this.sixModelWeight = sixModelWeight;
    }

    public Integer getSixUpModelWeight() {
        return sixUpModelWeight;
    }

    public void setSixUpModelWeight(Integer sixUpModelWeight) {
        this.sixUpModelWeight = sixUpModelWeight;
    }

    public Integer getTwoModelWeightNot() {
        return twoModelWeightNot;
    }

    public void setTwoModelWeightNot(Integer twoModelWeightNot) {
        this.twoModelWeightNot = twoModelWeightNot;
    }

    public Integer getThreeModelWeightNot() {
        return threeModelWeightNot;
    }

    public void setThreeModelWeightNot(Integer threeModelWeightNot) {
        this.threeModelWeightNot = threeModelWeightNot;
    }

    public Integer getFourModelWeightNot() {
        return fourModelWeightNot;
    }

    public void setFourModelWeightNot(Integer fourModelWeightNot) {
        this.fourModelWeightNot = fourModelWeightNot;
    }

    public Integer getFiveModelWeightNot() {
        return fiveModelWeightNot;
    }

    public void setFiveModelWeightNot(Integer fiveModelWeightNot) {
        this.fiveModelWeightNot = fiveModelWeightNot;
    }

    public Integer getSixModelWeightNot() {
        return sixModelWeightNot;
    }

    public void setSixModelWeightNot(Integer sixModelWeightNot) {
        this.sixModelWeightNot = sixModelWeightNot;
    }

    public Integer getSixUpModelWeightNot() {
        return sixUpModelWeightNot;
    }

    public void setSixUpModelWeightNot(Integer sixUpModelWeightNot) {
        this.sixUpModelWeightNot = sixUpModelWeightNot;
    }
}
