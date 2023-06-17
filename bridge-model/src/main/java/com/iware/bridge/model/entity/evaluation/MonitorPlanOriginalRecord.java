package com.iware.bridge.model.entity.evaluation;


import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="MonitorPlanOriginalRecord", description="监测计划原始记录表")
public class MonitorPlanOriginalRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
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

    @ApiModelProperty(value = "创建者id")
    private Integer createUserId;

    @ApiModelProperty(value = "状态")
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

    public String getRecordNumber() {
		return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public Integer getCreateUserId() {
		return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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
        return "MonitorPlanOriginalRecord{" +
            "  id=" + id +
            ", monitorPlanStructureRelId=" + monitorPlanStructureRelId +
            ", instrumentation=" + instrumentation +
            ", projectLocation=" + projectLocation +
            ", testBasis=" + testBasis +
            ", recordNumber=" + recordNumber +
            ", createUserId=" + createUserId +
            ", status=" + status +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
