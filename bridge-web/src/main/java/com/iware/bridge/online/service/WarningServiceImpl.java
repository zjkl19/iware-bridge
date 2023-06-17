package com.iware.bridge.online.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.service.CommonService;
import com.iware.bridge.model.dao.online.SensorDao;
import com.iware.bridge.model.entity.online.MeansPoint;
import com.iware.bridge.model.entity.online.Sensor;
import com.iware.bridge.model.entity.online.SensorConverge;
import com.iware.bridge.online.dao.SensorExpDao;
import com.iware.bridge.online.dao.WarningRecordExpDao;
import com.iware.bridge.online.vo.ProcessFilter;
import com.iware.bridge.online.vo.WarningRatio;
import com.iware.bridge.online.vo.WarningRecordFilter;
import com.iware.bridge.online.vo.WarningRecordVO;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.properties.PowerProperties;
import com.iware.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WJP
 * @date 2021-7-27
 */

@Service
public class WarningServiceImpl implements WarningService {

    @Autowired
    private PowerProperties power;
    @Autowired
    private CommonService commonService;
    @Autowired
    private PowerProperties powerProperties;
    @Autowired
    private WarningRecordExpDao warningRecordExpDao;
    @Autowired
    private SensorDao sensorDao;
    @Autowired
    private SensorExpDao sensorExpDao;

    @Override
    public PageInfo<WarningRecordVO> listWarningRecord(Integer pageNum, Integer pageSize, WarningRecordFilter filter) {
        PageHelper.startPage(pageNum, pageSize);
        filter.setUnitId(ThreadLocalMap.getUnitId());
        filter.setRoleId(ThreadLocalMap.getRoleId());
        filter.setPowerId(power.getOnlinePower());
        List<WarningRecordVO> list = warningRecordExpDao.selectWarning(filter);
        return new PageInfo<>(list);
    }

    @Override
    public void batchProcessing(ProcessFilter filter) {
        filter.setHandlerUserId(ThreadLocalMap.getUserId());
        warningRecordExpDao.updateWarningStatus(filter);
    }

    @Override
    public List<WarningRatio> getWarningStatistics(Integer type, Integer structureId) {
        WarningRecordFilter filter = new WarningRecordFilter();
        filter.setUnitId(ThreadLocalMap.getUnitId());
        filter.setRoleId(ThreadLocalMap.getRoleId());
        filter.setStartTime(DateUtils.getFirstTime(type));
        filter.setEndTime(new Date());
        filter.setStructureId(structureId);
        return warningRecordExpDao.getWarningStatistics(powerProperties.getOnlinePower(), filter);
    }

    @Override
    public Map<String, String> getSensorData(SensorConverge sensorConverge) {

        Map<String, String> result = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        Sensor sensor = sensorDao.findById(sensorConverge.getSensorId());
        MeansPoint meansPoint = new MeansPoint();
        Long interval;
        if (sensor != null && sensor.getWarningInterval() != null) {
            interval = sensor.getWarningInterval() * 1000L;
            Integer type = commonService.getSensorType(meansPoint.getCompanyId(), sensorConverge.getSensorDetailsId());
            result = warningRecordExpDao.selectDataNearWarning(sensor.getSensorCoding(), type,
                    sdf.format(sensorConverge.getSamplingTime().getTime() - interval),
                    sdf.format(sensorConverge.getSamplingTime().getTime() + interval));
        }
        return result;
    }

    @Override
    public Map<String, Integer> getOnlineNotice() {
        Map<String, Integer> map = new HashMap<>();
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer offlineCount = sensorExpDao.selectSensorCount(unitId, roleId,
                power.getOnlinePower(), null, 0);
        map.put("offlineCount", offlineCount == null ? 0 : offlineCount);
        Integer warningCount = warningRecordExpDao.getWarningCount(roleId, unitId, power.getOnlinePower(), 0);
        map.put("warningCount", warningCount == null ? 0 : warningCount);
        return map;
    }
}
