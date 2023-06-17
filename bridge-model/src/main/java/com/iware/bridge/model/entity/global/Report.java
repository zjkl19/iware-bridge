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

@ApiModel(value="Report", description="报表信息")
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "项目id")
    private Integer projectId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "上传人")
    private Integer userId;

    @ApiModelProperty(value = "1:监测报表 2:巡查报表 3:维养报表")
    private Integer type;

    @ApiModelProperty(value = "1:日报 2:月报 3:年报")
    private Integer specific;

    @ApiModelProperty(value = "路径")
    private String path;

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

    public String getName() {
		return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
		return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
		return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSpecific() {
		return specific;
    }

    public void setSpecific(Integer specific) {
        this.specific = specific;
    }

    public String getPath() {
		return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        return "Report{" +
            "  id=" + id +
            ", projectId=" + projectId +
            ", name=" + name +
            ", userId=" + userId +
            ", type=" + type +
            ", specific=" + specific +
            ", path=" + path +
            ", status=" + status +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
