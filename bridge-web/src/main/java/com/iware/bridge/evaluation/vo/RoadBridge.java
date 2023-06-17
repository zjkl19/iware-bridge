package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "BridgeRoad对象", description = "桥梁线路表")
public class RoadBridge {
    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "线路名称")
    private String name;

    @ApiModelProperty(value = "计划结构物id")
    private Integer monitorPlanStructureRelId;

    @ApiModelProperty(value = "1：梁桥 2：悬臂挂梁桥 3：钢构桥 4：桁架桥 5：钢结构拱桥圬工拱桥 5：钢筋混凝土拱桥圬工拱桥 6：人行天桥（梁桥） 7：人行天桥（钢桁架桥）")
    private Integer bridgeTypeId;

    @ApiModelProperty(value = "桥跨数量")
    private Integer spanNumber;

    @ApiModelProperty(value = "主跨数量")
    private Integer mainSpanNumber;

    @ApiModelProperty(value = "梯道数")
    private Integer stairwayNumber;

    @ApiModelProperty(value = "梯道跨数")
    private Integer stairwaySpanNumber;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "1：正常 0：被回收")
    private Byte status;

    @ApiModelProperty(value = "报告地址")
    private String reportPath;

    @ApiModelProperty(value = "bci评分")
    private float bridgeConditionIndex;


    @ApiModelProperty(value = "检测类型")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getBridgeConditionIndex() {
        return bridgeConditionIndex;
    }

    public void setBridgeConditionIndex(float bridgeConditionIndex) {
        this.bridgeConditionIndex = bridgeConditionIndex;
    }

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }
}
