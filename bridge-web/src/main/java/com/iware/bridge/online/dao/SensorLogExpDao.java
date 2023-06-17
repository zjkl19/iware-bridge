package com.iware.bridge.online.dao;

import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.model.entity.online.SensorRecord;
import com.iware.bridge.online.vo.SensorLogFilter;
import com.iware.bridge.online.vo.SensorLogVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WJP
 * @date 2021-7-27
 */

@Repository
public interface SensorLogExpDao {

    /** 获取维护日志列表 */
    public List<SensorLogVO> listSensorLog(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                                           @Param("powerId") Integer powerId, @Param("filter") SensorLogFilter filter);

    /** 获取维护记录列表 */
    public List<SensorRecord> listSensorRecord(@Param("meansPointId") Integer meansPointId,
                                        @Param("filter") InfoFilter filter);
}
