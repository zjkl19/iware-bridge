package com.iware.bridge.online.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "BoxData",description = "箱线图数据")
public class BoxData implements Serializable {

    private static final long serialVersionUID = -1177958122270511032L;

    @ApiModelProperty(value = "测点编号")
    private String name;
    @ApiModelProperty(value="最小/下四分位数/中位数/上四分位数/最大")
    private List<Float> calculate;
    @ApiModelProperty(value="异常数据")
    private List<Float> warningList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Float> getCalculate() {
        return calculate;
    }

    public void setCalculate(List<Float> calculate) {
        this.calculate = calculate;
    }

    public List<Float> getWarningList() {
        return warningList;
    }

    public void setWarningList(List<Float> warningList) {
        this.warningList = warningList;
    }
}
