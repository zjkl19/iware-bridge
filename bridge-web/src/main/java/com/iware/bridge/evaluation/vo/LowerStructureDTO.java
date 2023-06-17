package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author: CWY
 * @Date: 2021/5/11 11:50
 */
@ApiModel(value = "LowerStructureVO", description = "下部结构")
public class LowerStructureDTO implements Serializable {

    @ApiModelProperty(value = "下部结构id")
    private String ids;

    @ApiModelProperty(value = "跨集合")
    private List<AcrossDTO> acrossDTOList;

    @ApiModelProperty(value = "BCIx评分")
    private Double BCIx;

    @ApiModelProperty(value = "BSIx评分")
    private Double BSIx;

    @ApiModelProperty(value = "评定等级")
    private String specialLevel;

    public List<AcrossDTO> getAcrossDTOList() {
        return acrossDTOList;
    }

    public void setAcrossDTOList(List<AcrossDTO> acrossDTOList) {
        this.acrossDTOList = acrossDTOList;
    }

    public Double getBCIx() {
        return BCIx;
    }

    public void setBCIx(Double BCIx) {
        this.BCIx = BCIx;
    }

    public Double getBSIx() {
        return BSIx;
    }

    public void setBSIx(Double BSIx) {
        this.BSIx = BSIx;
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


















