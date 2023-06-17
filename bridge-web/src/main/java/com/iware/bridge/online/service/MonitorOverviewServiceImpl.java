package com.iware.bridge.online.service;

import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.online.dao.SensorExpDao;
import com.iware.bridge.online.dao.WarningRecordExpDao;
import com.iware.bridge.online.dto.MonitoringSituationDto;
import com.iware.bridge.video.dao.VideoExpDao;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.properties.PowerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WJP
 * @date 2021-7-27
 */

@Service
public class MonitorOverviewServiceImpl implements MonitorOverviewService {

    @Autowired
    private PowerProperties power;
    @Autowired
    private WarningRecordExpDao warningRecordExpDao;
    @Autowired
    private SensorExpDao sensorExpDao;
    @Autowired
    private VideoExpDao videoExpDao;


    @Override
    public Map<String, Integer> getCount() {
        Map<String, Integer> map = new HashMap<>();

        Integer roleId = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();

        map.put("bridgeCount", sensorExpDao.selectSensorBridgeCount(unitId, roleId, power.getOnlinePower()));
        map.put("sensorCount", sensorExpDao.selectSensorCount(unitId, roleId, power.getOnlinePower(), null, null));
        map.put("videoCount", videoExpDao.getUnitVideoCount(unitId, roleId, power.getOnlinePower()));
        Integer today = warningRecordExpDao.selectTodayWarningCount(unitId, roleId,power.getOnlinePower(), null, null);
        map.put("todayWarning", today);
        map.put("warningTend", today - warningRecordExpDao.selectYesterdayWarningCount(unitId, roleId,power.getOnlinePower(), null));
        return map;
    }

    @Override
    public List<EchartMap> getSensorTypeList(Integer structureId) {
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();
        return sensorExpDao.selectSensorTypeCount(unitId, roleId,power.getOnlinePower(), structureId);
    }

    @Override
    public List<MonitoringSituationDto> getScoreRank(Integer projectId) {
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();
        List<MonitoringSituationDto> monitoringSituationDtos = sensorExpDao.selectBridgeSensorWarning(unitId, roleId,
                power.getOnlinePower(), projectId);
        for (MonitoringSituationDto monitoringSituationDto : monitoringSituationDtos) {
            Integer online = sensorExpDao.selectSensorCount(unitId, roleId,power.getOnlinePower(),
                    monitoringSituationDto.getStructureId(), 1);
            Integer offline = sensorExpDao.selectSensorCount(unitId, roleId,power.getOnlinePower(),
                    monitoringSituationDto.getStructureId(), 0);
            monitoringSituationDto.setSensorCount(online + offline);
            monitoringSituationDto.setOnlineSensorCount(online);
            monitoringSituationDto.setDefaultSensorCount(offline);
        }
        return monitoringSituationDtos;
    }
}
