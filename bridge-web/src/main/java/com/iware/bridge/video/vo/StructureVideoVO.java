package com.iware.bridge.video.vo;

import com.iware.bridge.model.entity.global.Video;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "StructureVideoVO",description = "结构物视频信息")
public class StructureVideoVO implements Serializable {

    private static final long serialVersionUID = -1224122100970511032L;

    @ApiModelProperty(value="项目id")
    private Integer projectId;
    @ApiModelProperty(value="结构物id")
    private Integer structureId;
    @ApiModelProperty(value="结构物名称")
    private String name;
    @ApiModelProperty(value="视频列表")
    private List<Video> videoList;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}
