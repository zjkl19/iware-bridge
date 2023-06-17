package com.iware.bridge.info.dao;

import com.iware.bridge.info.vo.ElectronicArchivesVO;
import com.iware.bridge.model.entity.global.ElectronicArchives;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectronicArchivesExpDao {

    /** 分页获取电子档案 */
    public List<ElectronicArchivesVO> listByPage(ElectronicArchives electronicArchives);

    /** 修改电子档案 */
    public void update(ElectronicArchives electronicArchives);
}
