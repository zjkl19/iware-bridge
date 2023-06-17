package com.iware.bridge.model.entity.evaluation;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author code-generation
 * @since 2021-7-21 17:00:00
 * @version 1.3.1
 */

@ApiModel(value="Attachment", description="附件表")
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "附件名称")
    private String name;

    @ApiModelProperty(value = "附件地址")
    private String path;

    @ApiModelProperty(value = "附件类型 1：线路图片 2：桥垮图片 3：部位图片 4：构件图片 5：病害图片")
    private Integer type;

    @ApiModelProperty(value = "目标id")
    private Integer targetId;

    @ApiModelProperty(value = "1：桥面系 2：上部结构 3：下部结构")
    private Integer partType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty(value = "1：正常 0：删除 2:选中")
    private Integer status;

    public Integer getId() {
		return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getPartType() {
		return partType;
    }

    public void setPartType(Integer partType) {
        this.partType = partType;
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

    public Integer getStatus() {
		return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Attachment{" +
            "  id=" + id +
            ", name=" + name +
            ", path=" + path +
            ", type=" + type +
            ", targetId=" + targetId +
            ", partType=" + partType +
            ", createTime=" + createTime +
            ", modifyTime=" + modifyTime +
            ", status=" + status +
        "}";
    }
}
