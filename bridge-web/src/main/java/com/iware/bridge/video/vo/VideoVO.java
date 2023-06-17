package com.iware.bridge.video.vo;

import com.iware.bridge.model.entity.global.Video;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "VideoVO",description = "视频信息")
public class VideoVO extends Video implements Serializable {

    private static final long serialVersionUID = -4444122100970511032L;

    @ApiModelProperty(value="项目id")
    private Integer projectId;
    @ApiModelProperty(value="结构物名称")
    private String structureName;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }
}
