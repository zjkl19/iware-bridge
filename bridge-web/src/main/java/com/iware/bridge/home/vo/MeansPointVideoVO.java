package com.iware.bridge.home.vo;

import com.iware.bridge.model.entity.global.Video;
import com.iware.bridge.online.vo.MeansPointVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value="MeansPointVideoVO", description="测点、视频对象")
public class MeansPointVideoVO implements Serializable {

    private static final long serialVersionUID = 1177966100970511032L;

    @ApiModelProperty(value = "测点列表")
    private List<MeansPointVO> meansPointVOList;
    @ApiModelProperty(value = "视频列表")
    private List<Video> videoList;

    public List<MeansPointVO> getMeansPointVOList() {
        return meansPointVOList;
    }

    public void setMeansPointVOList(List<MeansPointVO> meansPointVOList) {
        this.meansPointVOList = meansPointVOList;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}
