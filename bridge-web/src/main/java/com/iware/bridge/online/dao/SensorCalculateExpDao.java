package com.iware.bridge.online.dao;

import com.iware.bridge.model.entity.online.MeansPoint;
import com.iware.bridge.online.vo.SensorCalculateFilter;
import com.iware.bridge.online.vo.SensorCalculateVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: wjp
 * @date: 2021年9月27日09:21:50
 * @since 1.0
 */
@Repository
public interface SensorCalculateExpDao {

    /** 根据编号和细项获取半小时内的数据  **/
    public List<Float> selectHalfHourData(@Param("sensorCoding") String sensorCoding, @Param("time") Date time,
                                          @Param("detailId") Integer detailId);

    /** 判断当前传感器编码和细项是否已经计算过频率 **/
    public List<Float> selectFrequency(@Param("sensorCoding") String sensorCoding, @Param("time")Date time,
                                       @Param("detailId") Integer detailId);

    /** 添加计算后的频率值 **/
    public void addCacul(@Param("sensorCoding") String sensorCoding, @Param("time")Date time,
                         @Param("detailId") Integer detailId, @Param("value")Float value, @Param("cableForce")Float cableForce);

    /** 查询频率值 **/
    public List<SensorCalculateVO>  selectFrequencyByTime(@Param("filter") SensorCalculateFilter sensorCalculateFilter);

    /** 获取测点信息 **/
    public MeansPoint selectDescribe(@Param("sensorCoding") String sensorCoding, @Param("detailId") Integer detailId);

    /** 获取原始数据根据时间(东华) **/
    public List<Float> getPythonDataDH(@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("sensorCoding") String sensorCoding ,@Param("detailId") Integer detailId);

    /** 获取原始数据根据时间(基康) **/
    public List<Float> getPythonDataJK(@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("sensorCoding") String sensorCoding);

    /** 获取原始数据根据时间(称重) **/
    public List<Float> getPythonDataCZ(@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("sensorCoding") String sensorCoding ,@Param("detailId") Integer detailId);
}
