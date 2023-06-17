package com.iware.bridge.evaluation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "ShowManage", description = "导出记录信息")
public class ShowManage {
    @ApiModelProperty(value = "线路名称")
    private String roadName;
    @ApiModelProperty(value = "跨名称")
    private String spanName;
    @ApiModelProperty(value = "构件类型")
    private String type;
    @ApiModelProperty(value = "照片信息")
    private List<PhotoUrl> photoUrls;
    @ApiModelProperty(value = "病害信息")
    private List<DetectionRecord> list;

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

    public List<PhotoUrl> getPhotoUrls() {
        return photoUrls;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPhotoUrls(List<PhotoUrl> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<DetectionRecord> getList() {
        return list;
    }

    public void setList(List<DetectionRecord> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ShowManage{" +
                "roadName='" + roadName + '\'' +
                ", spanName=" + spanName +
                ", type='" + type + '\'' +
                ", photoUrls=" + photoUrls +
                ", list=" + list +
                '}';
    }
}
