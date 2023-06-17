package com.iware.bridge.permission.dao;


import com.iware.bridge.model.entity.user.Power;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao {

    /**
     * 根据条件查询
     */
    List<Power> getByUserIdAndUrl(@Param("roleId") Integer roleId, @Param("unitId")Integer unitId,
                                  @Param("powerName")String powerName, @Param("routerUrl") String routerUrl);
}
