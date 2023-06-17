package com.iware.bridge.inspection.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("构件病害保修情况")
public class DiseaseRepairVo {

    @ApiModelProperty("病害类型名")
    private String part;

    @ApiModelProperty("报修情况")
    private Integer strategy;

    @ApiModelProperty("对应数量")
    private Integer count;

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public Integer getStrategy() {
        return strategy;
    }

    public void setStrategy(Integer strategy) {
        this.strategy = strategy;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
