package com.iware.bridge.model.entity.global;


import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="TunnelInfo", description="隧道信息表")
public class TunnelInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "结构结构物id")
    private Integer structureId;

    @ApiModelProperty(value = "路线编号")
    private String roadCode;

    @ApiModelProperty(value = "路线等级")
    private String roadGrade;

    @ApiModelProperty(value = "中心桩号")
    private String centerMileage;

    @ApiModelProperty(value = "长度")
    private Float length;

    @ApiModelProperty(value = "宽度")
    private Float width;

    @ApiModelProperty(value = "路面宽度")
    private Float roadWidth;

    @ApiModelProperty(value = "净高")
    private Float height;

    @ApiModelProperty(value = "岩层地质")
    private String stratumGeology;

    @ApiModelProperty(value = "围岩分类")
    private String wallRockTypes;

    @ApiModelProperty(value = "衬砌类型")
    private String liningType;

    @ApiModelProperty(value = "衬砌厚度")
    private Float liningThick;

    @ApiModelProperty(value = "洞门型式")
    private String portalForm;

    @ApiModelProperty(value = "路面类型")
    private String roadType;

    @ApiModelProperty(value = "照明设施")
    private String lightingInstallation;

    @ApiModelProperty(value = "通风设施")
    private String airInstallation;

    @ApiModelProperty(value = "消防与救援设施")
    private String fireInstallation;

    @ApiModelProperty(value = "监控设施")
    private String watchInstallation;

    @ApiModelProperty(value = "供配电设施")
    private String electricInstallation;

    @ApiModelProperty(value = "洞内纵坡")
    private String tunnelLongitudinalSlope;

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

    public Integer getStructureId() {
		return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public String getRoadCode() {
		return roadCode;
    }

    public void setRoadCode(String roadCode) {
        this.roadCode = roadCode;
    }

    public String getroadGrade() {
		return roadGrade;
    }

    public void setroadGrade(String roadGrade) {
        this.roadGrade = roadGrade;
    }

    public String getCenterMileage() {
		return centerMileage;
    }

    public void setCenterMileage(String centerMileage) {
        this.centerMileage = centerMileage;
    }

    public Float getLength() {
		return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getWidth() {
		return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getRoadWidth() {
		return roadWidth;
    }

    public void setRoadWidth(Float roadWidth) {
        this.roadWidth = roadWidth;
    }

    public Float getHeight() {
		return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getStratumGeology() {
		return stratumGeology;
    }

    public void setStratumGeology(String stratumGeology) {
        this.stratumGeology = stratumGeology;
    }

    public String getWallRockTypes() {
		return wallRockTypes;
    }

    public void setWallRockTypes(String wallRockTypes) {
        this.wallRockTypes = wallRockTypes;
    }

    public String getLiningType() {
		return liningType;
    }

    public void setLiningType(String liningType) {
        this.liningType = liningType;
    }

    public Float getLiningThick() {
		return liningThick;
    }

    public void setLiningThick(Float liningThick) {
        this.liningThick = liningThick;
    }

    public String getPortalForm() {
		return portalForm;
    }

    public void setPortalForm(String portalForm) {
        this.portalForm = portalForm;
    }

    public String getRoadType() {
		return roadType;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }

    public String getLightingInstallation() {
		return lightingInstallation;
    }

    public void setLightingInstallation(String lightingInstallation) {
        this.lightingInstallation = lightingInstallation;
    }

    public String getAirInstallation() {
		return airInstallation;
    }

    public void setAirInstallation(String airInstallation) {
        this.airInstallation = airInstallation;
    }

    public String getFireInstallation() {
		return fireInstallation;
    }

    public void setFireInstallation(String fireInstallation) {
        this.fireInstallation = fireInstallation;
    }

    public String getWatchInstallation() {
		return watchInstallation;
    }

    public void setWatchInstallation(String watchInstallation) {
        this.watchInstallation = watchInstallation;
    }

    public String getElectricInstallation() {
		return electricInstallation;
    }

    public void setElectricInstallation(String electricInstallation) {
        this.electricInstallation = electricInstallation;
    }

    public String getTunnelLongitudinalSlope() {
		return tunnelLongitudinalSlope;
    }

    public void setTunnelLongitudinalSlope(String tunnelLongitudinalSlope) {
        this.tunnelLongitudinalSlope = tunnelLongitudinalSlope;
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
        return "TunnelInfo{" +
            "  id=" + id +
            ", structureId=" + structureId +
            ", roadCode=" + roadCode +
            ", roadGrade=" + roadGrade +
            ", centerMileage=" + centerMileage +
            ", length=" + length +
            ", width=" + width +
            ", roadWidth=" + roadWidth +
            ", height=" + height +
            ", stratumGeology=" + stratumGeology +
            ", wallRockTypes=" + wallRockTypes +
            ", liningType=" + liningType +
            ", liningThick=" + liningThick +
            ", portalForm=" + portalForm +
            ", roadType=" + roadType +
            ", lightingInstallation=" + lightingInstallation +
            ", airInstallation=" + airInstallation +
            ", fireInstallation=" + fireInstallation +
            ", watchInstallation=" + watchInstallation +
            ", electricInstallation=" + electricInstallation +
            ", tunnelLongitudinalSlope=" + tunnelLongitudinalSlope +
            ", status=" + status +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
