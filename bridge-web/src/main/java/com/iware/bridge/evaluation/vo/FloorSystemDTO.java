package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author: CWY
 * @Date: 2021/5/11 11:45
 */
@ApiModel(value = "FloorSystemVO", description = "桥面系")
public class FloorSystemDTO implements Serializable {

    @ApiModelProperty(value = "桥面系id字符串")
    private String deckId;

    @ApiModelProperty(value = "构件集合")
    private List<ArtifactsDTO> artifactsDTOList;

    @ApiModelProperty(value = "BCIm评分")
    private Double BCIm;

    @ApiModelProperty(value = "BSIm评分")
    private Double BSIm;

    @ApiModelProperty(value = "特殊病害等级")
    private String specialLevel;

    public List<ArtifactsDTO> getArtifactsDTOList() {
        return artifactsDTOList;
    }

    public void setArtifactsDTOList(List<ArtifactsDTO> artifactsDTOList) {
        this.artifactsDTOList = artifactsDTOList;
    }

    public Double getBCIm() {
        return BCIm;
    }

    public void setBCIm(Double BCIm) {
        this.BCIm = BCIm;
    }

    public Double getBSIm() {
        return BSIm;
    }

    public void setBSIm(Double BSIm) {
        this.BSIm = BSIm;
    }

    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }

    public String getSpecialLevel() {
        return specialLevel;
    }

    public void setSpecialLevel(String specialLevel) {
        this.specialLevel = specialLevel;
    }
}















