package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ComponentDiseaseTypeNumber", description = "构件病害类型数量")
public class ComponentDiseaseTypeNumber {

    @ApiModelProperty(value = "病害名称")
    private String name;
    @ApiModelProperty(value = "病害数量")
    private String count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
