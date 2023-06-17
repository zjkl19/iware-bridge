package com.iware.bridge.online.service;

import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.online.dao.SensorExpDao;
import com.iware.bridge.online.dao.WarningRecordExpDao;
import com.iware.bridge.online.dto.WarningTimeDto;
import com.iware.bridge.online.vo.WarningRecordFilter;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.properties.PowerProperties;
import com.iware.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WJP
 * @date 2021-7-27
 */

@Service
public class StructureMonitorServiceImpl implements StructureMonitorService {

    @Autowired
    private WarningRecordExpDao warningRecordExpDao;
    @Autowired
    private SensorExpDao sensorExpDao;
    @Autowired
    private PowerProperties power;

    @Override
    public Map<String, Integer> getCount(Integer structureId) {
        Map<String, Integer> map = new HashMap<>();
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer today = warningRecordExpDao.selectTodayWarningCount(unitId, roleId,power.getOnlinePower(), structureId, null);
        Integer online = sensorExpDao.selectSensorCount(unitId, roleId, power.getOnlinePower(), structureId, 1);
        Integer offline = sensorExpDao.selectSensorCount(unitId, roleId, power.getOnlinePower(), structureId, 0);
        map.put("online", online);
        map.put("offline", offline);
        map.put("todayWarning", today);
        map.put("warningTend", today - warningRecordExpDao.selectYesterdayWarningCount(unitId, roleId,power.getOnlinePower(), structureId));
        map.put("todayPending", warningRecordExpDao.selectTodayWarningCount(unitId, roleId,power.getOnlinePower(), structureId, 0));

        return map;
    }

    @Override
    public List<EchartMap> getWarningRank(Integer structureId, Integer type) {
        WarningRecordFilter filter = new WarningRecordFilter();
        List<EchartMap> list;
        filter.setRoleId(ThreadLocalMap.getRoleId());
        filter.setUnitId(ThreadLocalMap.getUnitId());
        filter.setStructureId(structureId);
        filter.setStartTime(DateUtils.getFirstTime(type));
        filter.setPowerId(power.getOnlinePower());
        filter.setEndTime(new Date());
        list = warningRecordExpDao.selectWarningRank(filter);
        if (!CollectionUtils.isEmpty(list)) {
            list = this.fillEchartResult(list);
        }
        return list;
    }

    @Override
    public List<WarningTimeDto> getWarningTend(Integer type, Integer structure) {
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();
        List<WarningTimeDto> sensorTimeWarningDtos;
        if (type == 1) {
            sensorTimeWarningDtos = warningRecordExpDao.selectWarningTimeDay(unitId, roleId,power.getOnlinePower(), structure);
        } else if (type == 2) {
            sensorTimeWarningDtos = warningRecordExpDao.selectWarningTimeMonth(unitId, roleId,power.getOnlinePower(), structure);
        } else if (type == 3) {
            sensorTimeWarningDtos = warningRecordExpDao.selectWarningTimeYear(unitId, roleId,power.getOnlinePower(), structure);
        } else {
            sensorTimeWarningDtos = warningRecordExpDao.selectWarningTimeAll(unitId, roleId,power.getOnlinePower(), structure);
        }
        return sensorTimeWarningDtos;
    }

    public List<EchartMap> fillEchartResult(List<EchartMap> list) {
        List<EchartMap> result = new ArrayList<>();
        List<String> names = list.stream().map(EchartMap::getName).distinct().collect(Collectors.toList());
        for (String name: names) {
            List<EchartMap> echartMaps = list.stream().filter(item -> item.getName().equals(name)).collect(Collectors.toList());
            if (echartMaps.size() < 3) {

                Boolean hasEle1 = echartMaps.stream().anyMatch(a -> a.getStatus().equals("1"));
                Boolean hasEle2 = echartMaps.stream().anyMatch(a -> a.getStatus().equals("2"));
                Boolean hasEle3 = echartMaps.stream().anyMatch(a -> a.getStatus().equals("3"));

                if (!hasEle1) echartMaps.add(new EchartMap(name, 0, "1"));
                if (!hasEle2) echartMaps.add(new EchartMap(name, 0, "2"));
                if (!hasEle3) echartMaps.add(new EchartMap(name, 0, "3"));
            }
            result.addAll(echartMaps);
        }
        return result;
    }
}
