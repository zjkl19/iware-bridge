package com.iware.bridge.video.dao;

import com.iware.bridge.model.entity.global.Video;
import com.iware.bridge.video.vo.StructureVideoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: ZRB
 * @date: 2021-8-3
 */

@Repository
public interface VideoExpDao {

    /** 查询所有结构物及其视频列表 */
    public List<StructureVideoVO> getStructureVideoList(@Param("projectId") Integer projectId);

    /** 查询用户摄像头数 */
    public Integer getUnitVideoCount(@Param("unitId")Integer unitId,
                                     @Param("roleId") Integer roleId,
                                     @Param("powerId") Integer powerId);

    /** 查询用户结构物摄像头信息 */
    public List<Video> getVideoByStructure(@Param("roleId") Integer roleId,
                                           @Param("unitId")Integer unitId,
                                           @Param("structureId") Integer structureId,
                                           @Param("powerId") Integer powerId);
}
