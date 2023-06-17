package com.iware.bridge.inspection.vo;

import com.iware.bridge.model.entity.inspection.PlanDetail;
import com.iware.bridge.model.entity.inspection.PlanInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "PlanVO",description = "计划信息")
public class PlanVO extends PlanInfo implements Serializable {

    private static final long serialVersionUID = -1264996100970511032L;

    @ApiModelProperty(value = "创建者")
    private String realName;

    @ApiModelProperty(value = "巡查次数/维修项目个数")
    private Integer count;

    @ApiModelProperty(value = "巡查计划详情信息")
    private List<PlanDetail> planDetailList;

    @ApiModelProperty(value = "维修计划项信息")
    private List<MaintainItemVO> itemList;

    @ApiModelProperty(value = "项目名称")
    private String projectName;


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<MaintainItemVO> getItemList() {
        return itemList;
    }

    public void setItemList(List<MaintainItemVO> itemList) {
        this.itemList = itemList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<PlanDetail> getPlanDetailList() {
        return planDetailList;
    }

    public void setPlanDetailList(List<PlanDetail> planDetailList) {
        this.planDetailList = planDetailList;
    }
}
