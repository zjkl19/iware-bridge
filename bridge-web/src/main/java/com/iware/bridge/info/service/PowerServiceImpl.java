package com.iware.bridge.info.service;

import com.iware.bridge.info.dao.PowerExpDao;
import com.iware.bridge.info.vo.PowerVO;
import com.iware.bridge.info.vo.RolePowerVO;
import com.iware.bridge.model.dao.user.PowerDao;
import com.iware.bridge.model.dao.user.RoleDao;
import com.iware.bridge.model.dao.user.UnitPowerRelDao;
import com.iware.bridge.model.entity.user.Power;
import com.iware.bridge.model.entity.user.Role;
import com.iware.bridge.model.entity.user.UnitPowerRel;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZRB
 * @date 2021-10-19
 */

@Service
public class PowerServiceImpl implements PowerService {

    @Autowired
    private PowerDao powerDao;
    @Autowired
    private PowerExpDao powerExpDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UnitPowerRelDao unitPowerRelDao;


    @Override
    public List<PowerVO> getPowerList() {
        return powerExpDao.getPowerList(0);
    }

    @Override
    public List<Integer> getUnitPowers(Integer unitId) {
        List<Power> list = powerExpDao.getPowerByUnit(unitId);
        return list.stream().map(Power::getId).collect(Collectors.toList());
    }

    @Override
    public List<Integer> getPagePowerIds() {
        Power powerQry = new Power();
        powerQry.setActive(0);
        List<Power> list = powerDao.query(powerQry);
        return list.stream().map(Power::getId).collect(Collectors.toList());
    }

    @Override
    public List<RolePowerVO> getRoleDefaultPower(Integer type) {

        Integer roleId = ThreadLocalMap.getRoleId();
        List<Role> roleList = roleDao.findAll();
        List<RolePowerVO> rolePowerVOList = new ArrayList<>();

        if (type - 1 == 0) {
            //如果是查询显示所使用
            if (UserRoleEnum.ADMIN.getCode().equals(roleId)) {
                //admin超级用户 添加非admin的角色
                for (Role role : roleList) {
                    if (!UserRoleEnum.ADMIN.getCode().equals(role.getId())) {
                        RolePowerVO rolePowerVO = new RolePowerVO();
                        rolePowerVO.setRoleId(role.getId());
                        rolePowerVO.setRoleName(role.getName());
                        rolePowerVOList.add(rolePowerVO);
                    }
                }
            } else if (UserRoleEnum.UNDERTAKING_MAIN.getCode().equals(roleId)) {
                //承接单位主用户
                for (Role role : roleList) {
                    if (UserRoleEnum.UNDERTAKING_NORMAL.getCode().equals(role.getId())) {
                        RolePowerVO rolePowerVO = new RolePowerVO();
                        rolePowerVO.setRoleId(role.getId());
                        rolePowerVO.setRoleName(role.getName());
                        rolePowerVOList.add(rolePowerVO);
                    }
                }
            }
        } else if (type - 2 == 0) {
            //如果是新增/修改显示所使用
            if (UserRoleEnum.ADMIN.getCode().equals(roleId)) {
                //admin超级用户 只能添加业主和承接单位角色
                for (Role role : roleList) {
                    if (UserRoleEnum.OWNER.getCode().equals(role.getId()) ||
                            UserRoleEnum.UNDERTAKING_MAIN.getCode().equals(role.getId())) {
                        RolePowerVO rolePowerVO = new RolePowerVO();
                        rolePowerVO.setRoleId(role.getId());
                        rolePowerVO.setRoleName(role.getName());
                        rolePowerVO.setPowerList(powerExpDao.getRoleDefaultPower(role.getId()));
                        rolePowerVOList.add(rolePowerVO);
                    }
                }
            } else if (UserRoleEnum.UNDERTAKING_MAIN.getCode().equals(roleId)) {
                //承接单位主用户
                for (Role role : roleList) {
                    if (UserRoleEnum.UNDERTAKING_NORMAL.getCode().equals(role.getId())) {
                        RolePowerVO rolePowerVO = new RolePowerVO();
                        rolePowerVO.setRoleId(role.getId());
                        rolePowerVO.setRoleName(role.getName());
                        rolePowerVO.setPowerList(powerExpDao.getRoleDefaultPower(role.getId()));
                        rolePowerVOList.add(rolePowerVO);
                    }
                }
            }
        }

        return rolePowerVOList;
    }

    public void batchSavePowerRel(Integer unitId, List<Integer> relIds) {

        List<UnitPowerRel> unitPowerRelList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(relIds)) {
            for (Integer powerId : relIds) {
                UnitPowerRel rel = new UnitPowerRel();
                rel.setPowerId(powerId);
                rel.setUnitId(unitId);
                unitPowerRelList.add(rel);
            }
            unitPowerRelDao.batchSave(unitPowerRelList);
        }
    }

    @Override
    public void delPowerByUnitId(Integer unitId) {
        powerExpDao.delPowerByUnitId(unitId);
    }
}
