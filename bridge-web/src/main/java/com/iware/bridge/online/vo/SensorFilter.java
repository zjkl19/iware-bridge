package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SensorFilter", description = "传感器列表查询条件")
public class SensorFilter {
    @ApiModelProperty(value = "传感器类型id")
    private Integer sensorTypeId;
    @ApiModelProperty(value = "结构物id")
    private Integer structureId;
    @ApiModelProperty(value = "监测位置id")
    private Integer componentId;

    public Integer getSensorTypeId() {
        return sensorTypeId;
    }

    public void setSensorTypeId(Integer sensorTypeId) {
        this.sensorTypeId = sensorTypeId;
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
}
