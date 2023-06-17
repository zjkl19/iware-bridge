package com.iware.bridge.video.service;

import com.iware.bridge.model.entity.global.Video;
import com.iware.bridge.video.vo.StructureVideoVO;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-8-04
 */

public interface VideoService {

    /** 获取萤石云accessToken */
    public String getAccessToken();

    /** 查询所有结构物及其视频列表 */
    List<StructureVideoVO> getStructureVideoList(Integer projectId);

    /** 新增视频点 */
    public void addVideo(Video video);

    /** 修改视频点 */
    public void updVideo(Video video);

    /** 删除视频点 */
    public void deleteById(Integer id);
}
