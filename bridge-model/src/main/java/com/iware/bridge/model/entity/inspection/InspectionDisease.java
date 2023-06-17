package com.iware.bridge.model.entity.inspection;


import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="InspectionDisease", description="巡检病害表")
public class InspectionDisease implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "病害名称")
    private String name;

    @ApiModelProperty(value = "病害部位")
    private String diseasePart;

    @ApiModelProperty(value = "检查项")
    private String checkItem;

    @ApiModelProperty(value = "损害类型")
    private String damageType;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "1:桥梁病害 2:隧道病害")
    private Integer structureType;

    @ApiModelProperty(value = "1:常规 2:多选 3:输入框")
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

    public String getDiseasePart() {
        return diseasePart;
    }

    public void setDiseasePart(String diseasePart) {
        this.diseasePart = diseasePart;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getStructureType() {
        return structureType;
    }

    public void setStructureType(Integer structureType) {
        this.structureType = structureType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "InspectionDisease{" +
                "  id=" + id +
                ", name=" + name +
                ", diseasePart=" + diseasePart +
                ", checkItem=" + checkItem +
                ", damageType=" + damageType +
                ", unit=" + unit +
                ", structureType=" + structureType +
                ", type=" + type +
                "}";
    }
}

