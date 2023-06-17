package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: HX
 * @date: 2021-6-28
 * @since 1.0
 */
@ApiModel(value = "BridgeSurveyFirstTierVO", description = "线路信息")
@Data
public class BridgeSurveyFirstTierVO implements Serializable {
    private static final long serialVersionUID = -7159156970310499052L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "计划/结构物关联ID")
    private Integer monitorPlanStructureRelId;

    @ApiModelProperty(value = "1：梁桥 2：悬臂挂梁桥 3：钢构桥 4：桁架桥 5：钢结构拱桥圬工拱桥  6：人行天桥（梁桥） 7：人行天桥（钢桁架桥）")
    private Integer bridgeTypeId;

    @ApiModelProperty(value = "报告地址")
    private String reportPath;

    @ApiModelProperty(value = "桥跨数量")
    private Integer spanNumber;

    @ApiModelProperty(value = "桥跨总数量")
    private Integer mainSpanNumber;

    @ApiModelProperty(value = "梯道数")
    private Integer stairwayNumber;

    @ApiModelProperty(value = "梯道跨数")
    private Integer stairwaySpanNumber;

    @ApiModelProperty(value = "二级树菜单")
    private List<BridgeSurveySecondTierVO> children;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBridgeTypeId() {
        return bridgeTypeId;
    }

    public void setBridgeTypeId(Integer bridgeTypeId) {
        this.bridgeTypeId = bridgeTypeId;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
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

    public List<BridgeSurveySecondTierVO> getChildren() {
        return children;
    }

    public void setChildren(List<BridgeSurveySecondTierVO> children) {
        this.children = children;
    }
}
