package com.iware.bridge.model.entity.online;


import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="WarningRecord", description="预警记录表")
public class WarningRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "结构物id")
    private Integer structureId;

    @ApiModelProperty(value = "构件id")
    private Integer componentId;

    @ApiModelProperty(value = "预警时间")
    private Date warningTime;

    @ApiModelProperty(value = "1: I级 2:II级 3:III级")
    private Integer level;

    @ApiModelProperty(value = "处理人id")
    private Integer handlerUserId;

    @ApiModelProperty(value = "处理措施")
    private String measures;

    @ApiModelProperty(value = "处理时间")
    private Date handlerTime;

    @ApiModelProperty(value = "0: 否 1:是")
    private Integer sendMessage;

    @ApiModelProperty(value = "传感器id")
    private Integer sensorId;

    @ApiModelProperty(value = "传感器细项id")
    private Integer sensorDetailsId;

    @ApiModelProperty(value = "传感器编码")
    private String sensorCoding;

    @ApiModelProperty(value = "传感器名称")
    private String sensorName;

    @ApiModelProperty(value = "测值")
    private Float value;

    @ApiModelProperty(value = "传感器机器码")
    private Integer machineId;

    @ApiModelProperty(value = "0:未处理 1:已处理")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public Date getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(Date warningTime) {
        this.warningTime = warningTime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getHandlerUserId() {
        return handlerUserId;
    }

    public void setHandlerUserId(Integer handlerUserId) {
        this.handlerUserId = handlerUserId;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public Date getHandlerTime() {
        return handlerTime;
    }

    public void setHandlerTime(Date handlerTime) {
        this.handlerTime = handlerTime;
    }

    public Integer getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(Integer sendMessage) {
        this.sendMessage = sendMessage;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public Integer getSensorDetailsId() {
        return sensorDetailsId;
    }

    public void setSensorDetailsId(Integer sensorDetailsId) {
        this.sensorDetailsId = sensorDetailsId;
    }

    public String getSensorCoding() {
        return sensorCoding;
    }

    public void setSensorCoding(String sensorCoding) {
        this.sensorCoding = sensorCoding;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }


    @Override
    public String toString() {
        return "WarningRecord{" +
                "  id=" + id +
                ", structureId=" + structureId +
                ", componentId=" + componentId +
                ", warningTime=" + warningTime +
                ", level=" + level +
                ", handlerUserId=" + handlerUserId +
                ", measures=" + measures +
                ", handlerTime=" + handlerTime +
                ", sendMessage=" + sendMessage +
                ", sensorId=" + sensorId +
                ", sensorDetailsId=" + sensorDetailsId +
                ", sensorCoding=" + sensorCoding +
                ", sensorName=" + sensorName +
                ", value=" + value +
                ", machineId=" + machineId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                "}";
    }
}
