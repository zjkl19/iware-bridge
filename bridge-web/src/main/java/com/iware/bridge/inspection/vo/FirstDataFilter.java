package com.iware.bridge.inspection.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "FirstDataFilter",description = "查询当月第一次巡查时间条件")
public class FirstDataFilter {
    @ApiModelProperty("结构物id")
    private Integer[] structureId;
    @ApiModelProperty("年月")
    private String month;
    @ApiModelProperty("项目id")
    private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer[] getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer[] structureId) {
        this.structureId = structureId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
