package com.iware.bridge.evaluation.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.evaluation.dao.BridgeExpDao;
import com.iware.bridge.evaluation.dao.BridgeScoreExpDao;
import com.iware.bridge.evaluation.dao.DiseaseInstanceExpDao;
import com.iware.bridge.evaluation.dao.MonitorPlanExpDao;
import com.iware.bridge.evaluation.vo.*;
import com.iware.bridge.info.dao.StructureExpDao;
import com.iware.bridge.model.dao.evaluation.BridgeTypeDao;
import com.iware.bridge.model.dao.global.StructureDao;
import com.iware.bridge.model.entity.evaluation.BridgeType;
import com.iware.bridge.model.entity.evaluation.Component;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.properties.PowerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WJP
 * @date 2021-8-16
 */

@Service
public class EvaluationOverviewServiceImpl implements EvaluationOverviewService {

    @Autowired
    private PowerProperties power;
    @Autowired
    private StructureExpDao structureExpDao;
    @Autowired
    private BridgeExpDao bridgeExpDao;
    @Autowired
    private StructureDao structureDao;
    @Autowired
    private DiseaseInstanceExpDao diseaseInstanceExpDao;
    @Autowired
    private BridgeScoreExpDao bridgeScoreExpDao;
    @Autowired
    private BridgeTypeDao bridgeTypeDao;
    @Autowired
    private EvaluationAnalysisService evaluationAnalysisService;
    @Autowired
    private MonitorPlanExpDao monitorPlanExpDao;

    public List<Integer> getStructureIdList() {
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();
        List<Structure> structures = new ArrayList<>();
        if (roleId == 1 || roleId == 0) {
            structures = structureExpDao.getStructureCJ(unitId, power.getEvaluationPower());
        } else if (roleId == 2) {
            Structure structure = new Structure();
            structure.setUnitId(unitId);
            structures = structureExpDao.getStructureYZ(unitId);
        } else if (roleId == 3) {
            structures = structureDao.findAll();
        }
        List<Integer> integers = new ArrayList<>();
        if (structures.size() != 0) {
            integers = structures.stream().map(Structure::getId).collect(Collectors.toList());
        }
        return integers;
    }

    @Override
    public Map<String, Integer> getCount() {
        Map<String, Integer> map = new HashMap<>();
        List<AppointProjectVO> projectList = evaluationAnalysisService.getProjects();
        List<Integer> list = getStructureIdList();


        map.put("project", monitorPlanExpDao.getItemCount(projectList));
        map.put("structure", monitorPlanExpDao.getStructureCount(list));

        return map;
    }

    @Override
    public Map<String, List<TechnologyStatusVO>> listTechnologyStatus() {
        Map<String, List<TechnologyStatusVO>> map = new HashMap<>();
        List<Integer> structureIdList = getStructureIdList();
        if (structureIdList.size() != 0) {
            map.put("city", bridgeExpDao.listCityTechnologyStatus(structureIdList));
            map.put("road", bridgeExpDao.listRoadTechnologyStatus(structureIdList));
        }
        return map;
    }

    @Override
    public List<HashMap<String, Object>> getDiseaseCount(BridgeTypeDiseaseFilter filter) {
        List<HashMap<String, Object>> map = new ArrayList<>();
        List<Integer> structureIdList = getStructureIdList();
        if (structureIdList.size() != 0) {
            map = diseaseInstanceExpDao.getDiseaseCount(filter, structureIdList);
        }
        return map;
    }

    @Override
    public PageInfo<BridgeLevelRankVO> listBridgeRank(BridgeLevelRankFilter filter) {
        List<Integer> structures = getStructureIdList();
        List<ScoreSearchVO> scoreIds = new ArrayList<>(); //查询所保留的ID

        for (ScoreSearchVO scoreSearchVO : bridgeScoreExpDao.getAllScoreByStructures(structures)) {
            Integer structureId = scoreSearchVO.getStructureId();
            List<Integer> haveAddIds = scoreIds.stream().map(ScoreSearchVO::getStructureId).collect(Collectors.toList())
                    .stream().distinct().collect(Collectors.toList());  //将已经添加到保留列表的结构物ID 去重提取出来
            if (!haveAddIds.contains(structureId)) {
                //如果未添加到查询列表中，则做添加操作
                scoreIds.add(scoreSearchVO);
            }
        }
        filter.setList(scoreIds);
        PageHelper.startPage(filter.getPageNum(), filter.getPageSize());
        List<BridgeLevelRankVO> list = bridgeScoreExpDao.listAssessRank(filter);
        return new PageInfo<>(list);
    }

    @Override
    public List<String> listRankYear() {
        return bridgeExpDao.listRankYear();
    }

    @Override
    public List<BridgeType> listBridgeType() {
        List<BridgeType> list = bridgeTypeDao.findAll();
        Collections.reverse(list);
        return list;
    }

    @Override
    public List<Component> getComponent(Integer id) {
        return bridgeExpDao.selectComponentByBridgeTypeId(id);
    }
}
