package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ComponentDiseaseNumber", description = "构件病害数量")
public class ComponentDiseaseNumber {

    @ApiModelProperty(value = "构件名称：主梁、人行道等")
    private String name;
    @ApiModelProperty(value = "病害数量")
    private int count;
    @ApiModelProperty(value = "构件所属：桥面系、上部结构、下部结构")
    private String typeName;
    @ApiModelProperty(value = "构件所属Id：1：桥面系:2：上部结构:3：下部结构")
    private String typeId;
    @ApiModelProperty(value = "扣分病害数量")
    private int points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
