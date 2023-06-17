package com.iware.bridge.app.assess.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iware.bridge.app.assess.dao.AppOfflineDao;
import com.iware.bridge.app.assess.dao.AppOfflineExpDao;
import com.iware.bridge.app.assess.vo.offline.offlineData.*;
import com.iware.bridge.info.dao.StructureExpDao;
import com.iware.bridge.model.dao.evaluation.*;
import com.iware.bridge.model.dao.global.BridgeInfoDao;
import com.iware.bridge.model.dao.global.ProjectStructureRelDao;
import com.iware.bridge.model.entity.evaluation.*;
import com.iware.bridge.model.entity.global.BridgeInfo;
import com.iware.bridge.model.entity.global.ProjectStructureRel;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.exception.BusinessException;
import com.iware.common.utils.UUIDUtils;
import com.iware.common.utils.UnZipAnRar;
import com.iware.common.utils.ZipFileUnCompressingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AppUnzipServiceImpl implements AppUnzipService{

    @Value("${file.upload-path}")
    private String path;

    @Autowired
    private AppOfflineDao appOfflineDao;

    @Autowired
    private MonitorPlanStructureRelDao monitorPlanStructureRelDao;

    @Autowired
    private DiseaseInstanceDao diseaseInstanceDao;

    @Autowired
    private PierAbutmentDao pierAbutmentDao;

    @Autowired
    private AppZipService appZipService;

    @Autowired
    private AppCompWeightCalcService appCompWeightCalcService;

    @Autowired
    private MonitorPlanOriginalRecordDao monitorPlanOriginalRecordDao;

    @Autowired
    private BridgeInfoDao bridgeDao;

    @Resource
    private MonitorPlanDao monitorPlanDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private StructureExpDao structureExpDao;

    @Resource
    private AppOfflineExpDao appOfflineExpDao;

    @Resource
    private ProjectStructureRelDao projectStructureRelDao;

    public static Logger logger=LoggerFactory.getLogger(AppUnzipServiceImpl.class);

    /**
     * 输入压缩包文件地址，解压，解析json，修改数据库，读取并设置构件初始权重，读取并设置病害扣分，并返回提示已生成记录的桥梁，不做修改
     * @author linbinxiang
     * @param filePath 上传文件地址
     * @param fileName 上传压缩包文件名
     * @param lastFilePath 上一次下载文件地址
     * @param imageFilePath 图片保存地址
     */
    @Override
    @Transactional
    public List<MonitorPlanStructureRel> correctDatabase(String filePath, String fileName, String lastFilePath, String imageFilePath) {
        if(monitorPlanDao.findById(Integer.parseInt(fileName))==null){
            releaseLock("unzip:"+fileName);
            finishDeleteFile(filePath,filePath+"/"+fileName+".zip");
            throw new BusinessException("该计划已被删除，请重新下载项目数据");
        }
        //处理后需要添加的HashMap,以上级节点的id为key
        Date modifyTime=new Date();
        String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(modifyTime);
        try {
            modifyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        }catch (Exception e){
            StringBuilder builder=new StringBuilder();
            for(StackTraceElement element:e.getStackTrace()){
                builder.append(element).append("\n");
            }
            logger.error(builder.toString());
            finishDeleteFile(filePath,filePath+"/"+fileName+".zip");
        }
        HashMap<Integer,ArrayList<BridgeRoadVo>> resultRoadList=new HashMap<>();
        HashMap<Integer,ArrayList<BridgeSpanVo>> resultSpanList=new HashMap<>();
        HashMap<Integer,ArrayList<BridgeDeckSystemVo>> resultDeckSystemList=new HashMap<>();
        HashMap<Integer,ArrayList<BridgeDeckComponentVo>> resultDeckComponentList=new HashMap<>();
        HashMap<Integer,ArrayList<BridgeSubstructureVo>> resultSubstructureList=new HashMap<>();
        HashMap<Integer,ArrayList<BridgeSubcomponentVo>> resultSubcomponentList=new HashMap<>();
        HashMap<Integer,ArrayList<BridgeSupstructureVo>> resultSupstructureList=new HashMap<>();
        HashMap<Integer,ArrayList<BridgeSupcomponentVo>> resultSupcomponentList=new HashMap<>();
        HashMap<Integer,ArrayList<BridgeOtherStructureVo>> resultOtherStructureList=new HashMap<>();
        HashMap<Integer,ArrayList<PierAbutmentVo>> resultPierAbutmentList=new HashMap<>();

        //上传时文件
        List<MonitorPlanStructureRelVo> monitorPlanStructureRelList;
        List<BridgeRoadVo> bridgeRoadList=new ArrayList<>();
        List<BridgeSpanVo> bridgeSpanList=new ArrayList<>();
        List<BridgeDeckSystemVo> bridgeDeckSystemList=new ArrayList<>();
        List<BridgeDeckComponentVo> bridgeDeckComponentList=new ArrayList<>();
        List<BridgeSubstructureVo> bridgeSubstructureList=new ArrayList<>();
        List<BridgeSubcomponentVo> bridgeSubcomponentList=new ArrayList<>();
        List<BridgeSupstructureVo> bridgeSupstructureList=new ArrayList<>();
        List<BridgeSupcomponentVo> bridgeSupcomponentList=new ArrayList<>();
        List<BridgeOtherStructureVo> bridgeOtherStructureList=new ArrayList<>();
        List<DiseaseInstanceVo> diseaseInstanceList=new ArrayList<>();
        HashMap<Integer,List<DiseaseInstanceVo>> newDeckDiseaseInstanceList=new HashMap<>();
        HashMap<Integer,List<DiseaseInstanceVo>> newSupDiseaseInstanceList=new HashMap<>();
        HashMap<Integer,List<DiseaseInstanceVo>> newSubDiseaseInstanceList=new HashMap<>();
        HashMap<Integer,List<DiseaseInstanceVo>> newOtherDiseaseInstanceList=new HashMap<>();
//        List<StructureInfoVo> structureOfProject;
        List<StructureInfoVo> structureOfPlan=new ArrayList<>();
        List<AttachmentVo> attachments=new ArrayList<>();
        List<PierAbutmentVo> pierAbutmentList=new ArrayList<>();

        //下载时旧的文件
        List<MonitorPlanStructureRelVo> oldMonitorPlanStructureRelList;
        List<BridgeRoadVo> oldBridgeRoadList=new ArrayList<>();
        List<BridgeSpanVo> oldBridgeSpanList=new ArrayList<>();
        List<BridgeDeckSystemVo> oldBridgeDeckSystemList=new ArrayList<>();
        List<BridgeDeckComponentVo> oldBridgeDeckComponentList=new ArrayList<>();
        List<BridgeSubstructureVo> oldBridgeSubstructureList=new ArrayList<>();
        List<BridgeSubcomponentVo> oldBridgeSubcomponentList=new ArrayList<>();
        List<BridgeSupstructureVo> oldBridgeSupstructureList=new ArrayList<>();
        List<BridgeSupcomponentVo> oldBridgeSupcomponentList=new ArrayList<>();
        List<BridgeOtherStructureVo> oldBridgeOtherStructureList=new ArrayList<>();
        List<DiseaseInstanceVo> oldDiseaseInstanceList=new ArrayList<>();
        List<StructureInfoVo> oldStructureOfPlan=new ArrayList<>();
        List<AttachmentVo> oldAttachments=new ArrayList<>();
        List<PierAbutmentVo> oldPierAbutmentList=new ArrayList<>();
        HashMap<Integer,List<DiseaseInstanceVo>> deckDiseaseInstanceList=new HashMap<>();
        HashMap<Integer,List<DiseaseInstanceVo>> supDiseaseInstanceList=new HashMap<>();
        HashMap<Integer,List<DiseaseInstanceVo>> subDiseaseInstanceList=new HashMap<>();
        HashMap<Integer,List<DiseaseInstanceVo>> otherDiseaseInstanceList=new HashMap<>();

        List<MonitorPlanStructureRel> unableRenewRel=new ArrayList<>();

        String outFilePath=filePath+"/"+fileName+"/";
        UnzipFile(filePath+"/"+fileName+".zip",filePath);
        //获取MonitorPlanStructureRel列表
        logger.debug(new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"uploadPath1:"+outFilePath+"monitorPlanStructureRel.json");
        if(!new File(outFilePath+"monitorPlanStructureRel.json").exists()){
            logger.debug(new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"uploadErrorPath1:"+outFilePath+"monitorPlanStructureRel.json");
            //删除文件
            finishDeleteFile(filePath,filePath+"/"+fileName+".zip");
            releaseLock("unzip:"+fileName);
            throw new BusinessException("上传文件错误,解压失败");
        }
        monitorPlanStructureRelList=readToList(outFilePath+"monitorPlanStructureRel.json",
                MonitorPlanStructureRelVo.class);
        if(monitorPlanStructureRelList.isEmpty()){
            logger.debug(new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"uploadErrorPath2:"+outFilePath+"monitorPlanStructureRel.json");
            //删除文件
            finishDeleteFile(filePath,filePath+"/"+fileName+".zip");
            releaseLock("unzip:"+fileName);
            throw new BusinessException("上传文件错误,解压失败");
        }
        if(isListNotEmpty(monitorPlanStructureRelList))
        monitorPlanStructureRelList.removeIf(m -> m==null||m.getId() == 0);
        for(int i=0;i<monitorPlanStructureRelList.size();i++){
            MonitorPlanStructureRelVo rel=monitorPlanStructureRelList.get(i);
            if (!rel.isIdReal()){
                continue;
            }
            MonitorPlanOriginalRecord monitorPlanOriginalRecord=new MonitorPlanOriginalRecord();
            monitorPlanOriginalRecord.setMonitorPlanStructureRelId(rel.getId());
            List<MonitorPlanOriginalRecord> result=new ArrayList<>();
            try {
                result.addAll(monitorPlanOriginalRecordDao.query(monitorPlanOriginalRecord));
            }catch (Exception e){
                StringBuilder builder=new StringBuilder();
                for(StackTraceElement element:e.getStackTrace()){
                    builder.append(element).append("\n");
                }
                logger.error(builder.toString());
            }
            if(result.size()!=0){//查询有结果说明原始数据中含有该rel
                unableRenewRel.add(rel);
                monitorPlanStructureRelList.remove(i);
                i--;
            }
        }
        //项目下所有结构物
//        structureOfProject=readToList(outFilePath+"structuresOfProject.json",
//                StructureInfoVo.class);
//        if (isListNotEmpty(structureOfProject))
//        structureOfProject.removeIf(m -> m==null||m.getId() == 0);
        //每个桥梁结构对应一个文件夹
        for(MonitorPlanStructureRel rel:monitorPlanStructureRelList){
            String bridgeFilePath=outFilePath+rel.getStructureName()+"_"+rel.getStructureInfoId()+"/";
            /*----------------------------------------------------------------------*/
            List<BridgeRoadVo> roadToAddRelId=readToList(bridgeFilePath+"bridgeRoad.json",BridgeRoadVo.class);
            for(BridgeRoadVo i:roadToAddRelId){
                i.setMonitorPlanStructureRelId(rel.getId());
            }
            bridgeRoadList.addAll(roadToAddRelId);
            /*----------------------------------------------------------------------*/
//            bridgeRoadList.addAll(readToList(bridgeFilePath+"bridgeRoad.json",BridgeRoadVo.class));
            bridgeSpanList.addAll(readToList(bridgeFilePath+"bridgeSpan.json",BridgeSpanVo.class));
            bridgeDeckSystemList.addAll(readToList(bridgeFilePath+"bridgeDeckSystem.json",BridgeDeckSystemVo.class));
            bridgeDeckComponentList.addAll(readToList(bridgeFilePath+"bridgeDeckComponent.json",BridgeDeckComponentVo.class));
            bridgeSupstructureList.addAll(readToList(bridgeFilePath+"bridgeSupstructure.json",BridgeSupstructureVo.class));
            bridgeSupcomponentList.addAll(readToList(bridgeFilePath+"bridgeSupcomponent.json",BridgeSupcomponentVo.class));
            bridgeSubstructureList.addAll(readToList(bridgeFilePath+"bridgeSubstructure.json",BridgeSubstructureVo.class));
            bridgeSubcomponentList.addAll(readToList(bridgeFilePath+"bridgeSubcomponent.json",BridgeSubcomponentVo.class));
            bridgeOtherStructureList.addAll(readToList(bridgeFilePath+"bridgeOtherStructure.json",BridgeOtherStructureVo.class));
            diseaseInstanceList.addAll(readToList(bridgeFilePath+"diseaseInstance.json",DiseaseInstanceVo.class));
            structureOfPlan.add(readToObject(bridgeFilePath+"structureInfo.json",StructureInfoVo.class));
            pierAbutmentList.addAll(readToList(bridgeFilePath+"pierAbutment.json",PierAbutmentVo.class));
        }

        if (isListNotEmpty(bridgeRoadList))
        bridgeRoadList.removeIf(m -> m==null||m.getId() == 0);
        if (isListNotEmpty(bridgeSpanList))
        bridgeSpanList.removeIf(m -> m==null||m.getId() == 0);
        if (isListNotEmpty(bridgeDeckSystemList))
        bridgeDeckSystemList.removeIf(m -> m==null||m.getId() == 0);
        if (isListNotEmpty(bridgeDeckComponentList))
        bridgeDeckComponentList.removeIf(m -> m==null||m.getId() == 0);
        if (isListNotEmpty(bridgeSupstructureList))
        bridgeSupstructureList.removeIf(m -> m==null||m.getId() == 0);
        if (isListNotEmpty(bridgeSupcomponentList))
        bridgeSupcomponentList.removeIf(m -> m==null||m.getId() == 0);
        if (isListNotEmpty(bridgeSubstructureList))
        bridgeSubstructureList.removeIf(m -> m==null||m.getId() == 0);
        if (isListNotEmpty(bridgeSubcomponentList))
        bridgeSubcomponentList.removeIf(m -> m==null||m.getId() == 0);
        if (isListNotEmpty(bridgeOtherStructureList))
        bridgeOtherStructureList.removeIf(m -> m==null||m.getId() == 0);
        if (isListNotEmpty(diseaseInstanceList))
        diseaseInstanceList.removeIf(m -> m==null||m.getId() == 0);
        if (isListNotEmpty(structureOfPlan)) {
            structureOfPlan.removeIf(m -> m == null || m.getId() == 0);
        }
        if (isListNotEmpty(pierAbutmentList))
        pierAbutmentList.removeIf(m -> m==null||m.getId() == 0);

        Set<Integer> objectIdSet=new HashSet<>();

        /**
         * 判断新增桥梁是否重名
         */

        String conflictName="";
        for(StructureInfoVo infoVo:structureOfPlan){
            if(infoVo.isIdReal()){
                continue;
            }else{
                String structureName=structureExpDao.getStructureName(infoVo.getStructureName(), null);
                if(structureName==null||"".equals(structureName)){
                    //无同名
                }else{
                    if(!("".equals(conflictName))){
                        conflictName+=",";
                    }
                    conflictName+=structureName;
                }
            }
        }
        if(!("".equals(conflictName))) {
            finishDeleteFile(filePath,filePath+"/"+fileName+".zip");
            releaseLock("unzip:"+fileName);
            throw new BusinessException("已有同名桥梁:" + conflictName);
        }

        /**
         * 根据id与下载时文件对比，减少则进行删除操作，通过将status标记为0或在dao层删除行
         */
        //关联表
        oldMonitorPlanStructureRelList=readToList(lastFilePath+"monitorPlanStructureRel.json",
                MonitorPlanStructureRelVo.class);
        if(isListNotEmpty(oldMonitorPlanStructureRelList))
        oldMonitorPlanStructureRelList.removeIf(m -> m==null||m.getId() == 0);
        for(int i=0;i<oldMonitorPlanStructureRelList.size();i++){
            for(MonitorPlanStructureRel rel:unableRenewRel){
                if(oldMonitorPlanStructureRelList.get(i).getId()!=null&&oldMonitorPlanStructureRelList.get(i).getId().equals(rel.getId())){
                    oldMonitorPlanStructureRelList.remove(i);
                    i--;
                    break;
                }
            }
        }
        for(MonitorPlanStructureRelVo newMonitorPlanStructureRel:monitorPlanStructureRelList){
            if(newMonitorPlanStructureRel.isIdReal()) {
                objectIdSet.add(newMonitorPlanStructureRel.getId());
            }
        }
        for(MonitorPlanStructureRel monitorPlanStructureRel:oldMonitorPlanStructureRelList){
            Integer objectId=monitorPlanStructureRel.getId();
            if(!objectIdSet.contains(objectId)){//如果旧的列表元素不包含在新的里面(被删除)
                monitorPlanStructureRelDao.deleteById(objectId);
            }
        }
        objectIdSet.clear();
        for(MonitorPlanStructureRel m:oldMonitorPlanStructureRelList){
            String bridgeFilePath=lastFilePath+m.getStructureName()+"_"+m.getStructureInfoId()+"/";
            oldBridgeRoadList.addAll(readToList(bridgeFilePath+"bridgeRoad.json",BridgeRoadVo.class));
            oldBridgeSpanList.addAll(readToList(bridgeFilePath+"bridgeSpan.json",BridgeSpanVo.class));
            oldBridgeDeckSystemList.addAll(readToList(bridgeFilePath+"bridgeDeckSystem.json",BridgeDeckSystemVo.class));
            oldBridgeDeckComponentList.addAll(readToList(bridgeFilePath+"bridgeDeckComponent.json",BridgeDeckComponentVo.class));
            oldBridgeSupstructureList.addAll(readToList(bridgeFilePath+"bridgeSupstructure.json",BridgeSupstructureVo.class));
            oldBridgeSupcomponentList.addAll(readToList(bridgeFilePath+"bridgeSupcomponent.json",BridgeSupcomponentVo.class));
            oldBridgeSubstructureList.addAll(readToList(bridgeFilePath+"bridgeSubstructure.json",BridgeSubstructureVo.class));
            oldBridgeSubcomponentList.addAll(readToList(bridgeFilePath+"bridgeSubcomponent.json",BridgeSubcomponentVo.class));
            oldBridgeOtherStructureList.addAll(readToList(bridgeFilePath+"bridgeOtherStructure.json",BridgeOtherStructureVo.class));
            oldDiseaseInstanceList.addAll(readToList(bridgeFilePath+"diseaseInstance.json",DiseaseInstanceVo.class));
            oldStructureOfPlan.add(readToObject(bridgeFilePath+"structureInfo.json",StructureInfoVo.class));
            oldPierAbutmentList.addAll(readToList(bridgeFilePath+"pierAbutment.json",PierAbutmentVo.class));
        }
        //去除空文件(id为0)
        if(isListNotEmpty(oldBridgeRoadList))
        oldBridgeRoadList.removeIf(m -> m==null||m.getId() == 0);
        if(isListNotEmpty(oldBridgeSpanList))
        oldBridgeSpanList.removeIf(m -> m==null||m.getId() == 0);
        if(isListNotEmpty(oldBridgeDeckSystemList))
        oldBridgeDeckSystemList.removeIf(m -> m==null||m.getId() == 0);
        if(isListNotEmpty(oldBridgeDeckComponentList))
        oldBridgeDeckComponentList.removeIf(m -> m==null||m.getId() == 0);
        if(isListNotEmpty(oldBridgeSubstructureList))
        oldBridgeSubstructureList.removeIf(m -> m==null||m.getId() == 0);
        if(isListNotEmpty(oldBridgeSubcomponentList))
        oldBridgeSubcomponentList.removeIf(m -> m==null||m.getId() == 0);
        if(isListNotEmpty(oldBridgeSupstructureList))
        oldBridgeSupstructureList.removeIf(m -> m==null||m.getId() == 0);
        if(isListNotEmpty(oldBridgeSupcomponentList))
        oldBridgeSupcomponentList.removeIf(m -> m==null||m.getId() == 0);
        if(isListNotEmpty(oldBridgeOtherStructureList))
        oldBridgeOtherStructureList.removeIf(m -> m==null||m.getId() == 0);
        if(isListNotEmpty(oldDiseaseInstanceList))
        oldDiseaseInstanceList.removeIf(m -> m==null||m.getId() == 0);
        if(isListNotEmpty(oldStructureOfPlan))
        oldStructureOfPlan.removeIf(m -> m==null||m.getId() == 0);
        if(isListNotEmpty(oldPierAbutmentList))
        oldPierAbutmentList.removeIf(m -> m==null||m.getId() == 0);
        //附件，附件需要通过线路，桥跨，部分，构件，病害中获取
        for(BridgeRoadVo bridgeRoadVo:bridgeRoadList){
            attachments.addAll(bridgeRoadVo.getAttachments());
        }
        for(BridgeSpanVo bridgeSpanVo:bridgeSpanList){
            attachments.addAll(bridgeSpanVo.getAttachments());
        }
        for(BridgeDeckSystemVo deckSystemVo:bridgeDeckSystemList){
            attachments.addAll(deckSystemVo.getAttachments());
        }
        for(BridgeDeckComponentVo deckComponentVo:bridgeDeckComponentList){
            attachments.addAll(deckComponentVo.getAttachments());
        }
        for(BridgeSupstructureVo supstructureVo:bridgeSupstructureList){
            attachments.addAll(supstructureVo.getAttachments());
        }
        for(BridgeSupcomponentVo supcomponentVo:bridgeSupcomponentList){
            attachments.addAll(supcomponentVo.getAttachments());
        }
        for(BridgeSubstructureVo substructureVo:bridgeSubstructureList){
            attachments.addAll(substructureVo.getAttachments());
        }
        for(BridgeSubcomponentVo subcomponentVo:bridgeSubcomponentList){
            attachments.addAll(subcomponentVo.getAttachments());
        }
        for(DiseaseInstanceVo diseaseInstanceVo:diseaseInstanceList){
            attachments.addAll(diseaseInstanceVo.getAttachments());
        }
        //去除附件空文件(id为0)
        if(isListNotEmpty(attachments))
        attachments.removeIf(m -> m==null||m.getId() == 0||m.getStatus()==0);

        //先从新数据中将id加入set，遍历旧数据，不存在于set的数据即为需要删除数据，需要将数据status设为0然后修改
        //线路
        for(BridgeRoadVo newBridgeRoadVo:bridgeRoadList){
            if(newBridgeRoadVo.isIdReal()) {
                objectIdSet.add(newBridgeRoadVo.getId());
            }
        }
        for(BridgeRoadVo bridgeRoadVo:oldBridgeRoadList){
            if(bridgeRoadVo.isIdReal())
            if(!objectIdSet.contains(bridgeRoadVo.getId())){
                bridgeRoadVo.setStatus(0);
                appOfflineDao.insertOrUpdateBridgeRoad(bridgeRoadVo);
//                bridgeRoadList.add(bridgeRoadVo);
            }
        }
        objectIdSet.clear();
        //桥跨
        for(BridgeSpanVo newBridgeSpanVo:bridgeSpanList){
            if(newBridgeSpanVo.isIdReal()) {
                objectIdSet.add(newBridgeSpanVo.getId());
            }
        }
        for(BridgeSpanVo bridgeSpanVo:oldBridgeSpanList){
            if(bridgeSpanVo.isIdReal())
            if(!objectIdSet.contains(bridgeSpanVo.getId())){
                bridgeSpanVo.setStatus(0);
//                bridgeSpanList.add(bridgeSpanVo);
                appOfflineDao.insertOrUpdateBridgeSpan(bridgeSpanVo);
            }
        }
        objectIdSet.clear();
        //桥面系
        for(BridgeDeckSystemVo newBridgeDeckSystemVo:bridgeDeckSystemList){
            if(newBridgeDeckSystemVo.isIdReal()) {
                objectIdSet.add(newBridgeDeckSystemVo.getId());
            }
        }
        for(BridgeDeckSystemVo bridgeDeckSystemVo:oldBridgeDeckSystemList){
            if(bridgeDeckSystemVo.isIdReal())
            if(!objectIdSet.contains(bridgeDeckSystemVo.getId())){
                bridgeDeckSystemVo.setStatus(0);
//                bridgeDeckSystemList.add(bridgeDeckSystemVo);
                appOfflineDao.insertOrUpdateBridgeDeckSystem(bridgeDeckSystemVo);
            }
        }
        objectIdSet.clear();
        //桥面实例
        for(BridgeDeckComponentVo newBridgeDeckComponentVo:bridgeDeckComponentList){
            if(newBridgeDeckComponentVo.isIdReal()) {
                objectIdSet.add(newBridgeDeckComponentVo.getId());
            }
        }
        for(BridgeDeckComponentVo bridgeDeckComponentVo:oldBridgeDeckComponentList){
            if(bridgeDeckComponentVo.isIdReal())
            if(!objectIdSet.contains(bridgeDeckComponentVo.getId())){
                bridgeDeckComponentVo.setStatus(0);
//                bridgeDeckComponentList.add(bridgeDeckComponentVo);
                appOfflineDao.insertOrUpdateBridgeDeckComponent(bridgeDeckComponentVo);
            }
        }
        objectIdSet.clear();
        //上部
        for(BridgeSupstructureVo newSupstructureVo:bridgeSupstructureList){
            if(newSupstructureVo.isIdReal()) {
                objectIdSet.add(newSupstructureVo.getId());
            }
        }
        for(BridgeSupstructureVo supstructureVo:oldBridgeSupstructureList){
            if(supstructureVo.isIdReal())
            if(!objectIdSet.contains(supstructureVo.getId())){
                supstructureVo.setStatus(0);
//                bridgeSupstructureList.add(supstructureVo);
                appOfflineDao.insertOrUpdateBridgeSupstructure(supstructureVo);
            }
        }
        objectIdSet.clear();
        //上部实例
        for(BridgeSupcomponentVo newSupcomponentVo:bridgeSupcomponentList){
            if(newSupcomponentVo.isIdReal()) {
                objectIdSet.add(newSupcomponentVo.getId());
            }
        }
        for(BridgeSupcomponentVo supcomponentVo:oldBridgeSupcomponentList){
            if(supcomponentVo.isIdReal())
            if(!objectIdSet.contains(supcomponentVo.getId())){
                supcomponentVo.setStatus(0);
//                bridgeSupcomponentList.add(supcomponentVo);
                appOfflineDao.insertOrUpdateBridgeSupcomponent(supcomponentVo);
            }
        }
        objectIdSet.clear();
        //下部
        for(BridgeSubstructureVo newSubstructureVo:bridgeSubstructureList){
            if(newSubstructureVo.isIdReal()) {
                objectIdSet.add(newSubstructureVo.getId());
            }
        }
        for(BridgeSubstructureVo substructureVo:oldBridgeSubstructureList){
            if(substructureVo.isIdReal())
            if(!objectIdSet.contains(substructureVo.getId())){
                substructureVo.setStatus(0);
//                bridgeSubstructureList.add(substructureVo);
                appOfflineDao.insertOrUpdateBridgeSubstructure(substructureVo);
            }
        }
        objectIdSet.clear();
        //下部实例
        for(BridgeSubcomponentVo newSubcomponentVo:bridgeSubcomponentList){
            if(newSubcomponentVo.isIdReal()) {
                objectIdSet.add(newSubcomponentVo.getId());
            }
        }
        for(BridgeSubcomponentVo subcomponentVo:oldBridgeSubcomponentList){
            if(subcomponentVo.isIdReal())
            if(!objectIdSet.contains(subcomponentVo.getId())){
                subcomponentVo.setStatus(0);
//                bridgeSubcomponentList.add(subcomponentVo);
                appOfflineDao.insertOrUpdateBridgeSubcomponent(subcomponentVo);
            }
        }
        objectIdSet.clear();
        //其他结构
        for(BridgeOtherStructureVo newOtherStructure:bridgeOtherStructureList){
            if(newOtherStructure.isIdReal()) {
                objectIdSet.add(newOtherStructure.getId());
            }
        }
        for(BridgeOtherStructureVo otherStructure:oldBridgeOtherStructureList){
            if(otherStructure.isIdReal())
            if(!objectIdSet.contains(otherStructure.getId())){
                otherStructure.setStatus(0);
//                bridgeOtherStructureList.add(otherStructure);
                appOfflineDao.insertOrUpdateBridgeOtherStructure(otherStructure);
            }
        }
        objectIdSet.clear();
        //计划内桥梁info
        //桥梁不会被删除
//        for(StructureInfoVo newStructureInfoVo:structureOfPlan){
//            objectIdSet.add(newStructureInfoVo.getId());
//        }
//        for(StructureInfoVo structureInfoVo:oldStructureOfPlan){
//            if(!objectIdSet.contains(structureInfoVo.getId())){
//                appOfflineDao.deleteStructureInfo(structureInfoVo.getId());
//            }
//        }
        objectIdSet.clear();
        //桥墩
        for(PierAbutmentVo newPierAbutment:pierAbutmentList){
            if(newPierAbutment.isIdReal()) {
                objectIdSet.add(newPierAbutment.getId());
            }
        }
        for(PierAbutmentVo pierAbutment:oldPierAbutmentList){
            if(pierAbutment.isIdReal())
            if(!objectIdSet.contains(pierAbutment.getId())){
                pierAbutmentDao.deleteById(pierAbutment.getId());
            }
        }
        objectIdSet.clear();
        //将下载时病害按实例部位，实例id作为依据分类。之后将targetid重复时所有的旧病害插入记录表，并在病害表标记为回收(覆盖)
        for(DiseaseInstanceVo diseaseInstanceVo:oldDiseaseInstanceList){
            //在记录表中插入实例
//            DiseaseInstanceRecord record=new DiseaseInstanceRecord();
//            BeanUtils.copyProperties(diseaseInstanceVo,record);
//            record.setTdiId(diseaseInstanceVo.getId());
//            record.setDiseaseId(diseaseInstanceVo.getDiesaseId());
//            appOfflineDao.recordDiseaseInstance(record);
//            //在disease表中设置这些实例被回收(根据id)
//            appOfflineDao.setDiseaseStatus(diseaseInstanceVo.getId());
            switch (diseaseInstanceVo.getPartType()){
                case 1:{
                    if(deckDiseaseInstanceList.containsKey(diseaseInstanceVo.getTargetId())){
                        List<DiseaseInstanceVo> list=deckDiseaseInstanceList.get(diseaseInstanceVo.getTargetId());
                        list.add(diseaseInstanceVo);
                        deckDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }else{
                        ArrayList<DiseaseInstanceVo> list=new ArrayList<>();
                        list.add(diseaseInstanceVo);
                        deckDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }
                    break;
                }
                case 2:{
                    if(supDiseaseInstanceList.containsKey(diseaseInstanceVo.getTargetId())){
                        List<DiseaseInstanceVo> list=supDiseaseInstanceList.get(diseaseInstanceVo.getTargetId());
                        list.add(diseaseInstanceVo);
                        supDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }else{
                        ArrayList<DiseaseInstanceVo> list=new ArrayList<>();
                        list.add(diseaseInstanceVo);
                        supDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }
                    break;
                }
                case 3:{
                    if(subDiseaseInstanceList.containsKey(diseaseInstanceVo.getTargetId())){
                        List<DiseaseInstanceVo> list=subDiseaseInstanceList.get(diseaseInstanceVo.getTargetId());
                        list.add(diseaseInstanceVo);
                        subDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }else{
                        ArrayList<DiseaseInstanceVo> list=new ArrayList<>();
                        list.add(diseaseInstanceVo);
                        subDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }
                    break;
                }
                case 4:{
                    if(otherDiseaseInstanceList.containsKey(diseaseInstanceVo.getTargetId())){
                        List<DiseaseInstanceVo> list=otherDiseaseInstanceList.get(diseaseInstanceVo.getTargetId());
                        list.add(diseaseInstanceVo);
                        otherDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }else{
                        ArrayList<DiseaseInstanceVo> list=new ArrayList<>();
                        list.add(diseaseInstanceVo);
                        otherDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }
                    break;
                }
                default:
            }
        }

        //新病害数据按id分类
        for(DiseaseInstanceVo diseaseInstanceVo:diseaseInstanceList){
            switch (diseaseInstanceVo.getPartType()){
                case 1:{
                    if(newDeckDiseaseInstanceList.containsKey(diseaseInstanceVo.getTargetId())){
                        List<DiseaseInstanceVo> list=newDeckDiseaseInstanceList.get(diseaseInstanceVo.getTargetId());
                        list.add(diseaseInstanceVo);
                        newDeckDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }else{
                        ArrayList<DiseaseInstanceVo> list=new ArrayList<>();
                        list.add(diseaseInstanceVo);
                        newDeckDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }
                    break;
                }
                case 2:{
                    if(newSupDiseaseInstanceList.containsKey(diseaseInstanceVo.getTargetId())){
                        List<DiseaseInstanceVo> list=newSupDiseaseInstanceList.get(diseaseInstanceVo.getTargetId());
                        list.add(diseaseInstanceVo);
                        newSupDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }else{
                        ArrayList<DiseaseInstanceVo> list=new ArrayList<>();
                        list.add(diseaseInstanceVo);
                        newSupDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }
                    break;
                }
                case 3:{
                    if(newSubDiseaseInstanceList.containsKey(diseaseInstanceVo.getTargetId())){
                        List<DiseaseInstanceVo> list=newSubDiseaseInstanceList.get(diseaseInstanceVo.getTargetId());
                        list.add(diseaseInstanceVo);
                        newSubDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }else{
                        ArrayList<DiseaseInstanceVo> list=new ArrayList<>();
                        list.add(diseaseInstanceVo);
                        newSubDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }
                    break;
                }
                case 4:{
                    if(newOtherDiseaseInstanceList.containsKey(diseaseInstanceVo.getTargetId())){
                        List<DiseaseInstanceVo> list=newOtherDiseaseInstanceList.get(diseaseInstanceVo.getTargetId());
                        list.add(diseaseInstanceVo);
                        newOtherDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }else{
                        ArrayList<DiseaseInstanceVo> list=new ArrayList<>();
                        list.add(diseaseInstanceVo);
                        newOtherDiseaseInstanceList.put(diseaseInstanceVo.getTargetId(),list);
                    }
                    break;
                }
                default:
            }
        }

        //获取旧附件
        for(BridgeRoadVo bridgeRoadVo:oldBridgeRoadList){
            oldAttachments.addAll(bridgeRoadVo.getAttachments());
        }
        for(BridgeSpanVo bridgeSpanVo:oldBridgeSpanList){
            oldAttachments.addAll(bridgeSpanVo.getAttachments());
        }
        for(BridgeDeckSystemVo deckSystemVo:oldBridgeDeckSystemList){
            oldAttachments.addAll(deckSystemVo.getAttachments());
        }
        for(BridgeDeckComponentVo deckComponentVo:oldBridgeDeckComponentList){
            oldAttachments.addAll(deckComponentVo.getAttachments());
        }
        for(BridgeSupstructureVo supstructureVo:oldBridgeSupstructureList){
            oldAttachments.addAll(supstructureVo.getAttachments());
        }
        for(BridgeSupcomponentVo supcomponentVo:oldBridgeSupcomponentList){
            oldAttachments.addAll(supcomponentVo.getAttachments());
        }
        for(BridgeSubstructureVo substructureVo:oldBridgeSubstructureList){
            oldAttachments.addAll(substructureVo.getAttachments());
        }
        for(BridgeSubcomponentVo subcomponentVo:oldBridgeSubcomponentList){
            oldAttachments.addAll(subcomponentVo.getAttachments());
        }
        for(DiseaseInstanceVo diseaseInstanceVo:oldDiseaseInstanceList){
            oldAttachments.addAll(diseaseInstanceVo.getAttachments());
        }

        if(isListNotEmpty(oldAttachments))
        oldAttachments.removeIf(m -> m.getId() == 0);

        //设置缺少的附件的status
        for(AttachmentVo newAttachment:attachments){
            if(newAttachment.isIdReal()) {
                objectIdSet.add(newAttachment.getId());
            }
        }
        for(AttachmentVo attachment:oldAttachments){
            if (attachment.isIdReal())
            if(!objectIdSet.contains(attachment.getId())){
                attachment.setStatus(0);
                appOfflineDao.insertOrUpdateAttachment(attachment);
            }
        }
        objectIdSet.clear();

        //给新的数据添加至map
        for(BridgeRoadVo bridgeRoadVo:bridgeRoadList){
            ArrayList<BridgeRoadVo> roadVos;
            Integer roadsKey=bridgeRoadVo.getMonitorPlanStructureRelId();
            if(resultRoadList.containsKey(roadsKey)){
                roadVos=resultRoadList.get(roadsKey);
            }else{
                roadVos=new ArrayList<>();
            }
            roadVos.add(bridgeRoadVo);
            resultRoadList.put(roadsKey,roadVos);
        }
        for(BridgeSpanVo bridgeSpanVo:bridgeSpanList){
            Integer spansKey=bridgeSpanVo.getBridgeRoadId();
            ArrayList<BridgeSpanVo> spanVos;
            if(resultSpanList.containsKey(spansKey)){
                spanVos=resultSpanList.get(spansKey);
            }else{
                spanVos=new ArrayList<>();
            }
            spanVos.add(bridgeSpanVo);
            resultSpanList.put(spansKey,spanVos);
        }
        for(BridgeDeckSystemVo deckSystemVo:bridgeDeckSystemList){
            Integer decksKey=deckSystemVo.getBridgeSpanId();
            ArrayList<BridgeDeckSystemVo> deckSystemVos;
            if(resultDeckSystemList.containsKey(decksKey)){
                deckSystemVos=resultDeckSystemList.get(decksKey);
            }else{
                deckSystemVos=new ArrayList<>();
            }
            deckSystemVos.add(deckSystemVo);
            resultDeckSystemList.put(decksKey,deckSystemVos);
        }
        for(BridgeDeckComponentVo deckComponentVo:bridgeDeckComponentList){
            Integer deckComponentKey=deckComponentVo.getBridgeDeckSystemId();
            ArrayList<BridgeDeckComponentVo> deckComponentVos;
            if(resultDeckComponentList.containsKey(deckComponentKey)){
                deckComponentVos=resultDeckComponentList.get(deckComponentKey);
            }else{
                deckComponentVos=new ArrayList<>();
            }
            deckComponentVos.add(deckComponentVo);
            resultDeckComponentList.put(deckComponentKey,deckComponentVos);
        }
        for(BridgeSubstructureVo sub:bridgeSubstructureList){
            Integer subKey=sub.getBridgeSpanId();
            ArrayList<BridgeSubstructureVo> substructureVos;
            if(resultSubstructureList.containsKey(subKey)){
                substructureVos=resultSubstructureList.get(subKey);
            }else{
                substructureVos=new ArrayList<>();
            }
            substructureVos.add(sub);
            resultSubstructureList.put(subKey,substructureVos);
        }
        for(BridgeSubcomponentVo subcomponentVo:bridgeSubcomponentList){
            Integer subKey=subcomponentVo.getBridgeSubstructureId();
            ArrayList<BridgeSubcomponentVo> subcomponentVos;
            if(resultSubcomponentList.containsKey(subKey)){
                subcomponentVos=resultSubcomponentList.get(subKey);
            }else{
                subcomponentVos=new ArrayList<>();
            }
            subcomponentVos.add(subcomponentVo);
            resultSubcomponentList.put(subKey,subcomponentVos);
        }
        for(BridgeSupstructureVo sup:bridgeSupstructureList){
            Integer supKey=sup.getBridgeSpanId();
            ArrayList<BridgeSupstructureVo> supstructureVos;
            if(resultSupstructureList.containsKey(supKey)){
                supstructureVos=resultSupstructureList.get(supKey);
            }else{
                supstructureVos=new ArrayList<>();
            }
            supstructureVos.add(sup);
            resultSupstructureList.put(supKey,supstructureVos);
        }
        for(BridgeSupcomponentVo supcomponentVo:bridgeSupcomponentList){
            Integer supKey=supcomponentVo.getSupstructureId();
            ArrayList<BridgeSupcomponentVo> supcomponentVos;
            if(resultSupcomponentList.containsKey(supKey)){
                supcomponentVos=resultSupcomponentList.get(supKey);
            }else{
                supcomponentVos=new ArrayList<>();
            }
            supcomponentVos.add(supcomponentVo);
            resultSupcomponentList.put(supKey,supcomponentVos);
        }
        for(BridgeOtherStructureVo other:bridgeOtherStructureList){
            Integer otherKey=other.getBridgeSpanId();
            ArrayList<BridgeOtherStructureVo> otherStructureVos;
            if(resultOtherStructureList.containsKey(otherKey)){
                otherStructureVos=resultOtherStructureList.get(otherKey);
            }else{
                otherStructureVos=new ArrayList<>();
            }
            otherStructureVos.add(other);
            resultOtherStructureList.put(otherKey,otherStructureVos);
        }
        for(PierAbutmentVo pierAbutmentVo:pierAbutmentList){
            Integer pierKey=pierAbutmentVo.getBridgeSubstructureId();
            ArrayList<PierAbutmentVo> pierAbutmentVos;
            if(resultPierAbutmentList.containsKey(pierKey)){
                pierAbutmentVos=resultPierAbutmentList.get(pierKey);
            }else{
                pierAbutmentVos=new ArrayList<>();
            }
            pierAbutmentVos.add(pierAbutmentVo);
            resultPierAbutmentList.put(pierKey,pierAbutmentVos);
        }

        /**
         * 对比结束
         */

        //图片处理
        String imagePath=outFilePath+"attachment";    //原有附件文件夹
        new File(imageFilePath).mkdirs();

        //对数据库进行操作，结构若id相同则进行修改，病害则覆盖之前病害,图片需要存储并修改path
        //桥梁info，需要先添加新的桥梁得到id后，修改关联表中info_id
        for(StructureInfoVo structureInfoVo:structureOfPlan){
            if (structureInfoVo.isIdReal()){
                appOfflineDao.insertOrUpdateStructureInfo(structureInfoVo);
            }else{
                Integer structureCode=structureInfoVo.getId();
                structureInfoVo.setId(null);
                structureInfoVo.setUnitId(appOfflineDao.getProjectUnitIdByPlanId(Integer.parseInt(fileName)));
                structureInfoVo.setTechnology("城市桥隧技术状况");
                structureInfoVo.setStructureType(GlobalConstant.TYPE_BRIDGE);
                structureInfoVo.setStatus(1);
                appOfflineDao.insertNewStructureInfo(structureInfoVo);
                ProjectStructureRel rel=new ProjectStructureRel();
                rel.setStructureId(structureInfoVo.getId());
                rel.setProjectId(structureInfoVo.getProjectId());
                projectStructureRelDao.save(rel);
                BridgeInfo info=new BridgeInfo();
                info.setStructureId(structureInfoVo.getId());
                bridgeDao.save(info);
                Integer realInfoId=structureInfoVo.getId();
                for(MonitorPlanStructureRelVo relVo:monitorPlanStructureRelList){
                    if(relVo.getStructureInfoId().equals(structureCode)){
                        relVo.setStructureInfoId(realInfoId);
                        break;
                    }
                }
            }
        }
        //关联表
        for(MonitorPlanStructureRelVo m:monitorPlanStructureRelList){
            MonitorPlanStructureRel rel=new MonitorPlanStructureRel();
            BeanUtils.copyProperties(m,rel);
            Integer relId;
            Integer relCode=m.getId();
            if(m.isIdReal()){
                appOfflineDao.insertOrUpdatePlanStructureRel(rel);
                relId=m.getId();
            }else {
                rel.setId(null);
                appOfflineDao.insertNewMonitorPlanStructureRel(rel);
                relId = rel.getId();
            }
            //线路表
            if(resultRoadList.containsKey(relCode))
            for(BridgeRoadVo r:resultRoadList.get(relCode)){
                BridgeRoad road=new BridgeRoad();
                BeanUtils.copyProperties(r,road);
                road.setMonitorPlanStructureRelId(relId);
                road.setReportPath(null);
                Integer roadId;
                Integer roadCode=r.getId();
                if(r.isIdReal()){
                    appOfflineDao.insertOrUpdateBridgeRoad(road);
                    roadId=r.getId();
                }else{
                    road.setId(null);
                    appOfflineDao.insertNewBridgeRoad(road);
                    roadId=road.getId();
                }
                //线路附件
                for(AttachmentVo attachmentVo:r.getAttachments()){
                    if(attachmentVo.isIdReal()){
                        continue;
                    }
                    Attachment attachment=new Attachment();
                    BeanUtils.copyProperties(attachmentVo,attachment);
                    attachment.setTargetId(roadId);
                    String savePath=saveAttachment(imagePath,imageFilePath,attachmentVo);
                    if(savePath!=null){
                        attachment.setPath(savePath);
                    }
                    if(attachmentVo.isIdReal()){
                        appOfflineDao.insertOrUpdateAttachment(attachment);
                    }else{
                        attachment.setId(null);
                        appOfflineDao.insertNewAttachment(attachment);
                    }
                }
                //桥跨表
                if(resultSpanList.containsKey(roadCode))
                for(BridgeSpanVo s:resultSpanList.get(roadCode)){
                    BridgeSpan span=new BridgeSpan();
                    BeanUtils.copyProperties(s,span);
                    span.setBridgeRoadId(roadId);
                    Integer spanId;
                    Integer spanCode=s.getId();
                    if(s.isIdReal()){
                        appOfflineDao.insertOrUpdateBridgeSpan(span);
                        spanId=s.getId();
                    }else{
                        span.setId(null);
                        appOfflineDao.insertNewBridgeSpan(span);
                        spanId=span.getId();
                    }
                    //桥跨附件
                    for(AttachmentVo attachmentVo:s.getAttachments()){
                        if(attachmentVo.isIdReal()){
                            continue;
                        }
                        Attachment attachment=new Attachment();
                        BeanUtils.copyProperties(attachmentVo,attachment);
                        attachment.setTargetId(spanId);
                        String savePath=saveAttachment(imagePath,imageFilePath,attachmentVo);
                        if(savePath!=null){
                            attachment.setPath(savePath);
                        }
                        if(attachmentVo.isIdReal()){
                            appOfflineDao.insertOrUpdateAttachment(attachment);
                        }else{
                            attachment.setId(null);
                            appOfflineDao.insertNewAttachment(attachment);
                        }
                    }
                    //桥面系
                    if(resultDeckSystemList.containsKey(spanCode))
                    for(BridgeDeckSystemVo d:resultDeckSystemList.get(spanCode)){
                        BridgeDeckSystem deckSystem=new BridgeDeckSystem();
                        BeanUtils.copyProperties(d,deckSystem);
                        deckSystem.setBridgeSpanId(spanId);
                        Integer deckId;
                        Integer deckCode=d.getId();
                        if(d.isIdReal()){
                            appOfflineDao.insertOrUpdateBridgeDeckSystem(deckSystem);
                            deckId=d.getId();
                        }else{
                            deckSystem.setId(null);
                            appOfflineDao.insertNewBridgeDeckSystem(deckSystem);
                            deckId=deckSystem.getId();
                        }
//                        if(d.getMonitorDiagram()!=null&&!("".equals(d.getMonitorDiagram()))){
//                            /**
//                             * 桥面系示意图
//                             */
//                            AttachmentVo attachment=new AttachmentVo();
//                            attachment.setIdReal(false);
//                            attachment.setCreateTime(new Date());
//                            attachment.setModifyTime(new Date());
//                            attachment.setName(d.getMonitorDiagram());
//                            attachment.setStatus((byte)1);
//                            String attachmentPath=saveAttachment(imagePath,imageFilePath,attachment);
//                            if (attachmentPath!=null) {
//                                attachment.setPath(attachmentPath);
//                            }
//                            attachment.setId(null);
//                            appOfflineDao.insertNewAttachment(attachment);
//                        }
                        //桥面附件
                        for(AttachmentVo attachmentVo:d.getAttachments()){
                            if(attachmentVo.isIdReal()){
                                continue;
                            }
                            Attachment attachment=new Attachment();
                            BeanUtils.copyProperties(attachmentVo,attachment);
                            attachment.setTargetId(deckId);
                            if(attachment.getType()==6){//需要根据targetid将之前所有attachment置0
                                attachment.setModifyTime(modifyTime);
                                appOfflineDao.setOtherAttachmentByTargetId(attachment.getTargetId(),1);
                            }
                            String savePath=saveAttachment(imagePath,imageFilePath,attachmentVo);
                            if(savePath!=null){
                                attachment.setPath(savePath);
                            }
                            if(attachmentVo.isIdReal()){
                                appOfflineDao.insertOrUpdateAttachment(attachment);
                            }else{
                                attachment.setId(null);
                                appOfflineDao.insertNewAttachment(attachment);
                            }
                        }
                        //桥面实例
                        if(resultDeckComponentList.containsKey(deckCode))
                        for(BridgeDeckComponentVo deck:resultDeckComponentList.get(deckCode)){
                            BridgeDeckComponent deckComponent=new BridgeDeckComponent();
                            BeanUtils.copyProperties(deck,deckComponent);
                            deckComponent.setBridgeDeckSystemId(deckId);
                            Integer deckComponentId;
                            Integer deckComponentCode=deck.getId();
                            if(deck.isIdReal()){
                                appOfflineDao.insertOrUpdateBridgeDeckComponent(deckComponent);
                                deckComponentId=deck.getId();
                            }else{
                                deckComponent.setId(null);
                                deckComponent.setSerious(0);
                                appOfflineDao.insertNewBridgeDeckComponent(deckComponent);
                                deckComponentId=deckComponent.getId();
                            }
                            //桥面实例附件
                            for(AttachmentVo attachmentVo:deck.getAttachments()){
                                if(attachmentVo.isIdReal()){
                                    continue;
                                }
                                Attachment attachment=new Attachment();
                                BeanUtils.copyProperties(attachmentVo,attachment);
                                attachment.setTargetId(deckComponentId);
                                String savePath=saveAttachment(imagePath,imageFilePath,attachmentVo);
                                if(savePath!=null){
                                    attachment.setPath(savePath);
                                }
                                if(attachmentVo.isIdReal()){
                                    appOfflineDao.insertOrUpdateAttachment(attachment);
                                }else{
                                    attachment.setId(null);
                                    appOfflineDao.insertNewAttachment(attachment);
                                }
                            }
                            //桥面病害实例
                            if(newDeckDiseaseInstanceList.containsKey(deckComponentCode)){
                                HashMap<Integer,DiseaseInstanceVo> toDeleteMap=new HashMap<>();
                                List<DiseaseInstanceVo> list=new ArrayList<>();
                                if(deck.isIdReal()) {
                                    if (deckDiseaseInstanceList.containsKey(deckComponentId)) {   //与旧病害冲突，需要覆盖
                                        list = deckDiseaseInstanceList.get(deckComponentId);
                                        for (DiseaseInstanceVo todelete : list) {
                                            if(todelete.isIdReal()){
                                                toDeleteMap.put(todelete.getId(),todelete);
                                            }
//                                            if (todelete.isIdReal()) {
//                                                DiseaseInstanceRecord record = new DiseaseInstanceRecord();
//                                                BeanUtils.copyProperties(todelete, record);
//                                                record.setTdiId(todelete.getId());
//                                                record.setModifyTime(modifyTime);
//                                                diseaseSet.add(todelete.getId());
//                                                appOfflineDao.recordDiseaseInstance(record);
//                                            }
//                                            //在disease表中设置这些实例被回收(根据id)
//                                            DiseaseInstance update = new DiseaseInstance();
//                                            update.setId(null);
//                                            update.setModifyTime(modifyTime);
//                                            update.setStatus((byte) 0);
//                                            appOfflineDao.insertNewDiseaseInstance(update);
                                        }
                                    }
                                }
                                //添加病害数据
                                for(DiseaseInstanceVo diseaseInstanceVo:newDeckDiseaseInstanceList.get(deckComponentCode)){
                                    if(!diseaseInstanceVo.isIdReal()){//新增病害
                                        diseaseInstanceVo.setId(null);
                                        diseaseInstanceVo.setTargetId(deckComponentId);
                                        diseaseInstanceVo.setModifyTime(modifyTime);
//                                        if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                            if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                                diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                            }
//                                            if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                                diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                            }
//                                        }
                                        appOfflineDao.insertNewDiseaseInstance(diseaseInstanceVo);
                                    }else {
                                        if (toDeleteMap.containsKey(diseaseInstanceVo.getId())) {//旧病害与当前病害id冲突
                                            if(!diseaseInstanceVo.getCreator().equals(toDeleteMap.get(diseaseInstanceVo.getId()).getCreator())){
                                                //创建者不一样，旧病害set0，新病害新增
                                                diseaseInstanceVo.setId(null);
                                                diseaseInstanceVo.setTargetId(deckComponentId);
                                                diseaseInstanceVo.setModifyTime(modifyTime);
//                                                if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                                    if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                                        diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                                    }
//                                                    if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                                        diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                                    }
//                                                }
                                                appOfflineDao.insertNewDiseaseInstance(diseaseInstanceVo);
                                            }else {
                                                if (isEquals(diseaseInstanceVo, toDeleteMap.get(diseaseInstanceVo.getId()))) {//新病害与旧病害完全相同，病害不变
                                                    if(diseaseInstanceVo.getStatus()==0){
                                                        diseaseInstanceVo.setModifyTime(modifyTime);
                                                        appOfflineDao.insertOrUpdateDiseaseInstance(diseaseInstanceVo);
                                                    }
                                                    list.removeIf(i -> i.getId().equals(diseaseInstanceVo.getId()));
                                                } else {//旧病害与新病害有所不同,旧病害进入record表，新病害修改
                                                    /**--旧病害--*/
                                                    DiseaseInstanceRecord record = new DiseaseInstanceRecord();
                                                    BeanUtils.copyProperties(toDeleteMap.get(diseaseInstanceVo.getId()), record);
                                                    record.setTdiId(diseaseInstanceVo.getId());
                                                    record.setModifyTime(modifyTime);
                                                    appOfflineDao.recordDiseaseInstance(record);
                                                    list.removeIf(i -> i.getId().equals(diseaseInstanceVo.getId()));
                                                    /**--新病害--*/
                                                    diseaseInstanceVo.setModifyTime(modifyTime);
                                                    diseaseInstanceVo.setTargetId(deckComponentId);
//                                                    if (diseaseInstanceVo.getDegree() == null || "".equals(diseaseInstanceVo.getDegree())) {
//                                                        if (diseaseInstanceVo.getHeightDifference() != null && 0 != diseaseInstanceVo.getHeightDifference()) {
//                                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference() + "");
//                                                        }
//                                                        if (diseaseInstanceVo.getNumber() != null && 0 != diseaseInstanceVo.getNumber()) {
//                                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber() + "");
//                                                        }
//                                                    }
                                                    appOfflineDao.insertOrUpdateDiseaseInstance(diseaseInstanceVo);
                                                }
                                            }

                                        } else {
                                            diseaseInstanceVo.setId(null);
                                            diseaseInstanceVo.setTargetId(deckComponentId);
                                            diseaseInstanceVo.setModifyTime(modifyTime);
//                                            if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                                if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                                    diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                                }
//                                                if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                                    diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                                }
//                                            }
                                            appOfflineDao.insertNewDiseaseInstance(diseaseInstanceVo);
                                        }
                                    }
                                    Integer diseaseId = diseaseInstanceVo.getId();
                                    //病害附件
                                    for(int i = 0;i<diseaseInstanceVo.getAttachments().size();i++){
                                        AttachmentVo attachmentVo=diseaseInstanceVo.getAttachments().get(i);
                                        if(attachmentVo.isIdReal()){
                                            continue;
                                        }
                                        Attachment attachment=new Attachment();
                                        BeanUtils.copyProperties(attachmentVo,attachment);
                                        attachment.setTargetId(diseaseId);
                                        if(i==diseaseInstanceVo.getAttachments().size()-1){
                                            attachment.setStatus(2);
                                        }else{
                                            attachment.setStatus(1);
                                        }
                                        String savePath=saveAttachment(imagePath,imageFilePath,attachmentVo);
                                        if(savePath!=null){
                                            attachment.setPath(savePath);
                                        }
                                        if(attachmentVo.isIdReal()){
                                            appOfflineDao.insertOrUpdateAttachment(attachment);
                                        }else{
                                            attachment.setId(null);
                                            appOfflineDao.insertNewAttachment(attachment);
                                        }
                                    }
                                }
                                for(DiseaseInstanceVo diseaseInstanceVo:list){//需要设置status为0的病害
                                    diseaseInstanceVo.setModifyTime(modifyTime);
                                    diseaseInstanceVo.setStatus(0);
//                                    if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                        if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                        }
//                                        if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                        }
//                                    }
                                    appOfflineDao.insertOrUpdateDiseaseInstance(diseaseInstanceVo);
                                }
                            }
                        }
                    }
                    //上部结构
                    if(resultSupstructureList.containsKey(spanCode))
                    for(BridgeSupstructureVo sup:resultSupstructureList.get(spanCode)){
                        BridgeSupstructure supstructure=new BridgeSupstructure();
                        BeanUtils.copyProperties(sup,supstructure);
                        supstructure.setBridgeSpanId(spanId);
                        Integer supId;
                        Integer supCode=sup.getId();
                        if(sup.isIdReal()){
                            appOfflineDao.insertOrUpdateBridgeSupstructure(supstructure);
                            supId=sup.getId();
                        }else{
                            supstructure.setId(null);
                            appOfflineDao.insertNewBridgeSupstructure(supstructure);
                            supId=supstructure.getId();
                        }
//                        if(sup.getMonitorDiagram()!=null&&!("".equals(sup.getMonitorDiagram()))){
//                            /**
//                             * 桥上部示意图
//                             */
//                            AttachmentVo attachment=new AttachmentVo();
//                            attachment.setIdReal(false);
//                            attachment.setCreateTime(new Date());
//                            attachment.setModifyTime(new Date());
//                            attachment.setName(sup.getMonitorDiagram());
//                            attachment.setStatus((byte)1);
//                            String attachmentPath=saveAttachment(imagePath,imageFilePath,attachment);
//                            if (attachmentPath!=null) {
//                                attachment.setPath(attachmentPath);
//                            }
//                            attachment.setId(null);
//                            appOfflineDao.insertNewAttachment(attachment);
//                        }
                        //上部附件
                        for(AttachmentVo attachmentVo:sup.getAttachments()){
                            if(attachmentVo.isIdReal()){
                                continue;
                            }
                            Attachment attachment=new Attachment();
                            BeanUtils.copyProperties(attachmentVo,attachment);
                            attachment.setTargetId(supId);
                            String savePath=saveAttachment(imagePath,imageFilePath,attachmentVo);
                            if(savePath!=null){
                                attachment.setPath(savePath);
                            }
                            if(attachmentVo.isIdReal()){
                                appOfflineDao.insertOrUpdateAttachment(attachment);
                            }else{
                                attachment.setId(null);
                                appOfflineDao.insertNewAttachment(attachment);
                            }
                        }
                        //上部实例
                        if(resultSupcomponentList.containsKey(supCode))
                        for(BridgeSupcomponentVo supcomponentVo:resultSupcomponentList.get(supCode)){
                            BridgeSupcomponent supcomponent=new BridgeSupcomponent();
                            BeanUtils.copyProperties(supcomponentVo,supcomponent);
                            supcomponent.setSupstructureId(supId);
                            Integer supComponentId;
                            Integer supComponentCode=supcomponentVo.getId();
                            if(supcomponentVo.isIdReal()){
                                appOfflineDao.insertOrUpdateBridgeSupcomponent(supcomponent);
                                supComponentId=supcomponentVo.getId();
                            }else{
                                supcomponent.setId(null);
                                supcomponent.setSerious(0);
                                appOfflineDao.insertNewBridgeSupcomponent(supcomponent);
                                supComponentId=supcomponent.getId();
                            }
                            //上部实例附件
                            for(AttachmentVo attachmentVo:supcomponentVo.getAttachments()){
                                if(attachmentVo.isIdReal()){
                                    continue;
                                }
                                Attachment attachment=new Attachment();
                                BeanUtils.copyProperties(attachmentVo,attachment);
                                attachment.setTargetId(supComponentId);
                                if(attachment.getType()==6){//需要根据targetid将之前所有attachment置0
                                    attachment.setModifyTime(modifyTime);
                                    appOfflineDao.setOtherAttachmentByTargetId(attachment.getTargetId(),2);
                                }
                                String savePath=saveAttachment(imagePath,imageFilePath,attachmentVo);
                                if(savePath!=null){
                                    attachment.setPath(savePath);
                                }
                                if(attachmentVo.isIdReal()){
                                    appOfflineDao.insertOrUpdateAttachment(attachment);
                                }else{
                                    attachment.setId(null);
                                    appOfflineDao.insertNewAttachment(attachment);
                                }
                            }
                            //上部病害实例
                            if(newSupDiseaseInstanceList.containsKey(supComponentCode)){
                                HashMap<Integer,DiseaseInstanceVo> toDeleteMap=new HashMap<>();
                                List<DiseaseInstanceVo> list=new ArrayList<>();
                                if(supcomponentVo.isIdReal()) {
                                    if (supDiseaseInstanceList.containsKey(supComponentId)) {   //与旧病害冲突，需要覆盖
                                        list = supDiseaseInstanceList.get(supComponentId);
                                        for (DiseaseInstanceVo todelete : list) {
                                            if(todelete.isIdReal()){
                                                toDeleteMap.put(todelete.getId(),todelete);
                                            }
//                                            if (todelete.isIdReal()) {
//                                                DiseaseInstanceRecord record = new DiseaseInstanceRecord();
//                                                BeanUtils.copyProperties(todelete, record);
//                                                record.setTdiId(todelete.getId());
//                                                record.setModifyTime(modifyTime);
//                                                diseaseSet.add(todelete.getId());
//                                                appOfflineDao.recordDiseaseInstance(record);
//                                            }
//                                            //在disease表中设置这些实例被回收(根据id)
//                                            DiseaseInstance update = new DiseaseInstance();
//                                            update.setId(null);
//                                            update.setModifyTime(modifyTime);
//                                            update.setStatus((byte) 0);
//                                            appOfflineDao.insertNewDiseaseInstance(update);
                                        }
                                    }
                                }
                                //添加病害数据
                                for(DiseaseInstanceVo diseaseInstanceVo:newSupDiseaseInstanceList.get(supComponentCode)){
                                    if(!diseaseInstanceVo.isIdReal()){//新增病害
                                        diseaseInstanceVo.setId(null);
                                        diseaseInstanceVo.setTargetId(supComponentId);
                                        diseaseInstanceVo.setModifyTime(modifyTime);
//                                        if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                            if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                                diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                            }
//                                            if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                                diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                            }
//                                        }
                                        appOfflineDao.insertNewDiseaseInstance(diseaseInstanceVo);
                                    }else {
                                        if (toDeleteMap.containsKey(diseaseInstanceVo.getId())) {//旧病害与当前病害id冲突
                                            if(!diseaseInstanceVo.getCreator().equals(toDeleteMap.get(diseaseInstanceVo.getId()).getCreator())){
                                                //创建者不一样，旧病害set0，新病害新增
                                                diseaseInstanceVo.setId(null);
                                                diseaseInstanceVo.setTargetId(supComponentId);
                                                diseaseInstanceVo.setModifyTime(modifyTime);
//                                                if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                                    if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                                        diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                                    }
//                                                    if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                                        diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                                    }
//                                                }
                                                appOfflineDao.insertNewDiseaseInstance(diseaseInstanceVo);
                                            }else {
                                                if (isEquals(diseaseInstanceVo, toDeleteMap.get(diseaseInstanceVo.getId()))) {//新病害与旧病害完全相同，病害不变
                                                    if(diseaseInstanceVo.getStatus()==0){
                                                        diseaseInstanceVo.setModifyTime(modifyTime);
                                                        appOfflineDao.insertOrUpdateDiseaseInstance(diseaseInstanceVo);
                                                    }
                                                    list.removeIf(i -> i.getId().equals(diseaseInstanceVo.getId()));
                                                } else {//旧病害与新病害有所不同,旧病害进入record表，新病害修改
                                                    /**--旧病害--*/
                                                    DiseaseInstanceRecord record = new DiseaseInstanceRecord();
                                                    BeanUtils.copyProperties(toDeleteMap.get(diseaseInstanceVo.getId()), record);
                                                    record.setTdiId(diseaseInstanceVo.getId());
                                                    record.setModifyTime(modifyTime);
                                                    appOfflineDao.recordDiseaseInstance(record);
                                                    list.removeIf(i -> i.getId().equals(diseaseInstanceVo.getId()));
                                                    /**--新病害--*/
                                                    diseaseInstanceVo.setModifyTime(modifyTime);
                                                    diseaseInstanceVo.setTargetId(supComponentId);
//                                                    if (diseaseInstanceVo.getDegree() == null || "".equals(diseaseInstanceVo.getDegree())) {
//                                                        if (diseaseInstanceVo.getHeightDifference() != null && 0 != diseaseInstanceVo.getHeightDifference()) {
//                                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference() + "");
//                                                        }
//                                                        if (diseaseInstanceVo.getNumber() != null && 0 != diseaseInstanceVo.getNumber()) {
//                                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber() + "");
//                                                        }
//                                                    }
                                                    appOfflineDao.insertOrUpdateDiseaseInstance(diseaseInstanceVo);
                                                }
                                            }

                                        } else {
                                            diseaseInstanceVo.setId(null);
                                            diseaseInstanceVo.setTargetId(supComponentId);
                                            diseaseInstanceVo.setModifyTime(modifyTime);
//                                            if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                                if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                                    diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                                }
//                                                if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                                    diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                                }
//                                            }
                                            appOfflineDao.insertNewDiseaseInstance(diseaseInstanceVo);
                                        }
                                    }
                                    Integer diseaseId = diseaseInstanceVo.getId();
                                    //病害附件
                                    for(int i=0;i<diseaseInstanceVo.getAttachments().size();i++){
                                        AttachmentVo attachmentVo=diseaseInstanceVo.getAttachments().get(i);
                                        if(attachmentVo.isIdReal()){
                                            continue;
                                        }
                                        Attachment attachment=new Attachment();
                                        BeanUtils.copyProperties(attachmentVo,attachment);
                                        attachment.setTargetId(diseaseId);
                                        if(i==diseaseInstanceVo.getAttachments().size()-1){
                                            attachment.setStatus(2);
                                        }else{
                                            attachment.setStatus(1);
                                        }
                                        String savePath=saveAttachment(imagePath,imageFilePath,attachmentVo);
                                        if(savePath!=null){
                                            attachment.setPath(savePath);
                                        }
                                        if(attachmentVo.isIdReal()){
                                            appOfflineDao.insertOrUpdateAttachment(attachment);
                                        }else{
                                            attachment.setId(null);
                                            appOfflineDao.insertNewAttachment(attachment);
                                        }
                                    }
                                }
                                for(DiseaseInstanceVo diseaseInstanceVo:list){//需要设置status为0的病害
                                    diseaseInstanceVo.setModifyTime(modifyTime);
                                    diseaseInstanceVo.setStatus(0);
//                                    if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                        if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                        }
//                                        if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                        }
//                                    }
                                    appOfflineDao.insertOrUpdateDiseaseInstance(diseaseInstanceVo);
                                }
                            }
                        }
                    }
                    //下部结构
                    if(resultSubstructureList.containsKey(spanCode))
                    for(BridgeSubstructureVo sub:resultSubstructureList.get(spanCode)){
                        BridgeSubstructure substructure=new BridgeSubstructure();
                        BeanUtils.copyProperties(sub,substructure);
                        substructure.setBridgeSpanId(spanId);
                        Integer subId;
                        Integer subCode=sub.getId();
                        if(sub.isIdReal()){
                            appOfflineDao.insertOrUpdateBridgeSubstructure(substructure);
                            subId=sub.getId();
                        }else{
                            substructure.setId(null);
                            appOfflineDao.insertNewBridgeSubstructure(substructure);
                            subId=substructure.getId();
                        }
//                        if(sub.getMonitorDiagram()!=null&&!("".equals(sub.getMonitorDiagram()))){
//                            /**
//                             * 桥下部示意图
//                             */
//                            AttachmentVo attachment=new AttachmentVo();
//                            attachment.setIdReal(false);
//                            attachment.setCreateTime(new Date());
//                            attachment.setModifyTime(new Date());
//                            attachment.setName(sub.getMonitorDiagram());
//                            attachment.setStatus((byte)1);
//                            String attachmentPath=saveAttachment(imagePath,imageFilePath,attachment);
//                            if (attachmentPath!=null) {
//                                attachment.setPath(attachmentPath);
//                            }
//                            attachment.setId(null);
//                            appOfflineDao.insertNewAttachment(attachment);
//                        }
                        //下部附件
                        for(AttachmentVo attachmentVo:sub.getAttachments()){
                            if(attachmentVo.isIdReal()){
                                continue;
                            }
                            Attachment attachment=new Attachment();
                            BeanUtils.copyProperties(attachmentVo,attachment);
                            attachment.setTargetId(subId);
                            String savePath=saveAttachment(imagePath,imageFilePath,attachmentVo);
                            if(savePath!=null){
                                attachment.setPath(savePath);
                            }
                            if(attachmentVo.isIdReal()){
                                appOfflineDao.insertOrUpdateAttachment(attachment);
                            }else{
                                attachment.setId(null);
                                appOfflineDao.insertNewAttachment(attachment);
                            }
                        }
                        //桥墩
                        HashMap<Integer,Integer> oldToNew=new HashMap<>();
                        if (resultPierAbutmentList.containsKey(subCode))
                        for(PierAbutmentVo pier:resultPierAbutmentList.get(subCode)){
                            PierAbutment pierAbutment=new PierAbutment();
                            BeanUtils.copyProperties(pier,pierAbutment);
                            pierAbutment.setBridgeSubstructureId(subId);
                            Integer pierCode=pierAbutment.getId();
                            if(pier.isIdReal()) {
                                appOfflineDao.insertOrUpdatePierAbutment(pierAbutment);
                            }else{
                                pierAbutment.setId(null);
                                appOfflineDao.insertNewPierAbutment(pierAbutment);
                            }
                            Integer pierId=pierAbutment.getId();
                            oldToNew.put(pierCode,pierId);
                        }
                        //下部实例
                        if (resultSubcomponentList.containsKey(subCode))
                        for(BridgeSubcomponentVo subcomponentVo:resultSubcomponentList.get(subCode)){
                            BridgeSubcomponent subcomponent=new BridgeSubcomponent();
                            BeanUtils.copyProperties(subcomponentVo,subcomponent);
                            subcomponent.setBridgeSubstructureId(subId);

                            //更新pierAbutmentId
                            if(oldToNew.containsKey(subcomponent.getPierAbutmentId())){
                                Integer oldCode=subcomponent.getPierAbutmentId();
                                subcomponent.setPierAbutmentId(oldToNew.get(oldCode));
                            }

                            Integer subComponentId;
                            Integer subComponentCode=subcomponentVo.getId();
                            if(subcomponentVo.isIdReal()) {
                                appOfflineDao.insertOrUpdateBridgeSubcomponent(subcomponent);
                                subComponentId=subcomponentVo.getId();
                            }else{
                                subcomponent.setId(null);
                                subcomponent.setSerious(0);
                                appOfflineDao.insertNewBridgeSubcomponent(subcomponent);
                                subComponentId=subcomponent.getId();
                            }
                            //下部实例附件
                            for(AttachmentVo attachmentVo:subcomponentVo.getAttachments()){
                                if(attachmentVo.isIdReal()){
                                    continue;
                                }
                                Attachment attachment=new Attachment();
                                BeanUtils.copyProperties(attachmentVo,attachment);
                                attachment.setTargetId(subComponentId);
                                if(attachment.getType()==6){//需要根据targetid将之前所有attachment置0
                                    attachment.setModifyTime(modifyTime);
                                    appOfflineDao.setOtherAttachmentByTargetId(attachment.getTargetId(),3);
                                }
                                String savePath=saveAttachment(imagePath,imageFilePath,attachmentVo);
                                if(savePath!=null){
                                    attachment.setPath(savePath);
                                }
                                if(attachmentVo.isIdReal()){
                                    appOfflineDao.insertOrUpdateAttachment(attachment);
                                }else{
                                    attachment.setId(null);
                                    appOfflineDao.insertNewAttachment(attachment);
                                }
                            }
                            //下部病害实例
                            if(newSubDiseaseInstanceList.containsKey(subComponentCode)){
                                HashMap<Integer,DiseaseInstanceVo> toDeleteMap=new HashMap<>();
                                List<DiseaseInstanceVo> list=new ArrayList<>();
                                if(subcomponentVo.isIdReal()) {
                                    if (subDiseaseInstanceList.containsKey(subComponentId)) {   //与旧病害冲突，需要覆盖
                                        list = subDiseaseInstanceList.get(subComponentId);
                                        for (DiseaseInstanceVo todelete : list) {
                                            if(todelete.isIdReal()){
                                                toDeleteMap.put(todelete.getId(),todelete);
                                            }
//                                            if (todelete.isIdReal()) {
//                                                DiseaseInstanceRecord record = new DiseaseInstanceRecord();
//                                                BeanUtils.copyProperties(todelete, record);
//                                                record.setTdiId(todelete.getId());
//                                                record.setModifyTime(modifyTime);
//                                                diseaseSet.add(todelete.getId());
//                                                appOfflineDao.recordDiseaseInstance(record);
//                                            }
//                                            //在disease表中设置这些实例被回收(根据id)
//                                            DiseaseInstance update = new DiseaseInstance();
//                                            update.setId(null);
//                                            update.setModifyTime(modifyTime);
//                                            update.setStatus((byte) 0);
//                                            appOfflineDao.insertNewDiseaseInstance(update);
                                        }
                                    }
                                }
                                //添加病害数据
                                for(DiseaseInstanceVo diseaseInstanceVo:newSubDiseaseInstanceList.get(subComponentCode)){
                                    if(!diseaseInstanceVo.isIdReal()){//新增病害
                                        diseaseInstanceVo.setId(null);
                                        diseaseInstanceVo.setTargetId(subComponentId);
                                        diseaseInstanceVo.setModifyTime(modifyTime);
//                                        if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                            if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                                diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                            }
//                                            if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                                diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                            }
//                                        }
                                        appOfflineDao.insertNewDiseaseInstance(diseaseInstanceVo);
                                    }else {
                                        if (toDeleteMap.containsKey(diseaseInstanceVo.getId())) {//旧病害与当前病害id冲突
                                            if(!diseaseInstanceVo.getCreator().equals(toDeleteMap.get(diseaseInstanceVo.getId()).getCreator())){
                                                //创建者不一样，旧病害set0，新病害新增
                                                diseaseInstanceVo.setId(null);
                                                diseaseInstanceVo.setTargetId(subComponentId);
                                                diseaseInstanceVo.setModifyTime(modifyTime);
//                                                if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                                    if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                                        diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                                    }
//                                                    if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                                        diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                                    }
//                                                }
                                                appOfflineDao.insertNewDiseaseInstance(diseaseInstanceVo);
                                            }else {
                                                if (isEquals(diseaseInstanceVo, toDeleteMap.get(diseaseInstanceVo.getId()))) {//新病害与旧病害完全相同，病害不变
                                                    if(diseaseInstanceVo.getStatus()==0){
                                                        diseaseInstanceVo.setModifyTime(modifyTime);
                                                        appOfflineDao.insertOrUpdateDiseaseInstance(diseaseInstanceVo);
                                                    }
                                                    list.removeIf(i -> i.getId().equals(diseaseInstanceVo.getId()));
                                                } else {//旧病害与新病害有所不同,旧病害进入record表，新病害修改
                                                    /**--旧病害--*/
                                                    DiseaseInstanceRecord record = new DiseaseInstanceRecord();
                                                    BeanUtils.copyProperties(toDeleteMap.get(diseaseInstanceVo.getId()), record);
                                                    record.setTdiId(diseaseInstanceVo.getId());
                                                    record.setModifyTime(modifyTime);
                                                    appOfflineDao.recordDiseaseInstance(record);
                                                    list.removeIf(i -> i.getId().equals(diseaseInstanceVo.getId()));
                                                    /**--新病害--*/
                                                    diseaseInstanceVo.setModifyTime(modifyTime);
                                                    diseaseInstanceVo.setTargetId(subComponentId);
//                                                    if (diseaseInstanceVo.getDegree() == null || "".equals(diseaseInstanceVo.getDegree())) {
//                                                        if (diseaseInstanceVo.getHeightDifference() != null && 0 != diseaseInstanceVo.getHeightDifference()) {
//                                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference() + "");
//                                                        }
//                                                        if (diseaseInstanceVo.getNumber() != null && 0 != diseaseInstanceVo.getNumber()) {
//                                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber() + "");
//                                                        }
//                                                    }
                                                    appOfflineDao.insertOrUpdateDiseaseInstance(diseaseInstanceVo);
                                                }
                                            }

                                        } else {
                                            diseaseInstanceVo.setId(null);
                                            diseaseInstanceVo.setTargetId(subComponentId);
                                            diseaseInstanceVo.setModifyTime(modifyTime);
//                                            if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                                if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                                    diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                                }
//                                                if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                                    diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                                }
//                                            }
                                            appOfflineDao.insertNewDiseaseInstance(diseaseInstanceVo);
                                        }
                                    }
                                    Integer diseaseId = diseaseInstanceVo.getId();
                                    //病害附件
                                    for(int i = 0;i<diseaseInstanceVo.getAttachments().size();i++){
                                        AttachmentVo attachmentVo=diseaseInstanceVo.getAttachments().get(i);
                                        if(attachmentVo.isIdReal()){
                                            continue;
                                        }
                                        Attachment attachment=new Attachment();
                                        BeanUtils.copyProperties(attachmentVo,attachment);
                                        attachment.setTargetId(diseaseId);
                                        if(i==diseaseInstanceVo.getAttachments().size()-1){
                                            attachment.setStatus(2);
                                        }else{
                                            attachment.setStatus(1);
                                        }
                                        String savePath=saveAttachment(imagePath,imageFilePath,attachmentVo);
                                        if(savePath!=null){
                                            attachment.setPath(savePath);
                                        }
                                        if(attachmentVo.isIdReal()){
                                            appOfflineDao.insertOrUpdateAttachment(attachment);
                                        }else{
                                            attachment.setId(null);
                                            appOfflineDao.insertNewAttachment(attachment);
                                        }
                                    }
                                }
                                for(DiseaseInstanceVo diseaseInstanceVo:list){//需要设置status为0的病害
                                    diseaseInstanceVo.setModifyTime(modifyTime);
                                    diseaseInstanceVo.setStatus(0);
//                                    if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                        if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                        }
//                                        if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                        }
//                                    }
                                    appOfflineDao.insertOrUpdateDiseaseInstance(diseaseInstanceVo);
                                }
                            }
                        }
                    }
                    //其他结构
                    if(resultOtherStructureList.containsKey(spanCode))
                    for(BridgeOtherStructureVo other:resultOtherStructureList.get(spanCode)){
                        BridgeOtherStructure otherStructure=new BridgeOtherStructure();
                        BeanUtils.copyProperties(other,otherStructure);
                        otherStructure.setBridgeSpanId(spanId);
                        Integer otherCode=otherStructure.getId();
                        if(other.isIdReal()){
                            appOfflineDao.insertOrUpdateBridgeOtherStructure(otherStructure);
                        }else{
                            otherStructure.setId(null);
                            appOfflineDao.insertNewBridgeOtherStructure(otherStructure);
                        }
                        Integer otherId=otherStructure.getId();
                        //其他结构图片信息
                        for(AttachmentVo attachmentVo:other.getAttachments()){
                            if(attachmentVo.isIdReal()){
                                continue;
                            }
                            Attachment attachment=new Attachment();
                            BeanUtils.copyProperties(attachmentVo,attachment);
                            attachment.setTargetId(otherId);
                            String savePath=saveAttachment(imagePath,imageFilePath,attachmentVo);
                            if(savePath!=null){
                                attachment.setPath(savePath);
                            }
                            if(attachmentVo.isIdReal()){
                                appOfflineDao.insertOrUpdateAttachment(attachment);
                            }else{
                                attachment.setId(null);
                                appOfflineDao.insertNewAttachment(attachment);
                            }
                        }
                        //其他病害实例
                        if(newOtherDiseaseInstanceList.containsKey(otherCode)){
                            HashMap<Integer,DiseaseInstanceVo> toDeleteMap=new HashMap<>();
                            List<DiseaseInstanceVo> list=new ArrayList<>();
                            if(other.isIdReal()) {
                                if (otherDiseaseInstanceList.containsKey(otherId)) {   //与旧病害冲突，需要覆盖
                                    list = otherDiseaseInstanceList.get(otherId);
                                    for (DiseaseInstanceVo todelete : list) {
                                        if(todelete.isIdReal()){
                                            toDeleteMap.put(todelete.getId(),todelete);
                                        }
//                                            if (todelete.isIdReal()) {
//                                                DiseaseInstanceRecord record = new DiseaseInstanceRecord();
//                                                BeanUtils.copyProperties(todelete, record);
//                                                record.setTdiId(todelete.getId());
//                                                record.setModifyTime(modifyTime);
//                                                diseaseSet.add(todelete.getId());
//                                                appOfflineDao.recordDiseaseInstance(record);
//                                            }
//                                            //在disease表中设置这些实例被回收(根据id)
//                                            DiseaseInstance update = new DiseaseInstance();
//                                            update.setId(null);
//                                            update.setModifyTime(modifyTime);
//                                            update.setStatus((byte) 0);
//                                            appOfflineDao.insertNewDiseaseInstance(update);
                                    }
                                }
                            }
                            //添加病害数据
                            for(DiseaseInstanceVo diseaseInstanceVo:newOtherDiseaseInstanceList.get(otherCode)){
                                if(!diseaseInstanceVo.isIdReal()){//新增病害
                                    diseaseInstanceVo.setId(null);
                                    diseaseInstanceVo.setTargetId(otherId);
                                    diseaseInstanceVo.setModifyTime(modifyTime);
//                                        if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                            if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                                diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                            }
//                                            if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                                diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                            }
//                                        }
                                    appOfflineDao.insertNewDiseaseInstance(diseaseInstanceVo);
                                }else {
                                    if (toDeleteMap.containsKey(diseaseInstanceVo.getId())) {//旧病害与当前病害id冲突
                                        if(!diseaseInstanceVo.getCreator().equals(toDeleteMap.get(diseaseInstanceVo.getId()).getCreator())){
                                            //创建者不一样，旧病害set0，新病害新增
                                            diseaseInstanceVo.setId(null);
                                            diseaseInstanceVo.setTargetId(otherId);
                                            diseaseInstanceVo.setModifyTime(modifyTime);
//                                                if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                                    if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                                        diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                                    }
//                                                    if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                                        diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                                    }
//                                                }
                                            appOfflineDao.insertNewDiseaseInstance(diseaseInstanceVo);
                                        }else {
                                            if (isEquals(diseaseInstanceVo, toDeleteMap.get(diseaseInstanceVo.getId()))) {//新病害与旧病害完全相同，病害不变
                                                if(diseaseInstanceVo.getStatus()==0){
                                                    diseaseInstanceVo.setModifyTime(modifyTime);
                                                    appOfflineDao.insertOrUpdateDiseaseInstance(diseaseInstanceVo);
                                                }
                                                list.removeIf(i -> i.getId().equals(diseaseInstanceVo.getId()));
                                            } else {//旧病害与新病害有所不同,旧病害进入record表，新病害修改
                                                /**--旧病害--*/
                                                DiseaseInstanceRecord record = new DiseaseInstanceRecord();
                                                BeanUtils.copyProperties(toDeleteMap.get(diseaseInstanceVo.getId()), record);
                                                record.setTdiId(diseaseInstanceVo.getId());
                                                record.setModifyTime(modifyTime);
                                                appOfflineDao.recordDiseaseInstance(record);
                                                list.removeIf(i -> i.getId().equals(diseaseInstanceVo.getId()));
                                                /**--新病害--*/
                                                diseaseInstanceVo.setModifyTime(modifyTime);
                                                diseaseInstanceVo.setTargetId(otherId);
//                                                    if (diseaseInstanceVo.getDegree() == null || "".equals(diseaseInstanceVo.getDegree())) {
//                                                        if (diseaseInstanceVo.getHeightDifference() != null && 0 != diseaseInstanceVo.getHeightDifference()) {
//                                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference() + "");
//                                                        }
//                                                        if (diseaseInstanceVo.getNumber() != null && 0 != diseaseInstanceVo.getNumber()) {
//                                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber() + "");
//                                                        }
//                                                    }
                                                appOfflineDao.insertOrUpdateDiseaseInstance(diseaseInstanceVo);
                                            }
                                        }

                                    } else {
                                        diseaseInstanceVo.setId(null);
                                        diseaseInstanceVo.setTargetId(otherId);
                                        diseaseInstanceVo.setModifyTime(modifyTime);
//                                            if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                                if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                                    diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                                }
//                                                if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                                    diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                                }
//                                            }
                                        appOfflineDao.insertNewDiseaseInstance(diseaseInstanceVo);
                                    }
                                }
                                Integer diseaseId = diseaseInstanceVo.getId();
                                //病害附件
                                for(int i=0;i<diseaseInstanceVo.getAttachments().size();i++){
                                    AttachmentVo attachmentVo=diseaseInstanceVo.getAttachments().get(i);
                                    if(attachmentVo.isIdReal()){
                                        continue;
                                    }
                                    Attachment attachment=new Attachment();
                                    BeanUtils.copyProperties(attachmentVo,attachment);
                                    attachment.setTargetId(diseaseId);
                                    if(i==diseaseInstanceVo.getAttachments().size()-1){
                                        attachment.setStatus(2);
                                    }else{
                                        attachment.setStatus(1);
                                    }
                                    String savePath=saveAttachment(imagePath,imageFilePath,attachmentVo);
                                    if(savePath!=null){
                                        attachment.setPath(savePath);
                                    }
                                    if(attachmentVo.isIdReal()){
                                        appOfflineDao.insertOrUpdateAttachment(attachment);
                                    }else{
                                        attachment.setId(null);
                                        appOfflineDao.insertNewAttachment(attachment);
                                    }
                                }
                            }
                            for(DiseaseInstanceVo diseaseInstanceVo:list){//需要设置status为0的病害
                                diseaseInstanceVo.setModifyTime(modifyTime);
                                diseaseInstanceVo.setStatus(0);
//                                    if(diseaseInstanceVo.getDegree()==null||"".equals(diseaseInstanceVo.getDegree())){
//                                        if(diseaseInstanceVo.getHeightDifference()!=null&&0!=diseaseInstanceVo.getHeightDifference()){
//                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getHeightDifference()+"");
//                                        }
//                                        if(diseaseInstanceVo.getNumber()!=null&&0!=diseaseInstanceVo.getNumber()){
//                                            diseaseInstanceVo.setDegree(diseaseInstanceVo.getNumber()+"");
//                                        }
//                                    }
                                appOfflineDao.insertOrUpdateDiseaseInstance(diseaseInstanceVo);
                            }
                        }
                    }
                }
            }
        }


//        //projectStructure 没有用（不会变化）
//        for(StructureInfoVo structureInfoVo:structureOfProject){
//            appOfflineDao.insertOrUpdateStructureInfo(structureInfoVo);
//        }

        //更新计划下各个桥病害实例表排序
        MonitorPlanStructureRel condition=new MonitorPlanStructureRel();
        condition.setMonitorPlanId(Integer.parseInt(fileName));
        for(MonitorPlanStructureRel rel:monitorPlanStructureRelDao.query(condition)){
            appOfflineExpDao.updateDiseaseInstanceSortByModifyTime(rel.getId());
        }


        //根据线路bridgeRoadList从数据库读取所有有效桥面系实例
        List<BridgeRoadVo> bridgeRoadListaaa = appOfflineDao.findBridgeRoadByPlanId(Integer.parseInt(fileName));
        for(BridgeRoadVo bridgeRoadVo:bridgeRoadListaaa){
            if(bridgeRoadVo.getStatus()==null||bridgeRoadVo.getStatus()!=0) {
                //需要设置bridgeTypeId,线路bridgeRoadList字段
                appCompWeightCalcService.setBridgeType(bridgeRoadVo.getBridgeTypeId());
                ArrayList<BridgeDeckComponent> toSet = new ArrayList<>();
                ArrayList<BridgeDeckComponent> toCal = new ArrayList<>();
                objectIdSet.clear();
                for (BridgeSpanVo bridgeSpanVo : appZipService.findBridgeSpan(bridgeRoadVo.getId(),bridgeRoadVo.getId(),true)) {
                    if (bridgeSpanVo.getStatus() == null || bridgeSpanVo.getStatus() != 0) {
                        for (BridgeDeckSystemVo bridgeDeckSystemVo : appZipService.findBridgeDeckSystem(bridgeSpanVo.getId(),bridgeSpanVo.getId(),true)) {
                            if (bridgeDeckSystemVo.getStatus() == null || bridgeDeckSystemVo.getStatus() != 0)
                                for (BridgeDeckComponentVo componentVo : appZipService.findBridgeDeckComponent(bridgeDeckSystemVo.getId(),bridgeDeckSystemVo.getId(),true)) {
                                    if (componentVo.getStatus() == null || componentVo.getStatus() != 0) {
                                        if (!objectIdSet.contains(componentVo.getComponentId())) {
                                            toCal.add(componentVo);
                                            objectIdSet.add(componentVo.getComponentId());
                                        }
                                        toSet.add(componentVo);
                                    }
                                }
                        }
                        //上部
                        ArrayList<BridgeSupcomponent> supcomponents = new ArrayList<>();
                        for (BridgeSupstructure bridgeSupstructure : appZipService.findBridgeSupstructure(bridgeSpanVo.getId(),bridgeSpanVo.getId(),true)) {
                            if (bridgeSupstructure.getStatus() == null || bridgeSupstructure.getStatus() != 0)
                                for (BridgeSupcomponent componentVo : appZipService.findBridgeSupcomponent(bridgeSupstructure.getId(),bridgeSupstructure.getId(),true)) {
                                    if (componentVo.getStatus() == null || componentVo.getStatus() != 0) {
                                        supcomponents.add(componentVo);
                                    }
                                }
                        }
                        //计算上部初始权重并更新数据库
                        Map<Integer, Double> calSupResult = appCompWeightCalcService.getBridgeSupCompWeightList(supcomponents,false);
                        Map<Integer, Double> initialSupResult = appCompWeightCalcService.getBridgeSupCompWeightList(supcomponents,true);
                        for (BridgeSupcomponent component : supcomponents) {
                            if(component!=null) {
                                component.setInitialWeight(initialSupResult.get(component.getId()));
                                component.setActualWeight(calSupResult.get(component.getId()));
                                appOfflineDao.insertOrUpdateBridgeSupcomponent(component);
                            }
                        }
                        //下部
                        ArrayList<BridgeSubcomponent> subcomponents = new ArrayList<>();
                        for (BridgeSubstructure bridgeSubstructure : appZipService.findBridgeSubstructure(bridgeSpanVo.getId(),bridgeSpanVo.getId(),true)) {
                            if (bridgeSubstructure.getStatus() == null || bridgeSubstructure.getStatus() != 0)
                                for (BridgeSubcomponent componentVo : appZipService.findBridgeSubcomponent(bridgeSubstructure.getId(),bridgeSubstructure.getId(),null,true)) {
                                    if (componentVo.getStatus() == null || componentVo.getStatus() != 0) {
                                        subcomponents.add(componentVo);
                                    }
                                }
                        }
                        //计算下部初始权重并更新数据库
                        Map<Integer, Double> calSubResult = appCompWeightCalcService.getBridgeSubCompWeightList(subcomponents,false);
                        Map<Integer, Double> initialSubResult = appCompWeightCalcService.getBridgeSubCompWeightList(subcomponents,true);
                        for (BridgeSubcomponent component : subcomponents) {
                            if (component!=null) {
                                component.setInitialWeight(initialSubResult.get(component.getId()));
                                component.setActualWeight(calSubResult.get(component.getId()));
                                appOfflineDao.insertOrUpdateBridgeSubcomponent(component);
                            }
                        }
                    }

                }
                //计算桥面初始权重并更新数据库
                Map<Integer, Double> calResult = appCompWeightCalcService.getBridgeDeckCompWeightList(toCal,false);
                Map<Integer, Double> initialResult = appCompWeightCalcService.getBridgeDeckCompWeightList(toCal,true);
                for (BridgeDeckComponent component : toSet) {
                    if(component!=null) {
                        component.setInitialWeight(initialResult.get(component.getComponentId()));
                        component.setActualWeight(calResult.get(component.getComponentId()));
                        appOfflineDao.insertOrUpdateBridgeDeckComponent(component);
                    }
                }
            }
        }
//        bciEvaluationService.calculateDeduct(Integer.parseInt(fileName));
        //删除文件
        finishDeleteFile(filePath,filePath+"/"+fileName+".zip");
        //将原有下载文件覆盖
        try {
            appZipService.getPlanZip(Integer.parseInt(fileName), path + "/tmp/offlineFile",true);
        }catch (Exception e){
            StringBuilder builder=new StringBuilder();
            for(StackTraceElement element:e.getStackTrace()){
                builder.append(element).append("\n");
            }
            logger.error(builder.toString());
        }
        //更新检测完成时间
        MonitorPlan plan=monitorPlanDao.findById(Integer.parseInt(fileName));
        plan.setUpload(1);
        plan.setStatus(2);
//        plan.setEndTime(modifyTime);
        monitorPlanDao.update(plan);
        releaseLock("unzip:"+fileName);
        return unableRenewRel;
    }

    public void finishDeleteFile(String filePath,String zipPath){
        //删除文件
        File uploadZipFile=new File(filePath);
        File uploadZip=new File(zipPath);
        if(uploadZipFile.exists()){
            if(uploadZip.exists()){
                if(!uploadZip.delete()){
                    logger.error(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date())+"删除压缩文件失败");
                }
            }
            new UnZipAnRar().deleteFile(uploadZipFile);
        }
    }

    //imagePath是原来的路径,imageFilePath是存储路径
    public String saveAttachment(String imagePath,String imageFilePath,AttachmentVo attachment){
        //图片 图片需要保存并修改path
        if (attachment != null) {
            if (attachment.getStatus()==null||attachment.getStatus()!=(byte)0) {//若status不等于1，则为回收状态，不需要再进行附件的复制
//                try {
//                    FileWriter fw = new FileWriter(imageFilePath + "/" + attachment.getName());
//                    FileReader fr = new FileReader(imagePath + "/" + attachment.getName());
//                    // 读取和写入信息
//                    int len = -1;
//                    while ((len = fr.read()) != -1) {
//                        fw.write(len);
//                    }
//                    // 关闭流
//                    fr.close();
//                    fw.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return imageFilePath + "/" + attachment.getName();
                String imgUrl=imagePath+"/"+attachment.getName();
                File readFile=new File(imgUrl);
                String ext;
                if(attachment.getName().contains(".")){
                    ext = attachment.getName().substring(attachment.getName().lastIndexOf("."));
                }else{
                    ext = "";
                }
                String writeUrl=imageFilePath+"/"+ UUIDUtils.getUUID()+ext;
                int lastDirPosition=writeUrl.lastIndexOf('\\')!=-1?writeUrl.lastIndexOf('\\'):writeUrl.lastIndexOf('/');
                new File(writeUrl.substring(0,lastDirPosition)).mkdirs();
                File writeFile=new File(writeUrl);
                InputStream is = null;
                OutputStream os = null;
                BufferedInputStream bis = null;
                BufferedOutputStream bos = null;
                try{
                    is = new FileInputStream(readFile);
                    os = new FileOutputStream(writeFile);
                    // 创建缓冲流
                    bis = new BufferedInputStream(is);
                    bos = new BufferedOutputStream(os);
                    byte[] bytes=new byte[1024];
                    int content = 0;
                    while ((content = bis.read(bytes))!= -1) {
                        // 使用缓冲流写数据
                        bos.write(bytes,0,content);
                        // 刷新
                        bos.flush();
                    }
                } catch (IOException e){
                    StringBuilder builder=new StringBuilder();
                    for(StackTraceElement element:e.getStackTrace()){
                        builder.append(element).append("\n");
                    }
                    logger.error(builder.toString());
                } finally {
                    try {
                        if(is!=null) {
                            is.close();
                        }
                        if (os!=null) {
                            os.close();
                        }
                        if(bis!=null){
                            bis.close();
                        }
                        if(bos!=null){
                            bos.close();
                        }
                    }catch (IOException e){
                        StringBuilder builder=new StringBuilder();
                        for(StackTraceElement element:e.getStackTrace()){
                            builder.append(element).append("\n");
                        }
                        logger.error(builder.toString());
                    }
                }
                return writeUrl.replace(path,"/bridge/static");
            }
        }
        return null;
    }


    public void UnzipFile(String filePath,String outPath){
        try {
            ZipFileUnCompressingUtils.unzip(filePath, outPath);
        }catch (Exception e){
            StringBuilder builder=new StringBuilder();
            for(StackTraceElement element:e.getStackTrace()){
                builder.append(element).append("\n");
            }
            logger.error(builder.toString());
        }
    }

    private <T> T readToObject(String filePath,Class<T> outputClass){
        StringBuffer buffer = new StringBuffer();
        BufferedReader br=null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String line="";
            while ((line=br.readLine())!=null){
                buffer.append(line);
            }

        }catch (IOException e){
            StringBuilder builder=new StringBuilder();
            for(StackTraceElement element:e.getStackTrace()){
                builder.append(element).append("\n");
            }
            logger.error(builder.toString());
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
            }catch (IOException e){
                StringBuilder builder=new StringBuilder();
                for(StackTraceElement element:e.getStackTrace()){
                    builder.append(element).append("\n");
                }
                logger.error(builder.toString());
            }
        }
        return JSON.parseObject(buffer.toString(),outputClass);
    }

    private <T> List<T> readToList(String filePath, Class<T> outputClass){
        StringBuffer buffer = new StringBuffer();
        BufferedReader br=null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String line="";
            while ((line=br.readLine())!=null){
                buffer.append(line);
            }
        }catch (IOException e){
            StringBuilder builder=new StringBuilder();
            for(StackTraceElement element:e.getStackTrace()){
                builder.append(element).append("\n");
            }
            logger.error(builder.toString());
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
            }catch (IOException e){
                StringBuilder builder=new StringBuilder();
                for(StackTraceElement element:e.getStackTrace()){
                    builder.append(element).append("\n");
                }
                logger.error(builder.toString());
            }
        }
        ArrayList<T> result=new ArrayList<>();
        List<T> readList=JSONArray.parseArray(buffer.toString(),outputClass);
        if (readList==null||readList.size()<=0){

        }else{
            result.addAll(readList);
        }
        return result;
    }

