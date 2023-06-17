package com.iware.bridge.evaluation.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.evaluation.Utils.OriginalRecordUtils;
import com.iware.bridge.evaluation.Utils.TestReportUtils;
import com.iware.bridge.evaluation.api.EvaluationRecordApi;
import com.iware.bridge.evaluation.dao.*;
import com.iware.bridge.evaluation.enums.AttachStatusEnum;
import com.iware.bridge.evaluation.vo.*;
import com.iware.bridge.model.dao.evaluation.*;
import com.iware.bridge.model.entity.evaluation.*;
import com.iware.common.exception.BusinessException;
import com.iware.common.utils.InZipUntil;
import com.iware.common.utils.UUIDUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.xmlbeans.XmlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author WJP
 * @date 2021-8-16
 */
@Service
public class EvaluationRecordServiceImpl implements EvaluationRecordService {

    private static Logger logger = LoggerFactory.getLogger(EvaluationRecordApi.class);

    @Autowired
    private EvaluationAnalysisService evaluationAnalysisService;
    @Autowired
    private MonitorPlanStructureRelExpDao monitorPlanStructureRelExpDao;
    @Autowired
    private BridgeScoreExpDao bridgeScoreExpDao;
    @Autowired
    private DiseaseInstanceExpDao diseaseInstanceExpDao;
    @Autowired
    private BridgeExpDao bridgeExpDao;
    @Autowired
    private MonitorPlanOriginalRecordExpDao monitorPlanOriginalRecordExpDao;
    @Autowired
    private AttachmentExpDao attachmentExpDao;
    @Autowired
    private MonitorPlanOriginalRecordDao monitorPlanOriginalRecordDao;
    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private DeductionExpDao deductionExpDao;
    @Autowired
    private EvaluationRecordBciService evaluationRecordBciService;
    @Autowired
    private BridgeRoadDao bridgeRoadDao;
    @Autowired
    private EvaluationPlanServiceImpl evaluationPlanService;
    @Autowired
    private BridgeTypeDao bridgeTypeDao;
    @Autowired
    private BridgeSpanDao bridgeSpanDao;
    @Autowired
    private BridgeDeckSystemDao bridgeDeckSystemDao;
    @Autowired
    private BridgeSupstructureDao bridgeSupstructureDao;
    @Autowired
    private BridgeSubstructureDao bridgeSubstructureDao;
    @Autowired
    private BridgeSupcomponentDao bridgeSupcomponentDao;
    @Autowired
    private BridgeSubcomponentDao bridgeSubcomponentDao;
    @Autowired
    private BridgeOtherStructureDao bridgeOtherStructureDao;

    @Value("${file.upload-path}")
    private String path;
    @Value("${server.port}")
    private String port;
    @Value("${server.context-path}")
    private String contextPath;

    @Override
    public List<AssessRecordVO> getRecordList(AssessRecordFilter assessRecordFilter) {
        assessRecordFilter.setList(evaluationAnalysisService.getProjects());
        List<AssessRecordVO> resList = monitorPlanStructureRelExpDao.getRecordList(assessRecordFilter);
        for (AssessRecordVO assessRecordVO : resList) {
            AssessRecordFilter scoreFilter = new AssessRecordFilter();
            scoreFilter.setStructureInfoId(assessRecordVO.getId());
            List<BCIEvaluation> bciList = null;
            bciList = getBCIEvaluationList(scoreFilter);
            List<Integer> evaluateIndexs = new ArrayList<>();  //判断所有评分是否为全部评价,
            for (int i = 0; i < bciList.size(); i++) {
                if (bciList.get(i).getBCIScore() != null) {
                    evaluateIndexs.add(i);
                }
            }

            if (bciList.size() != evaluateIndexs.size() || bciList.size() == 0) {
                assessRecordVO.setRatingLevel("未评价");
            } else {
                assessRecordVO.setRatingLevel(bridgeScoreExpDao.getMinRatingLevel(bciList));
            }
        }
        return resList;
    }

