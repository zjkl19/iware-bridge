package com.iware.bridge.info.service;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.vo.ReportVO;
import com.iware.bridge.model.entity.global.Report;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ZRB
 * @date 2021-7-29
 */

public interface ReportService {

    /** 获取报告列表 */
    public PageInfo<ReportVO> listByPage(Integer pageNum, Integer pageSize, Report report);

    /** 添加报告 */
    public void addReport(MultipartFile file, Report report);

    /** 修改报告 */
    public void updateReport(MultipartFile file, Report report);

    /** 删除报告 */
    public void deleteReport(Integer reportId);

    /** 获取报告 */
    public Report findById(Integer id);
}
