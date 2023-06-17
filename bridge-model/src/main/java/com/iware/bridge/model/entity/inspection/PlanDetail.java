package com.iware.bridge.model.entity.inspection;


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

@ApiModel(value="PlanDetail", description="计划详情")
public class PlanDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "计划id")
    private Integer planId;

    @ApiModelProperty(value = "结构物id")
    private Integer structureId;

    @ApiModelProperty(value = "巡查人员")
    private String inspector;

    @ApiModelProperty(value = "巡查单位")
    private String inspectionUnit;

    @ApiModelProperty(value = "核对存档人")
    private String fileSaveCollator;

    @JSONField(format = "yyyy-MM-dd")
    @ApiModelProperty(value = "巡查日期")
    private Date inspectionTime;

    @ApiModelProperty(value = "天气")
    private String weather;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "位置信息")
    private String location;

    @ApiModelProperty(value = "0:未完成 1:已完成 2:已超时")
    private Integer status;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "修改日期")
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getInspectionUnit() {
        return inspectionUnit;
    }

    public void setInspectionUnit(String inspectionUnit) {
        this.inspectionUnit = inspectionUnit;
    }

    public String getFileSaveCollator() {
        return fileSaveCollator;
    }

    public void setFileSaveCollator(String fileSaveCollator) {
        this.fileSaveCollator = fileSaveCollator;
    }

    public Date getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(Date inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        return "PlanDetail{" +
                "  id=" + id +
                ", planId=" + planId +
                ", structureId=" + structureId +
                ", inspector=" + inspector +
                ", inspectionUnit=" + inspectionUnit +
                ", fileSaveCollator=" + fileSaveCollator +
                ", inspectionTime=" + inspectionTime +
                ", weather=" + weather +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", location=" + location +
                ", status=" + status +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                "}";
    }
}
