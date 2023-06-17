package com.iware.bridge.home.service;

import com.iware.bridge.home.vo.HomeBase;
import com.iware.bridge.home.vo.MeansPointVideoVO;
import com.iware.bridge.home.vo.StructureType;
import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.info.vo.StructureFilter;
import com.iware.bridge.model.entity.online.WarningRecord;

import java.util.List;
import java.util.Map;

/**
 * @author ZRB
 * @date 2021-8-4
 */

public interface HomeService {

    /** 首页获取结构物列表 */
    public StructureType listStructure(StructureFilter filter);

    /** 获取基础情况 */
    public HomeBase getBaseInfo();

    /** 获取巡查维养情况 */
    public Map<String, Integer> getInspMainInfo(Integer type);

    /** 获取综合评价指数 */
    public List<Double> getComprehensive(Integer structureId);

    /** 详情页获取检测概况 */
    public Map<String, String> getEvaluationInfo(Integer structureId);

    /** 详情页获取巡查维养概况 */
    public Map<String, String> getLastInspMainInfo(Integer structureId);

    /** 详情页获取巡查信息 */
    public Map<String, Integer> getSensorInfo(Integer structureId);

    /** 详情页获取历史检测维养 */
    public List<EchartMap> getMainEvaHistory(Integer structureId);

    /** 获取结构物传感器 */
    public MeansPointVideoVO getSensorAndVideo(Integer structureId);

    /** 获取传感器最新预警信息 */
    public WarningRecord getNewestWarning(Integer meansPointId);

    /** 获取传感器最新数据 */
    public Map<String, String> getNewestSensorData(Integer meansPointId, Integer sensorId);
}
