package com.iware.bridge.inspection.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "病害占比")
public class DiseaseRatioVo {
    @ApiModelProperty(value = "病害部位名")
    private String diseasePart;
    @ApiModelProperty(value = "对应数量")
    private Integer count;

    @ApiModelProperty(value = "对应占比,单位%")
    private Integer rate;

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getDiseasePart() {
        return diseasePart;
    }

    public void setDiseasePart(String diseasePart) {
        this.diseasePart = diseasePart;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
