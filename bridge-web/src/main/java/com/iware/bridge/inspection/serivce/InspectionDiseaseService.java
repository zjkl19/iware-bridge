package com.iware.bridge.inspection.serivce;

import com.iware.bridge.inspection.vo.InspectionDiseaseInstanceVO;
import com.iware.bridge.inspection.vo.InspectionDiseaseTableCellVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author LBX
 * @date 2021-8-2
 */

public interface InspectionDiseaseService {

    /** 获取病害情况 */
    public List<InspectionDiseaseInstanceVO> listDisease(Integer planDetailId, String keyword,Integer structureType,Integer projectId);

    /** 获取巡查病害表内容 */
    public HashMap<String,List<InspectionDiseaseTableCellVo>> selectInspectionDiseaseByStructureBridgeType(@Param("bridgeType") Integer bridgeType);


}
