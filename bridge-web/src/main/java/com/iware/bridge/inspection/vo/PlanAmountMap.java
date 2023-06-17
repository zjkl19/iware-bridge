package com.iware.bridge.inspection.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel(value = "PlanAmountMap",description = "修改计划金额参数体")
public class PlanAmountMap {

    @ApiModelProperty(value="计划id")
    private Integer planId;

    @ApiModelProperty(value="预算金额")
    private BigDecimal budget;

    @ApiModelProperty(value="实际金额")
    private BigDecimal expenditure;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(BigDecimal expenditure) {
        this.expenditure = expenditure;
    }
}
