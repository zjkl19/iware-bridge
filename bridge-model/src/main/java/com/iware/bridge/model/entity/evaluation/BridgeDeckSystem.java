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

@ApiModel(value="BridgeDeckSystem", description="桥面系信息表")
public class BridgeDeckSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "桥跨id")
    private Integer bridgeSpanId;

    @ApiModelProperty(value = "桥梁走向 ：东 南 西 北 东南 西南 东北 西北")
    private String direction;

    @ApiModelProperty(value = "倾斜角度")
    private Double tiltAngle;

    @ApiModelProperty(value = "有无人行道")
    private Integer sidewalk;

    @ApiModelProperty(value = "人行道宽")
    private Double sidewalkWidth;

    @ApiModelProperty(value = "人行道宽(左)")
    private Double leftSidewalkWidth;

    @ApiModelProperty(value = "人行道宽(右)")
    private Double rightSidewalkWidth;

    @ApiModelProperty(value = "行车道宽")
    private Double laneWidth;

    @ApiModelProperty(value = "中间隔离栏宽度")
    private Double midleBarrierWidth;

    @ApiModelProperty(value = "护栏(左)")
    private Double leftRailWidth;

    @ApiModelProperty(value = "护栏(右)")
    private Double rightRailWidth;

    @ApiModelProperty(value = "梯道")
    private String stairway;

    @ApiModelProperty(value = "梯道长(投影)")
    private Double stairwayShadowLength;

    @ApiModelProperty(value = "梯道长")
    private Double stairwayLength;

    @ApiModelProperty(value = "梯道宽")
    private Double stairwayWidth;

    @ApiModelProperty(value = "梯台数")
    private Integer platformNumber;

    @ApiModelProperty(value = "0：无 1：有")
    private Integer expansionJoint;

    @ApiModelProperty(value = "桥头搭板长")
    private Double bridgeHeadBoardLength;

    @ApiModelProperty(value = "模型地址")
    private String monitorDiagram;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "1：正常 0：回收")
    private Integer status;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBridgeSpanId() {
		return bridgeSpanId;
    }

    public void setBridgeSpanId(Integer bridgeSpanId) {
        this.bridgeSpanId = bridgeSpanId;
    }

    public String getDirection() {
		return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Double getTiltAngle() {
		return tiltAngle;
    }

    public void setTiltAngle(Double tiltAngle) {
        this.tiltAngle = tiltAngle;
    }

    public Integer getSidewalk() {
		return sidewalk;
    }

    public void setSidewalk(Integer sidewalk) {
        this.sidewalk = sidewalk;
    }

    public Double getSidewalkWidth() {
		return sidewalkWidth;
    }

    public void setSidewalkWidth(Double sidewalkWidth) {
        this.sidewalkWidth = sidewalkWidth;
    }

    public Double getLeftSidewalkWidth() {
		return leftSidewalkWidth;
    }

    public void setLeftSidewalkWidth(Double leftSidewalkWidth) {
        this.leftSidewalkWidth = leftSidewalkWidth;
    }

    public Double getRightSidewalkWidth() {
		return rightSidewalkWidth;
    }

    public void setRightSidewalkWidth(Double rightSidewalkWidth) {
        this.rightSidewalkWidth = rightSidewalkWidth;
    }

    public Double getLaneWidth() {
		return laneWidth;
    }

    public void setLaneWidth(Double laneWidth) {
        this.laneWidth = laneWidth;
    }

    public Double getMidleBarrierWidth() {
		return midleBarrierWidth;
    }

    public void setMidleBarrierWidth(Double midleBarrierWidth) {
        this.midleBarrierWidth = midleBarrierWidth;
    }

    public Double getLeftRailWidth() {
		return leftRailWidth;
    }

    public void setLeftRailWidth(Double leftRailWidth) {
        this.leftRailWidth = leftRailWidth;
    }

    public Double getRightRailWidth() {
		return rightRailWidth;
    }

    public void setRightRailWidth(Double rightRailWidth) {
        this.rightRailWidth = rightRailWidth;
    }

    public String getStairway() {
		return stairway;
    }

    public void setStairway(String stairway) {
        this.stairway = stairway;
    }

    public Double getStairwayShadowLength() {
		return stairwayShadowLength;
    }

    public void setStairwayShadowLength(Double stairwayShadowLength) {
        this.stairwayShadowLength = stairwayShadowLength;
    }

    public Double getStairwayLength() {
		return stairwayLength;
    }

    public void setStairwayLength(Double stairwayLength) {
        this.stairwayLength = stairwayLength;
    }

    public Double getStairwayWidth() {
		return stairwayWidth;
    }

    public void setStairwayWidth(Double stairwayWidth) {
        this.stairwayWidth = stairwayWidth;
    }

    public Integer getPlatformNumber() {
		return platformNumber;
    }

    public void setPlatformNumber(Integer platformNumber) {
        this.platformNumber = platformNumber;
    }

    public Integer getExpansionJoint() {
		return expansionJoint;
    }

    public void setExpansionJoint(Integer expansionJoint) {
        this.expansionJoint = expansionJoint;
    }

    public Double getBridgeHeadBoardLength() {
		return bridgeHeadBoardLength;
    }

    public void setBridgeHeadBoardLength(Double bridgeHeadBoardLength) {
        this.bridgeHeadBoardLength = bridgeHeadBoardLength;
    }

    public String getMonitorDiagram() {
		return monitorDiagram;
    }

    public void setMonitorDiagram(String monitorDiagram) {
        this.monitorDiagram = monitorDiagram;
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

    public String getCreator() {
		return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getStatus() {
		return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "BridgeDeckSystem{" +
            "  id=" + id +
            ", bridgeSpanId=" + bridgeSpanId +
            ", direction=" + direction +
            ", tiltAngle=" + tiltAngle +
            ", sidewalk=" + sidewalk +
            ", sidewalkWidth=" + sidewalkWidth +
            ", leftSidewalkWidth=" + leftSidewalkWidth +
            ", rightSidewalkWidth=" + rightSidewalkWidth +
            ", laneWidth=" + laneWidth +
            ", midleBarrierWidth=" + midleBarrierWidth +
            ", leftRailWidth=" + leftRailWidth +
            ", rightRailWidth=" + rightRailWidth +
            ", stairway=" + stairway +
            ", stairwayShadowLength=" + stairwayShadowLength +
            ", stairwayLength=" + stairwayLength +
            ", stairwayWidth=" + stairwayWidth +
            ", platformNumber=" + platformNumber +
            ", expansionJoint=" + expansionJoint +
            ", bridgeHeadBoardLength=" + bridgeHeadBoardLength +
            ", monitorDiagram=" + monitorDiagram +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
            ", creator=" + creator +
            ", status=" + status +
        "}";
    }
}
