package com.iware.bridge.online.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.model.dao.online.SensorLogDao;
import com.iware.bridge.model.dao.online.SensorRecordDao;
import com.iware.bridge.model.entity.online.SensorLog;
import com.iware.bridge.model.entity.online.SensorRecord;
import com.iware.bridge.online.dao.SensorLogExpDao;
import com.iware.bridge.online.vo.SensorLogFilter;
import com.iware.bridge.online.vo.SensorLogVO;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.properties.PowerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WJP
 * @date 2021-7-27
 */

@Service
public class SensorLogServiceImpl implements SensorLogService {

    @Autowired
    private PowerProperties power;
    @Autowired
    private SensorLogDao sensorLogDao;
    @Autowired
    private SensorLogExpDao sensorLogExpDao;
    @Autowired
    private SensorRecordDao sensorRecordDao;

    @Override
    public PageInfo<SensorLogVO> listSensorLog(Integer pageNum, Integer pageSize, SensorLogFilter filter) {
        PageHelper.startPage(pageNum, pageSize);
        List<SensorLogVO> list = sensorLogExpDao.listSensorLog(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), power.getOnlinePower(), filter);
        return new PageInfo<>(list);
    }

    @Override
    public void addSensorLog(SensorLog sensorLog) {
        sensorLogDao.save(sensorLog);
    }

    @Override
    public void updSensorLog(SensorLog sensorLog) {
        sensorLogDao.update(sensorLog);
    }

    @Override
    public void delSensorLog(Integer logId) {
        sensorLogDao.deleteById(logId);
    }

    @Override
    public PageInfo<SensorRecord> listSensorRecord(Integer pageNum, Integer pageSize, Integer meansPointId, InfoFilter filter) {
        PageHelper.startPage(pageNum, pageSize);
        List<SensorRecord> list = sensorLogExpDao.listSensorRecord(meansPointId, filter);
        return new PageInfo<>(list);
    }

    @Override
    public void addSensorRecord(SensorRecord sensorRecord) {
        sensorRecordDao.save(sensorRecord);
    }

    @Override
    public void updSensorRecord(SensorRecord sensorRecord) {
        sensorRecordDao.update(sensorRecord);
    }

    @Override
    public void delSensorRecord(Integer recordId) {
        sensorRecordDao.deleteById(recordId);
    }
}
