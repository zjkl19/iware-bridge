package com.iware.bridge.online.service;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.model.entity.online.SensorConverge;
import com.iware.bridge.online.vo.ProcessFilter;
import com.iware.bridge.online.vo.WarningRatio;
import com.iware.bridge.online.vo.WarningRecordFilter;
import com.iware.bridge.online.vo.WarningRecordVO;

import java.util.List;
import java.util.Map;

/**
 * @author WJP
 * @date 2021-7-27
 */

public interface WarningService {

    /** 获取预警记录列表 */
    public PageInfo<WarningRecordVO> listWarningRecord(Integer pageNum, Integer pageSize, WarningRecordFilter filter);

    /** 批量处理预警信息 */
    public void batchProcessing(ProcessFilter filter);

    /** 预警统计 */
    public List<WarningRatio> getWarningStatistics(Integer type, Integer structureId);

    /** 获取传感器预警点附近数据 */
    public Map<String, String> getSensorData(SensorConverge sensorConverge);

    /** 获取是否有未处理预警、故障传感器 */
    public Map<String, Integer> getOnlineNotice();
}
