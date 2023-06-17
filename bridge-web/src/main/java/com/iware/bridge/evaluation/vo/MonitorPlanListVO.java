package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author: wjp
 * @date: 2021年8月24日13:56:45
 * @since 1.0
 */
@ApiModel(value = "MonitorPlanListVO", description = "检测计划列表实体类")
public class MonitorPlanListVO {

    @ApiModelProperty(value = "id主键")
    private Integer id;

    @ApiModelProperty(value = "监测类型1：定期监测 2：特殊检测 3：静载实验")
    private Integer type;

    @ApiModelProperty(value = "项目id")
    private Integer projectInfoId;

    @ApiModelProperty(value = "开始日期")
    private String startTime;

    @ApiModelProperty(value = "结束日期")
    private String endTime;

    @ApiModelProperty(value = "app是否上传")
    private Integer upload;

    @ApiModelProperty(value = "创建者用户id")
    private Integer createUserId;

    @ApiModelProperty(value = "状态 1：未执行 2：执行中 3：已完成")
    private Integer status;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "修改日期")
    private Date modifyTime;

    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "检测类型名称")
    private String typeName;
    @ApiModelProperty(value = "结构物关联")
    private List<MonitorStructureRelVO> structureRelList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getProjectInfoId() {
        return projectInfoId;
    }

    public void setProjectInfoId(Integer projectInfoId) {
        this.projectInfoId = projectInfoId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getUpload() {
        return upload;
    }

    public void setUpload(Integer upload) {
        this.upload = upload;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<MonitorStructureRelVO> getStructureRelList() {
        return structureRelList;
    }

    public void setStructureRelList(List<MonitorStructureRelVO> structureRelList) {
        this.structureRelList = structureRelList;
    }
}
