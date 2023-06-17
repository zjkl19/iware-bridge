package com.iware.bridge.app.assess.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppOfflineExpDao {
    public void updateDiseaseInstanceSortByModifyTime(@Param("monitorPlanStructureRelId") Integer monitorPlanStructureRelId);
}
