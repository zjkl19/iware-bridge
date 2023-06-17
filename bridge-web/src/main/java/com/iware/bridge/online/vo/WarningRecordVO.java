package com.iware.bridge.online.vo;

import com.iware.bridge.model.entity.online.WarningRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "WarningRecordVO", description = "预警记录信息")
public class WarningRecordVO extends WarningRecord implements Serializable {

    private static final long serialVersionUID = -117958100970511032L;

    @ApiModelProperty(value = "结构物名称")
    private String structureName;
    @ApiModelProperty(value = "测点编号")
    private String sensorName;
    @ApiModelProperty(value = "传感器类型名称")
    private String sensorTypeName;
    @ApiModelProperty(value = "监测位置")
    private String componentName;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "一级预警上限")
    private float firstWarningUpper;
    @ApiModelProperty(value = "二级预警上限")
    private float secondWarningUpper;
    @ApiModelProperty(value = "三级预警上限")
    private float thirdWarningUpper;
    @ApiModelProperty(value = "一级预警下限")
    private float firstWarningLower;
    @ApiModelProperty(value = "二级预警下限")
    private float secondWarningLower;
    @ApiModelProperty(value = "三级预警下限")
    private float thirdWarningLower;
    @ApiModelProperty(value = "预警间隔")
    private float warningInterval;
    @ApiModelProperty(value = "处理人姓名")
    private String username;
    @ApiModelProperty(value = "处理时间")
    private Date handlerTime;

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    @Override
    public String getSensorName() {
        return sensorName;
    }

    @Override
    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorTypeName() {
        return sensorTypeName;
    }

    public void setSensorTypeName(String sensorTypeName) {
        this.sensorTypeName = sensorTypeName;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getFirstWarningUpper() {
        return firstWarningUpper;
    }

    public void setFirstWarningUpper(float firstWarningUpper) {
        this.firstWarningUpper = firstWarningUpper;
    }

    public float getSecondWarningUpper() {
        return secondWarningUpper;
    }

    public void setSecondWarningUpper(float secondWarningUpper) {
        this.secondWarningUpper = secondWarningUpper;
    }

    public float getThirdWarningUpper() {
        return thirdWarningUpper;
    }

    public void setThirdWarningUpper(float thirdWarningUpper) {
        this.thirdWarningUpper = thirdWarningUpper;
    }

    public float getFirstWarningLower() {
        return firstWarningLower;
    }

    public void setFirstWarningLower(float firstWarningLower) {
        this.firstWarningLower = firstWarningLower;
    }

    public float getSecondWarningLower() {
        return secondWarningLower;
    }

    public void setSecondWarningLower(float secondWarningLower) {
        this.secondWarningLower = secondWarningLower;
    }

    public float getThirdWarningLower() {
        return thirdWarningLower;
    }

    public void setThirdWarningLower(float thirdWarningLower) {
        this.thirdWarningLower = thirdWarningLower;
    }

    public float getWarningInterval() {
        return warningInterval;
    }

    public void setWarningInterval(float warningInterval) {
        this.warningInterval = warningInterval;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Date getHandlerTime() {
        return handlerTime;
    }

    @Override
    public void setHandlerTime(Date handlerTime) {
        this.handlerTime = handlerTime;
    }
}
