package com.iware.bridge.online.service;

import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.online.MeansPoint;
import com.iware.bridge.online.dto.SensorWeightDto;
import com.iware.bridge.online.vo.SensorWeightFilter;

import java.util.List;
import java.util.Map;

/**
 * @author: wjp
 * @date: 2021年8月19日14:15:20
 * @since 1.0
 */
public interface SensorWeightDataService {

    /** 获取称重传感器的车道号 **/
    public List<MeansPoint> getSensorWeightCarNo(Integer structureId,Integer type);

    /** 获取车流量 */
    public Map<String, List<SensorWeightDto>> getTrafficFlow(SensorWeightFilter sensorWeightFilter);

    /** 获取车型、车速分布 */
    public Map<String, List<SensorWeightDto>> getCarModelSpeed(SensorWeightFilter sensorWeightFilter);

    /** 获取最大车重 */
    public Map<String, List<SensorWeightDto>> maxAlexWeight(SensorWeightFilter sensorWeightFilter);

    /** 获取车重分布 */
    public Map<String, List<SensorWeightDto>> carWeight(SensorWeightFilter sensorWeightFilter);

    /** 获取重车统计 */
    public Map<String, List<SensorWeightDto>> countCarWeight(SensorWeightFilter sensorWeightFilter);

    /** 超重车统计 */
    public Map<String, Integer> transfiniteCar(SensorWeightFilter sensorWeightFilter);

    /** 获取称重传感器的结构物 **/
    public List<Structure> sensorStructure(Integer type);

    /** 每小时定时统计称重传感器的数据 **/
    public void weightHourData();

}


