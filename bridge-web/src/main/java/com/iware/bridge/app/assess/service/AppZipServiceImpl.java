package com.iware.bridge.app.assess.service;

import com.alibaba.fastjson.JSONArray;
import com.iware.bridge.app.assess.dao.AppOfflineDao;
import com.iware.bridge.app.assess.vo.offline.offlineData.*;
import com.iware.bridge.model.dao.evaluation.*;
import com.iware.bridge.model.entity.evaluation.*;
import com.iware.common.exception.BusinessException;
import com.iware.common.utils.InZipUntil;
import com.iware.common.utils.UnZipAnRar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class AppZipServiceImpl implements AppZipService{

    @Value("${file.upload-path}")
    private String path;

    @Autowired
    private MonitorPlanStructureRelDao monitorPlanStructureRelDao;
    @Autowired
    private MonitorPlanDao monitorPlanDao;
    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private BridgeRoadDao bridgeRoadDao;
    @Autowired
    private BridgeSpanDao bridgeSpanDao;
    @Autowired
    private AppOfflineDao appOfflineDao;
    @Autowired
    private BridgeDeckSystemDao bridgeDeckSystemDao;
    @Autowired
    private BridgeDeckComponentDao bridgeDeckComponentDao;
    @Autowired
    private BridgeSubstructureDao bridgeSubstructureDao;
    @Autowired
    private BridgeSubcomponentDao bridgeSubcomponentDao;
    @Autowired
    private BridgeSupstructureDao bridgeSupstructureDao;
    @Autowired
    private PierAbutmentDao pierAbutmentDao;
    @Autowired
    private BridgeSupcomponentDao bridgeSupcomponentDao;
    @Autowired
    private BridgeOtherStructureDao bridgeOtherStructureDao;
    @Autowired
    private DiseaseInstanceDao diseaseInstanceDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 根据计划id下载整个监测计划zip,返回压缩文件path
     * @author linbinxiang
     * @param planId 计划id
     */
    @Override
    public String getPlanZip(int planId,String path,boolean isRenew) {
        if (!isRenew){
            Boolean flag = getLock("unzip:" + planId);
            if (!flag) {
                throw new BusinessException("数据更新中，请稍后");
            } else {
                redisTemplate.expire("unzip:"+planId,(long)1*60, TimeUnit.SECONDS);
            }
        }
        if(path==null || path.equals("")) {
            releaseLock("unzip:"+planId);
            throw new BusinessException("请设置文件路径");
        }
        List<MonitorPlanStructureRelVo> monitorPlanStructureRelList=findMonitorPlanStructureRel(planId);   //使用计划查出桥的id,一计划可能对应多桥
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
        List<PierAbutmentVo> pierAbutmentList=new ArrayList<>();
        String fileZipPath=path+"/"+planId;
        //创建以计划id命名的目录
        File oldDir=new File(fileZipPath);
        //删除压缩文件并重新从数据库获取
        File oldZip=new File(path+"/"+planId+".zip");
        if(oldDir.exists()){
                new UnZipAnRar().deleteFile(oldDir);
                if(oldZip.exists()){
                    oldZip.delete();
                }
        }
        File dir=new File(fileZipPath);
        dir.mkdirs();
        //写入计划结构物关联表
        if(monitorPlanStructureRelList.size()==0){
            List<MonitorPlanStructureRelVo> emptyList=new ArrayList<>();
            emptyList.add(getEmptyMonitorPlanStructureRel());
            writeToFile(fileZipPath+"/monitorPlanStructureRel.json",emptyList);
        }else {
            writeToFile(fileZipPath + "/monitorPlanStructureRel.json", monitorPlanStructureRelList);
        }
        //根据id查找计划所属项目
//        Integer projectId=monitorPlanDao.findById(planId).getProjectInfoId();
        //根据项目id获取所有桥梁
//        List<StructureInfoVo> structures = appOfflineDao.selectStructureInfoByProjectId(projectId);
//        for(StructureInfoVo structureInfoVo:structures){
//            structureInfoVo.setIdReal(true);
//        }
        //写入所有桥梁
//        if(structures.size()==0){
//            structures.add(new StructureInfoVo());
//        }
//        writeToFile(fileZipPath+"/structuresOfProject.json",structures);
        //项目桥梁
        List<StructureVo> structuresOfPlan = appOfflineDao.selectStructureByPlanId(planId);
        if(structuresOfPlan.size()==0){
            structuresOfPlan.add(new StructureVo());
        }
        writeToFile(fileZipPath+"/structuresOfPlan.json",structuresOfPlan);
        //每个桥梁一个文件夹
        for(MonitorPlanStructureRel m:monitorPlanStructureRelList) {
            ArrayList<AttachmentVo> toCopyAttachments=new ArrayList<>();
            //通过id获取桥梁详细信息
            int structureId=m.getStructureInfoId();
            StructureInfoVo structureInfo=appOfflineDao.selectStructureInfoById(structureId);
            if(structureInfo!=null) {
                structureInfo.setIdReal(true);
            }
            //新建以桥梁名称命名的文件夹
            String bridgeFileName=m.getStructureName()+"_"+m.getStructureInfoId();
            File file=new File(fileZipPath+"/"+bridgeFileName);
            file.mkdirs();
            //获取线路
            bridgeRoadList.addAll(findBridgeRoad(m.getId(),m.getStructureInfoId()));
            //根据线路获取桥跨、给线路添加图片信息
            for (BridgeRoadVo r : bridgeRoadList) {
                for(Attachment a:findAttachmentByTypeAndTargetId(1,-1,r.getFalseKey())){
                    AttachmentVo avo=new AttachmentVo();
                    BeanUtils.copyProperties(a,avo);
                    avo.setIdReal(true);
                    if(a.getStatus()!=0) {
                        if(!r.isIdReal()){
                            avo.setTargetId(r.getId());
                            avo.setId(null);
                            appOfflineDao.insertNewAttachment(avo);
                        }
                        r.addAttachment(avo);
                        toCopyAttachments.add(avo);
                    }
                }
                bridgeSpanList.addAll(findBridgeSpan(r.getId(),r.getFalseKey(),r.isIdReal()));
            }
            //根据桥跨获取桥面系、上下部实例
            for (BridgeSpanVo s : bridgeSpanList) {
                //给桥跨添加图片信息
                for(Attachment a:findAttachmentByTypeAndTargetId(2,-1,s.getFalseKey())){
                    AttachmentVo avo=new AttachmentVo();
                    BeanUtils.copyProperties(a,avo);
                    avo.setIdReal(true);
                    if(a.getStatus()!=0) {
                        if(!s.isIdReal()){
                            avo.setTargetId(s.getId());
                            avo.setId(null);
                            appOfflineDao.insertNewAttachment(avo);
                        }
                        s.addAttachment(avo);
                        toCopyAttachments.add(avo);
                    }
                }
                //桥面系结构
                List<BridgeDeckSystemVo> deckList = findBridgeDeckSystem(s.getId(),s.getFalseKey(),s.isIdReal());
                bridgeDeckSystemList.addAll(deckList);
                for (BridgeDeckSystemVo d : deckList) {
                    //给桥面系添加图片信息
                    for(Attachment a:findAttachmentByTypeAndTargetId(3,1,d.getFalseKey())){
                        AttachmentVo avo=new AttachmentVo();
                        BeanUtils.copyProperties(a,avo);
                        avo.setIdReal(true);
                        if(a.getStatus()!=0) {
                            if(!d.isIdReal()){
                                avo.setTargetId(d.getId());
                                avo.setId(null);
                                appOfflineDao.insertNewAttachment(avo);
                            }
                            d.addAttachment(avo);
                            toCopyAttachments.add(avo);
                        }
                    }
                    //添加构件
                    bridgeDeckComponentList.addAll(findBridgeDeckComponent(d.getId(),d.getFalseKey(),d.isIdReal()));
                }
                //下部结构
                List<BridgeSubstructureVo> subList = findBridgeSubstructure(s.getId(),s.getFalseKey(),s.isIdReal());
                bridgeSubstructureList.addAll(subList);
                for (BridgeSubstructureVo sub : subList) {
                    //给下部结构添加图片信息
                    for(Attachment a:findAttachmentByTypeAndTargetId(3,3,sub.getFalseKey())){
                        AttachmentVo avo=new AttachmentVo();
                        BeanUtils.copyProperties(a,avo);
                        avo.setIdReal(true);
                        if(a.getStatus()!=0) {
                            if(!sub.isIdReal()){
                                avo.setTargetId(sub.getId());
                                avo.setId(null);
                                appOfflineDao.insertNewAttachment(avo);
                            }
                            sub.addAttachment(avo);
                            toCopyAttachments.add(avo);
                        }
                    }
                    //添加桥墩
                    pierAbutmentList.addAll(findPierAbutment(sub.getId(),sub.getFalseKey(),sub.isIdReal()));
                    HashMap<Integer,Integer> oldToNew=new HashMap<>();
                    for(PierAbutmentVo pier:pierAbutmentList){
                        oldToNew.put(pier.getFalseKey(),pier.getId());
                    }
                    //添加构件
                    bridgeSubcomponentList.addAll(findBridgeSubcomponent(sub.getId(),
                            sub.getFalseKey(),oldToNew,sub.isIdReal()));
                }
                //上部结构
                List<BridgeSupstructureVo> supList = findBridgeSupstructure(s.getId(),s.getFalseKey(),s.isIdReal());
                bridgeSupstructureList.addAll(supList);
                for (BridgeSupstructureVo sup : supList) {
                    //给上部结构添加图片信息
                    for(Attachment a:findAttachmentByTypeAndTargetId(3,2,sup.getFalseKey())){
                        AttachmentVo avo=new AttachmentVo();
                        BeanUtils.copyProperties(a,avo);
                        avo.setIdReal(true);
                        if(a.getStatus()!=0) {
                            if(!sup.isIdReal()){
                                avo.setTargetId(sup.getId());
                                avo.setId(null);
                                appOfflineDao.insertNewAttachment(avo);
                            }
                            sup.addAttachment(avo);
                            toCopyAttachments.add(avo);
                        }
                    }
                    //添加构件
                    bridgeSupcomponentList.addAll(findBridgeSupcomponent(sup.getId(),sup.getFalseKey(),sup.isIdReal()));
                }
                //其他结构
                List<BridgeOtherStructureVo> otherList=findBridgeOtherStructure(s.getId(),s.getFalseKey(),s.isIdReal());
                for(BridgeOtherStructureVo other:otherList){
                    for(Attachment a:findAttachmentByTypeAndTargetId(3,4,other.getFalseKey())){
                        AttachmentVo avo=new AttachmentVo();
                        BeanUtils.copyProperties(a,avo);
                        avo.setIdReal(true);
                        if(a.getStatus()!=0) {
                            if(!other.isIdReal()){
                                avo.setTargetId(other.getId());
                                avo.setId(null);
                                appOfflineDao.insertNewAttachment(avo);
                            }
                            other.addAttachment(avo);
                            toCopyAttachments.add(avo);
                        }
                    }
                    if(other.isIdReal()) {
                        diseaseInstanceList.addAll(findDiseaseInstance(other.getId(),(byte) 4,other.isIdReal()));
                    }
                }
                bridgeOtherStructureList.addAll(otherList);
            }
            //病害实例
            //桥面
            for (BridgeDeckComponentVo deck : bridgeDeckComponentList) {
                //给桥面构件添加图片信息
                for(Attachment a:findAttachmentByTypeAndTargetId(4,1,deck.getFalseKey())){
                    AttachmentVo avo=new AttachmentVo();
                    BeanUtils.copyProperties(a,avo);
                    avo.setIdReal(true);
                    if(a.getStatus()!=0) {
                        if(!deck.isIdReal()){
                            avo.setTargetId(deck.getId());
                            avo.setId(null);
                            appOfflineDao.insertNewAttachment(avo);
                        }
                        deck.addAttachment(avo);
                        toCopyAttachments.add(avo);
                    }
                }
                //添加病害实例
                if (deck.isIdReal()) {
                    diseaseInstanceList.addAll(findDiseaseInstance(deck.getId(), (byte) 1, deck.isIdReal()));
                }
            }
            //下部
            for (BridgeSubcomponentVo sub : bridgeSubcomponentList) {
                //给下部构件添加图片信息
                for(Attachment a:findAttachmentByTypeAndTargetId(4,3,sub.getFalseKey())){
                    AttachmentVo avo=new AttachmentVo();
                    BeanUtils.copyProperties(a,avo);
                    avo.setIdReal(true);
                    if(a.getStatus()!=0) {
                        if(!sub.isIdReal()){
                            avo.setTargetId(sub.getId());
                            avo.setId(null);
                            appOfflineDao.insertNewAttachment(avo);
                        }
                        sub.addAttachment(avo);
                        toCopyAttachments.add(avo);
                    }
                }
                //添加病害实例
                if (sub.isIdReal()) {
                    diseaseInstanceList.addAll(findDiseaseInstance(sub.getId(), (byte) 3, sub.isIdReal()));
                }
            }
            //上部
            for (BridgeSupcomponentVo sup : bridgeSupcomponentList) {
                //给上部构件添加图片信息
                for(Attachment a:findAttachmentByTypeAndTargetId(4,2,sup.getFalseKey())){
                    AttachmentVo avo=new AttachmentVo();
                    BeanUtils.copyProperties(a,avo);
                    avo.setIdReal(true);
                    if(a.getStatus()!=0) {
                        if(!sup.isIdReal()){
                            avo.setTargetId(sup.getId());
                            avo.setId(null);
                            appOfflineDao.insertNewAttachment(avo);
                        }
                        sup.addAttachment(avo);
                        toCopyAttachments.add(avo);
                    }
                }
                //添加病害实例
                if (sup.isIdReal()) {
                    diseaseInstanceList.addAll(findDiseaseInstance(sup.getId(), (byte) 2, sup.isIdReal()));
                }
            }
            //给病害实例添加图片信息
            for(DiseaseInstanceVo disease:diseaseInstanceList){
                for(Attachment a:findAttachmentByTypeAndTargetId(5,-1,disease.getId())){
                    AttachmentVo avo=new AttachmentVo();
                    BeanUtils.copyProperties(a,avo);
                    avo.setIdReal(true);
                    if(a.getStatus()!=0) {
                        disease.addAttachment(avo);
                        toCopyAttachments.add(avo);
                    }
                }
            }
            //写入照片
            copyAttachments(toCopyAttachments,fileZipPath);
            //写入文件
            writeToFile(fileZipPath+"/"+bridgeFileName+"/structureInfo.json",structureInfo);
            if(bridgeRoadList.size()==0){
                bridgeRoadList.add(getEmptyBridgeRoadVo());
            }else{
                for(BridgeRoadVo a:bridgeRoadList){
                    a.setIdReal(true);
                }
            }
            writeToFile(fileZipPath+"/"+bridgeFileName+"/bridgeRoad.json",bridgeRoadList);
            if(bridgeSpanList.size()==0){
                bridgeSpanList.add(getEmptyBridgeSpanVo());
            }else{
                for(BridgeSpanVo a:bridgeSpanList){
                    a.setIdReal(true);
                }
            }
            writeToFile(fileZipPath+"/"+bridgeFileName+"/bridgeSpan.json",bridgeSpanList);
            if(bridgeDeckSystemList.size()==0){
                bridgeDeckSystemList.add(getEmptyBridgeDeckSystemVo());
            }else{
                for(BridgeDeckSystemVo a:bridgeDeckSystemList){
                    a.setIdReal(true);
                }
            }
            writeToFile(fileZipPath+"/"+bridgeFileName+"/bridgeDeckSystem.json",bridgeDeckSystemList);
            if(bridgeDeckComponentList.size()==0){
                bridgeDeckComponentList.add(getEmptyBridgeDeckComponentVo());
            }else{
                for(BridgeDeckComponentVo a:bridgeDeckComponentList){
                    a.setIdReal(true);
                }
            }
            writeToFile(fileZipPath+"/"+bridgeFileName+"/bridgeDeckComponent.json",bridgeDeckComponentList);
            if(bridgeSubstructureList.size()==0){
                bridgeSubstructureList.add(getEmptyBridgeSubstructureVo());
            }else{
                for(BridgeSubstructureVo a:bridgeSubstructureList){
                    a.setIdReal(true);
                }
            }
            writeToFile(fileZipPath+"/"+bridgeFileName+"/bridgeSubstructure.json",bridgeSubstructureList);
            if(bridgeSubcomponentList.size()==0){
                bridgeSubcomponentList.add(getEmptyBridgeSubcomponentVo());
            }else{
                for(BridgeSubcomponentVo a:bridgeSubcomponentList){
                    a.setIdReal(true);
                }
            }
            writeToFile(fileZipPath+"/"+bridgeFileName+"/bridgeSubcomponent.json",bridgeSubcomponentList);
            if(bridgeSupstructureList.size()==0){
                bridgeSupstructureList.add(getEmptyBridgeSupstructureVo());
            }else{
                for(BridgeSupstructureVo a:bridgeSupstructureList){
                    a.setIdReal(true);
                }
            }
            writeToFile(fileZipPath+"/"+bridgeFileName+"/bridgeSupstructure.json",bridgeSupstructureList);
            if(bridgeSupcomponentList.size()==0){
                bridgeSupcomponentList.add(getEmptyBridgeSupcomponentVo());
            }else{
                for(BridgeSupcomponentVo a:bridgeSupcomponentList){
                    a.setIdReal(true);
                }
            }
            writeToFile(fileZipPath+"/"+bridgeFileName+"/bridgeSupcomponent.json",bridgeSupcomponentList);
            if(bridgeOtherStructureList.size()==0){
                bridgeOtherStructureList.add(getEmptyBridgeOtherStructure());
            }else{
                for(BridgeOtherStructureVo a:bridgeOtherStructureList){
                    a.setIdReal(true);
                }
            }
            writeToFile(fileZipPath+"/"+bridgeFileName+"/bridgeOtherStructure.json",bridgeOtherStructureList);
            if(diseaseInstanceList.size()==0){
                diseaseInstanceList.add(getEmptyDiseaseInstanceVo());
            }
            writeToFile(fileZipPath+"/"+bridgeFileName+"/diseaseInstance.json",diseaseInstanceList);
            if(pierAbutmentList.size()==0){
                PierAbutmentVo p=new PierAbutmentVo();
                p.setId(0);
                p.setBridgeSubstructureId(0);
                p.setCode("");
                p.setIdReal(false);
                pierAbutmentList.add(p);
            }else{
                for(PierAbutmentVo a:pierAbutmentList){
                    a.setIdReal(true);
                }
            }
            writeToFile(fileZipPath+"/"+bridgeFileName+"/pierAbutment.json",pierAbutmentList);
            bridgeRoadList.clear();
            bridgeSpanList.clear();
            bridgeDeckSystemList.clear();
            bridgeDeckComponentList.clear();
            bridgeSubstructureList.clear();
            bridgeSubcomponentList.clear();
            bridgeSupstructureList.clear();
            bridgeSupcomponentList.clear();
            bridgeOtherStructureList.clear();
            diseaseInstanceList.clear();
            pierAbutmentList.clear();
        }
        //压缩
        try {
            new InZipUntil().inZip(path+"/"+planId, new File(path+"/"+planId+".zip"));
        }catch (Exception e){
            e.printStackTrace();
            Logger logger=LoggerFactory.getLogger(AppUnzipServiceImpl.class);
            StringBuilder builder=new StringBuilder();
            for(StackTraceElement element:e.getStackTrace()){
                builder.append(element).append("\n");
            }
            logger.error(builder.toString());
            releaseLock("unzip:"+planId);
        }
        releaseLock("unzip:"+planId);
        return path+"/"+planId+".zip";
    }
    /**
     * 用计划id查找所有项目结构关联表
     * @param planId
     * @return
     */
    @Override
    public List<MonitorPlanStructureRelVo> findMonitorPlanStructureRel(Integer planId){
        MonitorPlanStructureRel conditionObject = new MonitorPlanStructureRel();
        conditionObject.setMonitorPlanId(planId);
        List<MonitorPlanStructureRel> queryResult=monitorPlanStructureRelDao.query(conditionObject);
        queryResult.sort(new Comparator<MonitorPlanStructureRel>() {
            @Override
            public int compare(MonitorPlanStructureRel o1, MonitorPlanStructureRel o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        List<MonitorPlanStructureRelVo> result=new ArrayList<>();
        for(MonitorPlanStructureRel rel:queryResult){
            MonitorPlanStructureRelVo relVo=new MonitorPlanStructureRelVo();
            BeanUtils.copyProperties(rel,relVo);
            relVo.setIdReal(true);
            result.add(relVo);
        }
        return result;
    }
    /**
     * 使用计划结构关联表id获取线路
     * @param planStructureRelId
     * @return
     */
    @Override
    public List<BridgeRoadVo> findBridgeRoad(Integer planStructureRelId,Integer structureInfoId){
        boolean isOldData=false;
        BridgeRoad conditionObject = new BridgeRoad();
        conditionObject.setStatus(1);
        conditionObject.setMonitorPlanStructureRelId(planStructureRelId);
        List<BridgeRoad> bridgeRoads=bridgeRoadDao.query(conditionObject);
        if(bridgeRoads.size()==0){//线路为空,需要根据桥梁id获取最新数据
            List<Integer> relIds=appOfflineDao.selectRelIdByStructureIdWhereInRoadTable(structureInfoId);
            if(relIds.size()!=0) {
                conditionObject.setMonitorPlanStructureRelId(relIds.get(0));
                bridgeRoads = bridgeRoadDao.query(conditionObject);
                isOldData=true;
            }
        }
        bridgeRoads.sort(new Comparator<BridgeRoad>() {
            @Override
            public int compare(BridgeRoad o1, BridgeRoad o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        ArrayList<BridgeRoadVo> result=new ArrayList<>();
        for(BridgeRoad root:bridgeRoads){
            BridgeRoadVo bridgeRoadVo=new BridgeRoadVo();
            BeanUtils.copyProperties(root,bridgeRoadVo);
            bridgeRoadVo.setIdReal(!isOldData);
            Integer falseKey=bridgeRoadVo.getId();
            if(isOldData){
                bridgeRoadVo.setMonitorPlanStructureRelId(planStructureRelId);
                bridgeRoadVo.setId(null);
                appOfflineDao.insertNewBridgeRoad(bridgeRoadVo);
            }
            bridgeRoadVo.setFalseKey(falseKey);
            result.add(bridgeRoadVo);
        }
        result.sort(new Comparator<BridgeRoadVo>() {
            @Override
            public int compare(BridgeRoadVo o1, BridgeRoadVo o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        return result;
    }
    /**
     * 使用线路id获取所有桥跨
     * @param roadId
     * @return
     */
    @Override
    public List<BridgeSpanVo> findBridgeSpan(Integer roadId,Integer roadCode,boolean isIdReal){
        BridgeSpan conditionObject = new BridgeSpan();
        conditionObject.setBridgeRoadId(roadCode);
        conditionObject.setStatus(1);
        List<BridgeSpan> bridgeSpans=bridgeSpanDao.query(conditionObject);
        bridgeSpans.sort(new Comparator<BridgeSpan>() {
            @Override
            public int compare(BridgeSpan o1, BridgeSpan o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        ArrayList<BridgeSpanVo> result=new ArrayList<>();
        for(BridgeSpan root:bridgeSpans){
            BridgeSpanVo bridgeSpanVo=new BridgeSpanVo();
            BeanUtils.copyProperties(root,bridgeSpanVo);
            bridgeSpanVo.setIdReal(isIdReal);
            Integer falseKey=bridgeSpanVo.getId();
            if(!isIdReal){
                bridgeSpanVo.setBridgeRoadId(roadId);
                bridgeSpanVo.setId(null);
                appOfflineDao.insertNewBridgeSpan(bridgeSpanVo);
            }
            bridgeSpanVo.setFalseKey(falseKey);
            result.add(bridgeSpanVo);
        }
        result.sort(new Comparator<BridgeSpan>() {
            @Override
            public int compare(BridgeSpan o1, BridgeSpan o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        return result;
    }
    /**
     * 桥跨id获取桥面系
     * @param spanId
     * @return
     */
    @Override
    public List<BridgeDeckSystemVo> findBridgeDeckSystem(Integer spanId,Integer spanCode,boolean isIdReal){
        BridgeDeckSystem conditionObject = new BridgeDeckSystem();
        conditionObject.setBridgeSpanId(spanCode);
        conditionObject.setStatus(1);
        List<BridgeDeckSystem> bridgeDeckSystems=bridgeDeckSystemDao.query(conditionObject);
        bridgeDeckSystems.sort(new Comparator<BridgeDeckSystem>() {
            @Override
            public int compare(BridgeDeckSystem o1, BridgeDeckSystem o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        ArrayList<BridgeDeckSystemVo> result=new ArrayList<>();
        for(BridgeDeckSystem root:bridgeDeckSystems){
            BridgeDeckSystemVo bridgeDeckSystemVo=new BridgeDeckSystemVo();
            BeanUtils.copyProperties(root,bridgeDeckSystemVo);
            bridgeDeckSystemVo.setIdReal(isIdReal);
            Integer falseKey=bridgeDeckSystemVo.getId();
            if(!isIdReal){
                bridgeDeckSystemVo.setBridgeSpanId(spanId);
                bridgeDeckSystemVo.setId(null);
                appOfflineDao.insertNewBridgeDeckSystem(bridgeDeckSystemVo);
            }
            bridgeDeckSystemVo.setFalseKey(falseKey);
            result.add(bridgeDeckSystemVo);
        }
        result.sort(new Comparator<BridgeDeckSystemVo>() {
            @Override
            public int compare(BridgeDeckSystemVo o1, BridgeDeckSystemVo o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        return result;
    }
    /**
     * 桥面系id获取部件
     * @param deckId
     * @return
     */
    @Override
    public List<BridgeDeckComponentVo> findBridgeDeckComponent(Integer deckId,Integer deckCode,boolean isIdReal){
        BridgeDeckComponent conditionObject = new BridgeDeckComponent();
        conditionObject.setBridgeDeckSystemId(deckCode);
        conditionObject.setStatus(1);
        List<BridgeDeckComponent> bridgeDeckComponents=bridgeDeckComponentDao.query(conditionObject);
        bridgeDeckComponents.sort(new Comparator<BridgeDeckComponent>() {
            @Override
            public int compare(BridgeDeckComponent o1, BridgeDeckComponent o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        ArrayList<BridgeDeckComponentVo> result=new ArrayList<>();
        for(BridgeDeckComponent root:bridgeDeckComponents){
            BridgeDeckComponentVo bridgeDeckComponentVo=new BridgeDeckComponentVo();
            BeanUtils.copyProperties(root,bridgeDeckComponentVo);
            bridgeDeckComponentVo.setIdReal(isIdReal);
            Integer falseKey=bridgeDeckComponentVo.getId();
            if(!isIdReal){
                bridgeDeckComponentVo.setBridgeDeckSystemId(deckId);
                bridgeDeckComponentVo.setId(null);
                bridgeDeckComponentVo.setSerious(0);
                appOfflineDao.insertNewBridgeDeckComponent(bridgeDeckComponentVo);
            }
            bridgeDeckComponentVo.setFalseKey(falseKey);
            result.add(bridgeDeckComponentVo);
        }
        result.sort(new Comparator<BridgeDeckComponentVo>() {
            @Override
            public int compare(BridgeDeckComponentVo o1, BridgeDeckComponentVo o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        return result;
    }
    /**
     * 下部
     * @param spanId
     * @return
     */
    @Override
    public List<BridgeSubstructureVo> findBridgeSubstructure(Integer spanId,Integer spanCode,boolean isIdReal){
        BridgeSubstructure conditionObject = new BridgeSubstructure();
        conditionObject.setBridgeSpanId(spanCode);
        conditionObject.setStatus(1);
        List<BridgeSubstructure> bridgeSubstructures=bridgeSubstructureDao.query(conditionObject);
        bridgeSubstructures.sort(new Comparator<BridgeSubstructure>() {
            @Override
            public int compare(BridgeSubstructure o1, BridgeSubstructure o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        ArrayList<BridgeSubstructureVo> result=new ArrayList<>();
        for(BridgeSubstructure root:bridgeSubstructures){
            BridgeSubstructureVo bridgeSubstructureVo=new BridgeSubstructureVo();
            BeanUtils.copyProperties(root,bridgeSubstructureVo);
            bridgeSubstructureVo.setIdReal(isIdReal);
            Integer falseKey=bridgeSubstructureVo.getId();
            if(!isIdReal){
                bridgeSubstructureVo.setBridgeSpanId(spanId);
                bridgeSubstructureVo.setId(null);
                appOfflineDao.insertNewBridgeSubstructure(bridgeSubstructureVo);
            }
            bridgeSubstructureVo.setFalseKey(falseKey);
            result.add(bridgeSubstructureVo);
        }
        result.sort(new Comparator<BridgeSubstructureVo>() {
            @Override
            public int compare(BridgeSubstructureVo o1, BridgeSubstructureVo o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        return result;
    }
    /**
     * 桥墩，隶属于桥下部结构
     * @param subId
     * @return
     */
    public List<PierAbutmentVo> findPierAbutment(Integer subId,Integer subCode,boolean isIdReal){
        PierAbutment conditionObject = new PierAbutment();
        conditionObject.setBridgeSubstructureId(subCode);
        List<PierAbutment> queryResult=pierAbutmentDao.query(conditionObject);
        queryResult.sort(new Comparator<PierAbutment>() {
            @Override
            public int compare(PierAbutment o1, PierAbutment o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        ArrayList<PierAbutmentVo> result=new ArrayList<>();
        for(PierAbutment root:queryResult){
            PierAbutmentVo pierAbutmentVo=new PierAbutmentVo();
            BeanUtils.copyProperties(root,pierAbutmentVo);
            pierAbutmentVo.setIdReal(isIdReal);
            Integer falseKey=pierAbutmentVo.getId();
            if(!isIdReal){
                pierAbutmentVo.setBridgeSubstructureId(subId);
                pierAbutmentVo.setId(null);
                appOfflineDao.insertNewPierAbutment(pierAbutmentVo);
            }
            pierAbutmentVo.setFalseKey(falseKey);
            result.add(pierAbutmentVo);
        }
        result.sort(new Comparator<PierAbutmentVo>() {
            @Override
            public int compare(PierAbutmentVo o1, PierAbutmentVo o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        return result;
    }
    /**
     * 下部构件
     * @param subId
     * @return
     */
    @Override
    public List<BridgeSubcomponentVo> findBridgeSubcomponent(Integer subId,Integer subCode,
                                                             HashMap<Integer, Integer> oldToNew,boolean isIdReal){
        BridgeSubcomponent conditionObject = new BridgeSubcomponent();
        conditionObject.setBridgeSubstructureId(subCode);
        conditionObject.setStatus(1);
        List<BridgeSubcomponent> bridgeSubcomponents=bridgeSubcomponentDao.query(conditionObject);
        bridgeSubcomponents.sort(new Comparator<BridgeSubcomponent>() {
            @Override
            public int compare(BridgeSubcomponent o1, BridgeSubcomponent o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        ArrayList<BridgeSubcomponentVo> result=new ArrayList<>();
        for(BridgeSubcomponent root:bridgeSubcomponents){
            BridgeSubcomponentVo bridgeSubcomponentVo=new BridgeSubcomponentVo();
            BeanUtils.copyProperties(root,bridgeSubcomponentVo);
            bridgeSubcomponentVo.setIdReal(isIdReal);
            Integer falseKey=bridgeSubcomponentVo.getId();
            if(!isIdReal){
                bridgeSubcomponentVo.setBridgeSubstructureId(subId);
                Integer newPierId=null;
                if(oldToNew.containsKey(bridgeSubcomponentVo.getPierAbutmentId())){
                    newPierId=oldToNew.get(bridgeSubcomponentVo.getPierAbutmentId());
                }
                bridgeSubcomponentVo.setPierAbutmentId(newPierId);
                bridgeSubcomponentVo.setId(null);
                bridgeSubcomponentVo.setSerious(0);
                appOfflineDao.insertNewBridgeSubcomponent(bridgeSubcomponentVo);
            }
            bridgeSubcomponentVo.setFalseKey(falseKey);
            result.add(bridgeSubcomponentVo);
        }
        result.sort(new Comparator<BridgeSubcomponentVo>() {
            @Override
            public int compare(BridgeSubcomponentVo o1, BridgeSubcomponentVo o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        return result;
    }
    /**
     * 上部
     * @param spanId
     * @return
     */
    @Override
    public List<BridgeSupstructureVo> findBridgeSupstructure(Integer spanId,Integer spanCode,boolean isIdReal){
        BridgeSupstructure conditionObject = new BridgeSupstructure();
        conditionObject.setBridgeSpanId(spanCode);
        conditionObject.setStatus(1);
        List<BridgeSupstructure> bridgeSupstructures=bridgeSupstructureDao.query(conditionObject);
        bridgeSupstructures.sort(new Comparator<BridgeSupstructure>() {
            @Override
            public int compare(BridgeSupstructure o1, BridgeSupstructure o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        ArrayList<BridgeSupstructureVo> result=new ArrayList<>();
        for(BridgeSupstructure root:bridgeSupstructures){
            BridgeSupstructureVo bridgeSupstructureVo=new BridgeSupstructureVo();
            BeanUtils.copyProperties(root,bridgeSupstructureVo);
            bridgeSupstructureVo.setIdReal(isIdReal);
            Integer falseKey=bridgeSupstructureVo.getId();
            if(!isIdReal){
                bridgeSupstructureVo.setBridgeSpanId(spanId);
                bridgeSupstructureVo.setId(null);
                appOfflineDao.insertNewBridgeSupstructure(bridgeSupstructureVo);
            }
            bridgeSupstructureVo.setFalseKey(falseKey);
            result.add(bridgeSupstructureVo);
        }
        result.sort(new Comparator<BridgeSupstructureVo>() {
            @Override
            public int compare(BridgeSupstructureVo o1, BridgeSupstructureVo o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        return result;
    }
    /**
     * 上部构件
     * @param supId
     * @return
     */
    @Override
    public List<BridgeSupcomponentVo> findBridgeSupcomponent(Integer supId,Integer supCode,boolean isIdReal){
        BridgeSupcomponent conditionObject = new BridgeSupcomponent();
        conditionObject.setSupstructureId(supCode);
        conditionObject.setStatus(1);
        List<BridgeSupcomponent> bridgeSupcomponents=bridgeSupcomponentDao.query(conditionObject);
        bridgeSupcomponents.sort(new Comparator<BridgeSupcomponent>() {
            @Override
            public int compare(BridgeSupcomponent o1, BridgeSupcomponent o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        ArrayList<BridgeSupcomponentVo> result=new ArrayList<>();
        for(BridgeSupcomponent root:bridgeSupcomponents){
            BridgeSupcomponentVo bridgeSupcomponentVo=new BridgeSupcomponentVo();
            BeanUtils.copyProperties(root,bridgeSupcomponentVo);
            bridgeSupcomponentVo.setIdReal(isIdReal);
            Integer falseKey=bridgeSupcomponentVo.getId();
            if(!isIdReal){
                bridgeSupcomponentVo.setSupstructureId(supId);
                bridgeSupcomponentVo.setId(null);
                bridgeSupcomponentVo.setSerious(0);
                appOfflineDao.insertNewBridgeSupcomponent(bridgeSupcomponentVo);
            }
            bridgeSupcomponentVo.setFalseKey(falseKey);
            result.add(bridgeSupcomponentVo);
        }
        result.sort(new Comparator<BridgeSupcomponentVo>() {
            @Override
            public int compare(BridgeSupcomponentVo o1, BridgeSupcomponentVo o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        return result;
    }
    /**
     * 获取其他结构
     * @param spanId
     * @return
     */
    @Override
    public List<BridgeOtherStructureVo> findBridgeOtherStructure(Integer spanId,Integer spanCode,boolean isIdReal){
        BridgeOtherStructure conditionObject = new BridgeOtherStructure();
        conditionObject.setBridgeSpanId(spanCode);
        conditionObject.setStatus(1);
        List<BridgeOtherStructure> queryResult=bridgeOtherStructureDao.query(conditionObject);
        queryResult.sort(new Comparator<BridgeOtherStructure>() {
            @Override
            public int compare(BridgeOtherStructure o1, BridgeOtherStructure o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        ArrayList<BridgeOtherStructureVo> result=new ArrayList<>();
        for(BridgeOtherStructure root:queryResult){
            BridgeOtherStructureVo bridgeOtherStructureVo=new BridgeOtherStructureVo();
            BeanUtils.copyProperties(root,bridgeOtherStructureVo);
            bridgeOtherStructureVo.setIdReal(isIdReal);
            Integer falseKey=bridgeOtherStructureVo.getId();
            if(!isIdReal){
                bridgeOtherStructureVo.setBridgeSpanId(spanId);
                bridgeOtherStructureVo.setId(null);
                appOfflineDao.insertNewBridgeOtherStructure(bridgeOtherStructureVo);
            }
            bridgeOtherStructureVo.setFalseKey(falseKey);
            result.add(bridgeOtherStructureVo);
        }
        result.sort(new Comparator<BridgeOtherStructureVo>() {
            @Override
            public int compare(BridgeOtherStructureVo o1, BridgeOtherStructureVo o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        return result;
    }

//    List  reverseList(List origin){
//        if(origin==null||origin.isEmpty()){
//            return new ArrayList();
//        }
//        ArrayList result=new ArrayList<>();
//        for(int i=origin.size()-1;i>=0;i--){
//            result.add(origin.get(i));
//        }
//        return result;
//    }

    /**
     * 根据构件id查询病害
     * @param componentId
     * @return
     */
    @Override
    public List<DiseaseInstanceVo> findDiseaseInstance(Integer componentId,Byte componentType,boolean isIdReal){
        DiseaseInstance conditionObject = new DiseaseInstance();
        conditionObject.setTargetId(componentId);
        conditionObject.setPartType(new Integer(componentType));
        List<DiseaseInstance> diseaseInstances=diseaseInstanceDao.query(conditionObject);
        diseaseInstances.sort(new Comparator<DiseaseInstance>() {
            @Override
            public int compare(DiseaseInstance o1, DiseaseInstance o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        ArrayList<DiseaseInstanceVo> result=new ArrayList<>();
        for(DiseaseInstance root:diseaseInstances){
            DiseaseInstanceVo diseaseInstanceVo=new DiseaseInstanceVo();
            BeanUtils.copyProperties(root,diseaseInstanceVo);
            diseaseInstanceVo.setIdReal(isIdReal);
            result.add(diseaseInstanceVo);
        }
        result.sort(new Comparator<DiseaseInstanceVo>() {
            @Override
            public int compare(DiseaseInstanceVo o1, DiseaseInstanceVo o2) {
                Integer i1=o1.getId();
                Integer i2=o2.getId();
                return i1.compareTo(i2);
            }
        });
        return result;
    }
    /**
     * 根据附件类型与部位类型获取对应附件列表
     * @param type
     * @param partType
     * @return
     */
    @Override
    public List<Attachment> findAttachmentByTypeAndTargetId(int type,int partType,int target){
        if(type==1||type==2||type==5) {
            ArrayList<Attachment> result=new ArrayList<>();
            Attachment queryObject = new Attachment();
            queryObject.setType(type);
            queryObject.setStatus(1);
            queryObject.setTargetId(target);
            result.addAll(attachmentDao.query(queryObject));
            queryObject.setStatus(2);
            result.addAll(attachmentDao.query(queryObject));
            return result;
        }
        if(type==3||type==4){
            Attachment queryObject = new Attachment();
            queryObject.setType(type);
            queryObject.setStatus(1);
            queryObject.setPartType(partType);
            queryObject.setTargetId(target);
            return attachmentDao.query(queryObject);
        }
        return new ArrayList<>();
    }
    public void writeToFile(String filePath,Object object){
        File relFile=new File(filePath);
        FileWriter fwRel=null;
        try {
            fwRel =new FileWriter(relFile);
            fwRel.write(JSONArray.toJSONString(object));
            fwRel.flush();
        }catch (Exception e){
            e.printStackTrace();
            Logger logger=LoggerFactory.getLogger(AppUnzipServiceImpl.class);
            StringBuilder builder=new StringBuilder();
            for(StackTraceElement element:e.getStackTrace()){
                builder.append(element).append("\n");
            }
            logger.error(builder.toString());
        }finally {
            try {
                if (fwRel!=null){
                    fwRel.close();
                }
            }catch (IOException e){

            }
        }
    }
    public BridgeRoadVo getEmptyBridgeRoadVo(){
        BridgeRoadVo result=new BridgeRoadVo();
        result.setId(0);
        result.setName("");
        result.setMonitorPlanStructureRelId(0);
        result.setSpanNumber(0);
        result.setStairwayNumber(0);
        result.setStairwaySpanNumber(0);
        result.setCreator("");
        result.setCreateTime(new Date(0));
        result.setStatus(0);
        return result;
    }
    public BridgeSpanVo getEmptyBridgeSpanVo(){
        BridgeSpanVo result=new BridgeSpanVo();
        result.setId(0);
        result.setBridgeRoadId(0);
        result.setBridgeShape(0);
        result.setSpanCode("");
        result.setLength((double)0);
        result.setOuterArcLength((double)0);
        result.setInnerArcLength((double)0);
        result.setConvex(0);
        result.setCreator("");
        result.setCreateTime(new Date(0));
        result.setModifyTime(new Date(0));
        result.setStatus(0);
        return result;
    }
    public BridgeDeckSystemVo getEmptyBridgeDeckSystemVo(){
        BridgeDeckSystemVo result=new BridgeDeckSystemVo();
        result.setId(0);
        result.setBridgeSpanId(0);
        result.setDirection("");
        result.setTiltAngle((double)0);
        result.setSidewalk(0);
        result.setSidewalkWidth((double)0);
        result.setLeftSidewalkWidth((double)0);
        result.setRightSidewalkWidth((double)0);
        result.setLaneWidth((double)0);
        result.setMidleBarrierWidth((double)0);
        result.setLeftRailWidth((double)0);
        result.setStairway("");
        result.setStairwayShadowLength((double)0);
        result.setStairwayLength((double)0);
        result.setStairwayWidth((double)0);
        result.setPlatformNumber(0);
        result.setExpansionJoint(0);
        result.setBridgeHeadBoardLength((double)0);
        result.setMonitorDiagram("");
        result.setCreateTime(new Date(0));
        result.setModifyTime(new Date(0));
        result.setCreator("");
        result.setStatus(0);
        return result;
    }
    public BridgeDeckComponentVo getEmptyBridgeDeckComponentVo(){
        BridgeDeckComponentVo result=new BridgeDeckComponentVo();
        result.setId(0);
        result.setBridgeDeckSystemId(0);
        result.setComponentId(0);
        result.setCode("");
        result.setInitialWeight((double)0);
        result.setActualWeight((double)0);
        result.setDeduct((double)0);
        result.setCreateTime(new Date(0));
        result.setModifyTime(new Date(0));
        result.setCreator("");
        result.setStatus(0);
        return result;
    }
    public BridgeSubstructureVo getEmptyBridgeSubstructureVo(){
        BridgeSubstructureVo result=new BridgeSubstructureVo();
        result.setId(0);
        result.setBridgeSpanId(0);
        result.setSingleRowDun(0);
        result.setSingleRowSupport(0);
        result.setArchFoot(0);
        result.setExteriorTrimPanel(0);
        result.setMonitorDiagram("");
        result.setCreator("");
        result.setCreateTime(new Date(0));
        result.setModifyTime(new Date(0));
        result.setStatus(0);
        return result;
    }
    public BridgeSubcomponentVo getEmptyBridgeSubcomponentVo(){
        BridgeSubcomponentVo result=new BridgeSubcomponentVo();
        result.setId(0);
        result.setBridgeSubstructureId(0);
        result.setComponentId(0);
        result.setCode("");
        result.setInitialWeight((double)0);
        result.setActualWeight((double)0);
        result.setDeduct((double)0);
        result.setCapBeamsHeight((double)0);
        result.setCapBeamsWidth((double)0);
        result.setCapBeamsThick((double)0);
        result.setAbutmentHeight((double)0);
        result.setActualWeight((double)0);
        result.setAbutmentThick((double)0);
        result.setAbutmentCapHeight((double)0);
        result.setPierShape(0);
        result.setPierHeight((double)0);
        result.setPierWidth((double)0);
        result.setPierThick((double)0);
        result.setPierRadius((double)0);
        result.setPierAbutmentId(0);
        result.setCreateTime(new Date(0));
        result.setModifyTime(new Date(0));
        result.setCreator("");
        result.setStatus(0);
        return result;
    }
    public BridgeSupstructureVo getEmptyBridgeSupstructureVo(){
        BridgeSupstructureVo result=new BridgeSupstructureVo();
        result.setId(0);
        result.setBridgeSpanId(0);
        result.setBeamType(0);
        result.setBeamNumber(0);
        result.setHangingBeam(0);
        result.setHangingBeamSupport(0);
        result.setAntiFallingBeam(0);
        result.setTruss(0);
        result.setPrimaryNode(0);
        result.setStringer(0);
        result.setCrossBeam(0);
        result.setConnector(0);
        result.setArchRing(0);
        result.setHorizontaConnection(0);
        result.setArchStructure(0);
        result.setMonitorDiagram("");
        result.setExteriorTrimPanel(0);
        result.setCreator("");
        result.setCreateTime(new Date(0));
        result.setModifyTime(new Date(0));
        result.setStatus(0);
        return result;
    }
    public BridgeSupcomponentVo getEmptyBridgeSupcomponentVo(){
        BridgeSupcomponentVo result=new BridgeSupcomponentVo();
        result.setId(0);
        result.setSupstructureId(0);
        result.setComponentId(0);
        result.setCode("");
        result.setInitialWeight((double)0);
        result.setActualWeight((double)0);
        result.setDeduct((double)0);
        result.setWebPlateHight((double)0);
        result.setWingPlateWidth((double)0);
        result.setBaseplateWidth((double)0);
        result.setDiaphragmsHeight((double)0);
        result.setDiaphragmsNumber(0);
        result.setCantileverLenght((double)0);
        result.setHangingBeamLength((double)0);
        result.setCreateTime(new Date(0));
        result.setModifyTime(new Date(0));
        result.setCreator("");
        result.setStatus(0);
        return result;
    }
    public BridgeOtherStructureVo getEmptyBridgeOtherStructure(){
        BridgeOtherStructureVo result=new BridgeOtherStructureVo();
        result.setId(0);
        result.setBridgeSpanId(0);
        result.setDescript("");
        result.setCreator("");
        result.setCreateTime(new Date(0));
        result.setModifyTime(new Date(0));
        result.setStatus(0);
        result.setIdReal(false);
        return result;
    }
    public DiseaseInstanceVo getEmptyDiseaseInstanceVo(){
        DiseaseInstanceVo result=new DiseaseInstanceVo();
        result.setId(0);
        result.setPartType(0);
        result.setTargetId(0);
        result.setLength((double)0);
        result.setWidth((double)0);
        result.setDepth((double)0);
        result.setSeamLength((double)0);
        result.setSeamWidth((double)0);
        result.setAngle((double)0);
        result.setDegree("");
        result.setXAxis((double)0);
        result.setYAxis((double)0);
        result.setRemark("");
        result.setHeightDifference((double)0);
        result.setNumber((double)0);
        result.setDeflectionValue((double)0);
        result.setTrend("");
        result.setCreator("");
        result.setCreateTime(new Date(0));
        result.setModifyTime(new Date(0));
        result.setStatus(0);
        result.setDiseaseId(0);
        result.setDeduct((double)0);
        result.setSort(0);
        result.setIsDelete(0);
        return result;
    }
    public MonitorPlanStructureRelVo getEmptyMonitorPlanStructureRel(){
        MonitorPlanStructureRelVo result=new MonitorPlanStructureRelVo();
        result.setId(0);
        result.setMonitorPlanId(0);
        result.setStructureInfoId(0);
        result.setStructureName("");
        result.setFileName("");
        result.setFileUrl("");
        result.setIdReal(false);
        return result;
    }
    public void copyAttachments(List<AttachmentVo> attachmentList,String copyPath){
        new File(copyPath+"/attachment").mkdirs();
        for(AttachmentVo attachment:attachmentList){
            Logger logger= LoggerFactory.getLogger(AppZipServiceImpl.class);
            if(!attachment.getPath().contains("/bridge/static")) {
                logger.debug(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"imgPath:"+attachment.getPath());
                return;
            }
            String imgUrl = attachment.getPath().replace("/bridge/static/", path);
            logger.debug(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"imgUrl:"+imgUrl);
            File readFile=new File(imgUrl);
            String writeUrl=copyPath+"/attachment/"+attachment.getName();
            logger.debug(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"writeUrl:"+writeUrl);
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
                e.printStackTrace();
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
                }catch (IOException e){
                    e.printStackTrace();
                    StringBuilder builder=new StringBuilder();
                    for(StackTraceElement element:e.getStackTrace()){
                        builder.append(element).append("\n");
                    }
                    logger.error(builder.toString());
                }
            }
        }
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
