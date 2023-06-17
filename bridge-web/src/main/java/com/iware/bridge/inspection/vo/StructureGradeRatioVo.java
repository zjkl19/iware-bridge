package com.iware.bridge.inspection.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("养护等级占比")
public class StructureGradeRatioVo {

    @ApiModelProperty("等级名")
    private String gradeName;

    @ApiModelProperty("对应数量")
    private Integer count;

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
