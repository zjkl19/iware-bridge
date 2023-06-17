package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author: CWY
 * @Date: 2021/5/11 11:52
 */
@ApiModel(value = "AcrossVO", description = "跨/墩台")
public class AcrossDTO implements Serializable {

    @ApiModelProperty(value = "评分id")
    private Long scoreId;

    @ApiModelProperty(value = "跨或墩台id")
    private Long spanId;

    @ApiModelProperty(value = "桥梁类型id")
    private Integer bridgeTypeId;

    @ApiModelProperty(value = "跨号或墩台号")
    private String spanCode;

    @ApiModelProperty(value = "构件集合")
    private List<ArtifactsDTO> artifactsDTOList;

    @ApiModelProperty(value = "BCIxj评分")
    private Double BCIxj;

    @ApiModelProperty(value = "BSIxj评分")
    private Double BSIxj;

    @ApiModelProperty(value = "BCIsi评分")
    private Double BCIsi;

    @ApiModelProperty(value = "BSIsi评分")
    private Double BSIsi;

    @ApiModelProperty(value = "评定等级")
    private String ratingLevel;

    @ApiModelProperty(value = "1：蹲 2：台")
    private Integer isCode;

    public Integer getIsCode() {
        return isCode;
    }

    public void setIsCode(Integer isCode) {
        this.isCode = isCode;
    }

    public String getSpanCode() {
        return spanCode;
    }

    public void setSpanCode(String spanCode) {
        this.spanCode = spanCode;
    }

    public List<ArtifactsDTO> getArtifactsDTOList() {
        return artifactsDTOList;
    }

    public void setArtifactsDTOList(List<ArtifactsDTO> artifactsDTOList) {
        this.artifactsDTOList = artifactsDTOList;
    }

    public Double getBCIxj() {
        return BCIxj;
    }

    public void setBCIxj(Double BCIxj) {
        this.BCIxj = BCIxj;
    }

    public Double getBCIsi() {
        return BCIsi;
    }

    public void setBCIsi(Double BCIsi) {
        this.BCIsi = BCIsi;
    }

    public Double getBSIxj() {
        return BSIxj;
    }

    public void setBSIxj(Double BSIxj) {
        this.BSIxj = BSIxj;
    }

    public Double getBSIsi() {
        return BSIsi;
    }

    public void setBSIsi(Double BSIsi) {
        this.BSIsi = BSIsi;
    }

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Long getSpanId() {
        return spanId;
    }

    public void setSpanId(Long spanId) {
        this.spanId = spanId;
    }

    public String getRatingLevel() {
        return ratingLevel;
    }

    public void setRatingLevel(String ratingLevel) {
        this.ratingLevel = ratingLevel;
    }

    public Integer getBridgeTypeId() {
        return bridgeTypeId;
    }

    public void setBridgeTypeId(Integer bridgeTypeId) {
        this.bridgeTypeId = bridgeTypeId;
    }
}

















