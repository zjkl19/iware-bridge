package com.iware.bridge.inspection.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.inspection.InspectionDiseaseInstance;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "InspectionDiseaseInstanceVO",description = "巡查病害信息")
public class InspectionDiseaseInstanceVO extends InspectionDiseaseInstance implements Serializable {

    private static final long serialVersionUID = -1264146101430511032L;

    @ApiModelProperty(value = "病害名称")
    private String diseaseName;
    @ApiModelProperty(value = "病害选项名")
    private String optionName;
    @ApiModelProperty(value = "损害类型")
    private String damageType;
    @ApiModelProperty(value = "图片")
    private List<Photo> photo;
    @ApiModelProperty(value = "桥梁id")
    private Integer structureId;
    @ApiModelProperty(value = "桥梁名称")
    private String structureName;
    @ApiModelProperty(value = "检查项")
    private String checkItem;
    @ApiModelProperty(value = "病害部位")
    private String diseasePart;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "展示字段,桥面系为数量,上下部为异常部位,桥区施工null,其他因素选项名,其他说明不展示")
    private String showItem;

    @ApiModelProperty(value = "巡查时间")
    @JSONField(format = "yyyy-MM-dd")
    private String inspectionTime;

    @ApiModelProperty(value = "维修时间")
    @JSONField(format = "yyyy-MM-dd")
    private String maintainTime;

    private String statusString;

    private String strategyString;

    private String exceptionTypeString;

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public String getStrategyString() {
        return strategyString;
    }

    public void setStrategyString(String strategyString) {
        this.strategyString = strategyString;
    }

    public String getExceptionTypeString() {
        return exceptionTypeString;
    }

    public void setExceptionTypeString(String exceptionTypeString) {
        this.exceptionTypeString = exceptionTypeString;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getMaintainTime() {
        return maintainTime;
    }

    public void setMaintainTime(String maintainTime) {
        this.maintainTime = maintainTime;
    }

    public String getShowItem() {
        return showItem;
    }

    public void setShowItem(String showItem) {
        this.showItem = showItem;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @ApiModelProperty(value = "病害选项id")
    private List<Integer> optionIds;

    public List<Integer> getOptionIds() {
        return optionIds;
    }

    public void setOptionIds(List<Integer> optionIds) {
        this.optionIds = optionIds;
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

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public List<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
}
