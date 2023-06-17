package com.iware.bridge.evaluation.service;

import com.iware.bridge.evaluation.dao.BridgeScoreExpDao;
import com.iware.bridge.evaluation.dao.DiseaseInstanceExpDao;
import com.iware.bridge.evaluation.dao.MonitorPlanExpDao;
import com.iware.bridge.evaluation.dao.MonitorPlanStructureRelExpDao;
import com.iware.bridge.evaluation.vo.*;
import com.iware.bridge.info.dao.ProjectExpDao;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.properties.PowerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WJP
 * @date 2021-8-16
 */

@Service
public class EvaluationAnalysisServiceImpl implements EvaluationAnalysisService {

    @Autowired
    private MonitorPlanStructureRelExpDao monitorPlanStructureRelExpDao;
    @Autowired
    private PowerProperties powerProperties;
    @Autowired
    private ProjectExpDao projectExpDao;
    @Autowired
    private DiseaseInstanceExpDao diseaseInstanceExpDao;
    @Autowired
    private BridgeScoreExpDao bridgeScoreExpDao;
    @Autowired
    private MonitorPlanExpDao monitorPlanExpDao;

    @Override
    public List<AppointProjectVO> getProjects() {
        Integer roleId = ThreadLocalMap.getRoleId();
        List<AppointProjectVO> projects = new ArrayList<>();
        if (roleId == 3) {
            projects = projectExpDao.selectProjectAll(ThreadLocalMap.getUnitId(), roleId);
        } else if (roleId == 2) {
            projects = projectExpDao.selectProjectAll(ThreadLocalMap.getUnitId(), roleId);
        } else if (roleId == 1 || roleId == 0) {
            projects = projectExpDao.selectProjectByUserId(ThreadLocalMap.getUnitId(), powerProperties.getEvaluationPower());
        }

        return projects;
    }

    @Override
    public EvaluationAnalysisVO getNewProject(MonitorAnalysisFilterVO monitorAnalysisFilterVO) {
        EvaluationAnalysisVO newProject = null;
        if (monitorAnalysisFilterVO.getProjectId() == null) {
            //如果前端没传回查询的项目ID，则从全部ID中查询
            monitorAnalysisFilterVO.setList(getProjects());
        }
        newProject = monitorPlanStructureRelExpDao.selectNewProject(monitorAnalysisFilterVO);
        return newProject;
    }

    @Override
    public MonitorAnalysisResultVO getPageInfo(MonitorAnalysisFilterVO monitorAnalysisFilterVO) {
        EvaluationAnalysisVO newProject = getNewProject(monitorAnalysisFilterVO);
        MonitorAnalysisResultVO ret = new MonitorAnalysisResultVO();
        if (newProject != null) {
            monitorAnalysisFilterVO.setId(newProject.getId());

            //设置检测次数
            ret.setDetectionNumber(monitorPlanStructureRelExpDao.selectDetectionNumber(monitorAnalysisFilterVO));

            //设置结构病害数量统计和检测病害数
            List<DiseaseNumber> list = diseaseInstanceExpDao.selectDiseaseNumber(monitorAnalysisFilterVO);
            ret.setDiseaseStatistics(list);
            for (DiseaseNumber diseaseNumber : list) {
                ret.setDetectionDiseaseNumber(ret.getDetectionDiseaseNumber() + diseaseNumber.getValue());
            }

            //设置构件病害数量统计
            ret.setComponentDiseaseNumber(diseaseInstanceExpDao.selectComponentDisease(monitorAnalysisFilterVO));

        } else {
            ret.setDiseaseStatistics(new ArrayList<>());
            ret.setComponentDiseaseNumber(new ArrayList<>());
        }
        return ret;
    }

    @Override
    public List<ComponentDiseaseTypeNumber> getComponentDiseaseType(MonitorAnalysisFilterVO monitorAnalysisFilterVO) {
        List<ComponentDiseaseTypeNumber> list = new ArrayList<>();
        EvaluationAnalysisVO newProject = getNewProject(monitorAnalysisFilterVO);
        if (newProject != null) {
            monitorAnalysisFilterVO.setId(newProject.getId());

            //设置构件病害类型统计
            String typeName = monitorAnalysisFilterVO.getTypeName();
            if (!typeName.equals("")) {
                monitorAnalysisFilterVO.setComponentType(typeName.split(":")[0].trim());
                monitorAnalysisFilterVO.setComponentName(typeName.split(":")[1].trim());
            }
            list = diseaseInstanceExpDao.selectComponentDiseaseType(monitorAnalysisFilterVO);
        }
        return list;
    }

    @Override
    public List<ScoreComparison> getBciBsi(MonitorAnalysisFilterVO monitorAnalysisFilterVO) {
        EvaluationAnalysisVO newProject = getNewProject(monitorAnalysisFilterVO);
        if (newProject != null) {
            monitorAnalysisFilterVO.setId(newProject.getId());
        }
        return bridgeScoreExpDao.selectBciBsi(monitorAnalysisFilterVO);
    }

    @Override
    public List<String> selectStructureTimeByStructureId(Integer id,Integer project) {
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();
        return monitorPlanExpDao.selectStructureTimeByStructureId(id,unitId,roleId,project);
    }
}
