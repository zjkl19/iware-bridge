package com.iware.bridge.evaluation.dao;

import com.iware.bridge.evaluation.vo.PhotoUrl;
import com.iware.bridge.evaluation.vo.StructPhoto;
import com.iware.bridge.evaluation.vo.StructPhotoFilter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WJP
 * @date 2021-8-13
 */
@Repository
public interface AttachmentExpDao {

    /** 获取桥梁图片 **/
    public List<StructPhoto> getStrunctPhotoBySelfFilter(@Param("structPhotoFilter") StructPhotoFilter structPhotoFilter,
                                                         @Param("limit") Integer limit);

    /** 查询照片数量 **/
    public Integer getCountForFilter(StructPhotoFilter structPhotoFilter);

    /** 获取病害图片地址 **/
    public List<PhotoUrl> selectPhotoUrl(@Param("targetId") Integer targetId);

    /** 桥面系的照片状态变为回收 **/
    public int updatePhotoStatusTo0DK(@Param("id") Integer id, @Param("state") Integer state, @Param("time") String time);

    /** 上部结构的照片状态变为回收 **/
    public int updatePhotoStatusTo0SUB(@Param("id") Integer id, @Param("state") Integer state, @Param("time") String time);

    /** 下部结构的照片状态变为回收 **/
    public int updatePhotoStatusToSUP(@Param("id") Integer id, @Param("state") Integer state, @Param("time") String time);

    /** 根据结构物计划id查询桥面系的病害位置图 **/
    public List<PhotoUrl> selectAttachmentByDK(@Param("structureId") Integer structureId);

    /** 根据结构物计划id查询上部结构的病害位置图 **/
    public List<PhotoUrl> selectAttachmentBySUP(@Param("structureId") Integer structureId);

    /** 根据结构物计划id查询下部结构的病害位置图  **/
    public List<PhotoUrl> selectAttachmentBySUB(@Param("structureId") Integer structureId);

    /** 根据线路id获取桥梁现状照片*/
    public List<PhotoUrl> selectPhotoByRoadId(@Param("id") Integer id);

    /** 根据线路id查询桥面系的病害位置图 **/
    public List<PhotoUrl> selectAttachmentByDKByRoadId(@Param("roadId") Integer roadId);

    /** 根据线路id查询上部结构的病害位置图 **/
    public List<PhotoUrl> selectAttachmentBySUPByRoadId(@Param("roadId") Integer roadId);

    /** 根据线路id查询下部结构的病害位置图  **/
    public List<PhotoUrl> selectAttachmentBySUBByRoadId(@Param("roadId") Integer roadId);
}
