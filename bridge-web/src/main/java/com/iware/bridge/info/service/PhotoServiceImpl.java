package com.iware.bridge.info.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.model.dao.global.PhotoDao;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.common.enums.FileTypeEnum;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.pojo.FileData;
import com.iware.common.utils.FileUtil;
import com.iware.common.utils.ZipFileUnCompressingUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.iware.common.utils.FileUtil.genFileFullPath;

/**
 * @author ZRB
 * @date 2021-7-6
 */

@Service
public class PhotoServiceImpl implements PhotoService {

    @Value("${server.context-path}")
    private String contextPath;

    @Autowired
    private PhotoDao photoDao;


    @Override
    public PageInfo<Photo> listByPage(Integer pageNum, Integer pageSize,
                                      Integer targetId, Integer type) {
        PageHelper.startPage(pageNum, pageSize);

        Photo photoQry = new Photo();
        photoQry.setTargetId(targetId);
        photoQry.setType(type);
        List<Photo> list = photoDao.query(photoQry);
        return new PageInfo<>(list);
    }

    @Override
    public List<Photo> getPhotoList(Integer targetId, Integer type) {

        Photo photoQry = new Photo();
        photoQry.setTargetId(targetId);
        photoQry.setType(type);
        return photoDao.query(photoQry);
    }

    @Override
    @Transactional
    public void uploadFiles(Photo photo, MultipartFile[] files) {

        List<Photo> photoList = new ArrayList<>();
        FileTypeEnum fileTypeEnum = this.getSuffix(photo.getType());
        if (files.length > 0) {
            try {
                for(MultipartFile file : files) {
                    Photo addPhoto = new Photo();
                    BeanUtils.copyProperties(photo, addPhoto);
                    FileData fileInfo = FileUtil.uploadFile(file, fileTypeEnum, photo.getTargetId());
                    addPhoto.setPath(fileInfo.getFilePath());
                    addPhoto.setName(fileInfo.getName());
                    photoList.add(addPhoto);
                }
            } catch (Exception e) {
                if (!CollectionUtils.isEmpty(photoList)) {
                    for (Photo image : photoList) {
                        FileUtil.delFileData(image.getPath());
                    }
                }
                throw new BusinessException(GlobalResultEnum.FILE_UPLOAD_ERROR);
            }
            photoDao.batchSave(photoList);
        }
    }

    private FileTypeEnum getSuffix(Integer type) {
        FileTypeEnum fileTypeEnum = null;
        switch (type) {
            case 1: fileTypeEnum = FileTypeEnum.BRIDGE_PHOTO;break; //桥梁
            case 2: fileTypeEnum = FileTypeEnum.TUNNEL_PHOTO;break; //隧道
            case 3: fileTypeEnum = FileTypeEnum.BRIDGE_ANNEX_FILE;break; //桥梁模型
            case 4: fileTypeEnum = FileTypeEnum.TUNNEL_ANNEX_FILE;break; //隧道模型
            case 5: fileTypeEnum = FileTypeEnum.INSPECTION_PHOTO;break; //日常巡查
            case 6:
            case 7:
            case 8: fileTypeEnum = FileTypeEnum.MAINTAIN_PHOTO;break; //维修养护
            default:break;
        }
        return fileTypeEnum;
    }

    @Override
    public void delete(Integer photoId) {
        Photo photo = photoDao.findById(photoId);
        FileUtil.delFileData(photo.getPath());
        photoDao.deleteById(photoId);
    }

    @Override
    @Transactional
    public void deleteByTypeAndTargetId(Integer targetId, Integer type) {

        List<Photo> list = this.getPhotoList(targetId, type);
        List<Integer> ids = list.stream().map(Photo::getId).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(list)) {
            for(Photo photo : list) {
                FileUtil.delFileData(photo.getPath());
            }
            photoDao.batchDelete(ids);
        }
    }

    @Override
    public void modelUpload(MultipartFile file, Photo photo) {

        FileTypeEnum fileTypeEnum = this.getSuffix(photo.getType());

        FileData fileInfo = FileUtil.uploadFile(file, fileTypeEnum, photo.getTargetId());
        photo.setName(fileInfo.getName());
        photo.setPath(fileInfo.getFilePath());
        //解压缩
        ZipFileUnCompressingUtils.unzip(String.format("%s%s", fileInfo.getFullFilePath(),
                fileInfo.getFilePath().substring(fileInfo.getFilePath().lastIndexOf('/'))), fileInfo.getFullFilePath());
        photoDao.save(photo);
    }

    @Override
    public void updateModel(MultipartFile file, Photo photo) {

        String fileFullPath = "";
        if (file != null && !file.isEmpty()) {

            FileTypeEnum fileTypeEnum = this.getSuffix(photo.getType());

            if (fileTypeEnum != null) {
                fileFullPath = String.format("%s/%s",genFileFullPath(fileTypeEnum.getCode()), photo.getTargetId());
            } else {
                throw new BusinessException("模型上传失败");
            }

            //删除源文件
            FileUtil.deleteAllFiles(new File(fileFullPath));

            FileData fileInfo = FileUtil.uploadFile(file, fileTypeEnum, photo.getTargetId());
            photo.setName(fileInfo.getName());
            photo.setPath(fileInfo.getFilePath());
            //解压缩
            ZipFileUnCompressingUtils.unzip(String.format("%s%s", fileInfo.getFullFilePath(),
                    fileInfo.getFilePath().substring(fileInfo.getFilePath().lastIndexOf("/"))),
                    fileInfo.getFullFilePath());

        }
        photoDao.update(photo);
    }

    @Override
    public Photo findById(Integer id) {
        return photoDao.findById(id);
    }
}
