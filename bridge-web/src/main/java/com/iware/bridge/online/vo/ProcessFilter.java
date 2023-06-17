package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "ProcessFilter",description = "处理措施")
public class ProcessFilter implements Serializable {

    private static final long serialVersionUID = -1177958100970511032L;

    @ApiModelProperty(value="预警id")
    private Integer warningRecordId;
    @ApiModelProperty(value="结构物id")
    private Integer structureId;
    @ApiModelProperty(value="预警列表")
    private List<Integer> levelList;
    @ApiModelProperty(value = "预警等级")
    private Integer warningLevel;
    @ApiModelProperty(value="处理措施")
    private String measures;
    @ApiModelProperty(value = "处理人id")
    private Integer handlerUserId;
    @ApiModelProperty(value = "处理时间")
    private String handlerTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getHandlerUserId() {
        return handlerUserId;
    }

    public void setHandlerUserId(Integer handlerUserId) {
        this.handlerUserId = handlerUserId;
    }

    public String getHandlerTime() {
        return handlerTime;
    }

    public void setHandlerTime(String handlerTime) {
        this.handlerTime = handlerTime;
    }

    public Integer getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(Integer warningLevel) {
        this.warningLevel = warningLevel;
    }

    public Integer getWarningRecordId() {
        return warningRecordId;
    }

    public void setWarningRecordId(Integer warningRecordId) {
        this.warningRecordId = warningRecordId;
    }

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public List<Integer> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<Integer> levelList) {
        this.levelList = levelList;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }
}
