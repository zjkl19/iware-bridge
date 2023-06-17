package com.iware.bridge.online.dao;

import com.iware.bridge.model.entity.global.ProjectAppoint;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.online.Sensor;
import com.iware.bridge.online.dto.SensorWeightDto;
import com.iware.bridge.online.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WJP
 * @date 2021-7-27
 */

@Repository
public interface SensorWeightExpDao {
    /**  车流量（时） **/
    public List<SensorWeightDto> selectTrafficFlowHour(@Param("filter") SensorWeightFilter filter,
                                                @Param("time") List<ProjectAppoint> appoints);

    /**  车流量（日） **/
    public List<SensorWeightDto> selectTrafficFlowDay(@Param("filter") SensorWeightFilter filter,
                                               @Param("time") List<ProjectAppoint> appoints);

    /** 车流量（月） **/
    public List<SensorWeightDto> selectTrafficFlowMonth(@Param("filter") SensorWeightFilter filter,
                                                 @Param("time") List<ProjectAppoint> appoints);

    /** 最大车重（时） **/
    public List<SensorWeightDto> selectMaxWeightHour(@Param("filter") SensorWeightFilter filter,
                                              @Param("time") List<ProjectAppoint> appoints);

    /** 最大车重（日） **/
    public List<SensorWeightDto> selectMaxWeightDay(@Param("filter") SensorWeightFilter filter,
                                             @Param("time") List<ProjectAppoint> appoints);

    /** 最大车重（月） **/
    public List<SensorWeightDto> selectMaxWeightMonth(@Param("filter") SensorWeightFilter filter,
                                               @Param("time") List<ProjectAppoint> appoints);

    /** 重车统计（时） **/
    public List<SensorWeightDto> selectCarWeightHour(@Param("filter") SensorWeightFilter filter,
                                              @Param("time") List<ProjectAppoint> appoints);

    /** 重车统计（日） **/
    public List<SensorWeightDto> selectCarWeightDay(@Param("filter") SensorWeightFilter filter,
                                             @Param("time") List<ProjectAppoint> appoints);

    /** 重车统计（月） **/
    public List<SensorWeightDto> selectCarWeightMonth( @Param("filter") SensorWeightFilter filter,
                                               @Param("time") List<ProjectAppoint> appoints);

    /** 查询车型分布 **/
    public SensorWeightModel selectCarModel(@Param("filter") SensorWeightFilter filter,
                                         @Param("time") List<ProjectAppoint> appoints);

    /** 查询车速分布 **/
    public SensorWeightSpeed selectCarSpeed(@Param("filter") SensorWeightFilter filter,
                           @Param("time") List<ProjectAppoint> appoints);

    /** 查询车重分布 **/
    public List<SensorWeightWeight> selectCarWeight(@Param("filter") SensorWeightFilter filter,
                                          @Param("time") List<ProjectAppoint> appoints);

    /** 查询超重车 **/
    public SensorWeightWeight selecttransfiniteCar( @Param("filter") SensorWeightFilter filter,
                                 @Param("time") List<ProjectAppoint> appoints);

    /** 查询传感器的结构物 **/
    public List<Structure> selectSensorWeightStructure(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId, @Param("powerId") Integer powerId, @Param("type") Integer type);

    /** 查询时间点 **/
    public List<String> selectTimeZero(@Param("type") Integer type, @Param("filter") SensorWeightFilter filter);

    /** 查询车道 **/
    public List<Sensor> selectCarNo(Sensor sensor);

    /** 查询权限 **/
    public List<ProjectAppoint> selectQuery(@Param("unitId") Integer unitId, @Param("powerId") Integer powerId, @Param("structureId") Integer structureId);

    /** 查询称重传感器 **/
    public List<SensorMeansVO> selectOnlineWeight();

    /** 查询每小时车重数据 **/
    public List<SensorWeightWeight> selectHourWeightData( @Param("startTime") String startTime,@Param("endTime") String endTime);

    /** 查询每小时车轴数据 **/
    public List<SensorWeightModel> selectHourModelData(@Param("startTime") String startTime,@Param("endTime") String endTime);

    /** 查询每小时车速数据 **/
    public List<SensorWeightSpeed> selectHourSpeedData( @Param("startTime") String startTime,@Param("endTime") String endTime);

    /** 添加每小时车轴表 **/
    public void addHourModel(@Param("model") List<SensorWeightModel> sensorWeightModel);

    /** 添加每小时车重表 **/
    public void addHourWeight(@Param("model") List<SensorWeightWeight> sensorWeightWeight);

    /** 添加每小时车速表 **/
    public void addHourSpeed(@Param("model") List<SensorWeightSpeed> sensorWeightSpeed);
}
