package com.iware.bridge.model.entity.inspection;


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

@ApiModel(value="MaintainItem", description="维修项表")
public class MaintainItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "计划id")
    private Integer planId;

    @ApiModelProperty(value = "结构物id")
    private Integer structureId;

    @ApiModelProperty(value = "病害实例id")
    private Integer diseaseInstanceId;

    @ApiModelProperty(value = "维修项名称")
    private String name;

    @ApiModelProperty(value = "0:日常保养 1:小修 2:中修 3:大修")
    private Integer type;

    @JSONField(format = "yyyy-MM-dd")
    @ApiModelProperty(value = "拟定日期")
    private Date proposedTime;

    @JSONField(format = "yyyy-MM-dd")
    @ApiModelProperty(value = "維修日期")
    private Date maintainTime;

    @ApiModelProperty(value = "维修人员")
    private String creator;

    @ApiModelProperty(value = "维修内容")
    private String content;

    @ApiModelProperty(value = "维修方法")
    private String method;

    @ApiModelProperty(value = "维修结果")
    private String result;

    @ApiModelProperty(value = "维修单位")
    private String maintenanceUnit;

    @ApiModelProperty(value = "工程量")
    private String quantities;

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

    public Integer getDiseaseInstanceId() {
        return diseaseInstanceId;
    }

    public void setDiseaseInstanceId(Integer diseaseInstanceId) {
        this.diseaseInstanceId = diseaseInstanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getProposedTime() {
        return proposedTime;
    }

    public void setProposedTime(Date proposedTime) {
        this.proposedTime = proposedTime;
    }

    public Date getMaintainTime() {
        return maintainTime;
    }

    public void setMaintainTime(Date maintainTime) {
        this.maintainTime = maintainTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMaintenanceUnit() {
        return maintenanceUnit;
    }

    public void setMaintenanceUnit(String maintenanceUnit) {
        this.maintenanceUnit = maintenanceUnit;
    }

    public String getQuantities() {
        return quantities;
    }

    public void setQuantities(String quantities) {
        this.quantities = quantities;
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
        return "MaintainItem{" +
                "  id=" + id +
                ", planId=" + planId +
                ", structureId=" + structureId +
                ", diseaseInstanceId=" + diseaseInstanceId +
                ", name=" + name +
                ", type=" + type +
                ", proposedTime=" + proposedTime +
                ", maintainTime=" + maintainTime +
                ", creator=" + creator +
                ", content=" + content +
                ", method=" + method +
                ", result=" + result +
                ", maintenanceUnit=" + maintenanceUnit +
                ", quantities=" + quantities +
                ", status=" + status +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                "}";
    }
}

