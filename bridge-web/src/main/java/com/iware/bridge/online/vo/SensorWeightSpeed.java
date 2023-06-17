package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年10月22日10:46:15
 * @since 1.0
 */
@ApiModel(value="SensorWeightModel", description="称重传感器每小时统计车速数据")
public class SensorWeightSpeed {
    @ApiModelProperty(value = "统计时间")
    private String time;
    @ApiModelProperty(value = "结构物id")
    private Integer structureId;
    @ApiModelProperty(value = "测点id")
    private Integer meansPointId;
    @ApiModelProperty(value = "车速小于20数量")
    private Integer twentySpeed;
    @ApiModelProperty(value = "车速20-40数量")
    private Integer twentyFortySpeed;
    @ApiModelProperty(value = "车速40-60数量")
    private Integer fortySixtySpeed;
    @ApiModelProperty(value = "车速60-80数量")
    private Integer sixtyEightySpeed;
    @ApiModelProperty(value = "车速80-100数量")
    private Integer eightyHundredSpeed;
    @ApiModelProperty(value = "车速大于100数量")
    private Integer hundredSpeed;


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

    public Integer getTwentySpeed() {
        return twentySpeed;
    }

    public void setTwentySpeed(Integer twentySpeed) {
        this.twentySpeed = twentySpeed;
    }

    public Integer getTwentyFortySpeed() {
        return twentyFortySpeed;
    }

    public void setTwentyFortySpeed(Integer twentyFortySpeed) {
        this.twentyFortySpeed = twentyFortySpeed;
    }

    public Integer getFortySixtySpeed() {
        return fortySixtySpeed;
    }

    public void setFortySixtySpeed(Integer fortySixtySpeed) {
        this.fortySixtySpeed = fortySixtySpeed;
    }

    public Integer getSixtyEightySpeed() {
        return sixtyEightySpeed;
    }

    public void setSixtyEightySpeed(Integer sixtyEightySpeed) {
        this.sixtyEightySpeed = sixtyEightySpeed;
    }

    public Integer getEightyHundredSpeed() {
        return eightyHundredSpeed;
    }

    public void setEightyHundredSpeed(Integer eightyHundredSpeed) {
        this.eightyHundredSpeed = eightyHundredSpeed;
    }

    public Integer getHundredSpeed() {
        return hundredSpeed;
    }

    public void setHundredSpeed(Integer hundredSpeed) {
        this.hundredSpeed = hundredSpeed;
    }
}
