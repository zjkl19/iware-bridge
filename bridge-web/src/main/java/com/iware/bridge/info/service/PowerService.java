package com.iware.bridge.info.service;

import com.iware.bridge.info.vo.PowerVO;
import com.iware.bridge.info.vo.RolePowerVO;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-10-19
 */

public interface PowerService {

    /** 查找所有权限 */
    public List<PowerVO> getPowerList();

    /** 查找单位权限 */
    public List<Integer> getUnitPowers(Integer unitId);

    /** 查找页面的权限id */
    public List<Integer> getPagePowerIds();

    /** 查询角色默认权限 **/
    public List<RolePowerVO> getRoleDefaultPower(Integer type);

    /** 批量保存单位权限 **/
    public void batchSavePowerRel(Integer unitId, List<Integer> relIds);

    /** 根据id和类型单位权限 **/
    public void delPowerByUnitId(Integer unitId);

}
