package com.iware.bridge.evaluation.dao;

import com.iware.bridge.evaluation.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WJP
 * @date 2021-8-16
 */
@Repository
public interface RecordDetailInfoDao {

    /**线路评分 **/
    public BCIEvaluationDetailDTO selBCIScore(BCIDetailVO bciDetailVO);

    /**BCI评价结果**/
    public BCIEvaluationResultDTO selBCIEvaluationResultDTO(@Param("roadId") Long roadId);

     /**桥面系**/
    public List<ArtifactsDTO> selArtifactsDTO(BCIDetailVO bciDetailVO);

     /**上部结构**/
    public List<AcrossDTO> selAcrossDTOByUpper(BCIDetailVO bciDetailVO);

     /**下部结构**/
    public List<AcrossDTO> selAcrossDTOByLower(BCIDetailVO bciDetailVO);

     /**构件病害集合**/
    public List<ArtifactsDTO> selArtifactsDTOList(@Param("list") List<ArtifactsDTO> list, @Param("type") Integer type);

     /**批量保存BCI**/
    public void saveAcrossDTOScore(List<BridgeScoreDTO> list);

     /**批量更新BCI**/
    public void updateAcrossDTOScore(List<BridgeScoreDTO> list);

     /**更新结构构件权重等内容**/
    public void updateUpperArtifactsDTO(@Param("list") List<ArtifactsDTO> list, @Param("tableName") String tableName);

     /**更新线路BCI**/
    public void updateRodeBCI(BridgeScoreDTO bridgeScoreDTO);

     /**保存线路BCI**/
    public void saveRodeBCI(BridgeScoreDTO bridgeScoreDTO);

    public MonitorStructureDTO selMonitorStructureDTOByRoadId(@Param("roadId") Long roadId);

     /**新增结构物评定等级**/
    public void saveMonitorStructure(BridgeScoreDTO bridgeScoreDTO);

    /**查询线路的BCI分数（）**/
    public RoadScoreDTO sleRoadScoreDTOList(@Param("roadId") Long roadId);

    /** 修改桥面系/上部结构/下部结构的BCI/BSI评分 **/
    public void updateDKSUBSUPBCI(BridgeScoreDTO bridgeScoreDTO);

    /** 查询当前桥梁的承接单位 **/
    public String selectUnit(@Param("roadId") Long roadId);
}
