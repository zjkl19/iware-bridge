package com.iware.bridge.inspection.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("维修费用趋势")
public class MaintainAccountMonthVo {

    @ApiModelProperty("年月")
    private String monthOfYear;

    @ApiModelProperty("对应计划金额")
    private Double budget;

    @ApiModelProperty("对应实际金额")
    private Double expenditure;

    public String getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(String monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(Double expenditure) {
        this.expenditure = expenditure;
    }
}
