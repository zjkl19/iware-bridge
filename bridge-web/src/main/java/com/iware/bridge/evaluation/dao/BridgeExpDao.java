package com.iware.bridge.evaluation.dao;

import com.iware.bridge.evaluation.vo.*;
import com.iware.bridge.model.entity.evaluation.BridgeRoad;
import com.iware.bridge.model.entity.evaluation.Component;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WJP
 * @date 2021-8-12
 */
@Repository
public interface BridgeExpDao {


    /**
     * @Description 城市桥隧技术状况统计
     **/
    public List<TechnologyStatusVO> listCityTechnologyStatus(@Param("list") List<Integer> list);

    /**
     * @Description 公路桥隧技术状况统计
     **/
    public List<TechnologyStatusVO> listRoadTechnologyStatus(@Param("list") List<Integer> list);

    /** 根据计划结构物id获取线路和桥跨信息 **/
    public List<BridgeSurveyFirstTierVO> getFirstTierByMpSrelId(@Param("mpSrelId")Integer mpSrelId);

    /** 根据桥跨id查询桥面系信息 **/
    public BridgeSurveyThirdTierVO getDeckSystem(@Param("bridgeSpanId") Integer bridgeSpanId);

    /** 根据桥跨id查询上部结构信息 **/
    public BridgeSurveyThirdTierVO getSuperstructure(@Param("bridgeSpanId") Integer bridgeSpanId);

    /** 根据桥跨id查询下部结构信息 **/
    public BridgeSurveyThirdTierVO getSubstructure(@Param("bridgeSpanId") Integer bridgeSpanId);

    /** 根据桥跨id查询其他结构信息 **/
    public BridgeSurveyThirdTierVO getOther(@Param("bridgeSpanId") Integer bridgeSpanId);

    /** 列出桥隧评分排名的年份 */
    public List<String> listRankYear();

    /** 根据结构物id获取最新一次检测的线路信息 */
    public List<BridgeRoad> selectRoadLimit1(@Param("structureId") Integer structureId);

    /** 根据结构物id获取桥跨的最长长度 */
    public Double selectSpanMaxLength(@Param("structureId") Integer structureId);

    /** 获取线路表信息 */
    public List<RoadBridge> selectRoadList(@Param("structureId") Integer structureId);

    /** 根据线路id获取委托单位名称*/
    public String selectUserNameByRoadId(@Param("id") Integer id);

    /** 根据线路id获取桥面系完好状况评估结果表信息*/
    public List<WordEvaluateResults> selectBDEvaResByRoadId(@Param("id") Integer id);

    /**根据线路id获取上部结构完好状况评估结果表信息*/
    public List<WordEvaluateResults> selectSUPEvaResByRoadId(@Param("id") Integer id);

    /**根据线路id获取下部结构完好状况评估结果表信息*/
    public List<WordEvaluateResults> selectSUBEvaResByRoadId(@Param("id") Integer id);

    /**根据线路id获取桥类型的权重*/
    public List<Float> selectWeightByRoadId(@Param("id") Integer id);

    /** 修改线路里的检测报告路径*/
    public int updateReportPath(@Param("id") Integer id, @Param("path") String path);

    /** 根据线路id查询桥面系/上部结构/下部结构的构件实例id**/
    public List<Score> selectComponentIdByRoad(@Param("roadId") Integer roadId);

    /**修改桥面系实例表的扣分值 **/
    public Integer updateComponent(Score model);

    /**修改上部结构实例表的扣分值**/
    public Integer updateComponent2(Score model);

    /**修改下部结构表的扣分值**/
    public Integer updateComponent3(Score model);

    /** 根据桥面系信息表id查询线路信息 **/
    public BridgeRoad selectRoadByDkId(@Param("id") Integer id);

    /** 根据上部结构信息表id查询线路信息 **/
    public BridgeRoad selectRoadBySupId(@Param("id") Integer id);

    /** 根据下部结构信息表id查询线路信息 **/
    public BridgeRoad selectRoadBySubId(@Param("id") Integer id);

    /** 根据上部结构实例表id查询线路信息 **/
    public BridgeRoad selectRoadBySupComponentId(@Param("id") Integer id);

    /** 根据下部结构实例表id查询线路信息 **/
    public BridgeRoad selectRoadBySubComponentId(@Param("id") Integer id);

    /** 根据线路id查询计划下的所有结构物计划id **/
    public List<Integer> selectStructureRelIdByRoadId(@Param("roadId") Long roadId);

    /** 获取构件类型 **/
    public  List<Component> selectComponentByBridgeTypeId(@Param("bridgeTypeId") Integer bridgeTypeId);

}
