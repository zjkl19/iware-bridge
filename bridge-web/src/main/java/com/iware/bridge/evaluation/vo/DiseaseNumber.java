package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "DiseaseNumber", description = "病害数量")
public class DiseaseNumber {
    @ApiModelProperty(value = "构件名称：上部结构、下部结构、桥面系")
    private String name;
    @ApiModelProperty(value = "病害数量")
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
