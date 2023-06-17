package com.iware.bridge.app.assess.service;


import com.iware.bridge.app.assess.vo.offline.offlineData.*;
import com.iware.bridge.model.entity.evaluation.Attachment;

import java.util.HashMap;
import java.util.List;

public interface AppZipService {

    public String getPlanZip(int planId,String fileZipPath,boolean isRenew);

    public List<MonitorPlanStructureRelVo> findMonitorPlanStructureRel(Integer planId);

    public List<BridgeRoadVo> findBridgeRoad(Integer planStructureRelId, Integer structureInfoId);

    public List<BridgeSpanVo> findBridgeSpan(Integer roadId, Integer roadCode, boolean isIdReal);

    public List<BridgeDeckSystemVo> findBridgeDeckSystem(Integer spanId, Integer spanCode, boolean isIdReal);

    public List<BridgeDeckComponentVo> findBridgeDeckComponent(Integer deckId, Integer deckCode, boolean isIdReal);

    public List<BridgeSubstructureVo> findBridgeSubstructure(Integer spanId,Integer spanCode,boolean isIdReal);

    public List<BridgeSubcomponentVo> findBridgeSubcomponent(Integer subId, Integer subCode,
                                                             HashMap<Integer, Integer> oldToNew, boolean isIdReal);

    public List<BridgeSupstructureVo> findBridgeSupstructure(Integer spanId,Integer spanCode,boolean isIdReal);

    public List<BridgeSupcomponentVo> findBridgeSupcomponent(Integer supId,Integer supCode,boolean isIdReal);

    public List<BridgeOtherStructureVo> findBridgeOtherStructure(Integer spanId,Integer spanCode,boolean isIdReal);

    public List<DiseaseInstanceVo> findDiseaseInstance(Integer componentId,Byte componentType,boolean isIdReal);

    public List<Attachment> findAttachmentByTypeAndTargetId(int type, int partType, int target);

}
