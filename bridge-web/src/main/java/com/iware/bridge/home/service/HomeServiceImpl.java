package com.iware.bridge.home.service;

import com.iware.bridge.home.dao.HomeDao;
import com.iware.bridge.home.vo.*;
import com.iware.bridge.info.dao.ProjectExpDao;
import com.iware.bridge.info.dao.StructureExpDao;
import com.iware.bridge.info.service.CommonService;
import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.info.vo.StructureFilter;
import com.iware.bridge.model.dao.online.MeansPointDao;
import com.iware.bridge.model.dao.online.SensorDao;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.inspection.PlanDetail;
import com.iware.bridge.model.entity.online.MeansPoint;
import com.iware.bridge.model.entity.online.Sensor;
import com.iware.bridge.model.entity.online.WarningRecord;
import com.iware.bridge.online.dao.SensorExpDao;
import com.iware.bridge.online.dao.WarningRecordExpDao;
import com.iware.bridge.online.service.WarningService;
import com.iware.bridge.online.vo.WarningRatio;
import com.iware.bridge.video.dao.VideoExpDao;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.UserRoleEnum;
import com.iware.common.properties.PowerProperties;
import com.iware.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ZRB
 * @date 2021-8-4
 */

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private PowerProperties power;
    @Autowired
    private HomeDao homeDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ProjectExpDao projectExpDao;
    @Autowired
    private StructureExpDao structureExpDao;
    @Autowired
    private WarningService warningService;
    @Autowired
    private MeansPointDao meansPointDao;
    @Autowired
    private SensorDao sensorDao;
    @Autowired
    private SensorExpDao sensorExpDao;
    @Autowired
    private VideoExpDao videoExpDao;
    @Autowired
    private WarningRecordExpDao warningRecordExpDao;

    @Override
    public StructureType listStructure(StructureFilter filter) {
        StructureType structureType = new StructureType();
        List<Structure> list = commonService.listUnitStructure(filter);
        structureType.setStructureList(list);
        if (!CollectionUtils.isEmpty(list)) {
            structureType.setOnlineList(this.listStructureByModel(power.getOnlinePower(), filter));
            structureType.setInspectionList(this.listStructureByModel(power.getInspectionPower(), filter));
            structureType.setMaintainList(this.listStructureByModel(power.getMaintainPower(), filter));
            structureType.setEvaluationList(this.listStructureByModel(power.getEvaluationPower(), filter));
        } else {
            structureType.setOnlineList(new ArrayList<>());
            structureType.setInspectionList(new ArrayList<>());
            structureType.setMaintainList(new ArrayList<>());
            structureType.setEvaluationList(new ArrayList<>());
        }
        return structureType;
    }

    /**
     * 获取用户有传感器/巡查/维养/检测计划的桥隧
     */
    private List<Integer> listStructureByModel(Integer powerId, StructureFilter filter) {
        filter.setPowerId(powerId);
        List<Structure> list = commonService.listUnitStructureByModel(filter);
        if (!CollectionUtils.isEmpty(list)) {
            return list.stream().map(Structure::getId).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public HomeBase getBaseInfo() {

        HomeBase homeBase = new HomeBase();
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();
        //项目数和项目变化数
        List<Integer> projectIds = projectExpDao.getUnitProjectIds(roleId, unitId);
        List<Integer> lastProjectIds = homeDao.getLastUnitProjectIds(roleId, unitId);

        homeBase.setProjectNum(projectIds.size());
        homeBase.setProjectChangeNum(projectIds.size() - lastProjectIds.size());

        List<Structure> structureList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(projectIds)) {
            //桥梁数、桥梁变化数，隧道数、隧道变化数
            structureList = structureExpDao.getStructureByProjectIds(projectIds);
            List<Structure> lastStructureList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(lastProjectIds)) {
                lastStructureList = structureExpDao.getStructureByProjectIds(lastProjectIds);
            }

            Long count = structureList.stream().filter(s ->
                    GlobalConstant.TYPE_BRIDGE.equals(s.getStructureType())
            ).count();
            Long oldCount = lastStructureList.stream().filter(s ->
                    GlobalConstant.TYPE_BRIDGE.equals(s.getStructureType())
            ).count();
            homeBase.setBridgeNum(count.intValue());
            homeBase.setBridgeChangeNum(count.intValue() - oldCount.intValue());

            count = structureList.stream().filter(s ->
                    GlobalConstant.TYPE_TUNNEL.equals(s.getStructureType())
            ).count();
            oldCount = lastStructureList.stream().filter(s ->
                    GlobalConstant.TYPE_TUNNEL.equals(s.getStructureType())
            ).count();
            homeBase.setTunnelNum(count.intValue());
            homeBase.setTunnelChangeNum(count.intValue() - oldCount.intValue());

        } else {
            homeBase.setBridgeNum(0);
            homeBase.setBridgeChangeNum(0);
            homeBase.setTunnelNum(0);
            homeBase.setTunnelChangeNum(0);
        }
        this.fillBridgeSortByType(homeBase, structureList, projectIds);
        this.fillRoadTechnology(homeBase, structureList);
        return homeBase;
    }

    /** 桥隧概况 */
    private void fillBridgeSortByType(HomeBase homeBase, List<Structure> list, List<Integer> projectIds) {
        //桥隧概况
        Integer extraLargeBridge = 0, bigBridge = 0, mediumBridge = 0, smallBridge = 0;
        Integer extraLargeTunnel = 0, longTunnel = 0, mediumTunnel = 0, shortTunnel = 0;
        Integer first = 0, second = 0, third = 0, four = 0, five = 0, six = 0;

        if (!CollectionUtils.isEmpty(list)) {
            for (Structure structure : list) {
                if (GlobalConstant.TYPE_BRIDGE.equals(structure.getStructureType())) {

                    if (!StringUtils.isEmpty(structure.getSpanType())) {
                        switch (structure.getSpanType()) {
                            case "特大桥": extraLargeBridge ++; break;
                            case "大桥": bigBridge ++; break;
                            case "中桥": mediumBridge ++; break;
                            case "小桥": smallBridge ++; break;
                            default: break;
                        }
                    }

                    if (!StringUtils.isEmpty(structure.getBridgeType())) {
                        switch (structure.getBridgeType()) {
                            case 1: first ++; break; case 2: second ++; break;
                            case 3: third ++; break; case 4: four ++; break;
                            case 5: five ++; break; case 6: six ++; break;
                            default: break;
                        }
                    }
                } else {

                    if (!StringUtils.isEmpty(structure.getSpanType())) {
                        switch (structure.getSpanType()) {
                            case "特长隧道": extraLargeTunnel++; break;
                            case "长隧道": longTunnel++; break;
                            case "中隧道": mediumTunnel++; break;
                            case "短隧道": shortTunnel++; break;
                            default: break;
                        }
                    }
                }
            }
        }

        List<EchartMap> typeList = new ArrayList<>();
        typeList.add(new EchartMap("特大桥", extraLargeBridge));
        typeList.add(new EchartMap("大桥", bigBridge));
        typeList.add(new EchartMap("中桥", mediumBridge));
        typeList.add(new EchartMap("小桥", smallBridge));
        typeList.add(new EchartMap("特长隧道", extraLargeTunnel));
        typeList.add(new EchartMap("长隧道", longTunnel));
        typeList.add(new EchartMap("中隧道", mediumTunnel));
        typeList.add(new EchartMap("短隧道", shortTunnel));
        typeList.sort((x, y) -> Integer.compare(y.getCount(), x.getCount()));
        homeBase.setSortByType(typeList);

        List<EchartMap> bridgeTypeList = new ArrayList<>();
        bridgeTypeList.add(new EchartMap("梁桥", first));
        bridgeTypeList.add(new EchartMap("拱桥", second));
        bridgeTypeList.add(new EchartMap("刚架拱桥", third));
        bridgeTypeList.add(new EchartMap("悬索桥", four));
        bridgeTypeList.add(new EchartMap("斜拉桥", five));
        bridgeTypeList.add(new EchartMap("钢管混凝土拱桥", six));
        bridgeTypeList.sort((x, y) -> Integer.compare(y.getCount(), x.getCount()));
        homeBase.setSortByBridgeType(bridgeTypeList);

        if (!CollectionUtils.isEmpty(projectIds)) {
            List<EchartMap> areaList = homeDao.sortByArea(projectIds);
            areaList.sort((x, y) -> Integer.compare(y.getCount(), x.getCount()));
            homeBase.setSortByArea(areaList);
        }

    }

    /** 公路桥隧技术状况 */
    private void fillRoadTechnology(HomeBase homeBase, List<Structure> list) {
        Integer bridgeRoadOne = 0, bridgeRoadTwo = 0, bridgeRoadThird = 0, bridgeRoadFour = 0, bridgeRoadFive = 0;
        Integer tunnelRoadOne = 0, tunnelRoadTwo = 0, tunnelRoadThird = 0, tunnelRoadFour = 0, tunnelRoadFive = 0;
        Integer bridgeCityOne = 0, bridgeCityTwo = 0, bridgeCityThird = 0, bridgeCityFour = 0, bridgeCityFive = 0,
                bridgeCitySix = 0, bridgeCitySeven = 0;
        Integer tunnelCityOne = 0, tunnelCityTwo = 0, tunnelCityThird = 0, tunnelCityFour = 0, tunnelCityFive = 0;

        if (!CollectionUtils.isEmpty(list)) {
            for (Structure structure:list) {
                if (!StringUtils.isEmpty(structure.getTechnology()) && !StringUtils.isEmpty(structure.getGrade())) {

                    if (GlobalConstant.TYPE_BRIDGE.equals(structure.getStructureType())) {

                        if ("公路桥隧技术状况".equals(structure.getTechnology())) {
                            switch (structure.getGrade()) {
                                case "1类": bridgeRoadOne++; break;
                                case "2类": bridgeRoadTwo++; break;
                                case "3类": bridgeRoadThird++; break;
                                case "4类": bridgeRoadFour++; break;
                                case "5类": bridgeRoadFive++; break;
                                default: break;
                            }
                        } else {
                            switch (structure.getGrade()) {
                                case "A级": bridgeCityOne++; break;
                                case "B级": bridgeCityTwo++; break;
                                case "C级": bridgeCityThird++; break;
                                case "D级": bridgeCityFour++; break;
                                case "E级": bridgeCityFive++; break;
                                case "合格级": bridgeCitySix++; break;
                                case "不合格级": bridgeCitySeven++; break;
                                default: break;
                            }
                        }

                    } else {
                        if ("公路桥隧技术状况".equals(structure.getTechnology())) {
                            switch (structure.getGrade()) {
                                case "1类": tunnelRoadOne++; break;
                                case "2类": tunnelRoadTwo++; break;
                                case "3类": tunnelRoadThird++; break;
                                case "4类": tunnelRoadFour++; break;
                                case "5类": tunnelRoadFive++; break;
                                default: break;
                            }
                        } else {
                            switch (structure.getGrade()) {
                                case "1类": tunnelCityOne++; break;
                                case "2类": tunnelCityTwo++; break;
                                case "3类": tunnelCityThird++; break;
                                case "4类": tunnelCityFour++; break;
                                case "5类": tunnelCityFive++; break;
                                default: break;
                            }
                        }
                    }
                }
            }
        }

        List<EchartMap> bridgeRoadList = new ArrayList<>();
        bridgeRoadList.add(new EchartMap("1类", bridgeRoadOne));
        bridgeRoadList.add(new EchartMap("2类", bridgeRoadTwo));
        bridgeRoadList.add(new EchartMap("3类", bridgeRoadThird));
        bridgeRoadList.add(new EchartMap("4类", bridgeRoadFour));
        bridgeRoadList.add(new EchartMap("5类", bridgeRoadFive));
        homeBase.setBridgeRoadTechnology(bridgeRoadList);

        List<EchartMap> tunnelRoadList = new ArrayList<>();
        tunnelRoadList.add(new EchartMap("1类", tunnelRoadOne));
        tunnelRoadList.add(new EchartMap("2类", tunnelRoadTwo));
        tunnelRoadList.add(new EchartMap("3类", tunnelRoadThird));
        tunnelRoadList.add(new EchartMap("4类", tunnelRoadFour));
        tunnelRoadList.add(new EchartMap("5类", tunnelRoadFive));
        homeBase.setTunnelRoadTechnology(tunnelRoadList);

        List<EchartMap> bridgeCityList = new ArrayList<>();
        bridgeCityList.add(new EchartMap("A级", bridgeCityOne));
        bridgeCityList.add(new EchartMap("B级", bridgeCityTwo));
        bridgeCityList.add(new EchartMap("C级", bridgeCityThird));
        bridgeCityList.add(new EchartMap("D级", bridgeCityFour));
        bridgeCityList.add(new EchartMap("E级", bridgeCityFive));
        bridgeCityList.add(new EchartMap("合格级", bridgeCitySix));
        bridgeCityList.add(new EchartMap("不合格级", bridgeCitySeven));
        homeBase.setBridgeCityTechnology(bridgeCityList);

        List<EchartMap> tunnelCityList = new ArrayList<>();
        tunnelCityList.add(new EchartMap("1类", tunnelCityOne));
        tunnelCityList.add(new EchartMap("2类", tunnelCityTwo));
        tunnelCityList.add(new EchartMap("3类", tunnelCityThird));
        tunnelCityList.add(new EchartMap("4类", tunnelCityFour));
        tunnelCityList.add(new EchartMap("5类", tunnelCityFive));

        homeBase.setTunnelCityTechnology(tunnelCityList);
    }

    @Override
    public Map<String, Integer> getInspMainInfo(Integer type) {
        Map<String, Integer> map = new HashMap<>();
        HomeFilter filter = new HomeFilter();
        filter.setRoleId(ThreadLocalMap.getRoleId());
        filter.setUnitId(ThreadLocalMap.getUnitId());
        filter.setStartTime(DateUtils.getFirstTime(type));
        filter.setEndTime(DateUtils.getLastTime(type));

        map.put("inspectionFinish", homeDao.getInspectionInfo(power.getInspectionPower(), 1, filter));
        map.put("inspectionIncomplete", homeDao.getInspectionInfo(power.getInspectionPower(), 0, filter));
        map.put("maintainFinish", homeDao.getMaintainInfo(power.getMaintainPower(), 1, filter));
        map.put("maintainIncomplete", homeDao.getMaintainInfo(power.getMaintainPower(), 0, filter));
        return map;
    }

    @Override
    public List<Double> getComprehensive(Integer structureId) {
        List<Double> result = new ArrayList<>();

        Boolean onlineModel = true, inspectionModel = true, maintainModel = true, evaluationModel = true;

        Integer roleId = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();

        HomeFilter homeFilter = new HomeFilter();
        homeFilter.setUnitId(unitId);
        homeFilter.setRoleId(roleId);
        homeFilter.setStructureId(structureId);
        homeFilter.setEndTime(DateUtils.getLastTime(1));

        //用户没有项目
        if (CollectionUtils.isEmpty(projectExpDao.getUnitProjectIds(roleId, unitId))) {
            return new ArrayList<>();
        }

        //查询用户桥梁评分
        List<BridgeScoreVO> scoreList = homeDao.getBridgeScore(roleId, unitId, structureId);
        if (!CollectionUtils.isEmpty(scoreList)) {
            Double avgValue = scoreList.stream().filter(item ->
                    item.getScore() != null).mapToDouble(BridgeScoreVO::getScore).average().getAsDouble();
            avgValue = new BigDecimal(avgValue).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            result.add(avgValue);
        } else {
            result.add(null);
        }


        if (UserRoleEnum.UNDERTAKING_MAIN.getCode().equals(roleId) ||
                UserRoleEnum.UNDERTAKING_NORMAL.getCode().equals(roleId)) {
            if (CollectionUtils.isEmpty(projectExpDao.getProjectByPower(unitId,
                    power.getOnlinePower(), null))) {
                onlineModel = false;//在线监测
            }
            if (CollectionUtils.isEmpty(projectExpDao.getProjectByPower(unitId,
                    power.getInspectionPower(), null))) {
                inspectionModel = false;//日常巡查
            }
            if (CollectionUtils.isEmpty(projectExpDao.getProjectByPower(unitId,
                    power.getMaintainPower(), null))) {
                maintainModel = false;//维修养护
            }
            if (CollectionUtils.isEmpty(projectExpDao.getProjectByPower(unitId,
                    power.getEvaluationPower(), null))) {
                evaluationModel = false;//检测评估
            }
        }
        Integer processed = 0, untreated = 0;
        if (Boolean.TRUE.equals(onlineModel)) {
            List<WarningRatio> warningRatioList = warningService.getWarningStatistics(0, structureId);
            if (!CollectionUtils.isEmpty(warningRatioList)) {
                processed = warningRatioList.stream().filter(warning ->
                        warning.getStatus().equals("已处理")).mapToInt(WarningRatio::getCount).sum();
                untreated = warningRatioList.stream().filter(warning ->
                        warning.getStatus().equals("未处理")).mapToInt(WarningRatio::getCount).sum();
                result.add(this.getResultScore(processed, untreated));
            } else {
                result.add(null);
            }
        } else {
            result.add(null);
        }

        if (Boolean.TRUE.equals(inspectionModel)) {
            processed = homeDao.getInspectionInfo(power.getInspectionPower(), 1, homeFilter);
            untreated = homeDao.getInspectionInfo(power.getInspectionPower(), 0, homeFilter);
            result.add(this.getResultScore(processed, untreated));
        } else {
            result.add(null);
        }

        if (Boolean.TRUE.equals(maintainModel)) {
            processed = homeDao.getMaintainInfo(power.getMaintainPower(), 1, homeFilter);
            untreated = homeDao.getMaintainInfo(power.getMaintainPower(), 0, homeFilter);
            result.add(this.getResultScore(processed, untreated));
        } else {
            result.add(null);
        }

        if (Boolean.TRUE.equals(evaluationModel)) {
            List<Integer> statusList = homeDao.getEvaluationPlanStatus(roleId, unitId, structureId);
            if (!CollectionUtils.isEmpty(statusList)) {
                processed = (int)(statusList.stream().filter(item -> item.equals(GlobalConstant.Y)).count());
                untreated = statusList.size() - processed;
                result.add(this.getResultScore(processed, untreated));
            } else {
                result.add(null);
            }
        } else {
            result.add(null);
        }
        return result;
    }

    @Override
    public Map<String, String> getEvaluationInfo(Integer structureId) {

        Map<String, String> map = new HashMap<>();
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();
        BridgeScoreVO bridgeScore = homeDao.getEvaluationInfo(roleId, unitId, structureId, power.getEvaluationPower());
        if (bridgeScore != null) {
            map.put("evaluationTime", DateUtils.formatDate(bridgeScore.getEvaluationTime(), DateUtils.FULL_DATE_FORMAT));
            map.put("score", bridgeScore.getScore() == null ? "0" : bridgeScore.getScore().toString());
            map.put("rantingLevel", bridgeScore.getRatingLevel());
        } else {
            map.put("evaluationTime", null);
            map.put("score", "0");
            map.put("rantingLevel", null);
        }


        if(bridgeScore != null && bridgeScore.getMonitorPlanRelId() != null) {
            map.put("diseaseCount", homeDao.getStructureDisease(bridgeScore.getMonitorPlanRelId()).toString());
        } else {
            map.put("diseaseCount", null);
        }
        return map;
    }

    @Override
    public Map<String, String> getLastInspMainInfo(Integer structureId) {


        Map<String, String> map = new HashMap<>();
        Map<String, String> lastInspection = new HashMap<>();
        List<EchartMap> lastMaintain = new ArrayList<>();

        HomeFilter filter = new HomeFilter();
        filter.setRoleId(ThreadLocalMap.getRoleId());
        filter.setUnitId(ThreadLocalMap.getUnitId());
        filter.setStartTime(DateUtils.getFirstTime(2));
        filter.setEndTime(DateUtils.getLastTime(2));
        filter.setStructureId(structureId);

        Integer count = homeDao.getInspectionInfo(power.getInspectionPower(), 1, filter);
        map.put("inspectionFinish", count == null ? "0" : count.toString());
        count = homeDao.getInspectionInfo(power.getInspectionPower(), 0, filter);
        map.put("inspectionIncomplete", count == null ? "0" : count.toString());

        PlanDetail planDetail = homeDao.getLastPlanDetail(ThreadLocalMap.getRoleId(), ThreadLocalMap.getUnitId(),
                structureId, power.getInspectionPower());

        if (planDetail != null) {
            lastInspection = homeDao.getInspectionLastInfo(planDetail.getId());
            lastMaintain = homeDao.getMaintainLastInfo(planDetail.getId());
        }

        if (!CollectionUtils.isEmpty(lastInspection)) {
            map.put("diseaseCount", lastInspection.get("count"));
            map.put("inspectionTime", lastInspection.get("time"));
        } else {
            map.put("diseaseCount", "0");
            map.put("inspectionTime", "/");
        }

        Integer allRepair = homeDao.getAllRepair(ThreadLocalMap.getRoleId(), ThreadLocalMap.getUnitId(),
                structureId, power.getInspectionPower());
        map.put("maintainFinish", allRepair.toString());
        map.put("maintainIncomplete", allRepair.toString());
        if (!CollectionUtils.isEmpty(lastMaintain)) {
            Integer lastRepair = lastMaintain.stream().filter(item -> "未修复".equals(item.getStatus()))
                    .map(EchartMap::getCount).findFirst().orElse(0);
            allRepair += lastRepair;
            map.put("maintainIncomplete", allRepair.toString());
        }

        return map;
    }

    @Override
    public Map<String, Integer> getSensorInfo(Integer structureId) {

        Map<String, Integer> map = new HashMap<>();

        MeansPoint meansPointQry = new MeansPoint();
        meansPointQry.setStructureId(structureId);
        List<MeansPoint> meansPointList = meansPointDao.query(meansPointQry);
        map.put("total", meansPointList.size());
        map.put("offline", 0);
        if (!CollectionUtils.isEmpty(meansPointList)) {
            Integer count = (int)meansPointList.stream().filter(item -> item.getStatus().equals(0)).count();
            map.put("offline", count);
        }

        List<WarningRatio> warningRatioList = warningService.getWarningStatistics(1, structureId);

        Integer total = 0;
        if (!CollectionUtils.isEmpty(warningRatioList)) {
            total = warningRatioList.stream().mapToInt(WarningRatio::getCount).sum();
            Integer processed = warningRatioList.stream().filter(warning -> warning.getStatus().equals("已处理")).mapToInt(WarningRatio::getCount).sum();
            Integer untreated = warningRatioList.stream().filter(warning -> warning.getStatus().equals("未处理")).mapToInt(WarningRatio::getCount).sum();
            Double result = this.getResultScore(processed, untreated);
            map.put("processingRate", (int)Math.round(result));
        } else {
            map.put("processingRate", 0);
        }
        map.put("warningCount", total);
        return map;
    }

    @Override
    public List<EchartMap> getMainEvaHistory(Integer structureId) {
        return homeDao.getMainEvaHistory(ThreadLocalMap.getRoleId(), ThreadLocalMap.getUnitId(),
                structureId, power.getMaintainPower());
    }

    private Double getResultScore(Integer processed, Integer untreated) {
        if (processed + untreated != 0) {
            return BigDecimal.valueOf((float)processed / (processed + untreated) * 100).
                    setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else {
            return null;
        }
    }

    @Override
    public MeansPointVideoVO getSensorAndVideo(Integer structureId) {
        MeansPointVideoVO meansPointVideoVO = new MeansPointVideoVO();
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();
        meansPointVideoVO.setMeansPointVOList(sensorExpDao.getSensorByStructureId(structureId));
        meansPointVideoVO.setVideoList(videoExpDao.getVideoByStructure(roleId, unitId, structureId, power.getOnlinePower()));
        return meansPointVideoVO;
    }

    @Override
    public WarningRecord getNewestWarning(Integer meansPointId) {
        return warningRecordExpDao.getNewestWarning(ThreadLocalMap.getRoleId(), ThreadLocalMap.getUnitId(), meansPointId);
    }

    @Override
    public Map<String, String> getNewestSensorData(Integer meansPointId, Integer sensorId) {

        MeansPoint meansPoint = meansPointDao.findById(meansPointId);
        Sensor sensor = sensorDao.findById(sensorId);

        Integer type = commonService.getSensorType(meansPoint.getCompanyId(), meansPoint.getSensorTypeId());

        return homeDao.getSensorData(sensor.getSensorCoding(), type);
    }
}
