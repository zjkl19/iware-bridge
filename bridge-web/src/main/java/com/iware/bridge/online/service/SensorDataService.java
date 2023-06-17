package com.iware.bridge.online.service;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.model.entity.online.AxleTypeModelRel;
import com.iware.bridge.online.dto.SensorHistoryDto;
import com.iware.bridge.online.vo.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ZRB
 * @date 2021-7-27
 */

public interface SensorDataService {

    /** 添加每小时数据 */
    public void addSensorDay();

    /** 添加每10分数据 */
    public void addSensorHour();

    /** 添加每1分数据 */
    public void addSensorMinute();

    /** 添加每3秒数据 */
    public void addSensorSecond();

    /** 添加每秒数据 */
    public void addSensorSecondOne();

    /** 获取最新十条 */
    public Map<String, List<SensorHistoryDto>> getLastTen(List<SensorVO> sensorVOList);

    /** 获取历史数据 */
    public List<SensorHistoryVO> getHistory(SensorDataFilter filter);

    /** 东华传感器计算半小时内的功率 索力3 加速度1 **/
    public void calculatePower(Date date, Calendar cal, String sensorCoding, Integer detailId);

    /** 创建批量导出Excel文件 */
    public String createExcel(SensorDataFilter filter);

    /** 获取历史数据列表 */
    public PageInfo<SensorHistoryDto> listData(Integer pageNum, Integer pageSize, SensorDataFilter filter);

    /** 获取车型列表 */
    public List<AxleTypeModelRel> listAxleType();

}
