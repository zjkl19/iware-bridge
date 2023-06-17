package com.iware.bridge.model.entity.evaluation;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="Property", description="桥属性表")
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "属性名")
    private String name;

    @ApiModelProperty(value = "属性名英文")
    private String englishName;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "1：常规属性 2：单选框类型 1代表有，0代表无  3：下拉框类型 4：多选")
    private Integer type;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
		return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
		return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getUnit() {
		return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getType() {
		return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Property{" +
            "  id=" + id +
            ", name=" + name +
            ", englishName=" + englishName +
            ", unit=" + unit +
            ", type=" + type +
        "}";
    }
}
