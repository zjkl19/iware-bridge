package com.iware.bridge.model.entity.inspection;


import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="PlanInfo", description="巡查维养计划表")
public class PlanInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "计划名称")
    private String name;

    @ApiModelProperty(value = "所属项目")
    private Integer projectId;

    @JSONField(format = "yyyy-MM")
    @ApiModelProperty(value = "巡查/维修月份")
    private Date planTime;

    @ApiModelProperty(value = "预算金额")
    private BigDecimal budget;

    @ApiModelProperty(value = "实际金额")
    private BigDecimal expenditure;

    @ApiModelProperty(value = "1:日常巡查 2:经常检查 3:特殊检查 4:维修养护")
    private Integer type;

    @ApiModelProperty(value = "创建者id")
    private Integer userId;

    @ApiModelProperty(value = "0:未执行 1:执行中 2:已完成 ")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

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

    public Integer getProjectId() {
		return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getPlanTime() {
		return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
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

    public Integer getType() {
		return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserId() {
		return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
		return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
		return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
		return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }


    @Override
    public String toString() {
        return "PlanInfo{" +
            "  id=" + id +
            ", name=" + name +
            ", projectId=" + projectId +
            ", planTime=" + planTime +
            ", budget=" + budget +
            ", expenditure=" + expenditure +
            ", type=" + type +
            ", userId=" + userId +
            ", status=" + status +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
