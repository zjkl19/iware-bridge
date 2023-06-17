package com.iware.bridge.online.vo;

import com.iware.bridge.model.entity.online.MeansPoint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="MeansPointStatusVO", description="传感器状态信息")
public class MeansPointStatusVO extends MeansPoint implements Serializable {

    private static final long serialVersionUID = 1227966100970511032L;

    @ApiModelProperty(value = "结构物名称")
    private String structureName;
    @ApiModelProperty(value = "传感器类型")
    private String sensorTypeName;
    @ApiModelProperty(value = "最新状态")
    private Integer currentStatus;

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getSensorTypeName() {
        return sensorTypeName;
    }

    public void setSensorTypeName(String sensorTypeName) {
        this.sensorTypeName = sensorTypeName;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }
}
