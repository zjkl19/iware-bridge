package com.iware.bridge.inspection.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "重复维修项占比及周期分析")
public class MaintainMethodCycleVo {
    @ApiModelProperty(value = "病害部位名")
    private String method;

    @ApiModelProperty(value = "对应占比,单位%")
    private Integer rate;

    @ApiModelProperty(value = "周期")
    private Integer cycle;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }
}
