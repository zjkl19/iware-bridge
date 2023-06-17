package com.iware.bridge.model.entity.online;


import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-10-19 17:00:00
 * @version 1.3.3
 */


@ApiModel(value="MeansPoint", description="测点信息表")
public class MeansPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "构件id")
    private Integer componentId;

    @ApiModelProperty(value = "产商id")
    private Integer companyId;

    @ApiModelProperty(value = "结构物id")
    private Integer structureId;

    @ApiModelProperty(value = "传感器类型id")
    private Integer sensorTypeId;

    @ApiModelProperty(value = "测点编号")
    private String name;

    @ApiModelProperty(value = "测点编号说明")
    private String describe;

    @ApiModelProperty(value = "部署日期")
    private Date deploymentDate;

    @ApiModelProperty(value = "截面位置")
    private String sectionPosition;

    @ApiModelProperty(value = "采集器信息")
    private String collectorInformation;

    @ApiModelProperty(value = "X坐标")
    private Float xAxis;

    @ApiModelProperty(value = "Y坐标")
    private Float yAxis;

    @ApiModelProperty(value = "Z坐标")
    private Float zAxis;

    @ApiModelProperty(value = "采样频率")
    private String samplingFrequency;

    @ApiModelProperty(value = "参考基频")
    private Float referenceFrequency;

    @ApiModelProperty(value = "频率浮动范围")
    private Float floatRange;

    @ApiModelProperty(value = "索长")
    private Float cableLength;

    @ApiModelProperty(value = "单位索长")
    private Float unitCableLength;

    @ApiModelProperty(value = "出产日期")
    private Date productionDate;

    @ApiModelProperty(value = "出产编号")
    private String productionCoding;

    @ApiModelProperty(value = "图片位置")
    private String photoUrl;

    @ApiModelProperty(value = "状态 0：离线 1：正常 2: 故障")
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

    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public Integer getSensorTypeId() {
        return sensorTypeId;
    }

    public void setSensorTypeId(Integer sensorTypeId) {
        this.sensorTypeId = sensorTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Date getDeploymentDate() {
        return deploymentDate;
    }

    public void setDeploymentDate(Date deploymentDate) {
        this.deploymentDate = deploymentDate;
    }

    public String getSectionPosition() {
        return sectionPosition;
    }

    public void setSectionPosition(String sectionPosition) {
        this.sectionPosition = sectionPosition;
    }

    public String getCollectorInformation() {
        return collectorInformation;
    }

    public void setCollectorInformation(String collectorInformation) {
        this.collectorInformation = collectorInformation;
    }

    public Float getXAxis() {
        return xAxis;
    }

    public void setXAxis(Float xAxis) {
        this.xAxis = xAxis;
    }

    public Float getYAxis() {
        return yAxis;
    }

    public void setYAxis(Float yAxis) {
        this.yAxis = yAxis;
    }

    public Float getZAxis() {
        return zAxis;
    }

    public void setZAxis(Float zAxis) {
        this.zAxis = zAxis;
    }

    public String getSamplingFrequency() {
        return samplingFrequency;
    }

    public void setSamplingFrequency(String samplingFrequency) {
        this.samplingFrequency = samplingFrequency;
    }

    public Float getReferenceFrequency() {
        return referenceFrequency;
    }

    public void setReferenceFrequency(Float referenceFrequency) {
        this.referenceFrequency = referenceFrequency;
    }

    public Float getFloatRange() {
        return floatRange;
    }

    public void setFloatRange(Float floatRange) {
        this.floatRange = floatRange;
    }

    public Float getCableLength() {
        return cableLength;
    }

    public void setCableLength(Float cableLength) {
        this.cableLength = cableLength;
    }

    public Float getUnitCableLength() {
        return unitCableLength;
    }

    public void setUnitCableLength(Float unitCableLength) {
        this.unitCableLength = unitCableLength;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public String getProductionCoding() {
        return productionCoding;
    }

    public void setProductionCoding(String productionCoding) {
        this.productionCoding = productionCoding;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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
        return "MeansPoint{" +
                "  id=" + id +
                ", componentId=" + componentId +
                ", companyId=" + companyId +
                ", structureId=" + structureId +
                ", sensorTypeId=" + sensorTypeId +
                ", name=" + name +
                ", describe=" + describe +
                ", deploymentDate=" + deploymentDate +
                ", sectionPosition=" + sectionPosition +
                ", collectorInformation=" + collectorInformation +
                ", xAxis=" + xAxis +
                ", yAxis=" + yAxis +
                ", zAxis=" + zAxis +
                ", samplingFrequency=" + samplingFrequency +
                ", referenceFrequency=" + referenceFrequency +
                ", floatRange=" + floatRange +
                ", cableLength=" + cableLength +
                ", unitCableLength=" + unitCableLength +
                ", productionDate=" + productionDate +
                ", productionCoding=" + productionCoding +
                ", photoUrl=" + photoUrl +
                ", status=" + status +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                "}";
    }
}