    @Override
    public List<BCIEvaluation> getBCIEvaluationList(AssessRecordFilter assessRecordFilter) {
        List<BCIEvaluation> bciEvaluationList = bridgeScoreExpDao.getBCIEvaluationList(assessRecordFilter);
        for (BCIEvaluation bciEvaluation : bciEvaluationList) {
            if (bciEvaluation.getEvaluationTime() == null) {
                bciEvaluation.setIs(false);
                continue;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String diseaseMaxTime = diseaseInstanceExpDao.getDiseaseMaxTime(bciEvaluation.getBridgeRoadId());
            if (diseaseMaxTime != null) {
                Date a = null;
                Date b = null;
                try {
                    a = sdf.parse(diseaseMaxTime);
                    b = sdf.parse(bciEvaluation.getEvaluationTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //如果病害时间大于评价时间表示更新了病害
                if (a.before(b) || a == b) {
                    bciEvaluation.setIs(true);
                } else if (b.before(a)) {
                    bciEvaluation.setIs(false);
                    bciEvaluation.setBCIScore(null);
                }
            } else {
                bciEvaluation.setIs(true);
            }
        }
        return bciEvaluationList;
    }

    @Override
    public List<BridgeSurveyFirstTierVO> getBridgeSurveyDetail(BridgeSurveyDetailListFilter bridgeSurveyDetailListFilter) {
        List<BridgeSurveyFirstTierVO> retList = new ArrayList<>();
        if (bridgeSurveyDetailListFilter.getBid() != null && bridgeSurveyDetailListFilter.getBid() != 0) {
            Integer mpSrelId = monitorPlanStructureRelExpDao.getMpSrelIdByBid(bridgeSurveyDetailListFilter.getBid());
            if (mpSrelId != null) {
                retList = getBridgeSurveyDetailByMpSrelId(mpSrelId);
            }

        } else if (bridgeSurveyDetailListFilter.getMpSrelId() != null && bridgeSurveyDetailListFilter.getMpSrelId() != 0) {
            retList = getBridgeSurveyDetailByMpSrelId(bridgeSurveyDetailListFilter.getMpSrelId());
        }
        return retList;
    }

    private List<BridgeSurveyFirstTierVO> getBridgeSurveyDetailByMpSrelId(Integer mpSrelId) {
        List<BridgeSurveyFirstTierVO> firstList = bridgeExpDao.getFirstTierByMpSrelId(mpSrelId);
        //↑设置一级和二级菜单
        for (BridgeSurveyFirstTierVO first : firstList) {
            //↓循环二级桥跨列表  设置三四级菜单
            for (BridgeSurveySecondTierVO second : first.getChildren()) {
                Integer bridgeSpanId = second.getId();
                if (bridgeSpanId == null || bridgeSpanId == 0) {
                    continue;
                }
                if (second.getChildren() == null) {
                    second.setChildren(new ArrayList<>());
                }
                List<BridgeSurveyThirdTierVO> thirdList = second.getChildren();

                //设置桥面系
                BridgeSurveyThirdTierVO deckSystem = bridgeExpDao.getDeckSystem(bridgeSpanId);
                if (deckSystem != null) {
                    deckSystem.setBridgeSubstructure(null);
                    deckSystem.setBridgeSupstructure(null);
                    deckSystem.setChildren(null);
                    thirdList.add(deckSystem);
                }

                //设置上部结构
                BridgeSurveyThirdTierVO superstructure = bridgeExpDao.getSuperstructure(bridgeSpanId);

                if (superstructure != null) {
                    superstructure.setBridgeDeckSystem(null);
                    superstructure.setBridgeSubstructure(null);
                    thirdList.add(superstructure);
                    if (superstructure.getChildren() != null) {
                        if (superstructure.getChildren().size() == 1 && superstructure.getChildren().get(0).getId() == null) {
                            superstructure.setChildren(null);
                        }
                    }
                }

                //设置下部结构
                BridgeSurveyThirdTierVO substructure = bridgeExpDao.getSubstructure(bridgeSpanId);
                if (substructure != null) {
                    substructure.setBridgeDeckSystem(null);
                    substructure.setBridgeSupstructure(null);
                    thirdList.add(substructure);
                    if (substructure.getChildren() != null) {
                        if (substructure.getChildren().size() == 1 && substructure.getChildren().get(0).getId() == null) {
                            substructure.setChildren(null);
                        }
                    }
                }

                //设置其他结构
                BridgeSurveyThirdTierVO bridgeOtherStructure = bridgeExpDao.getOther(bridgeSpanId);
                if (bridgeOtherStructure != null) {
                    bridgeOtherStructure.setBridgeSubstructure(null);
                    bridgeOtherStructure.setBridgeSupstructure(null);
                    bridgeOtherStructure.setChildren(null);
                    thirdList.add(bridgeOtherStructure);
                }

            }
        }
        return firstList;
    }

    @Override
    public boolean isOriginalRecordByStructure(Integer id) {
        return (monitorPlanOriginalRecordExpDao.selectOriginalRecordByStructure(id) != 0) ? true : false;
    }

    @Override
    public List<StructPhoto> getBridgeStructPhoto(StructPhotoFilter structPhotoFilter) {
        List<Byte> status = new ArrayList<Byte>();
        status.add((byte) 1);
        status.add((byte) 2);
        structPhotoFilter.setStatus(status);
        List<StructPhoto> photoList = attachmentExpDao.getStrunctPhotoBySelfFilter(structPhotoFilter, 6);
        MonitorPlanOriginalRecord morFilter = new MonitorPlanOriginalRecord();
        Integer moniPlanStructId = structPhotoFilter.getMoniPlanStructId();
        if (moniPlanStructId != null) {
            morFilter.setMonitorPlanStructureRelId(moniPlanStructId);
            List<MonitorPlanOriginalRecord> monitorPlanOriglRecdList = monitorPlanOriginalRecordDao.query(morFilter);
            if (!monitorPlanOriglRecdList.isEmpty()) {
                photoList.forEach(c -> {
                    c.setIsCanDel((byte) 0);
                });
            } else {
                photoList.forEach(c -> {
                    c.setIsCanDel((byte) 1);
                });
            }
        }
        return photoList;
    }

    @Override
    public List<DiseaseBridge> getDiseaseList(DiseaseBridgeListFilterVO diseaseBridgeListFilterVO) {
        List<DiseaseBridge> diseaseBridges = diseaseInstanceExpDao.selectDiseaseList(diseaseBridgeListFilterVO);
        for (DiseaseBridge diseaseBridge : diseaseBridges) {
            if (diseaseBridge.getValue6() != null) {
                continue;
            }
            if (diseaseBridge.getHeightDifference() != 0.0) {
                diseaseBridge.setValue6("" + "" + diseaseBridge.getHeightDifference() + "cm");
            } else if (diseaseBridge.getNumber() != 0.0) {
                diseaseBridge.setValue6("" + "" + diseaseBridge.getNumber() + "个");
            }
        }
        return diseaseBridges;
    }

    @Override
    public Attachment addStructPhoto(MultipartFile file, Integer type, Integer partType, Integer targetId) {

        if (partType == 1 ) {
            type = 1;
            partType = null;
        } else if (partType == 2) {
            type = 2;
            partType = null;
        }else if (partType == 3) {
            type = 3;
            partType = 1;
        } else if (partType == 4) {
            type = 3;
            partType = 2;
        } else if (partType == 5) {
            type = 3;
            partType = 3;
        } else if (partType == 6) {
            type = 4;
            partType = 2;
        } else if (partType == 7) {
            type = 4;
            partType = 3;
        }else if (partType == 8) {
            type = 3;
            partType = 4;
        }
        partType = (partType == null) ? 0 : partType;
        if (!getUploadIsPassUpLimit(partType, type, targetId, 6)) {
            throw new BusinessException("图片上传已超过上限,请删除不要的图片在进行上传");
        }

        String uploadFileName = file.getOriginalFilename();
        if (uploadFileName.length() > 32) {
            uploadFileName = uploadFileName.substring(uploadFileName.length() - 32);
        }

        String ext = null;
        if (uploadFileName.contains(".")) {
            ext = uploadFileName.substring(uploadFileName.lastIndexOf("."));
        } else {
            ext = "";
        }

        String newFilePath = "/struncts/" + type + "/" + partType + "/" + targetId + "/" + UUIDUtils.getUUID() + ext;
        String dest = path + newFilePath;
        logger.debug("dest：" + dest);
        File newFile = new File(dest);
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        try {
            file.transferTo(newFile);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String url = contextPath + "/static" + newFilePath;

        Attachment attachment = new Attachment();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        attachment.setName(uploadFileName);
        attachment.setPartType(partType);
        attachment.setType(type);
        attachment.setTargetId(targetId);
        attachment.setPath(url);
        attachment.setCreateTime(new Date());
        attachment.setStatus(1);
        attachmentDao.save(attachment);
        return attachment;
    }

    /**
     * 获取上传图片是不是已经超过上限
     *
     * @return true未超过上限，false 操作上限
     */
    private Boolean getUploadIsPassUpLimit(Integer partType, Integer type, Integer targetId, Integer limit) {
        StructPhotoFilter structPhotoFilter = new StructPhotoFilter();
        ArrayList<Byte> statusList = new ArrayList<Byte>();
        statusList.add(AttachStatusEnum.NORMAL.getCode());
        statusList.add(AttachStatusEnum.SELECTED.getCode());
        structPhotoFilter.setStatus(statusList);
        structPhotoFilter.setPartType(partType);
        structPhotoFilter.setType(type);
        structPhotoFilter.setTargetId(targetId);
        Integer alCount = attachmentExpDao.getCountForFilter(structPhotoFilter);
        if (alCount >= limit) {
            return false;
        }
        return true;
    }

    @Override
    public List<PhotoUrl> getPhotoUrl(Integer targetId) {
        return attachmentExpDao.selectPhotoUrl(targetId);
    }

    @Override
    public void addPhotoUrl(Map<String, String> map, MultipartFile file) {
        String fileName = map.get("dirName");
        Integer id = Integer.valueOf(map.get("targetId"));
        String name = file.getOriginalFilename();
        String date = "" + new Date().getTime();// 当前时间戳

        String ext = null;
        if (name.contains(".")) {
            ext = name.substring(name.lastIndexOf("."));
        } else {
            ext = "";
        }

        String nFileName = UUIDUtils.getUUID() + ext;
        //存储地址
        String dest = path + "/" + fileName + "/" + id + "/" + date + "/" + nFileName;
        File newFile = new File(dest);
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        File tempFile = null;
        tempFile = new File(dest);
        //生成文件
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = contextPath + "/static/" + fileName + "/" + id + "/" + date + "/" + nFileName;
        Integer type = Integer.valueOf(map.get("type"));
        Integer partType = Integer.valueOf(map.get("partType"));
        Integer status = Integer.valueOf(map.get("status"));
        Attachment attachment = new Attachment();
        attachment.setPartType(partType);
        attachment.setPath(path);
        attachment.setStatus(status);
        attachment.setType(type);
        attachment.setName(name);
        attachment.setTargetId(id);
        attachment.setCreateTime(new Date());
        attachmentDao.save(attachment);
    }

    @Override
    public void deletePhoto(Integer id) {
        Attachment attachment = new Attachment();
        attachment.setId(id);
        List<Attachment> query = attachmentDao.query(attachment);
        for (Attachment attachment1 : query) {
            String[] path = attachment1.getPath().split("/bridge/static/");
            if (path.length == 2)
                evaluationPlanService.delFile(path[1]);
        }

        attachmentDao.deleteById(id);
    }

    @Override
    public void selectDefaultStructPhoto(Attachment attachment) {
        attachmentDao.update(attachment);
    }

    @Override
    public String downloadStructPhoto(StructPhotoFilter structPhotoFilter) {
        Integer partType = structPhotoFilter.getPartType();
        Integer type = 0;
        if (partType == 1 ) {
            type = 1;
            partType = null;
        } else if (partType == 2) {
            type = 2;
            partType = null;
        }else if (partType == 3) {
            type = 3;
            partType = 1;
        } else if (partType == 4) {
            type = 3;
            partType = 2;
        } else if (partType == 5) {
            type = 3;
            partType = 3;
        } else if (partType == 6) {
            type = 4;
            partType = 2;
        } else if (partType == 7) {
            type = 4;
            partType = 3;
        }else if (partType == 8) {
            type = 3;
            partType = 4;
        }
        structPhotoFilter.setPartType(partType);
        structPhotoFilter.setType(type);
        delFile("tmp");
        List<Byte> status = new ArrayList<Byte>();
        status.add((byte) 1);
        status.add((byte) 2);
        structPhotoFilter.setStatus(status);
        List<StructPhoto> photoList = attachmentExpDao.getStrunctPhotoBySelfFilter(structPhotoFilter, 6);
        if (!photoList.isEmpty()) {
            List<String> files = new ArrayList<String>();
            photoList.forEach(photo -> {
                files.add(path + photo.getPath().replace(contextPath + "/static/", ""));
            });

            InZipUntil zipclient = new InZipUntil();
            String zipPath = "/tmp/" + UUIDUtils.getUUID() + ".zip";
            File zipFile = new File(path + zipPath);
            if (!zipFile.getParentFile().exists()) {
                zipFile.getParentFile().mkdirs();
            }
            zipclient.inZipManyFile(files, zipFile);
            return contextPath + "/static" + zipPath;
        }
        return null;
    }

    @Override
    public void updatePhoto(Attachment attachment) {
        attachmentDao.update(attachment);
    }

    @Override
    public PageInfo<RecordVO> getDiseaseRecordList(RecordListFilter recordListFilter) {
        PageHelper.startPage(recordListFilter.getCurrentPage(), recordListFilter.getPageSize());
        List<RecordVO> list = diseaseInstanceExpDao.selectDiseaseRecordList(recordListFilter);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<RecyclingVO> getRecyclingList(RecyclingListFilter recyclingListFilter) {
        PageHelper.startPage(recyclingListFilter.getCurrentPage(), recyclingListFilter.getPageSize());
        List<RecyclingVO> list = diseaseInstanceExpDao.selectRecyclingList(recyclingListFilter);
        return new PageInfo<>(list);
    }

    @Override
    public void replaceRecord(RecordDetailListFilter recordDetailListFilter) {
        List<DiseaseInstance> diseaseInstanceDTOS = diseaseInstanceExpDao.selectTypeAndTargetId(recordDetailListFilter);
        for (DiseaseInstance diseaseInstanceDTO : diseaseInstanceDTOS) {
            if (diseaseInstanceDTO.getPartType() == 1) {
                attachmentExpDao.updatePhotoStatusTo0DK(diseaseInstanceDTO.getTargetId(), 0, "");
            } else if (diseaseInstanceDTO.getPartType() == 2) {
                attachmentExpDao.updatePhotoStatusToSUP(diseaseInstanceDTO.getTargetId(), 0, "");
            } else if (diseaseInstanceDTO.getPartType() == 3) {
                attachmentExpDao.updatePhotoStatusTo0SUB(diseaseInstanceDTO.getTargetId(), 0, "");
            }
        }

        List<DiseaseInstance> diseaseInstanceDTOS2 = diseaseInstanceExpDao.selectTypeAndTargetId2(recordDetailListFilter);
        for (DiseaseInstance diseaseInstanceDTO : diseaseInstanceDTOS2) {
            if (diseaseInstanceDTO.getPartType() == 1) {
                attachmentExpDao.updatePhotoStatusTo0DK(diseaseInstanceDTO.getTargetId(), 1, diseaseInstanceDTO.getDegree());
            } else if (diseaseInstanceDTO.getPartType() == 2) {
                attachmentExpDao.updatePhotoStatusToSUP(diseaseInstanceDTO.getTargetId(), 1, diseaseInstanceDTO.getDegree());
            } else if (diseaseInstanceDTO.getPartType() == 3) {
                attachmentExpDao.updatePhotoStatusTo0SUB(diseaseInstanceDTO.getTargetId(), 1, diseaseInstanceDTO.getDegree());
            }
        }
        diseaseInstanceExpDao.updateStatusTo0(recordDetailListFilter);
        diseaseInstanceExpDao.updateStatusTo1(recordDetailListFilter);
    }

    @Override
    public List<DiseaseBridge> getRecordDetailList(RecordDetailListFilter recordDetailListFilter) {
        List<DiseaseBridge> diseaseBridges = diseaseInstanceExpDao.selectRecordDetailList(recordDetailListFilter);
        for (DiseaseBridge diseaseBridge : diseaseBridges) {
            if (diseaseBridge.getValue6() != null) {
                continue;
            }
            if (diseaseBridge.getHeightDifference() != 0.0) {
                diseaseBridge.setValue6("" + "" + diseaseBridge.getHeightDifference() + "cm");
            } else if (diseaseBridge.getNumber() != 0.0) {
                diseaseBridge.setValue6("" + "" + diseaseBridge.getNumber() + "个");
            }
        }
        return diseaseBridges;

    }

    @Override
    public void deleteStrunctPhoto(Integer Id) {
        Attachment attachment = attachmentDao.findById(Id);
        if (attachment != null) {
            String paths = path + attachment.getPath().replace(contextPath + "/static/", "");
            File file = new File(paths);
            if (file.exists()) {
                file.delete();
            }
        }
        attachmentDao.deleteById(Id);
    }

    @Override
    public List<ShowManage> getRecordDetail(Integer id) {
        List<PhotoUrl> photoUrlsBD = attachmentExpDao.selectAttachmentByDK(id);
        List<PhotoUrl> photoUrlsSUP = attachmentExpDao.selectAttachmentBySUP(id);
        List<PhotoUrl> photoUrlsSUB = attachmentExpDao.selectAttachmentBySUB(id);
        List<DetectionRecord> detectionRecordsBD = diseaseInstanceExpDao.selectDBByStructureId(id);
        List<DetectionRecord> detectionRecordsSUP = diseaseInstanceExpDao.selectSUPByStructureId(id);
        List<DetectionRecord> detectionRecordsSUB = diseaseInstanceExpDao.selectSUBByStructureId(id);
        List<ShowManage> str = new OriginalRecordUtils().str(photoUrlsBD, photoUrlsSUP, photoUrlsSUB, detectionRecordsBD, detectionRecordsSUP, detectionRecordsSUB);
        return str;
    }

    @Override
    public OriginalRecord getOriginalRecord(Integer id) {
        return monitorPlanOriginalRecordExpDao.selectRecordByStructureId(id);
    }

    @Override
    public String exportRecord(Integer id) {
        OriginalRecord originalRecord = monitorPlanOriginalRecordExpDao.selectRecordByStructureId(id);
        List<PhotoUrl> photoUrlsBD = attachmentExpDao.selectAttachmentByDK(id);
        List<PhotoUrl> photoUrlsSUP = attachmentExpDao.selectAttachmentBySUP(id);
        List<PhotoUrl> photoUrlsSUB = attachmentExpDao.selectAttachmentBySUB(id);

        List<DetectionRecord> detectionRecordsBD = diseaseInstanceExpDao.selectDBByStructureId(id);
        List<DetectionRecord> detectionRecordsSUP = diseaseInstanceExpDao.selectSUPByStructureId(id);
        List<DetectionRecord> detectionRecordsSUB = diseaseInstanceExpDao.selectSUBByStructureId(id);

        ExportData exportData = new ExportData();
        exportData.setPhotoUrlsBD(photoUrlsBD);
        exportData.setPhotoUrlsSUP(photoUrlsSUP);
        exportData.setPhotoUrlsSUB(photoUrlsSUB);
        exportData.setDetectionRecordsBD(detectionRecordsBD);
        exportData.setDetectionRecordsSUP(detectionRecordsSUP);
        exportData.setDetectionRecordsSUB(detectionRecordsSUB);
        exportData.setId(10);
        exportData.setFilePath(path);
        exportData.setFileContext(contextPath);
        exportData.setFileName(originalRecord.getProjectName() + originalRecord.getStructureName() + "原始记录");
        exportData.setOriginalRecord(originalRecord);
        String path = null;
        try {
            path = new OriginalRecordUtils().exportRecord(exportData);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        } catch (XmlException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        return path;
    }

    @Override
    public void insertRecord(OriginalRecord originalRecord) {
        if (monitorPlanOriginalRecordExpDao.existRecordNumber(originalRecord.getRecordNumber()) > 0 && !originalRecord.getRecordNumber().equals("")) {
            throw new BusinessException("记录编号已存在，请重新输入");
        }
        AssessRecordFilter assessRecordFilter = new AssessRecordFilter();
        assessRecordFilter.setStructureInfoId(originalRecord.getId());
        List<BCIEvaluation> list = getBCIEvaluationList(assessRecordFilter);

        List<Integer> evaluateIndexs = new ArrayList<>();  //判断所有评分是否为全部评价,
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getBCIScore() != null) {
                evaluateIndexs.add(i);
            }
        }

        originalRecord.setMonitorPlanStructureRelId(originalRecord.getId());
        if (list.size() == evaluateIndexs.size()) {
            monitorPlanOriginalRecordExpDao.insertRecord(originalRecord);
        } else {
            throw new BusinessException("请先进行BCI评价");
        }
    }

    @Override
    public List<RoadBridge> selectRoadList(Integer structureId) {
        List<RoadBridge> roadBridges = bridgeExpDao.selectRoadList(structureId);
        for (RoadBridge roadBridge : roadBridges) {
            if (roadBridge.getReportPath() != null) {
                String[] str = roadBridge.getReportPath().split("/");
                roadBridge.setReportPath(str[str.length - 1]);
            }
        }
        return roadBridges;
    }

    @Override
    public Integer generateReport(GenerateReportVO generateReportVO) {
        WordBasicData wordBasicData = new WordBasicData();
        String userName = bridgeExpDao.selectUserNameByRoadId(generateReportVO.getId());
        if (userName == null) {
            wordBasicData.setUserName(" ");
        } else {
            wordBasicData.setUserName(" " + userName);
        }

        OriginalRecord originalRecord = monitorPlanOriginalRecordExpDao.selectOriginalRecordByRoadId(generateReportVO.getId());
        if (originalRecord == null) {
            wordBasicData.setProjectSite(" ");
        } else {
            wordBasicData.setProjectSite(" " + originalRecord.getProjectLocation());
        }

        wordBasicData.setDetectionBase(" " + generateReportVO.getDetectionBase());
        wordBasicData.setDetectionType(" " + generateReportVO.getDetectionType());
        ScoreComparison scoreComparison = bridgeScoreExpDao.selectScoreByRoadId(generateReportVO.getId());

        if (scoreComparison == null) {
            wordBasicData.setBciScore(" ");
            wordBasicData.setBciLevel(" ");
        } else {
            wordBasicData.setBciScore("" + scoreComparison.getBci());
            wordBasicData.setBciLevel("" + scoreComparison.getLevel());
        }

        wordBasicData.setBridgeLevel(generateReportVO.getBridgeLevel());

        List<PhotoUrl> photoUrls = attachmentExpDao.selectPhotoByRoadId(generateReportVO.getId());
        wordBasicData.setPhotoManage(photoUrls);
        List<WordCheckResults> wordCheckResults = diseaseInstanceExpDao.selectBDDiseaseByRoadId(generateReportVO.getId());
        for (WordCheckResults wordCheckResult : wordCheckResults) {
            Attachment attachment = new Attachment();
            attachment.setType(5);
            attachment.setPartType(1);
            attachment.setTargetId(wordCheckResult.getId());
            wordCheckResult.setPathName(attachmentDao.query(attachment));
        }
        wordBasicData.setBD(wordCheckResults);


        List<String> list2 = new ArrayList<>();
        list2.add("桥面铺装");
        list2.add("桥头平顺");
        list2.add("伸缩装置");
        list2.add("排水系统");
        list2.add("人行道");
        list2.add("栏杆或护栏");
        list2.add("台帽");
        list2.add("台身");
        list2.add("主梁");
        list2.add("墩身");
        list2.add("盖梁");
        list2.add("悬臂梁");
        list2.add("挂梁");


        for (WordCheckResults wordCheckResult : wordCheckResults) {
            if (!list2.contains(wordCheckResult.getName())) {
                wordCheckResult.setSort(null);
                wordCheckResult.setCode(null);
            }
        }

        List<WordCheckResults> wordCheckResults2 = diseaseInstanceExpDao.selectSUPDiseaseByRoadId(generateReportVO.getId());
        for (WordCheckResults wordCheckResult : wordCheckResults2) {
            Attachment attachment = new Attachment();
            attachment.setType(5);
            attachment.setPartType(2);
            attachment.setTargetId(wordCheckResult.getId());
            wordCheckResult.setPathName(attachmentDao.query(attachment));
        }
        wordBasicData.setSUP(wordCheckResults2);

        for (WordCheckResults wordCheckResult : wordCheckResults2) {
            if (!list2.contains(wordCheckResult.getName())) {
                wordCheckResult.setSort(null);
                wordCheckResult.setCode(null);
            }
        }

        List<WordCheckResults> wordCheckResults3 = diseaseInstanceExpDao.selectSUBDiseaseByRoadId(generateReportVO.getId());
        for (WordCheckResults wordCheckResult : wordCheckResults3) {
            Attachment attachment = new Attachment();
            attachment.setType(5);
            attachment.setPartType(3);
            attachment.setTargetId(wordCheckResult.getId());
            wordCheckResult.setPathName(attachmentDao.query(attachment));
        }
        wordBasicData.setSUB(wordCheckResults3);

        for (WordCheckResults wordCheckResult : wordCheckResults3) {
            if (!list2.contains(wordCheckResult.getName())) {
                wordCheckResult.setSort(null);
                wordCheckResult.setCode(null);
            }
        }

        wordBasicData.setFile(path);
        wordBasicData.setContext(contextPath);


        BCIDetailVO bciDetailVO = new BCIDetailVO();
        bciDetailVO.setRoadId(Long.parseLong("" + generateReportVO.getId()));
        BCIEvaluationDetailDTO bciEvaluationDetailDTO = evaluationRecordBciService.selBCIEvaluationDetail(bciDetailVO);
        List<WordEvaluateResults> wordEvaluateResults = new ArrayList<>();
        for (ArtifactsDTO artifactsDTO : bciEvaluationDetailDTO.getFloorSystemDTO().getArtifactsDTOList()) {
            WordEvaluateResults newWordEvaluateResults = new WordEvaluateResults();
            newWordEvaluateResults.setActualWeight(Float.parseFloat("" + artifactsDTO.getActualWeight()));
            newWordEvaluateResults.setDeduct(Float.parseFloat("" + artifactsDTO.getDeduct()));
            newWordEvaluateResults.setName(artifactsDTO.getArtifactsName());
            wordEvaluateResults.add(newWordEvaluateResults);
        }
        wordBasicData.setBDEvaluateResults(wordEvaluateResults);

        List<WordEvaluateResults> wordEvaluateResults2 = new ArrayList<>();
        for (AcrossDTO acrossDTO : bciEvaluationDetailDTO.getUpperStructureDTO().getAcrossDTOList()) {
            for (ArtifactsDTO artifactsDTO : acrossDTO.getArtifactsDTOList()) {
                WordEvaluateResults newWordEvaluateResults = new WordEvaluateResults();
                newWordEvaluateResults.setActualWeight(Float.parseFloat("" + artifactsDTO.getActualWeight()));
                newWordEvaluateResults.setDeduct(Float.parseFloat("" + artifactsDTO.getDeduct()));
                newWordEvaluateResults.setName(artifactsDTO.getArtifactsName());
                newWordEvaluateResults.setSpanCode(acrossDTO.getSpanCode());
                wordEvaluateResults2.add(newWordEvaluateResults);
            }
        }
        wordBasicData.setSUPEvaluateResults(wordEvaluateResults2);


        List<WordEvaluateResults> wordEvaluateResults3 = new ArrayList<>();
        for (AcrossDTO acrossDTO : bciEvaluationDetailDTO.getLowerStructureDTO().getAcrossDTOList()) {
            for (ArtifactsDTO artifactsDTO : acrossDTO.getArtifactsDTOList()) {
                WordEvaluateResults newWordEvaluateResults = new WordEvaluateResults();
                newWordEvaluateResults.setActualWeight(Float.parseFloat("" + artifactsDTO.getActualWeight()));
                newWordEvaluateResults.setDeduct(Float.parseFloat("" + artifactsDTO.getDeduct()));
                newWordEvaluateResults.setName(artifactsDTO.getArtifactsName());
                newWordEvaluateResults.setSpanCode(acrossDTO.getSpanCode());
                wordEvaluateResults3.add(newWordEvaluateResults);
            }
        }
        wordBasicData.setSUBEvaluateResults(wordEvaluateResults3);


        List<Float> list = bridgeExpDao.selectWeightByRoadId(generateReportVO.getId());
        if (list.size() == 0) {
            wordBasicData.setBDWeight((float) 0);
            wordBasicData.setSUPWeight((float) 0);
            wordBasicData.setSUBWeight((float) 0);
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    wordBasicData.setBDWeight(list.get(i));
                } else if (i == 1) {
                    wordBasicData.setSUPWeight(list.get(i));
                } else if (i == 2) {
                    wordBasicData.setSUBWeight(list.get(i));
                }
            }
        }

        wordBasicData.setTotalBD(attachmentExpDao.selectAttachmentByDKByRoadId(generateReportVO.getId()));
        wordBasicData.setTotalSUP(attachmentExpDao.selectAttachmentBySUPByRoadId(generateReportVO.getId()));
        wordBasicData.setTotalSUB(attachmentExpDao.selectAttachmentBySUBByRoadId(generateReportVO.getId()));


        for (WordCheckResults model : wordBasicData.getBD()) {
            if (model.getRemark() == null) {
                model.setRemark("");
            }
            if (model.getDegree() == null) {
                model.setDegree("");
            }

        }
        for (WordCheckResults model : wordBasicData.getSUB()) {
            if (model.getRemark() == null) {
                model.setRemark("");
            }
            if (model.getDegree() == null) {
                model.setDegree("");
            }

        }
        for (WordCheckResults model : wordBasicData.getSUP()) {
            if (model.getRemark() == null) {
                model.setRemark("");
            }
            if (model.getDegree() == null) {
                model.setDegree("");
            }

        }


        String path = null;
        try {
            path = TestReportUtils.myCreateWord(wordBasicData, generateReportVO.getId(),
                    generateReportVO.getStructureName() + generateReportVO.getRoadName() + "定检报告");
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        } catch (XmlException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        BridgeRoad byId = bridgeRoadDao.findById(generateReportVO.getId());
        //删除之前的
        if (byId.getReportPath() != null) {
            evaluationPlanService.delFile(byId.getReportPath());
        }
        return bridgeExpDao.updateReportPath(generateReportVO.getId(), path);
//        return bridgeExpDao.updateReportPath(generateReportVO.getId(), contextPath + "/ " + path);
    }

    @Override
    public void delMonitorStructure(Integer id) {
        if (monitorPlanStructureRelExpDao.existMonitorStructure(id) > 0) {
            monitorPlanStructureRelExpDao.deleteMonitorStructure(id);
        } else {
            throw new BusinessException("检测计划已删除");
        }
    }

    @Override
    public List<DiseaseSummary> selectDiseaseSummary(Integer id) {
        List<DiseaseSummary> diseaseSummaries = diseaseInstanceExpDao.selectDiseaseSummary(id);
        for (int i = 0; i < diseaseSummaries.size(); i++) {
            DiseaseSummary diseaseSummary = diseaseSummaries.get(i);
            String value4 = "";

            if (diseaseSummary.getArea() != 0.0) {
                value4 = "累计面积:" + diseaseSummary.getArea() + "m²;";
            }
            if (diseaseSummary.getNumber() != 0.0) {
                value4 = value4 + "累计数量:" + diseaseSummary.getNumber() + "个;";
            }
            if (diseaseSummary.getDepth() != 0.0) {
                value4 = value4 + "最大深度:" + diseaseSummary.getDepth() + "mm;";
            }
            if (diseaseSummary.getSeamLength() != 0.0) {
                value4 = value4 + "最大缝长:" + diseaseSummary.getSeamLength() + "mm;";
            }
            if (diseaseSummary.getSeamWidth() != 0.0) {
                value4 = value4 + "最大缝宽:" + diseaseSummary.getSeamWidth() + "mm;";
            }
            if (diseaseSummary.getHeightDifference() != 0.0) {
                value4 = value4 + "高度差:" + diseaseSummary.getHeightDifference() + "cm;";
            }
            if (diseaseSummary.getDegree() != null) {
                value4 = value4 + "程度:" + diseaseSummary.getDegree();
            }
            if (diseaseSummary.getArea() == 0.0 && diseaseSummary.getNumber() == 0.0 && diseaseSummary.getDepth() == 0.0 && diseaseSummary.getSeamLength() == 0.0 && diseaseSummary.getSeamWidth() == 0.0
                    && diseaseSummary.getHeightDifference() == 0.0 && diseaseSummary.getDegree() == null) {
                value4 = "-";
            }
            diseaseSummary.setValue4(value4);
        }
        return diseaseSummaries;
    }

    @Override
    public void calculateDeduct(Integer roadId) {
        List<DiseaseInstance> diseaseDtos = diseaseInstanceExpDao.selectDiseaseIdByRoadId(roadId);
        //优先程度
        for (DiseaseInstance diseaseDto : diseaseDtos) {
            if (diseaseDto.getDegree() == null) {
                if (diseaseDto.getHeightDifference() != null) {
                    diseaseDto.setDegree("" + diseaseDto.getHeightDifference());
                }
                if (diseaseDto.getNumber() != null) {
                    diseaseDto.setDegree("" + diseaseDto.getNumber());
                }
            }
            Integer type = diseaseInstanceExpDao.selectDegreeType(diseaseDto.getDiseaseId(), diseaseDto.getTargetId());
            if (type != null) {
                //百分比
                if (type == 1) {
                    if (diseaseDto.getDegree() != null) {
                        String[] str = diseaseDto.getDegree().split("%");
                        diseaseDto.setDegree(str[0]);
                    }
                    //查询病害的扣分值
                    Integer integer = deductionExpDao.selectDeductByInteger(diseaseDto.getDiseaseId(), diseaseDto.getDegree(), diseaseDto.getTargetId());
                    if (integer != null) {
                        diseaseDto.setDeduct(Double.parseDouble("" + integer));
                    } else {
                        diseaseDto.setDeduct(0.0);
                    }
                    //个数
                } else if (type == 2) {
                    //查询病害的扣分值
                    Integer integer = deductionExpDao.selectDeductByInteger(diseaseDto.getDiseaseId(), diseaseDto.getDegree(), diseaseDto.getTargetId());
                    if (integer != null) {
                        diseaseDto.setDeduct(Double.parseDouble("" + integer));
                    } else {
                        diseaseDto.setDeduct(0.0);
                    }
                    //字符串
                } else if (type == 3) {
                    //查询病害的扣分值
                    Integer integer = deductionExpDao.selectDeductByString(diseaseDto.getDiseaseId(), diseaseDto.getDegree(), diseaseDto.getTargetId());
                    if (integer != null) {
                        diseaseDto.setDeduct(Double.parseDouble("" + integer));
                    } else {
                        diseaseDto.setDeduct(0.0);
                    }
                }
            } else {
                diseaseDto.setDeduct(0.0);
            }
        }
        //保存病害扣分值
        diseaseInstanceExpDao.updateDiseaseInstance(diseaseDtos);

        List<Score> bridgeDeckComponents = bridgeExpDao.selectComponentIdByRoad(roadId);
        Map<String, List<DiseaseInstanceVO>> bkMap = new HashMap<>();
        Map<String, Map<String, List<DiseaseInstanceVO>>> subMap = new HashMap<>();
        Map<String, Map<String, DeductScore>> subMapList = new HashMap<>();
        Map<String, Map<String, List<DiseaseInstanceVO>>> supMap = new HashMap<>();
        Map<String, Map<String, DeductScore>> supMapList = new HashMap<>();

        //按构件和构件类型名称分类，上下部结构还要在多一层桥跨名称分类
        for (Score bridgeDeckComponent : bridgeDeckComponents) {
            List<DiseaseInstanceVO> diseaseInstances = new ArrayList<>();
            if (bridgeDeckComponent.getCode().equals("桥面系")) {
                bridgeDeckComponent.setDeduct(0.0);
                bridgeExpDao.updateComponent(bridgeDeckComponent);
                diseaseInstances = diseaseInstanceExpDao.selectDiseaseInstance(1, bridgeDeckComponent.getId(),bridgeDeckComponent.getComponentId());
                if (bkMap.containsKey(bridgeDeckComponent.getCreator())) {
                    for (DiseaseInstanceVO diseaseInstance : diseaseInstances) {
                        bkMap.get(bridgeDeckComponent.getCreator()).add(diseaseInstance);
                    }

                } else {
                    bkMap.put(bridgeDeckComponent.getCreator(), new ArrayList<>());
                    for (DiseaseInstanceVO diseaseInstance : diseaseInstances) {
                        bkMap.get(bridgeDeckComponent.getCreator()).add(diseaseInstance);
                    }
                }
                continue;
            } else if (bridgeDeckComponent.getCode().equals("上部结构")) {
                bridgeDeckComponent.setDeduct(0.0);
                bridgeExpDao.updateComponent2(bridgeDeckComponent);
                diseaseInstances = diseaseInstanceExpDao.selectDiseaseInstance(2, bridgeDeckComponent.getId(),bridgeDeckComponent.getComponentId());
                if (supMap.containsKey(bridgeDeckComponent.getSpanCode())) {
                    if (supMap.get(bridgeDeckComponent.getSpanCode()).containsKey(bridgeDeckComponent.getCreator())) {
                        for (DiseaseInstanceVO diseaseInstance : diseaseInstances) {
                            supMap.get(bridgeDeckComponent.getSpanCode()).get(bridgeDeckComponent.getCreator()).add(diseaseInstance);
                        }
                    } else {
                        supMap.get(bridgeDeckComponent.getSpanCode()).put(bridgeDeckComponent.getCreator(), new ArrayList<>());
                        for (DiseaseInstanceVO diseaseInstance : diseaseInstances) {
                            supMap.get(bridgeDeckComponent.getSpanCode()).get(bridgeDeckComponent.getCreator()).add(diseaseInstance);
                        }
                    }
                } else {
                    supMap.put(bridgeDeckComponent.getSpanCode(), new HashMap<>());
                    if (supMap.get(bridgeDeckComponent.getSpanCode()).containsKey(bridgeDeckComponent.getCreator())) {
                        for (DiseaseInstanceVO diseaseInstance : diseaseInstances) {
                            supMap.get(bridgeDeckComponent.getSpanCode()).get(bridgeDeckComponent.getCreator()).add(diseaseInstance);
                        }
                    } else {
                        supMap.get(bridgeDeckComponent.getSpanCode()).put(bridgeDeckComponent.getCreator(), new ArrayList<>());
                        for (DiseaseInstanceVO diseaseInstance : diseaseInstances) {
                            supMap.get(bridgeDeckComponent.getSpanCode()).get(bridgeDeckComponent.getCreator()).add(diseaseInstance);
                        }
                    }

                }
            } else if (bridgeDeckComponent.getCode().equals("下部结构")) {
                bridgeDeckComponent.setDeduct(0.0);
                bridgeExpDao.updateComponent3(bridgeDeckComponent);
                diseaseInstances = diseaseInstanceExpDao.selectDiseaseInstance(3, bridgeDeckComponent.getId(),bridgeDeckComponent.getComponentId());
                if (subMap.containsKey(bridgeDeckComponent.getSpanCode())) {
                    if (subMap.get(bridgeDeckComponent.getSpanCode()).containsKey(bridgeDeckComponent.getCreator())) {
                        for (DiseaseInstanceVO diseaseInstance : diseaseInstances) {
                            subMap.get(bridgeDeckComponent.getSpanCode()).get(bridgeDeckComponent.getCreator()).add(diseaseInstance);
                        }
                    } else {
                        subMap.get(bridgeDeckComponent.getSpanCode()).put(bridgeDeckComponent.getCreator(), new ArrayList<>());
                        for (DiseaseInstanceVO diseaseInstance : diseaseInstances) {
                            subMap.get(bridgeDeckComponent.getSpanCode()).get(bridgeDeckComponent.getCreator()).add(diseaseInstance);
                        }
                    }
                } else {
                    subMap.put(bridgeDeckComponent.getSpanCode(), new HashMap<>());
                    if (subMap.get(bridgeDeckComponent.getSpanCode()).containsKey(bridgeDeckComponent.getCreator())) {
                        for (DiseaseInstanceVO diseaseInstance : diseaseInstances) {
                            subMap.get(bridgeDeckComponent.getSpanCode()).get(bridgeDeckComponent.getCreator()).add(diseaseInstance);
                        }
                    } else {
                        subMap.get(bridgeDeckComponent.getSpanCode()).put(bridgeDeckComponent.getCreator(), new ArrayList<>());
                        for (DiseaseInstanceVO diseaseInstance : diseaseInstances) {
                            subMap.get(bridgeDeckComponent.getSpanCode()).get(bridgeDeckComponent.getCreator()).add(diseaseInstance);
                        }
                    }

                }
            }

        }

        //上部结构要素扣分
        for (String s : supMap.keySet()) {
            supMapList.put(s, deduct(supMap.get(s)));
        }

        //下部结构要素扣分
        for (String s : subMap.keySet()) {
            subMapList.put(s, deduct(subMap.get(s)));
        }
        //桥面系要素扣分
        Map<String, DeductScore> scoreMap = deduct(bkMap);

        //保存相应的构件扣分值
        for (Score bridgeDeckComponent : bridgeDeckComponents) {
            if (bridgeDeckComponent.getCode().equals("桥面系")) {
                for (String s : scoreMap.keySet()) {
                    if (s.equals(bridgeDeckComponent.getCreator())) {
                        bridgeDeckComponent.setDeduct(scoreMap.get(s).getScore());
                        if ("限制".equals(scoreMap.get(s).getSpecial())){
                            bridgeDeckComponent.setLimit(1);
                        }else {
                            bridgeDeckComponent.setLimit(0);
                        }
                        bridgeExpDao.updateComponent(bridgeDeckComponent);
                    } else {
                        bridgeDeckComponent.setDeduct(0.0);
                    }
                }
            } else if (bridgeDeckComponent.getCode().equals("上部结构")) {
                for (String s : supMapList.keySet()) {
                    if (s.equals(bridgeDeckComponent.getSpanCode())) {
                        for (String s1 : supMapList.get(s).keySet()) {
                            if (s1.equals(bridgeDeckComponent.getCreator())) {
                                bridgeDeckComponent.setDeduct(supMapList.get(s).get(s1).getScore());
                                if ("限制".equals(supMapList.get(s).get(s1).getSpecial())){
                                    bridgeDeckComponent.setLimit(1);
                                }else {
                                    bridgeDeckComponent.setLimit(0);
                                }
                                bridgeExpDao.updateComponent2(bridgeDeckComponent);
                            } else {
                                bridgeDeckComponent.setDeduct(0.0);
                            }
                        }
                    } else {
                        bridgeDeckComponent.setDeduct(0.0);
                    }
                }
            } else if (bridgeDeckComponent.getCode().equals("下部结构")) {
                for (String s : subMapList.keySet()) {
                    if (s.equals(bridgeDeckComponent.getSpanCode())) {
                        for (String s1 : subMapList.get(s).keySet()) {
                            if (s1.equals(bridgeDeckComponent.getCreator())) {
                                bridgeDeckComponent.setDeduct(subMapList.get(s).get(s1).getScore());
                                if ("限制".equals(subMapList.get(s).get(s1).getSpecial())){
                                    bridgeDeckComponent.setLimit(1);
                                }else {
                                    bridgeDeckComponent.setLimit(0);
                                }
                                bridgeExpDao.updateComponent3(bridgeDeckComponent);
                            } else {
                                bridgeDeckComponent.setDeduct(0.0);
                            }
                        }
                    } else {
                        bridgeDeckComponent.setDeduct(0.0);
                    }
                }
            }
        }

    }

    /**
     * 计算构件的扣分值
     **/
    public Map<String, DeductScore> deduct(Map<String, List<DiseaseInstanceVO>> bkMap) {
        Map<String, DeductScore> scoreMap = new HashMap<>();
        if (bkMap.size() != 0) {
            for (String str : bkMap.keySet()) {
                DeductScore deductScore = new DeductScore();
                List<DiseaseInstanceVO> diseaseInstances1 = bkMap.get(str);
                Map<Integer, List<DiseaseInstanceVO>> diseaseMap = new HashMap<>();
                //病害分类
                for (DiseaseInstanceVO diseaseInstance : diseaseInstances1) {
                    if (diseaseInstance.getLevelLimit() == 1){
                        deductScore.setSpecial("限制");
                    }
                    if (diseaseMap.containsKey(diseaseInstance.getDiseaseId())) {
                        diseaseMap.get(diseaseInstance.getDiseaseId()).add(diseaseInstance);
                    } else {
                        diseaseMap.put(diseaseInstance.getDiseaseId(), new ArrayList<>());
                        diseaseMap.get(diseaseInstance.getDiseaseId()).add(diseaseInstance);
                    }
                }

                List<Double> list = new ArrayList<>();
                //相同病害只取最高分
                for (Integer integer : diseaseMap.keySet()) {
                    List<DiseaseInstanceVO> diseaseInstances = diseaseMap.get(integer);
                    double max = 0;
                    for (DiseaseInstanceVO diseaseInstance : diseaseInstances) {
                        if (diseaseInstance.getDeduct() > max) {
                            max = diseaseInstance.getDeduct();
                        }
                    }
                    list.add(max);
                }

                double score = 0;
                double total = 0;
                for (Double aDouble : list) {
                    total += aDouble;
                }
                if (total != 0) {
                    for (Double aDouble : list) {
                        double uhi = aDouble / total;
                        double whi = 3.0 * uhi * uhi * uhi - 5.5 * uhi * uhi + 3.5 * uhi;
                        score += aDouble * whi;
                        DecimalFormat df = new DecimalFormat("######0.00");
                        score = Double.parseDouble(df.format(score));
                    }
                    if (score > 100) {
                        score = 100;
                    }
                    deductScore.setScore(score);
                    scoreMap.put(str, deductScore);
                }
            }

        }
        return scoreMap;
    }

    @Override
    public List<BridgeRoad> downloadTestReport(Integer roadId) {
        BridgeRoad bridgeRoad = new BridgeRoad();
        bridgeRoad.setId(roadId);
        return bridgeRoadDao.query(bridgeRoad);
    }

    @Override
    public Map<String, Object> getBSInformation(BridgeSituationFilter bridgeSituationFilter) {
        List<BridgeSituationDTO> returnList = new ArrayList<>();
        Integer requestTypeId = bridgeSituationFilter.getType();
        //1:线路 2：桥跨 3：桥面系 4:上部结构 5：下部结构 6：上部结构构件 7：下部结构构件 8：其他结构
        if (requestTypeId == 1) {
            BridgeRoad roadModel = bridgeRoadDao.findById(bridgeSituationFilter.getId());
            Integer typeId = roadModel.getBridgeTypeId();
            addData(returnList, "线路名称", "" + roadModel.getName(), "");
            BridgeType bridgeTypeModel = bridgeTypeDao.findById(typeId);
            addData(returnList, "桥梁形式", "" + bridgeTypeModel.getName(), "");
            //7 8 为特殊桥（人行天桥）
            if (typeId == 7 || typeId == 8) {
                addData(returnList, "主跨数量", "" + roadModel.getMainSpanNumber(), "");
                addData(returnList, "梯道数", "" + roadModel.getStairwayNumber(), "");
                addData(returnList, "梯道跨数", "" + roadModel.getStairwaySpanNumber(), "");
            } else {
                addData(returnList, "桥跨数量", "" + roadModel.getSpanNumber(), "");
            }
        } else if (requestTypeId == 2) {
            BridgeSpan spanModel = bridgeSpanDao.findById(bridgeSituationFilter.getId());
            Integer typeId = spanModel.getBridgeShape();
            // 1：直线桥 2：曲线桥
            if (typeId == 1) {
                addData(returnList, "桥梁形状", "直线桥", "");
                addData(returnList, "桥梁长度", "" + spanModel.getLength(), "m");
            } else if (typeId == 2) {
                addData(returnList, "桥梁形状", "曲线桥", "");
                addData(returnList, "外弧长", "" + spanModel.getOuterArcLength(), "m");
                addData(returnList, "内弧长", "" + spanModel.getInnerArcLength(), "m");
                Integer convex = spanModel.getConvex();
                //1：凹 2：凸
                if (convex == 1) {
                    addData(returnList, "桥梁凹凸", "凹", "");
                } else if (convex == 2) {
                    addData(returnList, "桥梁凹凸", "凸", "");
                }
            }
        } else if (requestTypeId == 3) {
            BridgeRoad roadModel = bridgeExpDao.selectRoadByDkId(bridgeSituationFilter.getId());
            Integer bridgeTypeId = roadModel.getBridgeTypeId();
            BridgeDeckSystem dkModel = bridgeDeckSystemDao.findById(bridgeSituationFilter.getId());
            addData(returnList, "桥梁走向", dkModel.getDirection(), "");
            //7 8人行天桥特殊桥
            if (bridgeTypeId == 7 || bridgeTypeId == 8) {
                addData(returnList, "人行道宽", "" + dkModel.getSidewalkWidth(), "m");
                addData(returnList, "护栏(左)", "" + dkModel.getLeftRailWidth(), "m");
                addData(returnList, "护栏(右)", "" + dkModel.getRightRailWidth(), "m");
                Integer expansionJoint = dkModel.getExpansionJoint();
                //伸缩缝有无 0：无 1：有
                if (expansionJoint == 0) {
                    addData(returnList, "伸缩缝", "无", "m");
                } else if (expansionJoint == 1) {
                    addData(returnList, "伸缩缝", "有", "m");
                }
                addData(returnList, "梯道", dkModel.getStairway(), "");
                addData(returnList, "梯道长", "" + dkModel.getStairwayLength(), "m");
                addData(returnList, "梯道宽", "" + dkModel.getStairwayWidth(), "m");
                addData(returnList, "桥头搭板长", "" + dkModel.getBridgeHeadBoardLength(), "m");
            } else {
                addData(returnList, "倾斜角度", dkModel.getTiltAngle() + "°", "");
                Integer sidewalk = dkModel.getSidewalk();
                //人行道有无 0：无 1：有
                if (sidewalk == 0) {
                    addData(returnList, "人行道", "无", "");
                } else if (sidewalk == 1) {
                    addData(returnList, "人行道", "有", "");
                    addData(returnList, "人形道宽(左)", "" + dkModel.getLeftSidewalkWidth(), "m");
                    addData(returnList, "人形道宽(右)", "" + dkModel.getRightSidewalkWidth(), "m");
                }
                addData(returnList, "行车道宽", "" + dkModel.getLaneWidth(), "m");
                addData(returnList, "中间隔离栏", "" + dkModel.getMidleBarrierWidth(), "m");
                addData(returnList, "护栏(左)", "" + dkModel.getLeftRailWidth(), "m");
                addData(returnList, "护栏(右)", "" + dkModel.getRightRailWidth(), "m");
                Integer expansionJoint = dkModel.getExpansionJoint();
                //伸缩缝有无 0：无 1：有
                if (expansionJoint == 0) {
                    addData(returnList, "伸缩缝", "无", "");
                } else if (expansionJoint == 1) {
                    addData(returnList, "伸缩缝", "有", "m");
                }
                addData(returnList, "桥头搭板长", "" + dkModel.getBridgeHeadBoardLength(), "m");
            }
        } else if (requestTypeId == 4) {
            BridgeRoad roadModel = bridgeExpDao.selectRoadBySupId(bridgeSituationFilter.getId());
            Integer bridgeTypeId = roadModel.getBridgeTypeId();
            BridgeSupstructure supModel = bridgeSupstructureDao.findById(bridgeSituationFilter.getId());
            if (bridgeTypeId == 1 || bridgeTypeId == 3) {
                Integer beamTypeId = supModel.getBeamType();
                //梁类型 11：T梁 12：箱梁 13：板梁
                if (beamTypeId == 11) {
                    addData(returnList, "梁类型", "T梁", "");
                } else if (beamTypeId == 12) {
                    addData(returnList, "梁类型", "箱梁", "");
                } else if (beamTypeId == 13) {
                    addData(returnList, "梁类型", "板梁", "");
                }
                addData(returnList, "梁数量", "" + supModel.getBeamNumber(), "");
            } else if (bridgeTypeId == 2) {
                addData(returnList, "挂梁数", "" + supModel.getHangingBeam(), "");
                addData(returnList, "挂梁支座数", "" + supModel.getHangingBeamSupport(), "");
                addData(returnList, "防落梁装置数", "" + supModel.getAntiFallingBeam(), "");
            } else if (bridgeTypeId == 4) {
                addData(returnList, "桁片数", "" + supModel.getTruss(), "");
                addData(returnList, "主节点数", "" + supModel.getPrimaryNode(), "");
                addData(returnList, "纵梁数", "" + supModel.getStringer(), "");
                addData(returnList, "横梁数", "" + supModel.getCrossBeam(), "");
                addData(returnList, "连接件数", "" + supModel.getConnector(), "");
            } else if (bridgeTypeId == 5) {
                addData(returnList, "主拱圈数", "" + supModel.getArchRing(), "");
                addData(returnList, "横向联系数", "" + supModel.getHorizontaConnection(), "");
            } else if (bridgeTypeId == 6) {
                addData(returnList, "主拱圈数", "" + supModel.getArchRing(), "");
                addData(returnList, "拱上构造数", "" + supModel.getArchStructure(), "");
                addData(returnList, "横向联系数", "" + supModel.getHorizontaConnection(), "");
            } else if (bridgeTypeId == 7) {
                addData(returnList, "梁数量", "" + supModel.getBeamNumber(), "");
                addData(returnList, "外部装饰板数", "" + supModel.getExteriorTrimPanel(), "");
                addData(returnList, "横向联系数", "" + supModel.getHorizontaConnection(), "");
            } else if (bridgeTypeId == 8) {
                addData(returnList, "桁片数", "" + supModel.getTruss(), "");
                addData(returnList, "主节点数", "" + supModel.getPrimaryNode(), "");
                addData(returnList, "纵梁数", "" + supModel.getStringer(), "");
                addData(returnList, "横梁数", "" + supModel.getCrossBeam(), "");
                addData(returnList, "连接件数", "" + supModel.getConnector(), "");
                addData(returnList, "外部装饰板数", "" + supModel.getExteriorTrimPanel(), "");
            }
        } else if (requestTypeId == 5) {
            BridgeRoad roadModel = bridgeExpDao.selectRoadBySubId(bridgeSituationFilter.getId());
            Integer bridgeTypeId = roadModel.getBridgeTypeId();
            BridgeSubstructure subModel = bridgeSubstructureDao.findById(bridgeSituationFilter.getId());
            addData(returnList, "单排墩数", "" + subModel.getSingleRowDun(), "");
            if (bridgeTypeId == 1 || bridgeTypeId == 2 || bridgeTypeId == 3 || bridgeTypeId == 4) {
                addData(returnList, "单排支座数", "" + subModel.getSingleRowSupport(), "");
            } else if (bridgeTypeId == 5 || bridgeTypeId == 6) {
                addData(returnList, "拱脚数", "" + subModel.getArchFoot(), "");
            } else if (bridgeTypeId == 7 || bridgeTypeId == 8) {
                addData(returnList, "单排支座数", "" + subModel.getSingleRowSupport(), "");
                addData(returnList, "外部装饰板", "" + subModel.getExteriorTrimPanel(), "");
            }
        } else if (requestTypeId == 6) {
            BridgeRoad roadModel = bridgeExpDao.selectRoadBySupComponentId(bridgeSituationFilter.getId());
            Integer bridgeTypeId = roadModel.getBridgeTypeId();
            BridgeSupcomponent componentModel = bridgeSupcomponentDao.findById(bridgeSituationFilter.getId());
            Integer componentId = componentModel.getComponentId();
            if (bridgeTypeId == 1 || bridgeTypeId == 2) {
                // 7主梁 8横向联系
                if (componentId == 7) {
                    addData(returnList, "腹板高度", "" + componentModel.getWebPlateHight(), "m");
                    addData(returnList, "翼板宽度", "" + componentModel.getWingPlateWidth(), "m");
                    addData(returnList, "底板宽度", "" + componentModel.getBaseplateWidth(), "m");
                } else if (componentId == 8) {
                    addData(returnList, "横隔板高度", "" + componentModel.getDiaphragmsHeight(), "m");
                    addData(returnList, "横隔板道数", "" + componentModel.getDiaphragmsNumber(), "");
                }
            } else if (bridgeTypeId == 3) {
                // 9悬臂梁 10挂梁 11挂梁支座 12防落梁装置
                if (componentId == 9) {
                    addData(returnList, "悬臂长", "" + componentModel.getCantileverLenght(), "m");
                    addData(returnList, "翼板宽", "" + componentModel.getWingPlateWidth(), "m");
                    addData(returnList, "腹板高", "" + componentModel.getWebPlateHight(), "m");
                    addData(returnList, "底板宽", "" + componentModel.getBaseplateWidth(), "m");
                } else if (componentId == 10) {
                    addData(returnList, "挂梁长", "" + componentModel.getHangingBeamLength(), "m");
                    addData(returnList, "翼板宽", "" + componentModel.getWingPlateWidth(), "m");
                    addData(returnList, "腹板高", "" + componentModel.getWebPlateHight(), "m");
                    addData(returnList, "底板宽", "" + componentModel.getBaseplateWidth(), "m");
                } else if (componentId == 11 || componentId == 12) {
                    addData(returnList, "构件编号", "" + componentModel.getCode(), "");
                }
            } else if (bridgeTypeId == 4) {
                // 13桁片 14主节点 15纵梁 16横梁 17连接件
                if (componentId == 13 || componentId == 14 || componentId == 15 || componentId == 16 || componentId == 17) {
                    addData(returnList, "构件编号", "" + componentModel.getCode(), "");
                }
            } else if (bridgeTypeId == 5) {
                // 18主拱圈 8横向联系
                if (componentId == 18 || componentId == 8) {
                    addData(returnList, "构件编号", "" + componentModel.getCode(), "");
                }
            } else if (bridgeTypeId == 6) {
                // 18主拱圈 19拱上构造 8横向联系
                if (componentId == 18 || componentId == 8 || componentId == 19) {
                    addData(returnList, "构件编号", "" + componentModel.getCode(), "");
                }
            } else if (bridgeTypeId == 7) {
                // 7主梁 8横向联系 20外部装饰板
                if (componentId == 7) {
                    addData(returnList, "腹板高度", "" + componentModel.getWebPlateHight(), "m");
                    addData(returnList, "翼板宽度", "" + componentModel.getWingPlateWidth(), "m");
                    addData(returnList, "底板宽度", "" + componentModel.getBaseplateWidth(), "m");
                } else if (componentId == 8) {
                    addData(returnList, "横隔板高度", "" + componentModel.getDiaphragmsHeight(), "m");
                    addData(returnList, "横隔板道数", "" + componentModel.getDiaphragmsNumber(), "");
                } else if (componentId == 20) {
                    addData(returnList, "构件编号", "" + componentModel.getCode(), "");
                }
            }

        } else if (requestTypeId == 7) {
            BridgeRoad roadModel = bridgeExpDao.selectRoadBySubComponentId(bridgeSituationFilter.getId());
            Integer bridgeTypeId = roadModel.getBridgeTypeId();
            BridgeSubcomponent componentModel = bridgeSubcomponentDao.findById(bridgeSituationFilter.getId());
            Integer componentId = componentModel.getComponentId();
            if (bridgeTypeId == 1 || bridgeTypeId == 2 || bridgeTypeId == 3 || bridgeTypeId == 4) {
                // 21盖梁 22墩身 23基础  24支座 26台帽 27台身 28耳墙
                if (componentId == 21) {
                    addData(returnList, "盖梁高度", "" + componentModel.getCapBeamsHeight(), "m");
                    addData(returnList, "盖梁宽度", "" + componentModel.getCapBeamsWidth(), "m");
                    addData(returnList, "盖梁厚度", "" + componentModel.getCapBeamsThick(), "m");
                } else if (componentId == 22) {
                    Integer pierShape = componentModel.getPierShape();
                    //墩身 1：矩形 2：圆形
                    if (pierShape == 1) {
                        addData(returnList, "墩身", "矩形", "");
                        addData(returnList, "墩身宽度", "" + componentModel.getPierWidth(), "m");
                        addData(returnList, "墩身厚度", "" + componentModel.getPierThick(), "m");
                        addData(returnList, "墩身高度", "" + componentModel.getPierHeight(), "m");
                    } else if (pierShape == 2) {
                        addData(returnList, "墩身", "圆形", "");
                        addData(returnList, "墩身高度", "" + componentModel.getPierHeight(), "m");
                        addData(returnList, "墩身半径", "" + componentModel.getPierRadius(), "m");
                    }
                } else if (componentId == 26) {
                    addData(returnList, "台帽高度", "" + componentModel.getAbutmentCapHeight(), "m");
                } else if (componentId == 27) {
                    addData(returnList, "台身宽度", "" + componentModel.getAbutmentWidth(), "m");
                    addData(returnList, "台身高度", "" + componentModel.getAbutmentHeight(), "m");
                    addData(returnList, "台身厚度", "" + componentModel.getAbutmentThick(), "m");
                }

            } else if (bridgeTypeId == 5 || bridgeTypeId == 6) {
                // 21盖梁 22墩身 23基础  25拱脚 26台帽 27台身 28耳墙
                if (componentId == 21) {
                    addData(returnList, "盖梁高度", "" + componentModel.getCapBeamsHeight(), "m");
                    addData(returnList, "盖梁宽度", "" + componentModel.getCapBeamsWidth(), "m");
                    addData(returnList, "盖梁厚度", "" + componentModel.getCapBeamsThick(), "m");
                } else if (componentId == 22) {
                    Integer pierShape = componentModel.getPierShape();
                    //墩身 1：矩形 2：圆形
                    if (pierShape == 1) {
                        addData(returnList, "墩身", "矩形", "");
                        addData(returnList, "墩身宽度", "" + componentModel.getPierWidth(), "m");
                        addData(returnList, "墩身厚度", "" + componentModel.getPierThick(), "m");
                        addData(returnList, "墩身高度", "" + componentModel.getPierHeight(), "m");
                    } else if (pierShape == 2) {
                        addData(returnList, "墩身", "圆形", "");
                        addData(returnList, "墩身高度", "" + componentModel.getPierHeight(), "m");
                        addData(returnList, "墩身半径", "" + componentModel.getPierRadius(), "m");
                    }
                } else if (componentId == 26) {
                    addData(returnList, "台帽高度", "" + componentModel.getAbutmentCapHeight(), "m");
                } else if (componentId == 27) {
                    addData(returnList, "台身宽度", "" + componentModel.getAbutmentWidth(), "m");
                    addData(returnList, "台身高度", "" + componentModel.getAbutmentHeight(), "m");
                    addData(returnList, "台身厚度", "" + componentModel.getAbutmentThick(), "m");
                }
            } else if (bridgeTypeId == 7 || bridgeTypeId == 8) {
                // 21盖梁 22墩身 23基础  24支座 26台帽 27台身 20外部装饰板
                if (componentId == 21) {
                    addData(returnList, "盖梁高度", "" + componentModel.getCapBeamsHeight(), "m");
                    addData(returnList, "盖梁宽度", "" + componentModel.getCapBeamsWidth(), "m");
                    addData(returnList, "盖梁厚度", "" + componentModel.getCapBeamsThick(), "m");
                } else if (componentId == 22) {
                    Integer pierShape = componentModel.getPierShape();
                    //墩身 1：矩形 2：圆形
                    if (pierShape == 1) {
                        addData(returnList, "墩身", "矩形", "");
                        addData(returnList, "墩身宽度", "" + componentModel.getPierWidth(), "m");
                        addData(returnList, "墩身厚度", "" + componentModel.getPierThick(), "m");
                        addData(returnList, "墩身高度", "" + componentModel.getPierHeight(), "m");
                    } else if (pierShape == 2) {
                        addData(returnList, "墩身", "圆形", "");
                        addData(returnList, "墩身高度", "" + componentModel.getPierHeight(), "m");
                        addData(returnList, "墩身半径", "" + componentModel.getPierRadius(), "m");
                    }
                } else if (componentId == 26) {
                    addData(returnList, "台帽高度", "" + componentModel.getAbutmentCapHeight(), "m");
                } else if (componentId == 27) {
                    addData(returnList, "台身宽度", "" + componentModel.getAbutmentWidth(), "m");
                    addData(returnList, "台身高度", "" + componentModel.getAbutmentHeight(), "m");
                    addData(returnList, "台身厚度", "" + componentModel.getAbutmentThick(), "m");
                }
            }
        }else if (requestTypeId == 8){
            BridgeOtherStructure byId = bridgeOtherStructureDao.findById(bridgeSituationFilter.getId());
            addData(returnList, "描述", "" + byId.getDescript(), "");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", returnList);

        StructPhotoFilter structPhotoFilter = new StructPhotoFilter();
        structPhotoFilter.setMoniPlanStructId(bridgeSituationFilter.getMoniPlanStructId());
        List<Byte> byteList = new ArrayList<>();
        byteList.add((byte) 1);
        byteList.add((byte) 2);
        structPhotoFilter.setStatus(byteList);
        structPhotoFilter.setTargetId(bridgeSituationFilter.getId());
        if (requestTypeId == 1 || requestTypeId == 2) {
            structPhotoFilter.setType(requestTypeId);
        } else if (requestTypeId == 3) {
            structPhotoFilter.setType(3);
            structPhotoFilter.setPartType(1);
        } else if (requestTypeId == 4) {
            structPhotoFilter.setType(3);
            structPhotoFilter.setPartType(2);
        } else if (requestTypeId == 5) {
            structPhotoFilter.setType(3);
            structPhotoFilter.setPartType(3);
        } else if (requestTypeId == 6) {
            structPhotoFilter.setType(4);
            structPhotoFilter.setPartType(2);
        } else if (requestTypeId == 7) {
            structPhotoFilter.setType(4);
            structPhotoFilter.setPartType(3);
        }else if (requestTypeId == 8) {
            structPhotoFilter.setType(3);
            structPhotoFilter.setPartType(4);
        }

        List<StructPhoto> bridgeStructPhoto = getBridgeStructPhoto(structPhotoFilter);
        map.put("photo", bridgeStructPhoto);
        return map;
    }

    public void delFile(String filePath) {
        File file = new File(path + filePath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                file1.delete();
            }
        }
    }

    public void addData(List<BridgeSituationDTO> list, String name, String value, String unit) {
        BridgeSituationDTO situationDTO = new BridgeSituationDTO();
        situationDTO.setName(name);
        situationDTO.setValue(value);
        situationDTO.setUnit(unit);
        list.add(situationDTO);
    }
}
