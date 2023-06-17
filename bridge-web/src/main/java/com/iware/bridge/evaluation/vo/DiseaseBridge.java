package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "DiseaseBridge", description = "桥梁病害表格信息")
public class DiseaseBridge {

    @ApiModelProperty(value = "线路")
    private String value1;
    @ApiModelProperty(value = "桥跨")
    private String value2;
    @ApiModelProperty(value = "部位")
    private String value3;
    @ApiModelProperty(value = "构件")
    private String value4;
    @ApiModelProperty(value = "病害类型")
    private String value5;
    @ApiModelProperty(value = "病害状况")
    private String value6;
    @ApiModelProperty(value = "病害编号")
    private String value7;
    @ApiModelProperty(value = "备注")
    private String value8;
    @ApiModelProperty(value = "检测人员")
    private String value9;
    @ApiModelProperty(value = "0 无修改记录 1 有修改记录")
    private String value10;
    @ApiModelProperty(value = "构件类型id")
    private Integer partType;
    @ApiModelProperty(value = "构件实例化id")
    private Integer targetId;
    @ApiModelProperty(value = "病害id")
    private Integer diseaseId;
    @ApiModelProperty(value = "病害实例化id")
    private Integer id;
    @ApiModelProperty(value = "流水序号")
    private Integer sort;
    @ApiModelProperty(value = "高度差")
    private float heightDifference;
    @ApiModelProperty(value = "数量")
    private float number;
    @ApiModelProperty(value = "是否有图片 0:无 1：有")
    private Integer photo;

    public Integer getPhoto() {
        return photo;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }

    public float getHeightDifference() {
        return heightDifference;
    }

    public void setHeightDifference(float heightDifference) {
        this.heightDifference = heightDifference;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getValue4() {
        return value4;
    }

    public void setValue4(String value4) {
        this.value4 = value4;
    }

    public String getValue5() {
        return value5;
    }

    public void setValue5(String value5) {
        this.value5 = value5;
    }

    public String getValue6() {
        return value6;
    }

    public void setValue6(String value6) {
        this.value6 = value6;
    }

    public String getValue7() {
        return value7;
    }

    public void setValue7(String value7) {
        this.value7 = value7;
    }

    public String getValue8() {
        return value8;
    }

    public void setValue8(String value8) {
        this.value8 = value8;
    }

    public String getValue9() {
        return value9;
    }

    public void setValue9(String value9) {
        this.value9 = value9;
    }

    public String getValue10() {
        return value10;
    }

    public void setValue10(String value10) {
        this.value10 = value10;
    }

    public Integer getPartType() {
        return partType;
    }

    public void setPartType(Integer partType) {
        this.partType = partType;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Integer diseaseId) {
        this.diseaseId = diseaseId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DiseaseBridge{" +
                "value1='" + value1 + '\'' +
                ", value2='" + value2 + '\'' +
                ", value3='" + value3 + '\'' +
                ", value4='" + value4 + '\'' +
                ", value5='" + value5 + '\'' +
                ", value6='" + value6 + '\'' +
                ", value7='" + value7 + '\'' +
                ", value8='" + value8 + '\'' +
                ", value9='" + value9 + '\'' +
                ", value10='" + value10 + '\'' +
                ", partType=" + partType +
                ", targetId=" + targetId +
                ", diseaseId=" + diseaseId +
                ", id=" + id +
                ", sort=" + sort +
                ", heightDifference=" + heightDifference +
                ", number=" + number +
                '}';
    }
}