//    public void copyAttachments(List<Attachment> attachmentList,String copyPath){
//        for(Attachment attachment:attachmentList){
//            try{
//                new File(copyPath+"/attachment").mkdirs();
//                FileWriter fw = new FileWriter(copyPath+"/attachment/"+attachment.getName());
//                FileReader fr = new FileReader(attachment.getPath());
//                // 读取和写入信息
//                int len = -1;
//                while ((len = fr.read()) != -1) {
//                    fw.write(len);
//                }
//                // 关闭流
//                fr.close();
//                fw.close();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//    }

    public boolean isListNotEmpty(List list){
        if(list!=null&&list.size()>0){
            return true;
        }
        return false;
    }

    public boolean isEquals(DiseaseInstance a,DiseaseInstance b){
        if(!isObjectEquals(a.getDiseaseId(),b.getDiseaseId())){
            return false;
        }
        if(!isObjectEquals(a.getLength(),b.getLength())){
            return false;
        }
        if(!isObjectEquals(a.getWidth(),b.getWidth())){
            return false;
        }
        if(!isObjectEquals(a.getDepth(),b.getDepth())){
            return false;
        }
        if(!isObjectEquals(a.getSeamLength(),b.getSeamLength())){
            return false;
        }
        if(!isObjectEquals(a.getSeamWidth(),b.getSeamWidth())){
            return false;
        }
        if(!isObjectEquals(a.getAngle(),b.getAngle())){
            return false;
        }
        if(!isObjectEquals(a.getDegree(),b.getDegree())){
            return false;
        }
        if(!isObjectEquals(a.getXAxis(),b.getXAxis())){
            return false;
        }
        if(!isObjectEquals(a.getYAxis(),b.getYAxis())){
            return false;
        }
        if(!isObjectEquals(a.getRemark(),b.getRemark())){
            return false;
        }
        if(!isObjectEquals(a.getHeightDifference(),b.getHeightDifference())){
            return false;
        }
        if(!isObjectEquals(a.getNumber(),b.getNumber())){
            return false;
        }
        if(!isObjectEquals(a.getDeflectionValue(),b.getDeflectionValue())){
            return false;
        }
        if(!isObjectEquals(a.getTrend(),b.getTrend())){
            return false;
        }
        return true;
    }

    public boolean isObjectEquals(Object a,Object b){
        if(a==null&&b==null){
            return true;
        }
        if(a==null||b==null){
            return false;
        }
        return a.equals(b);
    }

    public boolean getLock(String lockId) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockId, "lock");
        return success != null && success;
    }

    public long getExpire(String lockId){
        return redisTemplate.getExpire(lockId);
    }

    public void releaseLock(String lockId) {
        redisTemplate.delete(lockId);
    }

}
