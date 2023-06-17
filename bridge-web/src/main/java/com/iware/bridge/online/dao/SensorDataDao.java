package com.iware.bridge.online.dao;

import com.iware.bridge.model.entity.online.Sensor;
import com.iware.bridge.online.dto.SensorHistoryDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-27
 */


@Repository
public interface SensorDataDao {

    /** 添加传感器数据 */
    public void addSensorData(@Param("value") Float value, @Param("sensorCoding") String sensorCoding,
                              @Param("samplingTime") String samplingTime);

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

    /** 获取称重最新10条 **/
    public List<SensorHistoryDto> getLastTen(@Param("coding") String coding);

    /** 获取称重传感器历史数据 **/
    public List<SensorHistoryDto> historyDataByWeight(@Param("coding") String coding, @Param("startDate") Date startDate,
                                                      @Param("endDate") Date endDate);

    /** 获取基康传感器历史数据 **/
    public List<SensorHistoryDto> historyDataByJK(@Param("coding") String coding, @Param("startDate") Date startDate,
                                                  @Param("endDate") Date endDate);

    /** 获取动画传感器历史数据（分表） **/
    public List<SensorHistoryDto> historyDataByDH(@Param("coding") String coding, @Param("type") Integer type,
                                                  @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /** 基康箱线图 **/
    public List<Float> boxDiagramByJK(@Param("coding") String coding, @Param("startDate")Date startDate,
                                      @Param("endDate") Date endDate);

    /** 东华箱线图 **/
    public List<Float> boxDiagramByDH(Sensor sensor);

    /** 传感器历史数据 */
    public List<SensorHistoryDto> sensorHistoryData(@Param("weightSensor") List<String> weightSensor,
                                                    @Param("jkSensor") List<String> jkSensor,
                                                    @Param("dhSensor") List<String> dhSensor,
                                                    @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
