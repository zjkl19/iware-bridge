package com.iware.bridge.online.vo;

import com.iware.bridge.model.entity.online.MeansPoint;
import com.iware.bridge.model.entity.online.Sensor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value="MeansPointBO", description="测点信息")
public class MeansPointBO extends MeansPoint implements Serializable {

    private static final long serialVersionUID = 1177966100970511032L;

    @ApiModelProperty(value = "传感器列表")
    private List<Sensor> sensorList;

    public List<Sensor> getSensorList() {
        return sensorList;
    }

    public void setSensorList(List<Sensor> sensorList) {
        this.sensorList = sensorList;
    }
}
