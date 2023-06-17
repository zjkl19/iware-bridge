package com.iware.bridge.app.assess.dao;


import com.iware.bridge.app.assess.vo.offline.offlineData.BridgeRoadVo;
import com.iware.bridge.app.assess.vo.offline.offlineData.StructureInfoVo;
import com.iware.bridge.app.assess.vo.offline.offlineData.StructureVo;
import com.iware.bridge.model.entity.evaluation.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppOfflineDao {

    public List<StructureVo> selectStructureByProjectId(int projectId);

    public List<StructureVo> selectStructureByPlanId(int planId);

    public List<StructureInfoVo> selectStructureInfoByProjectId(int projectId);

    public StructureInfoVo selectStructureInfoById(int id);

    public void insertOrUpdatePlanStructureRel(MonitorPlanStructureRel rels);

    public void insertOrUpdateBridgeRoad(BridgeRoad road);

    public void insertOrUpdateBridgeSpan(BridgeSpan span);

    public void insertOrUpdateBridgeDeckSystem(BridgeDeckSystem deckSystem);

    public void insertOrUpdateBridgeSupstructure(BridgeSupstructure supstructure);

    public void insertOrUpdateBridgeSubstructure(BridgeSubstructure substructure);

    public void insertOrUpdateBridgeDeckComponent(BridgeDeckComponent deckComponent);

    public void insertOrUpdateBridgeSupcomponent(BridgeSupcomponent supcomponent);

    public void insertOrUpdateBridgeSubcomponent(BridgeSubcomponent subcomponent);

    public void insertOrUpdateBridgeOtherStructure(BridgeOtherStructure otherStructure);

    public void insertOrUpdatePierAbutment(PierAbutment pierAbutment);

    public void insertOrUpdateStructureInfo(StructureInfoVo structureInfoVo);

    public void insertOrUpdateDiseaseInstance(DiseaseInstance diseaseInstance);

    public void recordDiseaseInstance(DiseaseInstanceRecord diseaseInstanceRecord);

    public void deleteStructureInfo(Integer structureInfoVoId);

    public void insertOrUpdateAttachment(Attachment attachment);

    //根据桥梁结构id，查询有线路对应的关联表id
    public List<Integer> selectRelIdByStructureIdWhereInRoadTable(Integer structureId);

    public Integer insertNewMonitorPlanStructureRel(MonitorPlanStructureRel rel);

    public Integer insertNewBridgeRoad(BridgeRoad road);

    public Integer insertNewBridgeSpan(BridgeSpan span);

    public Integer insertNewBridgeDeckSystem(BridgeDeckSystem deckSystem);

    public Integer insertNewBridgeDeckComponent(BridgeDeckComponent component);

    public Integer insertNewDiseaseInstance(DiseaseInstance diseaseInstance);

    public Integer insertNewBridgeSupstructure(BridgeSupstructure supstructure);

    public Integer insertNewBridgeSupcomponent(BridgeSupcomponent supcomponent);

    public Integer insertNewBridgeSubstructure(BridgeSubstructure substructure);

    public Integer insertNewPierAbutment(PierAbutment pierAbutment);

    public Integer insertNewBridgeSubcomponent(BridgeSubcomponent subcomponent);

    public Integer insertNewBridgeOtherStructure(BridgeOtherStructure otherStructure);

    public Integer insertNewAttachment(Attachment attachment);

    public Integer insertNewStructureInfo(StructureInfoVo structureInfoVo);

    public Integer getProjectUnitIdByPlanId(Integer planId);

    public List<BridgeRoadVo> findBridgeRoadByPlanId(Integer planId);

    public void setDiseaseStatus(Integer id);

    public void setOtherAttachmentByTargetId(@Param("targetId") Integer targetId,@Param("type") Integer type);

}
