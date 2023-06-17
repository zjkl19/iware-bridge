package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value="MonitorRecord",description="检测记录")
public class MonitorRecord implements Serializable{

	private static final long serialVersionUID = 2153095425411779724L;

	@ApiModelProperty(value="计划结构物关联表id")
	private Integer monitorStructureId;
	@ApiModelProperty(value="检测计划id")
	private Integer monitorPlanId;
	@ApiModelProperty(value="检测类型")
	private Integer type;
	@ApiModelProperty(value="检测类型名称")
	private String typeName;
	@ApiModelProperty(value="结束时间")
	private Date endTime;
	@ApiModelProperty(value="评定等级")
	private String ratingLevel;
	@ApiModelProperty(value="BCI分数")
	private Double bridgeConditionIndex;
	@ApiModelProperty(value="BSI分数")
	private Double bridgeStructureIndex;
	@ApiModelProperty(value="结构物id")
	private Integer structureInfoId;
	@ApiModelProperty(value="结构物名称")
	private String structureName;
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	public Integer getMonitorStructureId() {
		return monitorStructureId;
	}

	public void setMonitorStructureId(Integer monitorStructureId) {
		this.monitorStructureId = monitorStructureId;
	}

	public Integer getMonitorPlanId() {
		return monitorPlanId;
	}

	public void setMonitorPlanId(Integer monitorPlanId) {
		this.monitorPlanId = monitorPlanId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRatingLevel() {
		return ratingLevel;
	}

	public void setRatingLevel(String ratingLevel) {
		this.ratingLevel = ratingLevel;
	}

	public Double getBridgeConditionIndex() {
		return bridgeConditionIndex;
	}

	public void setBridgeConditionIndex(Double bridgeConditionIndex) {
		this.bridgeConditionIndex = bridgeConditionIndex;
	}

	public Double getBridgeStructureIndex() {
		return bridgeStructureIndex;
	}

	public void setBridgeStructureIndex(Double bridgeStructureIndex) {
		this.bridgeStructureIndex = bridgeStructureIndex;
	}

	public Integer getStructureInfoId() {
		return structureInfoId;
	}

	public void setStructureInfoId(Integer structureInfoId) {
		this.structureInfoId = structureInfoId;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
}
