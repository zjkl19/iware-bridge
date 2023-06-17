package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: CWY
 * @Date: 2021/5/14 10:19
 */
@ApiModel(value = "ElementWeightDTO", description = "要素权重")
public class ElementWeightDTO implements Serializable {

    @ApiModelProperty(value = "要素名称")
    private String elementName;

    @ApiModelProperty(value = "权重值")
    private Double weightNum;

    public ElementWeightDTO() {
    }

    public ElementWeightDTO(String elementName, Double weightNum) {
        this.elementName = elementName;
        this.weightNum = weightNum;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public Double getWeightNum() {
        return weightNum;
    }

    public void setWeightNum(Double weightNum) {
        this.weightNum = weightNum;
    }
}
