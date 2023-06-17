package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: CWY
 * @Date: 2021/5/13 11:26
 */
@ApiModel(value = "DiseaseInstanceDTO", description = "病害")
public class DiseaseInstanceDTO implements Serializable {

    @ApiModelProperty(value = "病害Id")
    private Long diseaseId;

    @ApiModelProperty(value = "病害名称")
    private String diseaseName;

    @ApiModelProperty(value = "病害编号")
    private String diseaseCode;

    @ApiModelProperty(value = "长度")
    private Double length;

    @ApiModelProperty(value = "宽度")
    private Double width;

    @ApiModelProperty(value = "深度")
    private Double depth;

    @ApiModelProperty(value = "缝长")
    private Double seamLength;

    @ApiModelProperty(value = "缝宽")
    private Double seamWidth;

    @ApiModelProperty(value = "程度")
    private String degree;

    @ApiModelProperty(value = "扣分")
    private Double deduct;

    @ApiModelProperty(value = "数量")
    private Double number;

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Double getDeduct() {
        return deduct;
    }

    public void setDeduct(Double deduct) {
        this.deduct = deduct;
    }

    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Double getSeamLength() {
        return seamLength;
    }

    public void setSeamLength(Double seamLength) {
        this.seamLength = seamLength;
    }

    public Double getSeamWidth() {
        return seamWidth;
    }

    public void setSeamWidth(Double seamWidth) {
        this.seamWidth = seamWidth;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }
}























