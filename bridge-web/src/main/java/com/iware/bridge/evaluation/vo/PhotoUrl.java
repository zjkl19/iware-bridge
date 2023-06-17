package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PhotoUrl", description = "照片信息")
public class PhotoUrl {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "文件名")
    private String name;
    @ApiModelProperty(value = "文件路径")
    private String path;
    @ApiModelProperty(value = "附件类型id")
    private Integer type;
    @ApiModelProperty(value = "构件类型id")
    private Integer partType;
    @ApiModelProperty(value = "病害实例化id")
    private Integer targetId;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "修改时间")
    private String modifyTime;
    @ApiModelProperty(value = "状态id")
    private Integer status;

    @ApiModelProperty(value = "线路名称")
    private String roadName;
    @ApiModelProperty(value = "桥跨名称")
    private String spanName;

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getSpanName() {
        return spanName;
    }

    public void setSpanName(String spanName) {
        this.spanName = spanName;
    }

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

    public Integer getPartType() {
        return partType;
    }

    public void setPartType(Integer partType) {
        this.partType = partType;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
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
        return "PhotoUrl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", type=" + type +
                ", partType=" + partType +
                ", targetId=" + targetId +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", status=" + status +
                ", roadName='" + roadName + '\'' +
                ", spanName=" + spanName +
                '}';
    }
}
