package com.iware.bridge.model.entity.global;


import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="Structure", description="结构物信息表")
public class Structure implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "结构物代码")
    private String code;

    @ApiModelProperty(value = "结构物名称")
    private String name;

    @ApiModelProperty(value = "结构物编号")
    private String number;

    @ApiModelProperty(value = "所在省")
    private Integer provinceId;

    @ApiModelProperty(value = "所在市")
    private Integer cityId;

    @ApiModelProperty(value = "所在区(县)")
    private Integer countyId;

    @ApiModelProperty(value = "相关责任人")
    private String chargeName;

    @ApiModelProperty(value = "责任人电话")
    private String chargePhone;

    @ApiModelProperty(value = "路线名称")
    private String roadName;

    @JSONField(format = "yyyy-MM")
    @ApiModelProperty(value = "建成年月")
    private Date buildTime;

    @ApiModelProperty(value = "1:I类 2:II类 3:III类 4:IV类 5:V类")
    private Integer maintainCategory;

    @ApiModelProperty(value = "养护等级 1: I等 2:II等 3:III等")
    private Integer maintainGrade;

    @ApiModelProperty(value = "养护单位")
    private String maintainDepartment;

    @ApiModelProperty(value = "养护单位电话")
    private String maintainDepartPhone;

    @ApiModelProperty(value = "设计荷载")
    private String designLoad;

    @ApiModelProperty(value = "跨径分类")
    private String spanType;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "1:桥梁 2:隧道")
    private Integer structureType;

    @ApiModelProperty(value = "桥梁类型 1:梁桥 2:拱桥 3:刚架拱桥 4:悬索桥 5:斜拉桥 6:钢管混凝土拱桥")
    private Integer bridgeType;

    @ApiModelProperty(value = "运行管理单位")
    private String runningDepartment;

    @ApiModelProperty(value = "监理单位")
    private String supervisionDepartment;

    @ApiModelProperty(value = "建设单位")
    private String buildingDepartment;

    @ApiModelProperty(value = "施工单位")
    private String constructionDepartment;

    @ApiModelProperty(value = "设计单位")
    private String designDepartment;

    @ApiModelProperty(value = "所属业主单位id")
    private Integer unitId;

    @ApiModelProperty(value = "创建者id")
    private Integer createUserId;

    @ApiModelProperty(value = "技术状况")
    private String technology;

    @ApiModelProperty(value = "状况等级")
    private String grade;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
		return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
		return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
		return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getProvinceId() {
		return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
		return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
		return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getChargeName() {
		return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public String getChargePhone() {
		return chargePhone;
    }

    public void setChargePhone(String chargePhone) {
        this.chargePhone = chargePhone;
    }

    public String getRoadName() {
		return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public Date getBuildTime() {
		return buildTime;
    }

    public void setBuildTime(Date buildTime) {
        this.buildTime = buildTime;
    }

    public Integer getMaintainCategory() {
		return maintainCategory;
    }

    public void setMaintainCategory(Integer maintainCategory) {
        this.maintainCategory = maintainCategory;
    }

    public Integer getMaintainGrade() {
		return maintainGrade;
    }

    public void setMaintainGrade(Integer maintainGrade) {
        this.maintainGrade = maintainGrade;
    }

    public String getMaintainDepartment() {
		return maintainDepartment;
    }

    public void setMaintainDepartment(String maintainDepartment) {
        this.maintainDepartment = maintainDepartment;
    }

    public String getMaintainDepartPhone() {
		return maintainDepartPhone;
    }

    public void setMaintainDepartPhone(String maintainDepartPhone) {
        this.maintainDepartPhone = maintainDepartPhone;
    }

    public String getDesignLoad() {
		return designLoad;
    }

    public void setDesignLoad(String designLoad) {
        this.designLoad = designLoad;
    }

    public String getSpanType() {
		return spanType;
    }

    public void setSpanType(String spanType) {
        this.spanType = spanType;
    }

    public BigDecimal getLongitude() {
		return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
		return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Integer getStructureType() {
		return structureType;
    }

    public void setStructureType(Integer structureType) {
        this.structureType = structureType;
    }

    public Integer getBridgeType() {
		return bridgeType;
    }

    public void setBridgeType(Integer bridgeType) {
        this.bridgeType = bridgeType;
    }

    public String getRunningDepartment() {
		return runningDepartment;
    }

    public void setRunningDepartment(String runningDepartment) {
        this.runningDepartment = runningDepartment;
    }

    public String getSupervisionDepartment() {
		return supervisionDepartment;
    }

    public void setSupervisionDepartment(String supervisionDepartment) {
        this.supervisionDepartment = supervisionDepartment;
    }

    public String getBuildingDepartment() {
		return buildingDepartment;
    }

    public void setBuildingDepartment(String buildingDepartment) {
        this.buildingDepartment = buildingDepartment;
    }

    public String getConstructionDepartment() {
		return constructionDepartment;
    }

    public void setConstructionDepartment(String constructionDepartment) {
        this.constructionDepartment = constructionDepartment;
    }

    public String getDesignDepartment() {
		return designDepartment;
    }

    public void setDesignDepartment(String designDepartment) {
        this.designDepartment = designDepartment;
    }

    public Integer getUnitId() {
		return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getCreateUserId() {
		return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getTechnology() {
		return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getGrade() {
		return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getStatus() {
		return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
		return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
		return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }


    @Override
    public String toString() {
        return "Structure{" +
            "  id=" + id +
            ", code=" + code +
            ", name=" + name +
            ", number=" + number +
            ", provinceId=" + provinceId +
            ", cityId=" + cityId +
            ", countyId=" + countyId +
            ", chargeName=" + chargeName +
            ", chargePhone=" + chargePhone +
            ", roadName=" + roadName +
            ", buildTime=" + buildTime +
            ", maintainCategory=" + maintainCategory +
            ", maintainGrade=" + maintainGrade +
            ", maintainDepartment=" + maintainDepartment +
            ", maintainDepartPhone=" + maintainDepartPhone +
            ", designLoad=" + designLoad +
            ", spanType=" + spanType +
            ", longitude=" + longitude +
            ", latitude=" + latitude +
            ", structureType=" + structureType +
            ", bridgeType=" + bridgeType +
            ", runningDepartment=" + runningDepartment +
            ", supervisionDepartment=" + supervisionDepartment +
            ", buildingDepartment=" + buildingDepartment +
            ", constructionDepartment=" + constructionDepartment +
            ", designDepartment=" + designDepartment +
            ", unitId=" + unitId +
            ", createUserId=" + createUserId +
            ", technology=" + technology +
            ", grade=" + grade +
            ", status=" + status +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
