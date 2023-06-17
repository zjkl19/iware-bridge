package com.iware.bridge.info.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.dao.ReportExpDao;
import com.iware.bridge.info.vo.ReportVO;
import com.iware.bridge.model.dao.global.ReportDao;
import com.iware.bridge.model.entity.global.Report;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.FileTypeEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.pojo.FileData;
import com.iware.common.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-29
 */

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;
    @Autowired
    private ReportExpDao reportExpDao;

    @Override
    public PageInfo<ReportVO> listByPage(Integer pageNum, Integer pageSize, Report report) {
        PageHelper.startPage(pageNum, pageSize);
        List<ReportVO> list = reportExpDao.listByPage(ThreadLocalMap.getRoleId(), ThreadLocalMap.getUnitId(), report);
        return new PageInfo<>(list);
    }
    private FileTypeEnum getSuffix(Integer type) {
        FileTypeEnum fileTypeEnum = null;
        switch (type) {
            case 1: fileTypeEnum = FileTypeEnum.ONLINE_REPORT;break; //在线监测
            case 2: fileTypeEnum = FileTypeEnum.INSPECTION_REPORT;break; //日常巡查
            case 3: fileTypeEnum = FileTypeEnum.MAINTAIN_REPORT;break; //维养报表
            default:break;
        }
        return fileTypeEnum;
    }

    @Override
    public void addReport(MultipartFile file, Report report) {
        FileData fileInfo;
        FileTypeEnum fileTypeEnum = this.getSuffix(report.getType());

        fileInfo = FileUtil.uploadFile(file, fileTypeEnum.getCode());
        if (fileInfo == null) {
            throw new BusinessException("上传文件内容不得为空");
        }
        report.setStatus(1);
        report.setUserId(ThreadLocalMap.getUserId());
        report.setPath(fileInfo.getFilePath());
        reportDao.save(report);
    }

    @Override
    public void updateReport(MultipartFile file, Report report) {

        if (file != null && !file.isEmpty()) {
            FileData fileInfo = new FileData();
            FileTypeEnum fileTypeEnum = this.getSuffix(report.getType());

            // 获取旧报表
            Report oldRecord = reportDao.findById(report.getId());

            //根据旧报表里面的文件路径删除源文件
            FileUtil.delFileData(oldRecord.getPath());

            // 保存新的文件
            if (fileTypeEnum != null) {
                fileInfo = FileUtil.uploadFile(file, fileTypeEnum.getCode());
            }

            report.setPath(fileInfo.getFilePath());
        }
        reportDao.update(report);
    }

    @Override
    public void deleteReport(Integer reportId) {
        Report report = reportDao.findById(reportId);
        FileUtil.delFileData(report.getPath());
        reportDao.deleteById(reportId);

    }

    @Override
    public Report findById(Integer id) {
        return reportDao.findById(id);
    }
}
