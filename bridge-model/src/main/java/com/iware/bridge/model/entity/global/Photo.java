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

@ApiModel(value="Photo", description="图片表")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "1:桥梁图片 2:隧道图片")
    private Integer type;

    @ApiModelProperty(value = "目标id")
    private Integer targetId;

    @ApiModelProperty(value = "文件名")
    private String name;

    @ApiModelProperty(value = "存储位置")
    private String path;

    @ApiModelProperty(value = "是否加载地形")
    private Integer terrain;

    @ApiModelProperty(value = "备注")
    private String remarks;

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

    public Integer getType() {
		return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTargetId() {
		return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getName() {
		return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
		return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getTerrain() {
		return terrain;
    }

    public void setTerrain(Integer terrain) {
        this.terrain = terrain;
    }

    public String getRemarks() {
		return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        return "Photo{" +
            "  id=" + id +
            ", type=" + type +
            ", targetId=" + targetId +
            ", name=" + name +
            ", path=" + path +
            ", terrain=" + terrain +
            ", remarks=" + remarks +
            ", status=" + status +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
