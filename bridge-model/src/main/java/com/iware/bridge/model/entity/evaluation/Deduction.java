package com.iware.bridge.model.entity.evaluation;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="Deduction", description="桥梁病害扣分表")
public class Deduction implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "构件id")
    private Integer componentId;

    @ApiModelProperty(value = "病害id")
    private Integer diseaseId;

    @ApiModelProperty(value = "程度")
    private String degress;

    @ApiModelProperty(value = "")
    private Integer lower;

    @ApiModelProperty(value = "范围上限")
    private Integer upper;

    @ApiModelProperty(value = "扣分值")
    private Integer value;

    @ApiModelProperty(value = "1：百分比或范围 2：个数 3：字符串")
    private Integer type;

    @ApiModelProperty(value = "最高等级不得超过D级 0：不限制  1：限制")
    private Integer levelLimit;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getComponentId() {
		return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public Integer getDiseaseId() {
		return diseaseId;
    }

    public void setDiseaseId(Integer diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDegress() {
		return degress;
    }

    public void setDegress(String degress) {
        this.degress = degress;
    }

    public Integer getLower() {
		return lower;
    }

    public void setLower(Integer lower) {
        this.lower = lower;
    }

    public Integer getUpper() {
		return upper;
    }

    public void setUpper(Integer upper) {
        this.upper = upper;
    }

    public Integer getValue() {
		return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getType() {
		return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLevelLimit() {
		return levelLimit;
    }

    public void setLevelLimit(Integer levelLimit) {
        this.levelLimit = levelLimit;
    }


    @Override
    public String toString() {
        return "Deduction{" +
            "  id=" + id +
            ", componentId=" + componentId +
            ", diseaseId=" + diseaseId +
            ", degress=" + degress +
            ", lower=" + lower +
            ", upper=" + upper +
            ", value=" + value +
            ", type=" + type +
            ", levelLimit=" + levelLimit +
        "}";
    }
}
