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

@ApiModel(value="ProjectAppoint", description="项目指派记录表")
public class ProjectAppoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "项目id")
    private Integer projectId;

    @ApiModelProperty(value = "指派方id")
    private Integer appointUnitId;

    @ApiModelProperty(value = "接收方id")
    private Integer receiveUnitId;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "创建者id")
    private Integer createUserId;

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

    public Integer getProjectId() {
		return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getAppointUnitId() {
		return appointUnitId;
    }

    public void setAppointUnitId(Integer appointUnitId) {
        this.appointUnitId = appointUnitId;
    }

    public Integer getReceiveUnitId() {
		return receiveUnitId;
    }

    public void setReceiveUnitId(Integer receiveUnitId) {
        this.receiveUnitId = receiveUnitId;
    }

    public Date getStartTime() {
		return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
		return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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


    @Override
    public String toString() {
        return "ProjectAppoint{" +
            "  id=" + id +
            ", projectId=" + projectId +
            ", appointUnitId=" + appointUnitId +
            ", receiveUnitId=" + receiveUnitId +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", createUserId=" + createUserId +
            ", status=" + status +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
