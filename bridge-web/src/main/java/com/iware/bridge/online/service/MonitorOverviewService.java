package com.iware.bridge.online.service;

import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.online.dto.MonitoringSituationDto;

import java.util.List;
import java.util.Map;

/**
 * @author WJP
 * @date 2021-7-27
 */

public interface MonitorOverviewService {

    /** 查询监测桥隧数/传感器数/摄像头/当日预警数 */
    public Map<String, Integer> getCount();

    /** 传感器类型统计 */
    public List<EchartMap> getSensorTypeList(Integer structureId);

    /** 监测评分排行 */
    public List<MonitoringSituationDto> getScoreRank(Integer projectId);

}
