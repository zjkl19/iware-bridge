package com.iware.bridge.online.service;

import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.online.dto.WarningTimeDto;

import java.util.List;
import java.util.Map;

/**
 * @author WJP
 * @date 2021-7-27
 */

public interface StructureMonitorService {

    /** 查询结构物在线、离线传感器数/今日预警数/今日待处理 */
    public Map<String, Integer> getCount(Integer structureId);

    /** 测点预警排行 */
    public List<EchartMap> getWarningRank(Integer structureId, Integer type);

    /** 获取预警趋势 */
    public List<WarningTimeDto> getWarningTend(Integer type, Integer structure);

}
