package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "OriginalRecord", description = "原始记录")
public class OriginalRecord {

    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "监测计划结构物关联id")
    private Integer monitorPlanStructureRelId;
    @ApiModelProperty(value = "检测仪器")
    private String instrumentation;
    @ApiModelProperty(value = "工程地点")
    private String projectLocation;
    @ApiModelProperty(value = "检测依据")
    private String testBasis;
    @ApiModelProperty(value = "记录编号")
    private String recordNumber;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "结构物名称")
    private String structureName;

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMonitorPlanStructureRelId() {
        return monitorPlanStructureRelId;
    }

    public void setMonitorPlanStructureRelId(Integer monitorPlanStructureRelId) {
        this.monitorPlanStructureRelId = monitorPlanStructureRelId;
    }

    public String getInstrumentation() {
        return instrumentation;
    }

    public void setInstrumentation(String instrumentation) {
        this.instrumentation = instrumentation;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
    }

    public String getTestBasis() {
        return testBasis;
    }

    public void setTestBasis(String testBasis) {
        this.testBasis = testBasis;
    }

    @Override
    public String toString() {
        return "OriginalRecord{" +
                "id=" + id +
                ", monitorPlanStructureRelId=" + monitorPlanStructureRelId +
                ", instrumentation='" + instrumentation + '\'' +
                ", projectLocation='" + projectLocation + '\'' +
                ", testBasis='" + testBasis + '\'' +
                '}';
    }
}
