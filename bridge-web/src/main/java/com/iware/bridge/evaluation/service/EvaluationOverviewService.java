package com.iware.bridge.evaluation.service;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.evaluation.vo.BridgeLevelRankFilter;
import com.iware.bridge.evaluation.vo.BridgeLevelRankVO;
import com.iware.bridge.evaluation.vo.BridgeTypeDiseaseFilter;
import com.iware.bridge.evaluation.vo.TechnologyStatusVO;
import com.iware.bridge.model.entity.evaluation.BridgeType;
import com.iware.bridge.model.entity.evaluation.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WJP
 * @date 2021-8-16
 */

public interface EvaluationOverviewService {

    /** 获取角色下的结构物id集合 **/
    List<Integer> getStructureIdList();

    /** 查询检测项目数/检测桥隧数 */
    public Map<String, Integer> getCount();

    /** 查询桥隧技术状况 */
    public Map<String,List<TechnologyStatusVO>> listTechnologyStatus();

    /** 构件类型病害统计 */
    public List<HashMap<String, Object>> getDiseaseCount(BridgeTypeDiseaseFilter filter);

    /** 桥隧评分排行 */
    public PageInfo<BridgeLevelRankVO> listBridgeRank(BridgeLevelRankFilter filter);

    /** 年份 **/
    public List<String> listRankYear();

    /** 桥梁类型 **/
    public List<BridgeType> listBridgeType();

    /** 桥梁类型 **/
    public List<Component> getComponent(Integer id);
}
