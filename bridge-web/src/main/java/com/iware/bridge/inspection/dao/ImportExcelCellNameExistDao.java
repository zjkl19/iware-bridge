package com.iware.bridge.inspection.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportExcelCellNameExistDao {

    public Integer countProjectNameExist(@Param("unitId")Integer unitId, @Param("roleId") Integer roleId,
                                         @Param("powerId") Integer powerId, @Param("names") List<String> names);

    public Integer countStructureNameExist(@Param("unitId")Integer unitId, @Param("roleId") Integer roleId,
                                           @Param("powerId") Integer powerId, @Param("names") List<String> names);

    public Integer countUserNameExist(@Param("names") List<String> names);

    public Integer selectStructureIdByName(@Param("structureName")String structureName);

    public Integer selectInstanceIdByInspectionDiseaseName(@Param("diseasePart") String diseasePart,
                                                           @Param("checkItem") String checkItem,
                                                           @Param("damageType") String damageType);

    public Integer selectProjectIdByName(@Param("projectName") String projectName);

}
