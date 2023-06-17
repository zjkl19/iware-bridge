package com.iware.bridge.online.vo;

import com.iware.bridge.model.entity.online.Sensor;

/**
 * @author: wjp
 * @date: 2021年10月22日11:58:53
 * @since 1.0
 */
public class SensorMeansVO extends Sensor {

    private String name;

    private Integer structureId;

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
