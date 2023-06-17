package com.iware.bridge.online.service;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.model.entity.online.SensorLog;
import com.iware.bridge.model.entity.online.SensorRecord;
import com.iware.bridge.online.vo.SensorLogFilter;
import com.iware.bridge.online.vo.SensorLogVO;

/**
 * @author WJP
 * @date 2021-7-28
 */

public interface SensorLogService {

    /** 获取维护日志列表 */
    public PageInfo<SensorLogVO> listSensorLog(Integer pageNum, Integer pageSize, SensorLogFilter filter);

    /** 新增一条维护日志 */
    public void addSensorLog(SensorLog sensorLog);

    /** 修改一条维护日志 */
    public void updSensorLog(SensorLog sensorLog);

    /** 删除一条维护日志 */
    public void delSensorLog(Integer logId);

    /** 获取维护记录列表 */
    public PageInfo<SensorRecord> listSensorRecord(Integer pageNum, Integer pageSize, Integer meansPointId, InfoFilter filter);

    /** 新增一条维护记录 */
    public void addSensorRecord(SensorRecord sensorRecord);

    /** 修改一条维护记录 */
    public void updSensorRecord(SensorRecord sensorRecord);

    /** 删除一条维护记录 */
    public void delSensorRecord(Integer recordId);
}
