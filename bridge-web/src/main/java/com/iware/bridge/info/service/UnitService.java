package com.iware.bridge.info.service;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.info.vo.UnitVO;
import com.iware.bridge.model.entity.user.Unit;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-10-19
 */

public interface UnitService {

    /** 添加单位 */
    public void addUnit(UnitVO unitVO);

    /** 修改单位 */
    public void editUnit(UnitVO unitVO);

    /** 删除单位 **/
    public void delUnit(Integer unitId);

    /** 分页获取单位列表 **/
    public PageInfo<UnitVO> getUnitList(Integer pageNum, Integer pageSize, InfoFilter filter);

    /** 创建用户获取单位列表 **/
    public List<Unit> getUserUnitList();

    /**根据权限、父级单位id获取单位 */
    public List<Unit> getUnitByRole(Integer roleId, Integer parentId);

}
