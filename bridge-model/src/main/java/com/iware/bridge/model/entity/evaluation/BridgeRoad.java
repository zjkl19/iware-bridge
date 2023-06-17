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

@ApiModel(value="BridgeRoad", description="桥梁线路表")
public class BridgeRoad implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "线路名称")
    private String name;

    @ApiModelProperty(value = "计划结构物id")
    private Integer monitorPlanStructureRelId;

    @ApiModelProperty(value = "桥梁类型id")
    private Integer bridgeTypeId;

    @ApiModelProperty(value = "桥跨数量")
    private Integer spanNumber;

    @ApiModelProperty(value = "主跨数量")
    private Integer mainSpanNumber;

    @ApiModelProperty(value = "梯道数")
    private Integer stairwayNumber;

    @ApiModelProperty(value = "梯道跨数")
    private Integer stairwaySpanNumber;

    @ApiModelProperty(value = "报告路径")
    private String reportPath;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "1：正常 0：被回收")
    private Integer status;

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

    public Integer getMonitorPlanStructureRelId() {
		return monitorPlanStructureRelId;
    }

    public void setMonitorPlanStructureRelId(Integer monitorPlanStructureRelId) {
        this.monitorPlanStructureRelId = monitorPlanStructureRelId;
    }

    public Integer getBridgeTypeId() {
		return bridgeTypeId;
    }

    public void setBridgeTypeId(Integer bridgeTypeId) {
        this.bridgeTypeId = bridgeTypeId;
    }

    public Integer getSpanNumber() {
		return spanNumber;
    }

    public void setSpanNumber(Integer spanNumber) {
        this.spanNumber = spanNumber;
    }

    public Integer getMainSpanNumber() {
		return mainSpanNumber;
    }

    public void setMainSpanNumber(Integer mainSpanNumber) {
        this.mainSpanNumber = mainSpanNumber;
    }

    public Integer getStairwayNumber() {
		return stairwayNumber;
    }

    public void setStairwayNumber(Integer stairwayNumber) {
        this.stairwayNumber = stairwayNumber;
    }

    public Integer getStairwaySpanNumber() {
		return stairwaySpanNumber;
    }

    public void setStairwaySpanNumber(Integer stairwaySpanNumber) {
        this.stairwaySpanNumber = stairwaySpanNumber;
    }

    public String getReportPath() {
		return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public String getCreator() {
		return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public Integer getStatus() {
		return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "BridgeRoad{" +
            "  id=" + id +
            ", name=" + name +
            ", monitorPlanStructureRelId=" + monitorPlanStructureRelId +
            ", bridgeTypeId=" + bridgeTypeId +
            ", spanNumber=" + spanNumber +
            ", mainSpanNumber=" + mainSpanNumber +
            ", stairwayNumber=" + stairwayNumber +
            ", stairwaySpanNumber=" + stairwaySpanNumber +
            ", reportPath=" + reportPath +
            ", creator=" + creator +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
            ", status=" + status +
        "}";
    }
}
