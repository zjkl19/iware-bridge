package com.iware.bridge.inspection.dao;


import com.iware.bridge.inspection.vo.DiseaseNormalOptionRel;
import com.iware.bridge.inspection.vo.InspectionDiseaseInstanceVO;
import com.iware.bridge.inspection.vo.InspectionDiseaseTableCellVo;
import com.iware.bridge.model.entity.inspection.InspectionDisease;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linbinxiang
 * 2021-8-5
 */

@Repository
public interface InspectionDiseaseExpDao {

    public List<InspectionDiseaseInstanceVO> listDisease(@Param("planDetailId") Integer planDetailId,
                                                         @Param("keyword") String keyword);

    public List<InspectionDiseaseInstanceVO> listDiseaseWithTableRowLoop(@Param("planDetailId") Integer planDetailId,
                                                                         @Param("keyword") String keyword);

    public List<InspectionDiseaseInstanceVO> listDiseaseWithTablePlaceholder(@Param("planDetailId") Integer planDetailId,
                                                                         @Param("keyword") String keyword);

    public List<InspectionDiseaseInstanceVO> listProjectDisease(@Param("keyword") String keyword,
                                                                @Param("structureType") Integer structureType,
                                                                @Param("projectId") Integer projectId,
                                                                @Param("unitId") Integer unitId,
                                                                @Param("roleId") Integer roleId,
                                                                @Param("powerId") Integer powerId);

    public InspectionDiseaseInstanceVO selDiseaseByMaintainId(@Param("maintainId") Integer maintainId);

    public List<Integer> selectOptionIdsByDiseaseInstanceId(@Param("instanceId") Integer instanceId);

    public List<InspectionDisease> selectInspectionDiseaseByStructureBridgeType(@Param("bridgeType") Integer bridgeType);

    /** 获取巡查病害表树结构 */
    public List<InspectionDiseaseTableCellVo> selectInspectionDiseaseTreeByBridgeType(@Param("bridgeType") Integer bridgeType,
                                                                                      @Param("keywords") ArrayList<String> keywords);

    public List<DiseaseNormalOptionRel> getNormalOptionNameByDiseaseId();
}
