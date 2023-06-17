package com.iware.bridge.info.dao;

import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.info.vo.UnitVO;
import com.iware.bridge.model.entity.user.Unit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-10-19
 */

@Repository
public interface UnitExpDao {

    /** 判断是否存在同名单位 **/
    public Unit existUnit(@Param("name") String name, @Param("id") Integer id);

    /** 获取单位列表 */
    public List<UnitVO> getUnitList(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId,
                             @Param("filter") InfoFilter filter);

    /** 创建用户获取单位列表 **/
    public List<Unit> getUserUnitList(@Param("roleId") Integer roleId, @Param("unitId") Integer unitId);
}
