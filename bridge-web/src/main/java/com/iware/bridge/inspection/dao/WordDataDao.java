package com.iware.bridge.inspection.dao;

import com.iware.bridge.inspection.vo.InspectionWordDataVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author LBX
 * @date 2021-8-17
 */
@Repository
public interface WordDataDao {
    public InspectionWordDataVo getWordDataInspection(@Param("planId")Integer planId);
}
