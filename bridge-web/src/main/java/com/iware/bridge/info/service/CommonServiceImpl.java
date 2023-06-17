package com.iware.bridge.info.service;

import com.iware.bridge.info.dao.CommonDao;
import com.iware.bridge.info.dao.ProjectExpDao;
import com.iware.bridge.info.dao.StructureExpDao;
import com.iware.bridge.info.dao.UserExpDao;
import com.iware.bridge.info.vo.AppointTime;
import com.iware.bridge.info.vo.AreaVO;
import com.iware.bridge.info.vo.StructureFilter;
import com.iware.bridge.info.vo.StructureVO;
import com.iware.bridge.model.dao.evaluation.BridgeTypeDao;
import com.iware.bridge.model.dao.global.*;
import com.iware.bridge.model.entity.evaluation.BridgeType;
import com.iware.bridge.model.entity.global.*;
import com.iware.bridge.model.entity.user.Unit;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.UserRoleEnum;
import com.iware.common.properties.PowerProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonDao commonDao;
    @Autowired
    private PowerProperties power;
    @Autowired
    private StructureExpDao structureExpDao;
    @Autowired
    private BridgeInfoDao bridgeInfoDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectExpDao projectExpDao;
    @Autowired
    private VideoDao videoDao;
    @Autowired
    private PhotoDao photoDao;
    @Autowired
    private UserExpDao userExpDao;
    @Autowired
    private BridgeTypeDao bridgeTypeDao;

    @Override
    public List<AreaVO> getAreaList(Integer parentId) {
        return commonDao.getAreaList(0);
    }

    public Integer getPowerType(Integer powerId) {
        Integer type = null;
        if (powerId != null) {
            if (power.getOnlinePower().equals(powerId)) type = 1;
            else if (power.getInspectionPower().equals(powerId)) type = 2;
            else if (power.getMaintainPower().equals(powerId)) type = 3;
            else if (power.getEvaluationPower().equals(powerId)) type = 4;
            else if (power.getVideo().equals(powerId)) type = 5;
        }
        return type;
    }

    @Override
    public List<AreaVO> listAreaByModel(Integer powerId) {
        List<Structure> structureList;
        List<AreaVO> areaVOList = new ArrayList<>();
        StructureFilter filter = new StructureFilter();
        if (powerId == null) {
            structureList = this.listUnitStructure(filter);
        } else {
            filter.setPowerId(powerId);
            structureList = this.listUnitStructureByModel(filter);
        }

        if (!CollectionUtils.isEmpty(structureList)) {
            String ids = structureList.stream().map(Structure::getId).map(String::valueOf).collect(Collectors.joining(","));
            areaVOList = commonDao.listAreaByStructure(ids);
        }
        return areaVOList;
    }

    @Override
    public List<Project> listUserProject(Integer powerId, Integer onTime) {

        Integer roleId = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();

        List<Project> projectList;

        if (powerId != null && power.getVideo().equals(powerId))
            powerId = power.getOnlinePower();
        if (UserRoleEnum.UNDERTAKING_MAIN.getCode().equals(roleId) || UserRoleEnum.UNDERTAKING_NORMAL.getCode().equals(roleId)) {
            projectList = projectExpDao.getProjectByPower(unitId, powerId, onTime);
        } else if (UserRoleEnum.OWNER.getCode().equals(roleId)) {
            Project projectQry = new Project();
            projectQry.setUnitId(unitId);
            projectList = projectDao.query(projectQry);
        } else {
            projectList = projectDao.findAll();
        }
        return projectList;
    }

    @Override
    public List<Project> listUserProjectByModel(Integer powerId) {
        Integer type = this.getPowerType(powerId);
        if (type == 5) powerId = power.getOnlinePower();
        if(type == 2) return commonDao.listUserInspectionProject(ThreadLocalMap.getRoleId(), ThreadLocalMap.getUnitId(), powerId);
        return commonDao.listUserProject(ThreadLocalMap.getRoleId(), ThreadLocalMap.getUnitId(), type, powerId);
    }

    @Override
    public List<Structure> listUnitStructure(StructureFilter filter) {

        List<Structure> structureList;
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();
        //无power参数默认获取用户所有结构物
        structureList = commonDao.listUnitStructure(roleId, unitId, filter);
        return structureList;
    }

    @Override
    public List<Structure> listUnitStructureByModel(StructureFilter filter) {

        List<Structure> structureList = new ArrayList<>();
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();

        //获取用户有传感器/巡查/维养/检测计划的桥隧
        if (power.getOnlinePower().equals(filter.getPowerId())) {
            structureList = commonDao.listStructureByOnline(roleId, unitId, filter);
        } else if (power.getInspectionPower().equals(filter.getPowerId())) {
            structureList = commonDao.listStructureByInspMain(roleId, unitId, 1, filter);
        } else if (power.getMaintainPower().equals(filter.getPowerId()))
            structureList = commonDao.listStructureByInspMain(roleId, unitId, 4, filter);
        else if (power.getEvaluationPower().equals(filter.getPowerId())) {
            structureList = commonDao.listStructureByEvaluation(roleId, unitId, filter);
        }
        return structureList;
    }

    @Override
    public StructureVO getStructureById(Integer structureId, Integer type) {
        StructureVO structureVO = new StructureVO();
        Structure structure = structureExpDao.findById(structureId);

        BeanUtils.copyProperties(structure, structureVO);

        if (type == 1 || type == 2) {
            Photo photoQry = new Photo();
            photoQry.setType(structureVO.getStructureType());
            photoQry.setTargetId(structureId);
            structureVO.setPhotoList(photoDao.query(photoQry));
            if (type == 1) {
                Video videoQry = new Video();
                videoQry.setStructureId(structureId);
                structureVO.setVideoList(videoDao.query(videoQry));
            }

            if (type == 2 && GlobalConstant.TYPE_BRIDGE.equals(structureVO.getStructureType())) {
                BridgeInfo bridgeInfoQry = new BridgeInfo();
                bridgeInfoQry.setStructureId(structureId);
                structureVO.setBridgeInfo(bridgeInfoDao.query(bridgeInfoQry).get(0));
            }
        }
        return structureVO;
    }

    @Override
    public List<AppointTime> getUnitAppointTime(Integer projectId, Integer powerId) {
        return commonDao.getUnitAppointTime(ThreadLocalMap.getUnitId(), projectId, powerId);
    }

    @Override
    public List<Unit> listUnitByRoleAndPower(Integer roleId, Integer powerId) {
        List<Unit> list = new ArrayList<>();
        Integer currentRole = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer type = this.getPowerType(powerId);
        if (UserRoleEnum.OWNER.getCode().equals(roleId)) {
            list = userExpDao.listOwnerByPower(currentRole, unitId, type, powerId);
        } else if (UserRoleEnum.UNDERTAKING_MAIN.getCode().equals(roleId)) {
            list = userExpDao.listMainByPower(currentRole, unitId, type, powerId);
        }
        return list;
    }

    @Override
    public List<BridgeType> getBridgeType() {
        return bridgeTypeDao.findAll();
    }

    /**
     * 传感器分类 1：称重 2：基康 3：其他
     * companyId: 产商
     * sensorTypeId：传感器类型
     */
    @Override
    public Integer getSensorType(Integer companyId, Integer sensorDetailsId) {

        Integer type = 3;
        if (GlobalConstant.SENSOR_WEIGHT.equals(sensorDetailsId)) {
            return 1;
        }
        if (GlobalConstant.COMPANY_JK.equals(companyId)) { //基康数据
            return 2;
        }
        if (!GlobalConstant.COMPANY_JK.equals(companyId) && !GlobalConstant.SENSOR_WEIGHT.equals(sensorDetailsId)) { //其他
            return 3;
        }
        return type;
    }
}
