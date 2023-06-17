package com.iware.bridge.inspection.vo;

import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.inspection.PlanDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "PlanDetailVO",description = "计划详情信息")
public class PlanDetailVO extends PlanDetail implements Serializable {

    private static final long serialVersionUID = -1264996100970511032L;

    @ApiModelProperty(value = "结构物名称")
    private String structureName;
    @ApiModelProperty(value = "病害数量")
    private Integer diseaseCount;
    @ApiModelProperty(value = "巡查类型")
    private String inspectionPlanType;
    @ApiModelProperty(value = "巡查时间yyyy-MM-dd")
    private String inspectionDate;
    @ApiModelProperty(value = "结构物类型")
    private Integer structureType;

    public Integer getStructureType() {
        return structureType;
    }

    public void setStructureType(Integer structureType) {
        this.structureType = structureType;
    }

    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    @ApiModelProperty(value = "病害列表")
    private List<DiseaseInstanceExpVo> diseaseInstanceList;

    @ApiModelProperty(value = "图片列表")
    private List<Photo> photoList;

    @ApiModelProperty(value = "其他说明")
    private String otherRemark;

    public String getOtherRemark() {
        return otherRemark;
    }

    public void setOtherRemark(String otherRemark) {
        this.otherRemark = otherRemark;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

    public List<DiseaseInstanceExpVo> getDiseaseInstanceList() {
        return diseaseInstanceList;
    }

    public void setDiseaseInstanceList(List<DiseaseInstanceExpVo> diseaseInstanceList) {
        this.diseaseInstanceList = diseaseInstanceList;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public Integer getDiseaseCount() {
        return diseaseCount;
    }

    public void setDiseaseCount(Integer diseaseCount) {
        this.diseaseCount = diseaseCount;
    }

    public String getInspectionPlanType() {
        return inspectionPlanType;
    }

    public void setInspectionPlanType(String inspectionPlanType) {
        this.inspectionPlanType = inspectionPlanType;
    }
}
