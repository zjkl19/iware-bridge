package com.iware.bridge.inspection.vo;

import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.inspection.MaintainItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "MaintainItemVO",description = "维修项信息")
public class MaintainItemVO extends MaintainItem implements Serializable {

    private static final long serialVersionUID = -1264141501430511032L;

    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "结构物名称")
    private String structureName;
    @ApiModelProperty(value = "结构物类型")
    private String structureType;
    @ApiModelProperty(value = "管理单位")
    private String manageDepartment;
    @ApiModelProperty(value = "维养单位")
    private String maintainDepartment;
    @ApiModelProperty(value = "联系方式")
    private String phone;
    @ApiModelProperty(value = "维养计划")
    private String maintainPlanName;
    @ApiModelProperty(value = "病害名称")
    private String diseaseName;
    @ApiModelProperty(value = "病害描述")
    private String diseaseRemark;
    @ApiModelProperty(value = "病害程度")
    private String quantity;
    @ApiModelProperty(value = "病害类型")
    private String damageType;
    @ApiModelProperty(value = "修理类型名称")
    private String maintainTypeName;
    @ApiModelProperty(value = "维修前图片")
    private List<Photo> beforeList;
    @ApiModelProperty(value = "维修中图片")
    private List<Photo> progressList;
    @ApiModelProperty(value = "维修后图片")
    private List<Photo> afterList;
    @ApiModelProperty(value = "病害程度单位")
    private String unit;
    @ApiModelProperty(value = "选项名")
    private String optionName;
    @ApiModelProperty(value = "病害表id")
    private Integer inspectionDiseaseId;
    @ApiModelProperty(value = "病害部位")
    private String exceptionPart;
    @ApiModelProperty(value = "展示字段,桥面系为数量,上下部为异常部位,桥区施工null,其他因素选项名,其他说明不展示")
    private String showItem;
    @ApiModelProperty(value = "维养日期")
    private String maintainDate;
    @ApiModelProperty(value = "计划维修日期")
    private String proposedDate;
    @ApiModelProperty(value = "维修项名称")
    private String maintainName;

    public String getStructureType() {
        return structureType;
    }

    public void setStructureType(String structureType) {
        this.structureType = structureType;
    }

    public String getMaintainName() {
        return maintainName;
    }

    public void setMaintainName(String maintainName) {
        this.maintainName = maintainName;
    }

    public String getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(String proposedDate) {
        this.proposedDate = proposedDate;
    }

    public String getMaintainDate() {
        return maintainDate;
    }

    public void setMaintainDate(String maintainDate) {
        this.maintainDate = maintainDate;
    }

    public String getExceptionPart() {
        return exceptionPart;
    }

    public void setExceptionPart(String exceptionPart) {
        this.exceptionPart = exceptionPart;
    }

    public Integer getInspectionDiseaseId() {
        return inspectionDiseaseId;
    }

    public void setInspectionDiseaseId(Integer inspectionDiseaseId) {
        this.inspectionDiseaseId = inspectionDiseaseId;
    }

    public String getShowItem() {
        return showItem;
    }

    public void setShowItem(String showItem) {
        this.showItem = showItem;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getManageDepartment() {
        return manageDepartment;
    }

    public void setManageDepartment(String manageDepartment) {
        this.manageDepartment = manageDepartment;
    }

    public String getMaintainDepartment() {
        return maintainDepartment;
    }

    public void setMaintainDepartment(String maintainDepartment) {
        this.maintainDepartment = maintainDepartment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMaintainPlanName() {
        return maintainPlanName;
    }

    public void setMaintainPlanName(String maintainPlanName) {
        this.maintainPlanName = maintainPlanName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseRemark() {
        return diseaseRemark;
    }

    public void setDiseaseRemark(String diseaseRemark) {
        this.diseaseRemark = diseaseRemark;
    }

    public String getMaintainTypeName() {
        return maintainTypeName;
    }

    public void setMaintainTypeName(String maintainTypeName) {
        this.maintainTypeName = maintainTypeName;
    }

    public List<Photo> getBeforeList() {
        return beforeList;
    }

    public void setBeforeList(List<Photo> beforeList) {
        this.beforeList = beforeList;
    }

    public List<Photo> getProgressList() {
        return progressList;
    }

    public void setProgressList(List<Photo> progressList) {
        this.progressList = progressList;
    }

    public List<Photo> getAfterList() {
        return afterList;
    }

    public void setAfterList(List<Photo> afterList) {
        this.afterList = afterList;
    }
}
