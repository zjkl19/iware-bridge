package com.iware.bridge.online.dao;

import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.model.entity.online.AxleTypeModelRel;
import com.iware.bridge.model.entity.online.MeansPoint;
import com.iware.bridge.model.entity.online.Sensor;
import com.iware.bridge.online.dto.MonitoringSituationDto;
import com.iware.bridge.online.dto.SensorTreeDto;
import com.iware.bridge.online.vo.MeansPointBO;
import com.iware.bridge.online.vo.MeansPointStatusVO;
import com.iware.bridge.online.vo.MeansPointVO;
import com.iware.bridge.online.vo.SensorFilter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WJP
 * @date 2021-7-27
 */

@Repository
public interface SensorExpDao {

    /** 查询传感器列表 **/
    public List<MeansPointVO> selectSensorList(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                               @Param("powerId") Integer powerId,@Param("filter") SensorFilter filter);

    /** 查询用户有传感器桥隧数 **/
    public Integer selectSensorBridgeCount(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                    @Param("powerId") Integer powerId);

    /** 查询传感器数 **/
    public Integer selectSensorCount(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                     @Param("powerId") Integer powerId, @Param("structureId") Integer structureId,
                                     @Param("status") Integer status);

    /** 修改测点信息 **/
    public void updateMeansPoint(MeansPoint meansPoint);

    /** 传感器类型统计 **/
    public List<EchartMap> selectSensorTypeCount(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                          @Param("powerId") Integer powerId, @Param("structureId") Integer structureId);


    /** 查询监测概况信息对象 **/
    public List<MonitoringSituationDto> selectBridgeSensorWarning(@Param("unitId") Integer unitId,
                                                           @Param("roleId") Integer roleId,
                                                           @Param("powerId") Integer powerId,
                                                           @Param("projectId") Integer projectId);

    /** 获取结构物传感器 */
    public List<MeansPointVO> getSensorByStructureId(@Param("structureId") Integer structureId);

    /** 根据结构物id查询传感器类型 **/
    public List<SensorTreeDto> selectSensorByType(@Param("type") Integer type,
                                                  @Param("structureId") Integer structureId,
                                                  @Param("queryAll") Integer queryAll);

    /**查看一天内传感器是否有数据返回*/
    public List<MeansPointStatusVO> getMeansPointStatus();

    /** 批量修改传感器 **/
    public void batchUpdateSensor(@Param("list") List<Sensor> updList);

    /** 根据测点id删除传感器 **/
    public void deleteSensorByMeansPointId(@Param("meansPointId") Integer meansPointId);

    /** 批量修改状态 */
    public void batchUpdateStatus(@Param("list") List<MeansPointStatusVO> list);

    /** 查看是否存在同名传感器编号 */
    public String getSensorCoding(@Param("sensorCoding") String sensorCoding, @Param("id") Integer id);

    /** 查看是否存在同名测点编号 */
    public String getMeansPointName(@Param("meansPointName") String meansPointName,@Param("id") Integer id);

    /** 图片路径设置为空 */
    public void deletePhotoUrl(@Param("id") Integer id);

    /** 获取车型类型 */
    public List<AxleTypeModelRel> listAxleType();

    /** 获取所有测点信息 */
    public List<MeansPointBO> getAllMeansPoint();
}
