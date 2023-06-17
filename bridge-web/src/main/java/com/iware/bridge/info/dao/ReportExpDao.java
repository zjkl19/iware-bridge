package com.iware.bridge.info.dao;

import com.iware.bridge.info.vo.ReportVO;
import com.iware.bridge.model.entity.global.Report;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportExpDao {

    /** 获取报告列表 */
    public List<ReportVO> listByPage(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId, @Param("model") Report report);
}
