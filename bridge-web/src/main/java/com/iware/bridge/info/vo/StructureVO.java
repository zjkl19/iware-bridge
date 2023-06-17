package com.iware.bridge.info.vo;

import com.iware.bridge.model.entity.global.BridgeInfo;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.global.Video;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "StructureVO",description = "结构物信息")
public class StructureVO extends Structure implements Serializable {

    private static final long serialVersionUID = -1114126100970511032L;

    @ApiModelProperty(value="项目名称")
    private String projectName;
    @ApiModelProperty(value="桥梁类型")
    private String bridgeTypeName;
    @ApiModelProperty(value="桥梁详情")
    private BridgeInfo bridgeInfo;
    @ApiModelProperty(value="图片列表")
    private List<Photo> photoList;
    @ApiModelProperty(value="视频列表")
    private List<Video> videoList;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBridgeTypeName() {
        return bridgeTypeName;
    }

    public void setBridgeTypeName(String bridgeTypeName) {
        this.bridgeTypeName = bridgeTypeName;
    }

    public BridgeInfo getBridgeInfo() {
        return bridgeInfo;
    }

    public void setBridgeInfo(BridgeInfo bridgeInfo) {
        this.bridgeInfo = bridgeInfo;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}
