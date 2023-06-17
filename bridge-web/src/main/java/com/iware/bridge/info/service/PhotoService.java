package com.iware.bridge.info.service;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.model.entity.global.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-6
 */

public interface PhotoService {

    /** 根据目标id、类型获取图片列表 */
    public PageInfo<Photo> listByPage(Integer pageNum, Integer pageSize, Integer targetId, Integer type);

    public List<Photo> getPhotoList(Integer targetId, Integer type);

    /** 批量上传文件 */
    public void uploadFiles(Photo photo, MultipartFile[] files);

    /** 上传模型 */
    public void modelUpload(MultipartFile file, Photo photo);

    /** 修改模型 */
    public void updateModel(MultipartFile file, Photo photo);

    /** 查询文件 */
    public Photo findById(Integer id);

    /** 删除文件 */
    public void delete(Integer photoId);

    /** 根据条件删除图片 */
    public void deleteByTypeAndTargetId(Integer targetId, Integer type);

}
