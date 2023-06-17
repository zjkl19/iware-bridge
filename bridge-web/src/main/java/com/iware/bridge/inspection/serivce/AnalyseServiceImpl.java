package com.iware.bridge.inspection.serivce;

import com.iware.bridge.inspection.dao.AnalyseExpDao;
import com.iware.bridge.inspection.vo.DiseaseRatioVo;
import com.iware.bridge.inspection.vo.DiseaseRepairVo;
import com.iware.bridge.inspection.vo.DiseaseTypeVo;
import com.iware.bridge.inspection.vo.MaintainMethodCycleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author LBX
 * @date 2021-7-29
 */

@Service
public class AnalyseServiceImpl implements AnalyseService {

    @Autowired
    private AnalyseExpDao analyseExpDao;

    @Override
    public List<DiseaseTypeVo> getDiseaseType(Integer structureId) {
        return analyseExpDao.getDiseaseType(structureId);
    }

    @Override
    public List<DiseaseRatioVo> compDiseaseRank(Integer structureId) {
        return analyseExpDao.getDiseasePartStructure(structureId, null, null);
    }

    @Override
    public List<DiseaseRatioVo> diseaseMonthFrequency(Integer structureId, Integer month) {
        String filterTime = Calendar.getInstance().get(Calendar.YEAR) + "-" + (month < 10 ? ("0" + month) : month);
        return analyseExpDao.getDiseasePartStructure(structureId, filterTime, null);
    }

    @Override
    public List<DiseaseRepairVo> diseaseRepair(Integer structureId) {
        return analyseExpDao.countDiseaseRepair(structureId);
    }

    @Override
    public Map<String, List<DiseaseRatioVo>> diseaseYearRank(Integer structureId) {
        HashMap<String, List<DiseaseRatioVo>> result = new HashMap<>();
        Integer nowYear = Calendar.getInstance().get(Calendar.YEAR);
        int count = 4;
        for(int year = nowYear; count >= 0; year--, count--) {
            List<DiseaseRatioVo> yearData = analyseExpDao.getDiseasePartStructure(structureId, null, year + "");
            result.put(year + "", yearData);
        }
        return result;
    }

    @Override
    public Map<String, Integer> getAccountTrend(Integer structureId) {
        HashMap<String, Integer> result = new HashMap<>();
        Integer nowYear = Calendar.getInstance().get(Calendar.YEAR);
        int count = 4;
        for(int year = nowYear; count >= 0; year--, count--) {
            Integer yearData = analyseExpDao.sumMaintainExpenditure(structureId, year + "");
            result.put(year + "", yearData);
        }
        return result;
    }

    @Override
    public Map<String, List<DiseaseRatioVo>> getRepairTrend(Integer structureId) {
        HashMap<String, List<DiseaseRatioVo>> result = new HashMap<>();
        Integer nowYear = Calendar.getInstance().get(Calendar.YEAR);
        int count = 4;
        for(int year = nowYear; count >= 0; year--, count--) {
            List<DiseaseRatioVo> yearData = analyseExpDao.countMaintainDiseasePart(structureId, year + "");
            result.put(year + "", yearData);
        }
        return result;
    }

    @Override
    public List<MaintainMethodCycleVo> itemRepeatAnalyse(Integer structureId) {
        return analyseExpDao.countMaintainMethodCycle(structureId);
    }

    @Override
    public List<DiseaseRatioVo> maintainRatio(Integer structureId) {
        Integer nowYear = Calendar.getInstance().get(Calendar.YEAR);
        List<DiseaseRatioVo> result = analyseExpDao.countMaintainDiseasePart(structureId, nowYear + "");
        Integer sumMaintain = 0;
        for(DiseaseRatioVo diseaseRatioVo : result){
            if(diseaseRatioVo == null){
                continue;
            }
            sumMaintain += diseaseRatioVo.getCount() != null ? diseaseRatioVo.getCount() : 0;
        }
        for(DiseaseRatioVo diseaseRatioVo : result){
            if(diseaseRatioVo == null){
                continue;
            }
            if(sumMaintain!=0) {
                diseaseRatioVo.setRate((Integer) Math.round((float) diseaseRatioVo.getCount() * 100 / (float) sumMaintain));
            }else{
                diseaseRatioVo.setRate(0);
            }
        }
        return result;
    }

    @Override
    public Map<String, Integer> maintainItemStatistics(Integer structureId) {
        HashMap<String, Integer> result = new HashMap<>();
        Integer nowYear = Calendar.getInstance().get(Calendar.YEAR);
        int count = 4;
        for(int year=nowYear; count >= 0; year--, count--) {
            Integer yearData = analyseExpDao.countMaintainItemStructure(structureId, year + "");
            result.put(year + "", yearData);
        }
        return result;
    }
}
