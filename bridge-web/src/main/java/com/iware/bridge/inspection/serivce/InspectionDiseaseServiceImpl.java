package com.iware.bridge.inspection.serivce;

import com.iware.bridge.inspection.dao.InspectionDiseaseExpDao;
import com.iware.bridge.inspection.vo.InspectionDiseaseInstanceVO;
import com.iware.bridge.inspection.vo.InspectionDiseaseTableCellVo;
import com.iware.bridge.model.dao.global.PhotoDao;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.properties.PowerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author LBX
 * @date 2021-8-2
 */

@Service
public class InspectionDiseaseServiceImpl implements InspectionDiseaseService {

    @Autowired
    private InspectionDiseaseExpDao inspectionDiseaseExpDao;
    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private PowerProperties power;

    @Override
    public List<InspectionDiseaseInstanceVO> listDisease(Integer planDetailId, String keyword,Integer structureType,Integer projectId) {
        List<InspectionDiseaseInstanceVO> result;
        if(projectId!=null) {
            result = inspectionDiseaseExpDao.listProjectDisease(keyword,structureType, projectId,
                    ThreadLocalMap.getUnitId(),ThreadLocalMap.getRoleId(),power.getInspectionPower());
        }else{
            result= inspectionDiseaseExpDao.listDisease(planDetailId, keyword);
        }
        for (InspectionDiseaseInstanceVO diseaseInstanceVO : result){
            Photo condition = new Photo();
            condition.setTargetId(diseaseInstanceVO.getId());
            condition.setType(GlobalConstant.PHOTO_INSPECTION);
            List<Photo> photos = photoDao.query(condition);
            diseaseInstanceVO.setPhoto(photos);
            diseaseInstanceVO.setOptionIds(inspectionDiseaseExpDao.selectOptionIdsByDiseaseInstanceId(diseaseInstanceVO.getId()));
        }
        return result;
    }

    @Override
    public HashMap<String, List<InspectionDiseaseTableCellVo>> selectInspectionDiseaseByStructureBridgeType(Integer bridgeType) {
        HashMap<String, List<InspectionDiseaseTableCellVo>> result=new HashMap<>();
        ArrayList<String> queryKeys=new ArrayList<>();
        if(bridgeType!=0){
            queryKeys.add("桥名牌/限载牌");
        }else{
            queryKeys.add("洞门");
            queryKeys.add("洞口");
            queryKeys.add("路面");
            queryKeys.add("检修道");
            queryKeys.add("衬砌");
            queryKeys.add("排水设施");
            queryKeys.add("内饰");
        }
        result.put("list1",inspectionDiseaseExpDao.selectInspectionDiseaseTreeByBridgeType(bridgeType,queryKeys));
        queryKeys.clear();
        if(bridgeType!=0){
            queryKeys.add("桥面系");
        }else{
            queryKeys.add("吊顶/预埋件");
        }
        result.put("list2",inspectionDiseaseExpDao.selectInspectionDiseaseTreeByBridgeType(bridgeType,queryKeys));
        queryKeys.clear();
        if(bridgeType!=0){
            queryKeys.add("上部结构");
            queryKeys.add("台/墩/附属物");
        }else{
            queryKeys.add("标志标线轮廓标");
        }
        result.put("list3",inspectionDiseaseExpDao.selectInspectionDiseaseTreeByBridgeType(bridgeType,queryKeys));
        queryKeys.clear();
        if(bridgeType!=0){
            queryKeys.add("施工");
            queryKeys.add("其他病害因素");
            queryKeys.add("其他说明");
        }else{
            queryKeys.add("其它说明");
        }
        result.put("list4",inspectionDiseaseExpDao.selectInspectionDiseaseTreeByBridgeType(bridgeType,queryKeys));
        return result;
    }
}
