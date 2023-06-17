package com.iware.bridge.inspection.serivce;

import com.iware.bridge.inspection.dao.OverViewExpDao;
import com.iware.bridge.inspection.vo.*;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.properties.PowerProperties;
import com.iware.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LBX
 * @date 2021-7-29
 */

@Service
public class OverviewServiceImpl implements OverviewService {


    @Autowired
    private PowerProperties power;
    @Autowired
    private OverViewExpDao overViewExpDao;

    @Override
    public Map<String, Integer> getInspectionCount() {

        Map<String, Integer> map = new HashMap<>();
        Integer powerId = power.getInspectionPower();
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();

        Integer totalCount = overViewExpDao.countInspectionPlan(roleId, unitId, powerId,null);
        map.put("totalCount", totalCount);
        Integer monthCount = overViewExpDao.countInspectionPlan(roleId, unitId, powerId,
                DateUtils.formatDate(new Date(), "yyyy-MM"));
        map.put("monthCount", monthCount);
        Integer diseaseCount = overViewExpDao.countLastInspectionDisease(roleId, unitId, powerId,null);
        map.put("diseaseCount", diseaseCount);
        Integer structureCount = overViewExpDao.countInspectionStructure(roleId, unitId, powerId);
        map.put("structureCount", structureCount);
        return map;
    }

    @Override
    public Map<String, Double> getMaintainCount() {
        Map<String, Double> map = new HashMap<>();
        Integer powerId = power.getMaintainPower();
        Integer structureCount = overViewExpDao.countMaintainStructure(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), powerId);
        map.put("structureCount", structureCount != null ? (double)structureCount : 0);
        Integer pendingCount = overViewExpDao.countLastInspectionDisease(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), powerId, 2);
        map.put("pendingCount", pendingCount != null ? (double)pendingCount : 0);
        Integer ProcessedCount = overViewExpDao.countMaintainDisease(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), powerId);
        map.put("ProcessedCount", ProcessedCount != null ? (double)ProcessedCount : 0);
        //金额只统计已完成的
        Double totalAmount = overViewExpDao.sumMaintainAmount(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), powerId);
        map.put("totalAmount", totalAmount != null ? totalAmount : 0);
        return map;
    }

    @Override
    public List<Date> getWorkRecord(Integer type, Integer year, Integer month) {
        Integer powerId;
        if(type == 1){
            powerId = power.getInspectionPower();
        }else {
            powerId = power.getMaintainPower();
        }
        String filterTime = month < 10 ? year + "-0" + month : year + "-" + month;
        return overViewExpDao.getWorkRecord(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), powerId, type, filterTime);
    }

    @Override
    public List<DiseaseRatioVo> getDiseaseRatio() {
        Integer powerId = power.getInspectionPower();
        List<DiseaseRatioVo> diseaseRatioVos = overViewExpDao.getDiseaseRatio(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), powerId);
        diseaseRatioVos.removeIf(i -> i == null || i.getDiseasePart() == null);
        return diseaseRatioVos;
    }

    @Override
    public Map<String, Integer> getDiseaseCount() {
        Map<String, Integer> map = new HashMap<>();
        Integer powerId = power.getMaintainPower();
        Integer pendingCount = overViewExpDao.countLastInspectionDisease(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), powerId, 2);
        map.put("pendingCount", pendingCount != null ? pendingCount : 0);
        Integer ProcessedCount=overViewExpDao.countMaintainDisease(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), powerId);
        map.put("ProcessedCount", ProcessedCount != null ? ProcessedCount : 0);
        return map;
    }

    @Override
    public List<StructureGradeRatioVo> getMaintainGradeRatio() {
        //结构物统计不包括隧道
        Integer powerId = power.getMaintainPower();
        return overViewExpDao.getMaintainGradeRatio(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), powerId);
    }

    @Override
    public List<MaintainTypeVo> getTypeRatio() {
        Integer powerId = power.getMaintainPower();
        return overViewExpDao.getTypeRatio(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), powerId);
    }

    @Override
    public List<InspectionDiseaseDateVo> getDiseaseTrend(Integer year, Integer month) {
        Integer powerId = power.getInspectionPower();
        String filterTime;
        if(month < 10) {
            filterTime = year + "-0" + month;
        }else{
            filterTime = year + "-" + month;
        }
        return overViewExpDao.getDiseaseTrend(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), powerId, filterTime);
    }

    @Override
    public List<MaintainAccountMonthVo> getAccountTrend(Integer year, Integer month) {
        //金额只统计已完成的
        Integer powerId = power.getMaintainPower();
        String beginMonth;
        String endMonth;
        endMonth = month < 10 ? year + "-0" + month : year + "-" + month;
        month = month == 12 ? 1 : month + 1;
        year -= 1;
        beginMonth = month < 10 ? year + "-0" + month : year + "-" + month;
        return overViewExpDao.getAccountTrend(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), powerId, beginMonth, endMonth);
    }
}
