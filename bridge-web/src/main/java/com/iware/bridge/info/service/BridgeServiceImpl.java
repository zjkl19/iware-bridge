package com.iware.bridge.info.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.evaluation.dao.MonitorPlanStructureRelExpDao;
import com.iware.bridge.evaluation.vo.MonitorRecord;
import com.iware.bridge.info.dao.StructureExpDao;
import com.iware.bridge.info.vo.BridgeTunnelDetailVO;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.inspection.dao.PlanDetailExpDao;
import com.iware.bridge.inspection.vo.MaintainItemVO;
import com.iware.bridge.inspection.vo.PlanDetailVO;
import com.iware.bridge.inspection.vo.PlanRecordFilter;
import com.iware.bridge.model.dao.global.BridgeInfoDao;
import com.iware.bridge.model.dao.global.StructureDao;
import com.iware.bridge.model.entity.global.BridgeInfo;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.enums.UserRoleEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.properties.PowerProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-5
 */

@Service
public class BridgeServiceImpl implements BridgeService {

    @Autowired
    private PowerProperties power;
    @Autowired
    private StructureDao structureDao;
    @Autowired
    private BridgeInfoDao bridgeInfoDao;
    @Autowired
    private StructureService structureService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private StructureExpDao structureExpDao;
    @Autowired
    private MonitorPlanStructureRelExpDao monitorPlanStructureRelExpDao;
    @Autowired
    private PlanDetailExpDao planDetailExpDao;
    @Override
    public PageInfo<Structure> getBridgeList(Integer pageNum, Integer pageSize,
                                             InfoFilter infoFilter) {

        PageHelper.startPage(pageNum, pageSize);
        infoFilter.setStructureType(GlobalConstant.TYPE_BRIDGE);
        List<Structure> list = structureExpDao.getStructureList(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), infoFilter);
        return new PageInfo<>(list);
    }

    @Override
    public List<Structure> getBridgeListByProjectId(Integer projectId) {
        return structureExpDao.getStructureByProjectId(projectId);
    }

    @Override
    public BridgeTunnelDetailVO getBridgeDetail(Integer structureId) {
        BridgeTunnelDetailVO detailVO = new BridgeTunnelDetailVO();
        Structure structure = structureDao.findById(structureId);
        BeanUtils.copyProperties(structure, detailVO);

        BridgeInfo bridgeQry = new BridgeInfo();
        bridgeQry.setStructureId(structureId);

        List<BridgeInfo> list = bridgeInfoDao.query(bridgeQry);
        if (!CollectionUtils.isEmpty(list)) {
            if (list.size() > 1) {
                throw new BusinessException(GlobalResultEnum.INTERNAL_SERVER_ERROR);
            }
            detailVO.setBridgeInfo(list.get(0));
        } else {
            throw new BusinessException(GlobalResultEnum.INTERNAL_SERVER_ERROR);
        }

        return detailVO;
    }

    @Override
    @Transactional
    public void addBridge(Structure structure) {

        structureService.checkStructureExist(structure);
        structure.setStructureType(GlobalConstant.TYPE_BRIDGE);
        structure.setCreateUserId(ThreadLocalMap.getUserId());
        if (UserRoleEnum.OWNER.getCode().equals(ThreadLocalMap.getRoleId())) {
            structure.setUnitId(ThreadLocalMap.getUnitId());
        }
        structureDao.save(structure);

        //添加一条对应的详细信息记录
        BridgeInfo bridgeInfo = new BridgeInfo();
        bridgeInfo.setStructureId(structure.getId());
        bridgeInfoDao.save(bridgeInfo);
    }

    @Override
    @Transactional
    public void updateBridge(Structure structure) {

        structureService.checkStructureExist(structure);
        structureExpDao.update(structure);
    }

    @Override
    @Transactional
    public void updateBridgeDetail(BridgeTunnelDetailVO detailVO) {

        structureService.checkStructureExist(detailVO);
        structureExpDao.update(detailVO);
        structureExpDao.updateBridgeDetail(detailVO.getBridgeInfo());
    }

    @Override
    @Transactional
    public void deleteBridge(Integer structureId) {

        //检查结构物关联
        structureService.checkStructureRel(structureId);

        //删除图片
        photoService.deleteByTypeAndTargetId(structureId, 1);
        //删除附件
        photoService.deleteByTypeAndTargetId(structureId, 5);
        //删除桥梁信息
        structureDao.deleteById(structureId);
        structureExpDao.deleteBridgeInfo(structureId);

    }

    @Override
    public PageInfo<Photo> getAnnexList(Integer structureId, Integer pageNum, Integer pageSize) {
        return photoService.listByPage(pageNum, pageSize, structureId, GlobalConstant.PHOTO_BRIDGE_ANNEX);
    }

    @Override
    public PageInfo<MonitorRecord> getMonitorRecord(Integer pageNum, Integer pageSize, Integer structureId) {
        PageHelper.startPage(pageNum, pageSize);
        List<MonitorRecord> list = monitorPlanStructureRelExpDao.getMonitorRecordList(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), power.getEvaluationPower(), structureId);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<PlanDetailVO> getInspectionRecordList(Integer pageNum, Integer pageSize, Integer structureId) {
        PageHelper.startPage(pageNum, pageSize);
        List<PlanDetailVO> list = planDetailExpDao.listInspectionRecordByStructure(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), power.getInspectionPower(), structureId);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<MaintainItemVO> getMaintainRecordList(Integer pageNum, Integer pageSize, Integer structureId) {
        PageHelper.startPage(pageNum, pageSize);
        PlanRecordFilter filter = new PlanRecordFilter();
        filter.setStructureId(structureId);
        List<MaintainItemVO> list = planDetailExpDao.listMaintainRecord(ThreadLocalMap.getUnitId(),
                ThreadLocalMap.getRoleId(), power.getMaintainPower(), filter);
        return new PageInfo<>(list);
    }
}
