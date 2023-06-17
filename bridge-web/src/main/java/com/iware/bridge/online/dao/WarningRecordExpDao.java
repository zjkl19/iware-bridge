package com.iware.bridge.online.dao;

import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.model.entity.online.WarningRecord;
import com.iware.bridge.online.dto.WarningTimeDto;
import com.iware.bridge.online.vo.ProcessFilter;
import com.iware.bridge.online.vo.WarningRatio;
import com.iware.bridge.online.vo.WarningRecordFilter;
import com.iware.bridge.online.vo.WarningRecordVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author WJP
 * @date 2021-7-27
 */

@Repository
public interface WarningRecordExpDao {

    /** 获取预警统计 */
    public List<WarningRatio> getWarningStatistics(@Param("powerId") Integer powerId,
                                                   @Param("filter") WarningRecordFilter filter);

    /** 查询当日预警数 **/
    public Integer selectTodayWarningCount(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                    @Param("powerId") Integer powerId, @Param("structureId") Integer structureId,
                                    @Param("status") Integer status);

    /** 查询昨日预警数 **/
    public Integer selectYesterdayWarningCount(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                        @Param("powerId") Integer powerId, @Param("structureId") Integer structureId);

    /** 查询测点下的各个预警数（测点预警排行） **/
    public List<EchartMap> selectWarningRank(WarningRecordFilter filter);

    /** 查询预警时间的预警数（今日）**/
    public List<WarningTimeDto> selectWarningTimeDay(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                              @Param("powerId") Integer powerId, @Param("structureId") Integer structureId);

    /** 查询预警时间的预警数（本月）**/
    public List<WarningTimeDto> selectWarningTimeMonth(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                                @Param("powerId") Integer powerId, @Param("structureId") Integer structureId);

    /** 查询预警时间的预警数（今年） **/
    public List<WarningTimeDto> selectWarningTimeYear(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                               @Param("powerId") Integer powerId, @Param("structureId") Integer structureId);

    /** 查询预警时间的预警数（全部） **/
    public List<WarningTimeDto> selectWarningTimeAll(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                              @Param("powerId") Integer powerId, @Param("structureId") Integer structureId);

    /** 查询预警信息列表 **/
    public List<WarningRecordVO> selectWarning(WarningRecordFilter warningRecordFilter);

    /** 修改/批量修改预警处理状态 **/
    public Integer updateWarningStatus(ProcessFilter processFilter);

    /** 获取传感器预警点附近数据 **/
    public Map<String, String> selectDataNearWarning(@Param("sensorCoding") String sensorCoding,
                                                     @Param("type") Integer type,
                                                     @Param("startTime") String startTime,
                                                     @Param("endTime") String endTime);

    /** 获取用户最新预警信息 */
    public WarningRecord getNewestWarning(@Param("roleId") Integer roleId,
                                          @Param("unitId") Integer unitId,
                                          @Param("meansPointId") Integer meansPointId);

    /** 获取预警未处理/已处理/总数量 */
    public Integer getWarningCount(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                   @Param("powerId") Integer powerId, @Param("status") Integer status);
}
