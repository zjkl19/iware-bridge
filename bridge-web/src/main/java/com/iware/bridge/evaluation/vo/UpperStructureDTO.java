package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author: CWY
 * @Date: 2021/5/11 11:49
 */
@ApiModel(value = "UpperStructureVO", description = "上部结构")
public class UpperStructureDTO implements Serializable {

    @ApiModelProperty(value = "上部结构id")
    private String ids;

    @ApiModelProperty(value = "跨集合")
    private List<AcrossDTO> acrossDTOList;

    @ApiModelProperty(value = "BCIs评分")
    private Double BCIs;

    @ApiModelProperty(value = "BSIs评分")
    private Double BSIs;

    @ApiModelProperty(value = "评定等级")
    private String specialLevel;

    public List<AcrossDTO> getAcrossDTOList() {
        return acrossDTOList;
    }

    public void setAcrossDTOList(List<AcrossDTO> acrossDTOList) {
        this.acrossDTOList = acrossDTOList;
    }

    public Double getBCIs() {
        return BCIs;
    }

    public void setBCIs(Double BCIs) {
        this.BCIs = BCIs;
    }

    public Double getBSIs() {
        return BSIs;
    }

    public void setBSIs(Double BSIs) {
        this.BSIs = BSIs;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getSpecialLevel() {
        return specialLevel;
    }

    public void setSpecialLevel(String specialLevel) {
        this.specialLevel = specialLevel;
    }
}





















