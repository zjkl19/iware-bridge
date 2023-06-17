package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author: CWY
 * @Date: 2021/5/11 11:59
 */
@ApiModel(value = "ArtifactsVO", description = "构件")
public class ArtifactsDTO implements Serializable {

    @ApiModelProperty(value = "构件实例Id")
    private Long artifactsId;

    @ApiModelProperty(value = "构件名称")
    private String artifactsName;

    @ApiModelProperty(value = "原始权重")
    private Double initialWeight;

    @ApiModelProperty(value = "实际权重")
    private Double actualWeight;

    @ApiModelProperty(value = "扣分")
    private Double deduct;

    @ApiModelProperty(value = "评分")
    private Double score;

    @ApiModelProperty(value = "桥梁类型id")
    private Long bridgeTypeId;

    @ApiModelProperty(value = "综合扣分值")
    private Double MDPh;

    @ApiModelProperty(value = "构件id")
    private Long componentId;

    @ApiModelProperty(value = "是否带* 1带 2不带")
    private Integer serious;

    public Integer getSerious() {
        return serious;
    }

    public void setSerious(Integer serious) {
        this.serious = serious;
    }

    @ApiModelProperty(value = "病害集合")
    private List<DiseaseInstanceDTO> instanceDTOList;

    public Long getArtifactsId() {
        return artifactsId;
    }

    public void setArtifactsId(Long artifactsId) {
        this.artifactsId = artifactsId;
    }

    public String getArtifactsName() {
        return artifactsName;
    }

    public void setArtifactsName(String artifactsName) {
        this.artifactsName = artifactsName;
    }

    public Double getInitialWeight() {
        return initialWeight;
    }

    public void setInitialWeight(Double initialWeight) {
        this.initialWeight = initialWeight;
    }

    public Double getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(Double actualWeight) {
        this.actualWeight = actualWeight;
    }

    public Double getDeduct() {
        return deduct;
    }

    public void setDeduct(Double deduct) {
        this.deduct = deduct;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<DiseaseInstanceDTO> getInstanceDTOList() {
        return instanceDTOList;
    }

    public void setInstanceDTOList(List<DiseaseInstanceDTO> instanceDTOList) {
        this.instanceDTOList = instanceDTOList;
    }

    public Double getMDPh() {
        return MDPh;
    }

    public void setMDPh(Double MDPh) {
        this.MDPh = MDPh;
    }

    public Long getComponentId() {
        return componentId;
    }

    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }

    public Long getBridgeTypeId() {
        return bridgeTypeId;
    }

    public void setBridgeTypeId(Long bridgeTypeId) {
        this.bridgeTypeId = bridgeTypeId;
    }
}
















