package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: wjp
 * @date: 2021年9月23日13:39:54
 * @since 1.0
 */
@ApiModel(value = "BridgeSituationDTO", description = "检测记录-桥梁概况详细数据返回对象")
public class BridgeSituationDTO {
    @ApiModelProperty(value = "字段名")
    private String name;
    @ApiModelProperty(value = "字段名对应的值")
    private String value;
    @ApiModelProperty(value = "单位")
    private String  unit;


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BridgeSituationDTO{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
