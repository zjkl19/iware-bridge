package com.iware.bridge.evaluation.vo;

import com.iware.bridge.model.entity.evaluation.MonitorPlan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "MonitorPlanVO",description = "检测计划实体类")
public class MonitorPlanVO extends MonitorPlan implements Serializable {

    private static final long serialVersionUID = -1164221111970511032L;

    @ApiModelProperty(value="项目名称")
    private String projectName;
    @ApiModelProperty(value="检测类型名称")
    private String typeName;
    @ApiModelProperty(value="结构物关联")
    private List<MonitorStructureRelVO> structureRelList;
    @ApiModelProperty(value="结构化开始时间")
    private String startDate;
    @ApiModelProperty(value="结构化结束时间")
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<MonitorStructureRelVO> getStructureRelList() {
        return structureRelList;
    }

    public void setStructureRelList(List<MonitorStructureRelVO> structureRelList) {
        this.structureRelList = structureRelList;
    }

    @Override
    public String toString() {
        return "MonitorPlanVO{" +
                "projectName='" + projectName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", structureRelList=" + structureRelList +
                '}';
    }
}
