package com.iware.bridge.info.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.dao.ProjectAppointExpDao;
import com.iware.bridge.info.dao.ProjectExpDao;
import com.iware.bridge.info.dao.UnitExpDao;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.info.vo.UnitVO;
import com.iware.bridge.model.dao.global.StructureDao;
import com.iware.bridge.model.dao.user.UnitDao;
import com.iware.bridge.model.dao.user.UserDao;
import com.iware.bridge.model.entity.global.ProjectAppoint;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.user.Unit;
import com.iware.bridge.model.entity.user.User;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.enums.UserRoleEnum;
import com.iware.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-10-19
 */

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitDao unitDao;
    @Autowired
    private UnitExpDao unitExpDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PowerService powerService;
    @Autowired
    private ProjectExpDao projectExpDao;
    @Autowired
    private ProjectAppointExpDao projectAppointExpDao;
    @Autowired
    private StructureDao structureDao;

    @Override
    @Transactional
    public void addUnit(UnitVO unitVO) {
        unitVO.setCreateUserId(ThreadLocalMap.getUserId());
        unitVO.setStatus(1);

        //查询是否存在同名单位
        Unit unit = unitExpDao.existUnit(unitVO.getName(), null);
        if (unit != null) {
            throw new BusinessException(GlobalResultEnum.UNIT_EXIST_ERROR);
        }

        //添加承接单位普通用用户，指定其父级单位
        if(UserRoleEnum.UNDERTAKING_NORMAL.getCode().equals(unitVO.getRoleId())) {
            unitVO.setParentId(ThreadLocalMap.getUnitId());
        }
        unitDao.save(unitVO);
        powerService.batchSavePowerRel(unitVO.getId(), unitVO.getPowerIds());
    }

    @Override
    @Transactional
    public void editUnit(UnitVO unitVO) {
        //查询是否存在同名单位
        Unit unit = unitExpDao.existUnit(unitVO.getName(), unitVO.getId());
        if (unit != null) {
            throw new BusinessException(GlobalResultEnum.UNIT_EXIST_ERROR);
        }

        unitDao.update(unitVO);
        //编辑单位成功 更新单位与权限关联表
        powerService.delPowerByUnitId(unitVO.getId());  //先删除所有单位关联的权限，再新增
        powerService.batchSavePowerRel(unitVO.getId(), unitVO.getPowerIds());
    }

    @Override
    @Transactional
    public void delUnit(Integer unitId) {

        Unit unit = unitDao.findById(unitId);

        //单位下有用户，不允许删除
        User userQry = new User();
        userQry.setUnitId(unitId);
        List<User> userList = userDao.query(userQry);

        if (!CollectionUtils.isEmpty(userList)) {
            throw new BusinessException("当前单位下存在用户，无法删除！");
        }

        if (UserRoleEnum.OWNER.getCode().equals(unit.getRoleId())) {
            //业主单位查看是否存在项目、结构物
            List<Integer> projectIds = projectExpDao.getUnitProjectIds(unit.getRoleId(), unitId);
            if (!CollectionUtils.isEmpty(projectIds)) {
                throw new BusinessException("当前单位下存在项目，无法删除！");
            }

            Structure structureQry = new Structure();
            structureQry.setUnitId(unitId);
            List<Structure> structureList = structureDao.query(structureQry);
            if (!CollectionUtils.isEmpty(structureList)) {
                throw new BusinessException("当前单位下存在结构物，无法删除！");
            }
        } else {
            //承接单位查看是否被指派过
            List<ProjectAppoint> list = projectAppointExpDao.getBeAppointedByUnit(unitId);
            if (!CollectionUtils.isEmpty(list)) {
                throw new BusinessException("当前单位存在指派记录，无法删除！");
            }

            //主单位查看是否有子单位
            Unit unitQry = new Unit();
            unitQry.setParentId(unitId);
            List<Unit> unitList = unitDao.query(unitQry);

            if (!CollectionUtils.isEmpty(unitList)) {
                throw new BusinessException("当前单位存在子单位，无法删除！");
            }
        }

        unitDao.deleteById(unitId);
        powerService.delPowerByUnitId(unitId);
    }

    @Override
    public PageInfo<UnitVO> getUnitList(Integer pageNum, Integer pageSize, InfoFilter filter) {
        PageHelper.startPage(pageNum, pageSize);
        List<UnitVO> list = unitExpDao.getUnitList(ThreadLocalMap.getRoleId(), ThreadLocalMap.getUnitId(), filter);
        return new PageInfo<>(list);
    }

    @Override
    public List<Unit> getUserUnitList() {
        return unitExpDao.getUserUnitList(ThreadLocalMap.getRoleId(), ThreadLocalMap.getUnitId());
    }

    @Override
    public List<Unit> getUnitByRole(Integer roleId, Integer parentId) {
        Unit unitQry = new Unit();
        unitQry.setRoleId(roleId);
        if (parentId != 0) {
            unitQry.setParentId(parentId);
        }
        return unitDao.query(unitQry);
    }
}
