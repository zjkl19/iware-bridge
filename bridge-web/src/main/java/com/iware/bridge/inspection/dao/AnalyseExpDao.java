package com.iware.bridge.inspection.dao;


import com.iware.bridge.inspection.vo.DiseaseRatioVo;
import com.iware.bridge.inspection.vo.DiseaseRepairVo;
import com.iware.bridge.inspection.vo.DiseaseTypeVo;
import com.iware.bridge.inspection.vo.MaintainMethodCycleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalyseExpDao {

    public List<DiseaseTypeVo> getDiseaseType(@Param("structureId") Integer structureId);

    public List<DiseaseRatioVo> getDiseasePartStructure(@Param("structureId") Integer structureId,
                                                        @Param("filterMonth") String filterMonth,
                                                        @Param("filterYear") String filterYear);

    public List<DiseaseRepairVo> countDiseaseRepair(@Param("structureId") Integer structureId);

    public Integer sumMaintainExpenditure(@Param("structureId") Integer structureId,
                                          @Param("filterYear") String filterYear);

    public Integer countMaintainItemStructure(@Param("structureId") Integer structureId,
                                              @Param("filterYear") String filterYear);

    public List<DiseaseRatioVo> countMaintainDiseasePart(@Param("structureId") Integer structureId,
                                                         @Param("filterYear") String filterYear);

    public List<MaintainMethodCycleVo> countMaintainMethodCycle(@Param("structureId") Integer structureId);


}
