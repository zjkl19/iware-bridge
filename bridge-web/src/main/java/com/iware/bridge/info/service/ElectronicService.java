package com.iware.bridge.info.service;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.vo.ElectronicArchivesVO;
import com.iware.bridge.model.entity.global.ElectronicArchives;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ZRB
 * @date 2021-7-29
 */

public interface ElectronicService {

    /** 分页获取电子档案 */
    public PageInfo<ElectronicArchivesVO> listByPage(Integer pageNum, Integer pageSize, ElectronicArchives electronicArchives);

    /** 添加电子档案 */
    public void addElectronic(MultipartFile file, ElectronicArchives electronicArchives);

    /** 修改电子档案 */
    public void updElectronic(MultipartFile file, ElectronicArchives electronicArchives);

    /** 删除电子档案 */
    public void delElectronic(Integer electronicId);

    /** 根据id查询电子档案 */
    public ElectronicArchives findById(Integer id);

}
