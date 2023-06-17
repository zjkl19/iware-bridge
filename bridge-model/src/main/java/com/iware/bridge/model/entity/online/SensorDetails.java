package com.iware.bridge.model.entity.online;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="SensorDetails", description="传感器细项表")
public class SensorDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "细项名称")
    private String detailName;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "默认精度")
    private Float precision;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetailName() {
		return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getUnit() {
		return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getPrecision() {
		return precision;
    }

    public void setPrecision(Float precision) {
        this.precision = precision;
    }


    @Override
    public String toString() {
        return "SensorDetails{" +
            "  id=" + id +
            ", detailName=" + detailName +
            ", unit=" + unit +
            ", precision=" + precision +
        "}";
    }
}
