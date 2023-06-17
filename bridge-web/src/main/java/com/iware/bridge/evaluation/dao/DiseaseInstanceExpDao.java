package com.iware.bridge.evaluation.dao;

import com.iware.bridge.evaluation.vo.*;
import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.model.entity.evaluation.DiseaseInstance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author WJP
 * @date 2021-8-13
 */

@Repository
public interface DiseaseInstanceExpDao {
    /** 列出一个桥型构件病害统计 */
    public List<HashMap<String, Object>> getDiseaseCount(@Param("filter") BridgeTypeDiseaseFilter filter, @Param("list") List<Integer> list);

    /** 根据构件查询病害 **/
    public List<EchartMap> selectComponentDiseaseCount(@Param("unitId") Integer unitId, @Param("roleId") Integer roleId,
                                                @Param("structureId") Integer structureId, @Param("componentId") Integer componentId);

    /** 获取结构病害数量 **/
    public List<DiseaseNumber> selectDiseaseNumber(MonitorAnalysisFilterVO monitorAnalysisFilterVO);

    /** 获取构件病害数量统计 **/
    public List<ComponentDiseaseNumber> selectComponentDisease(MonitorAnalysisFilterVO monitorAnalysisFilterVO);

    /** 获取构件病害类型统计 **/
    public List<ComponentDiseaseTypeNumber> selectComponentDiseaseType(MonitorAnalysisFilterVO monitorAnalysisFilterVO);

    /** 获取线路里的最大病害上传时间**/
    public String getDiseaseMaxTime(@Param("roadId") Long roleId);

    /** 获取病害数据 **/
    public List<DiseaseBridge> selectDiseaseList(DiseaseBridgeListFilterVO diseaseBridgeListFilterVO);

    /** 获取病害查看记录数据 */
    public List<RecordVO> selectDiseaseRecordList(RecordListFilter recordListFilter);

    /** 获取回收站数据 **/
    public List<RecyclingVO> selectRecyclingList(RecyclingListFilter recyclingListFilter);

    /** 查询病害的目标id和构件类型id **/
    public List<DiseaseInstance> selectTypeAndTargetId(RecordDetailListFilter recordDetailListFilter);

    /** 查询回收病害的目标id和构件类型id **/
    public List<DiseaseInstance> selectTypeAndTargetId2(RecordDetailListFilter recordDetailListFilter);

    /** 修改病害数据变成回收 **/
    public void updateStatusTo0(RecordDetailListFilter recordDetailListFilter);

    /** 修改病害数据变成正常 **/
    public void updateStatusTo1(RecordDetailListFilter recordDetailListFilter);

    /**获取查看数据详情数据 */
    public List<DiseaseBridge> selectRecordDetailList(RecordDetailListFilter recordDetailListFilter);

    /** 根据结构物计划ID查询桥面系病害信息 **/
    public List<DetectionRecord> selectDBByStructureId(@Param("structureId") Integer structureId);

    /** 根据结构物计划ID查询上部结构病害信息 **/
    public List<DetectionRecord> selectSUPByStructureId(@Param("structureId") Integer structureId);

    /** 根据结构物计划ID查询下部结构病害信息 **/
    public List<DetectionRecord> selectSUBByStructureId(@Param("structureId") Integer structureId);

    /** 根据线路id获取桥面系检测结果汇总表信息 */
    public List<WordCheckResults> selectBDDiseaseByRoadId(@Param("id") Integer id);

    /**根据线路id获取上部结构检测结果汇总表信息*/
    public List<WordCheckResults> selectSUPDiseaseByRoadId(@Param("id") Integer id);

    /**根据线路id获取下部结构检测结果汇总表信息*/
    public List<WordCheckResults> selectSUBDiseaseByRoadId(@Param("id") Integer id);

    /** 查询线路病害*/
    public List<DiseaseSummary> selectDiseaseSummary(@Param("id") Integer id);

    /** 根据线路id获取其下所有病害信息 **/
    public List<DiseaseInstance> selectDiseaseIdByRoadId(@Param("roadId") Integer roadId);

    /** 根据病害id获取该病害的类型 **/
    public Integer selectDegreeType(@Param("diseaseId") Integer diseaseId, @Param("partType") Integer partType);

    /**修改病害实例表 **/
    public Integer updateDiseaseInstance(List<DiseaseInstance> list);

    /**根据构件实例表id和构件类型id查询病害信息**/
    public List<DiseaseInstanceVO> selectDiseaseInstance(@Param("partType") Integer partType, @Param("targetId") Integer targetId, @Param("componentId") Integer componentId);
}
