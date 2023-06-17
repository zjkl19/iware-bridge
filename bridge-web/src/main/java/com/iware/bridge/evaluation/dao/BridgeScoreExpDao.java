package com.iware.bridge.evaluation.dao;

import com.iware.bridge.evaluation.vo.*;
import com.iware.bridge.model.entity.evaluation.BridgeScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WJP
 * @date 2021-8-13
 */
@Repository
public interface BridgeScoreExpDao {

    /** 桥隧评分排行列表 **/
    public List<BridgeLevelRankVO> listAssessRank(BridgeLevelRankFilter filter);

    /** 根据结构物id获取最新一次线路/桥面系/上部结构/下部结构的bci/bsi评分  **/
    public BridgeScore selectRoadBciBsiLimit1(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                       @Param("structureId") Integer structureId, @Param("typeId")Integer typeId);

    /** 根据结构物id获取历年评分变化 **/
    public List<ScoreVO> selectRoadBciBsiYear(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                       @Param("structureId") Integer structureId, @Param("typeId")Integer typeId);

    /** 获取检测概况列表 **/
    public List<OverviewVO> selectTestingSituation(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                            @Param("structureId") Integer structureId);

    /** 获取bci/bsi **/
    public List<ScoreComparison> selectBciBsi(MonitorAnalysisFilterVO monitorAnalysisFilterVO);

    /** 通过所有BCI的评价，找出最低评分 **/
    public String getMinRatingLevel(@Param("list")List<BCIEvaluation> list);

    /** 查询BCI评价列表 **/
    public List<BCIEvaluation> getBCIEvaluationList(AssessRecordFilter assessRecordFilter);

    /** 查询权限结构物所有评分的列表 **/
    public List<ScoreSearchVO> getAllScoreByStructures(@Param("list") List<Integer> list);

    /** 根据线路id获取bci评分等级*/
    public ScoreComparison selectScoreByRoadId(@Param("id") Integer id);

    /** 全桥BCI **/
    public List<ScoreVO> selectRelBci(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId, @Param("structureId") Integer structureId);

}
