package com.iware.bridge.inspection.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("巡查病害数变化")
public class InspectionDiseaseDateVo {

    @ApiModelProperty("巡查病害数横轴天")
    private String dayOfMonth;

    @ApiModelProperty("巡查病害数纵轴数量")
    private Integer count;

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
