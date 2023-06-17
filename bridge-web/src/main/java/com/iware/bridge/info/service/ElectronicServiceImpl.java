package com.iware.bridge.info.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.dao.ElectronicArchivesExpDao;
import com.iware.bridge.info.vo.ElectronicArchivesVO;
import com.iware.bridge.model.dao.global.ElectronicArchivesDao;
import com.iware.bridge.model.entity.global.ElectronicArchives;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.FileTypeEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.pojo.FileData;
import com.iware.common.utils.FileUtil;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-29
 */

@Service
public class ElectronicServiceImpl implements ElectronicService {

    @Autowired
    private ElectronicArchivesDao electronicArchivesDao;
    @Autowired
    private ElectronicArchivesExpDao electronicArchivesExpDao;

    @Override
    public PageInfo<ElectronicArchivesVO> listByPage(Integer pageNum, Integer pageSize, ElectronicArchives electronicArchives) {
        PageHelper.startPage(pageNum, pageSize);
        List<ElectronicArchivesVO> list = electronicArchivesExpDao.listByPage(electronicArchives);
        return new PageInfo<>(list);
    }

    @Override
    public void addElectronic(MultipartFile file, ElectronicArchives electronicArchives) {
        FileData fileInfo = new FileData();
        fileInfo = FileUtil.uploadFile(file, FileTypeEnum.ELECTRONIC_FILE.getCode(),
                electronicArchives.getStructureId());

        if (fileInfo == null) {
            throw new BusinessException("上传文件内容不得为空");
        }
        electronicArchives.setStatus(1);
        electronicArchives.setUserId(ThreadLocalMap.getUserId());
        electronicArchives.setPath(fileInfo.getFilePath());
        electronicArchivesDao.save(electronicArchives);
    }

    @Override
    public void updElectronic(MultipartFile file, ElectronicArchives electronicArchives) {

        if (file != null && !file.isEmpty()) {

            // 获取旧电子文档
            ElectronicArchives oldElectronic = electronicArchivesDao.findById(electronicArchives.getId());

            //根据旧电子文档的文件路径删除源文件
            FileUtil.delFileData(oldElectronic.getPath());

            // 保存新的文件
            FileData fileInfo = FileUtil.uploadFile(file, FileTypeEnum.ELECTRONIC_FILE.getCode(),
                    electronicArchives.getStructureId());
            electronicArchives.setPath(fileInfo.getFilePath());
        }
        electronicArchivesDao.update(electronicArchives);
    }

    @Override
    public void delElectronic(Integer electronicId) {
        ElectronicArchives electronicArchives = electronicArchivesDao.findById(electronicId);
        FileUtil.delFileData(electronicArchives.getPath());
        electronicArchivesDao.deleteById(electronicId);
    }

    @Override
    public ElectronicArchives findById(Integer id) {
        return electronicArchivesDao.findById(id);
    }
}
