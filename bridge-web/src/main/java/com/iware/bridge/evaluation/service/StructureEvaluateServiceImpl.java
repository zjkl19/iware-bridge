package com.iware.bridge.evaluation.service;

import com.iware.bridge.evaluation.dao.BridgeScoreExpDao;
import com.iware.bridge.evaluation.dao.DiseaseInstanceExpDao;
import com.iware.bridge.evaluation.vo.*;
import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.model.dao.evaluation.BridgeTypeDao;
import com.iware.bridge.model.dao.evaluation.ComponentDao;
import com.iware.bridge.model.dao.global.*;
import com.iware.bridge.model.entity.evaluation.BridgeType;
import com.iware.bridge.model.entity.evaluation.Component;
import com.iware.bridge.model.entity.global.BridgeInfo;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.global.TunnelInfo;
import com.iware.common.data.ThreadLocalMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WJP
 * @date 2021-8-16
 */

@Service
public class StructureEvaluateServiceImpl implements StructureEvaluateService {

    @Autowired
    private BridgeScoreExpDao bridgeScoreExpDao;
    @Autowired
    private DiseaseInstanceExpDao diseaseInstanceExpDao;
    @Autowired
    private StructureDao structureDao;
    @Autowired
    private PhotoDao photoDao;
    @Autowired
    private BridgeInfoDao bridgeInfoDao;
    @Autowired
    private TunnelInfoDao tunnelInfoDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private BridgeTypeDao bridgeTypeDao;
    @Autowired
    private ComponentDao componentDao;
    @Autowired
    private EvaluationRecordServiceImpl evaluationRecordService;

    @Override
    public Map<String, String> getNewestScore(Integer structureId) {
        Map<String, String> map = new HashMap<>();
        List<OverviewVO> evaluationInfo = getEvaluationInfo(structureId);
        Double min = 100.0;
        OverviewVO result = null;
        for (OverviewVO overviewVO : evaluationInfo) {
            if (min >= overviewVO.getBridgeConditionIndex()) {
                min = overviewVO.getBridgeConditionIndex();
                result = overviewVO;
            }
        }
        if (result == null) {
            map.put("bci", "-");
            map.put("bsim", "-");
            map.put("bsis", "-");
            map.put("bsix", "-");
        } else {
            String value = BigDecimal.valueOf(result.getBridgeConditionIndex()).stripTrailingZeros().toPlainString();
            map.put("bci", "" + value);
            String value2 = BigDecimal.valueOf(result.getDeckSystemBSIm()).stripTrailingZeros().toPlainString();
            map.put("bsim", "" + value2);
            String value3 = BigDecimal.valueOf(result.getUpperStructureBSIs()).stripTrailingZeros().toPlainString();
            map.put("bsis", "" + value3);
            String value4 = BigDecimal.valueOf(result.getLowerStructureBSIx()).stripTrailingZeros().toPlainString();
            map.put("bsix", "" + value4);
        }

        return map;
    }

    @Override
    public List<ScoreVO> getScoreTrend(Integer structureId, Integer partType) {
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();
        List<ScoreVO> list = new ArrayList<>();
        if (partType == 3 || partType == 4 || partType == 5) {
            list = bridgeScoreExpDao.selectRoadBciBsiYear(unitId, roleId, structureId, partType);
        } else if (partType == 2) {
            list = bridgeScoreExpDao.selectRelBci(unitId, roleId, structureId);
        }
        return list;
    }

    @Override
    public List<EchartMap> getDiseaseByComponent(Integer structureId, Integer componentId) {
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();
        return diseaseInstanceExpDao.selectComponentDiseaseCount(unitId, roleId, structureId, componentId);
    }

    @Override
    public List<OverviewVO> getEvaluationInfo(Integer structureId) {
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();
        List<OverviewVO> overviewVOS = bridgeScoreExpDao.selectTestingSituation(unitId, roleId, structureId);
        List<OverviewVO> result = new ArrayList<>();
        for (OverviewVO overviewVO : overviewVOS) {
            AssessRecordFilter scoreFilter = new AssessRecordFilter();
            scoreFilter.setStructureInfoId(overviewVO.getMonitorStructureId());
            List<BCIEvaluation> bciList = evaluationRecordService.getBCIEvaluationList(scoreFilter);
            Boolean is = true;
            for (BCIEvaluation bciEvaluation : bciList) {
                if (bciEvaluation.getId() == null) {
                    is = false;
                    break;
                }
            }
            if (is) {
                result.add(overviewVO);
            }
        }
        for (OverviewVO overviewVO : result) {
            DecimalFormat df = new DecimalFormat("######0.00");
            overviewVO.setBridgeConditionIndex(Double.parseDouble(df.format(overviewVO.getBridgeConditionIndex())));
            overviewVO.setDeckSystemBSIm(Double.parseDouble(df.format(overviewVO.getDeckSystemBSIm())));
            overviewVO.setLowerStructureBSIx(Double.parseDouble(df.format(overviewVO.getLowerStructureBSIx())));
            overviewVO.setUpperStructureBSIs(Double.parseDouble(df.format(overviewVO.getUpperStructureBSIs())));
        }
        return result;
    }

    @Override
    public BasicVO getBasic(Integer structureId) {
        BasicVO basicVO = new BasicVO();
        Structure structure = structureDao.findById(structureId);
        BeanUtils.copyProperties(structure, basicVO);

        Photo photo = new Photo();
        photo.setType(1);
        photo.setTargetId(structureId);
        basicVO.setList(photoDao.query(photo));

        //判断是桥梁还是隧道
        if (structure.getStructureType() == 1) {
            BridgeInfo bridgeInfo = new BridgeInfo();
            bridgeInfo.setStructureId(structureId);
            List<BridgeInfo> bridgeInfos = bridgeInfoDao.query(bridgeInfo);
            if (bridgeInfos.size() != 0) {
                basicVO.setBridgeInfo(bridgeInfos.get(0));
            }
        } else {
            TunnelInfo tunnelInfo = new TunnelInfo();
            tunnelInfo.setStructureId(structureId);
            List<TunnelInfo> tunnelInfos = tunnelInfoDao.query(tunnelInfo);
            if (tunnelInfos.size() != 0) {
                basicVO.setTunnelInfo(tunnelInfos.get(0));
            }

        }

        BridgeType bridgeType = new BridgeType();
        bridgeType.setId(structure.getBridgeType());
        List<BridgeType> bridgeTypeList = bridgeTypeDao.query(bridgeType);
        if (bridgeTypeList.size() != 0) {
            basicVO.setBridgeTypeName(bridgeTypeList.get(0).getName());
        }

//        com.iware.bridge.model.entity.global.Project project = new Project();
//        photo.setId(projectId);
//        List<Project> projectList = projectDao.query(project);
//
//        basicVO.setProjectName(projectList.get(0).getName());

        return basicVO;
    }

    @Override
    public List<Component> getComponent() {
        return componentDao.findAll();
    }
}
