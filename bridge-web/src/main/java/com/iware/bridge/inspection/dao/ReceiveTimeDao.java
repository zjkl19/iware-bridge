package com.iware.bridge.inspection.dao;

import com.iware.bridge.inspection.vo.ReceiveTime;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LBX
 * @date 2021-8-4
 */

@Repository
public interface ReceiveTimeDao {
    List<ReceiveTime> selectReceiveTimeByUserAndProject(@Param("unitId") Integer unitId,
                                                        @Param("projectId") Integer projectId,
                                                        @Param("powerId") Integer powerId,
                                                        @Param("beginTime") String beginTime);
}
