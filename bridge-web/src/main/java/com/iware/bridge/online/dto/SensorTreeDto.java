package com.iware.bridge.online.dto;

import com.iware.bridge.online.vo.MeansPointVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "SensorTreeDto", description = "传感器树形结构（按位置/按类型）")
public class SensorTreeDto {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "类型名字/位置名字")
    private String name;

    @ApiModelProperty(value = "传感器集合")
    private List<MeansPointVO> sensorVOList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MeansPointVO> getSensorVOList() {
        return sensorVOList;
    }

    public void setSensorVOList(List<MeansPointVO> sensorVOList) {
        this.sensorVOList = sensorVOList;
    }
}
