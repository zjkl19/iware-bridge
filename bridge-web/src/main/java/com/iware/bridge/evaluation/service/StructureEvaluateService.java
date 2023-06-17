package com.iware.bridge.evaluation.service;

import com.iware.bridge.evaluation.vo.BasicVO;
import com.iware.bridge.evaluation.vo.OverviewVO;
import com.iware.bridge.evaluation.vo.ScoreVO;
import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.model.entity.evaluation.Component;

import java.util.List;
import java.util.Map;

/**
 * @author WJP
 * @date 2021-8-16
 */

public interface StructureEvaluateService {

    /** 获取结构物最近一次评分 */
    public Map<String, String> getNewestScore(Integer structureId);

    /** 历年评分变化 */
    public List<ScoreVO> getScoreTrend(Integer structureId, Integer partType);

    /** 构件病害数排行 */
    public List<EchartMap> getDiseaseByComponent(Integer structureId, Integer componentId);

    /** 检测概况 */
    public List<OverviewVO> getEvaluationInfo(Integer structureId);

    /** 获取基本信息和照片 */
    public BasicVO getBasic(Integer structureId);

    /** 获取构件类型 */
    public List<Component> getComponent();
}
