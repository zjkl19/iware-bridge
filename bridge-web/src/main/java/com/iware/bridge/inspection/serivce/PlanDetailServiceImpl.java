package com.iware.bridge.inspection.serivce;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.app.assess.service.AppUnzipServiceImpl;
import com.iware.bridge.app.assess.service.LoginUserService;
import com.iware.bridge.inspection.dao.InspectionDiseaseExpDao;
import com.iware.bridge.inspection.dao.PlanDetailExpDao;
import com.iware.bridge.inspection.dao.WordDataDao;
import com.iware.bridge.inspection.vo.*;
import com.iware.bridge.model.dao.global.PhotoDao;
import com.iware.bridge.model.dao.global.StructureDao;
import com.iware.bridge.model.dao.inspection.InspectionDiseaseInstanceDao;
import com.iware.bridge.model.dao.inspection.MaintainItemDao;
import com.iware.bridge.model.dao.inspection.PlanDetailDao;
import com.iware.bridge.model.dao.inspection.PlanInfoDao;
import com.iware.bridge.model.dao.user.UserDao;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.inspection.*;
import com.iware.bridge.model.entity.user.User;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.exception.BusinessException;
import com.iware.common.pojo.FieldInfo;
import com.iware.common.properties.PowerProperties;
import com.iware.common.utils.ExcelUtil;
import com.iware.common.utils.WordTempUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LBX
 * @date 2021-7-29
 */

@Service
public class PlanDetailServiceImpl implements PlanDetailService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private WordDataDao wordDataDao;
    @Autowired
    private PhotoDao photoDao;
    @Autowired
    private PlanInfoDao planInfoDao;
    @Autowired
    private PlanDetailExpDao planDetailExpDao;
    @Autowired
    private InspectionDiseaseExpDao inspectionDiseaseExpDao;
    @Autowired
    private MaintainItemDao maintainItemDao;
    @Autowired
    private PlanDetailDao planDetailDao;
    @Autowired
    private InspectionDiseaseInstanceDao inspectionDiseaseInstanceDao;
    @Autowired
    private ReceiveTimeService receiveTimeService;
    @Autowired
    private LoginUserService loginUserServ;
    @Autowired
    private PowerProperties power;
    @Resource
    private StructureDao structureDao;

    @Value("${file.upload-path}")
    private String path;
    @Value("${file.template-path.inspection-word}")
    private String inspectionTemplePath;
    @Value("${file.template-path.inspection-word-tunnel}")
    private String inspectionTunnelTemplePath;
    @Value("${file.template-path.maintain-word}")
    private String maintainTemplePath;

    SimpleDateFormat daySdf = new SimpleDateFormat("yyyy-MM-dd");

    Logger logger=LoggerFactory.getLogger(PlanDetailServiceImpl.class);

    @Override
    public PageInfo<PlanDetailVO> listPlanDetail(Integer pageNum, Integer pageSize, PlanDetailFilter filter, Boolean isApp) {

        if(isApp){
            Integer unitId=loginUserServ.getUser().getUnitId();
            PageHelper.startPage(pageNum, pageSize);
            return new PageInfo<PlanDetailVO>(planDetailExpDao.listPlanDetail(unitId, 1,
                    power.getInspectionPower(), filter));
        }else {
            PageHelper.startPage(pageNum, pageSize);
            return new PageInfo<PlanDetailVO>(planDetailExpDao.listPlanDetail(ThreadLocalMap.getUnitId(), ThreadLocalMap.getRoleId(),
                    power.getInspectionPower(), filter));
        }
    }

    @Override
    public void addPlanDetail(PlanDetail detail) {
        Structure structure=structureDao.findById(detail.getStructureId());
        if(structure.getStructureType()==1&&structure.getMaintainGrade()==null){
            throw new BusinessException(structure.getName()+"无养护等级信息,无法生成细项计划");
        }
        if(detail.getInspectionTime().getTime() < (new Date().getTime() - 24 * 3600 * 1000)){
            detail.setStatus(-1);
        }else{
            detail.setStatus(0);
        }
        planDetailDao.save(detail);
        PlanInfo planInfo = planInfoDao.findById(detail.getPlanId());
        //判断状态
        PlanDetail condition = new PlanDetail();
        condition.setPlanId(planInfo.getId());
        condition.setStatus(1);
        if(planDetailDao.query(condition).size() > 0){
            planInfo.setStatus(1);
        }else{
            planInfo.setStatus(0);
        }
        planInfoDao.update(planInfo);
    }

    @Override
    public void delPlanDetail(Integer planDetailId) {
        PlanDetail planDetail = planDetailDao.findById(planDetailId);
        PlanInfo planInfo = planInfoDao.findById(planDetail.getPlanId());
        PlanDetail condition = new PlanDetail();
        condition.setStatus(0);
        condition.setPlanId(planInfo.getId());
        planDetailDao.deleteById(planDetailId);
        if(planDetailDao.query(condition).size() <= 0){
            planInfo.setStatus(2);
            planInfoDao.update(planInfo);
        }
    }

    @Override
    public void updPlanDetail(PlanDetailVO planDetailVO) {
        planDetailDao.update(planDetailVO);
    }

    @Override
    public PlanDetailVO selPlanDetail(Integer planDetailId) {
        PlanDetailVO result = planDetailExpDao.selPlanDetail(planDetailId);
        result.setOtherRemark(planDetailExpDao.selOtherRemark(planDetailId));
        return result;
    }

    @Override
    public PageInfo<MaintainItemVO> listMaintainItem(Integer pageNum, Integer pageSize, PlanDetailFilter filter, Boolean isApp) {
        PageInfo<MaintainItemVO> result;
        if(isApp){
            Integer unitId=loginUserServ.getUser().getUnitId();
            PageHelper.startPage(pageNum, pageSize);
            result = new PageInfo<>(planDetailExpDao.listMaintainItem(unitId,
                    1,
                    power.getMaintainPower(),
                    filter)
            );
        }else {
            PageHelper.startPage(pageNum, pageSize);
            result = new PageInfo<>(planDetailExpDao.listMaintainItem(ThreadLocalMap.getUnitId(),
                    ThreadLocalMap.getRoleId(),
                    power.getMaintainPower(),
                    filter)
            );
        }
        //维养前图片筛选条件
        Photo beforeCondition = new Photo();
        beforeCondition.setType(GlobalConstant.PHOTO_MAINTAIN_BEFORE);
        //维养中图片筛选条件
        Photo progressCondition = new Photo();
        progressCondition.setType(GlobalConstant.PHOTO_MAINTAIN_PROCESS);
        //维养后图片筛选条件
        Photo afterCondition = new Photo();
        afterCondition.setType(GlobalConstant.PHOTO_MAINTAIN_AFTER);
        for(MaintainItemVO maintainItem:result.getList()) {
            //前
            beforeCondition.setTargetId(maintainItem.getId());
            maintainItem.setBeforeList(photoDao.query(beforeCondition));
            //中
            progressCondition.setTargetId(maintainItem.getId());
            maintainItem.setProgressList(photoDao.query(progressCondition));
            //后
            afterCondition.setTargetId(maintainItem.getId());
            maintainItem.setAfterList(photoDao.query(afterCondition));
        }
        return result;
    }

    @Override
    public List<PlanDetailVO> listInspectionRecord(PlanRecordFilter filter) {
        return planDetailExpDao.listInspectionRecord(ThreadLocalMap.getUnitId(), ThreadLocalMap.getRoleId(),
                power.getInspectionPower(), filter);
    }

    @Override
    public List<InspectionDiseaseInstanceVO> getInspectionDiseaseByStructureId(Integer structureId, String words) {
        List<InspectionDiseaseInstanceVO> result = planDetailExpDao.getInspectionDiseaseByStructureId(structureId, words);
        for(InspectionDiseaseInstanceVO diseaseInstanceVO : result){
            Photo condition=new Photo();
            condition.setTargetId(diseaseInstanceVO.getId());
            condition.setType(GlobalConstant.PHOTO_INSPECTION);
            diseaseInstanceVO.setPhoto(photoDao.query(condition));
        }
        return result;
    }

    @Override
    public List<MaintainItemVO> listMaintainRecordByTime(String date) {
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();
        if(roleId == null){
            throw new BusinessException("登录信息缺失");
        }else{
            if ((roleId==0||roleId==1)&&unitId == null){
                throw new BusinessException("登录信息缺失");
            }
        }
        return planDetailExpDao.listMaintainRecordByTime(date, unitId, roleId, power.getMaintainPower());
    }

    @Override
    public List<PlanDetailVO> listInspectionRecordByTime(String date) {
        Integer unitId=ThreadLocalMap.getUnitId();
        Integer roleId=ThreadLocalMap.getRoleId();
        if(roleId == null){
            throw new BusinessException("登录信息缺失");
        }else{
            if ((roleId==0||roleId==1)&&unitId == null){
                throw new BusinessException("登录信息缺失");
            }
        }
        return planDetailExpDao.listInspectionRecordByTime(date, unitId, roleId, power.getInspectionPower());
    }

    @Override
    public String batchExport(Integer type, List<Integer> ids, PlanRecordFilter filter, Integer exportAll) {

        String fileName;
        String sheetName = "sheet1";
        String now = ExcelUtil.formatDate(new Date(), "yyyy-MM-dd");
        List<FieldInfo> fieldInfoList = new ArrayList<>();
        if (type == 2) {
            List<MaintainItemVO> result;
            if (exportAll == 1) {
                result = planDetailExpDao.listMaintainRecord(ThreadLocalMap.getUnitId(), ThreadLocalMap.getRoleId(),
                        power.getMaintainPower(), filter);
            }else{
                result = planDetailExpDao.listMaintainRecordByIds(ids);
            }
            for(MaintainItemVO vo:result){
                vo.setMaintainDate(new SimpleDateFormat("yyyy-MM-dd").format(vo.getMaintainTime()));
                vo.setProposedDate(new SimpleDateFormat("yyyy-MM-dd").format(vo.getProposedTime()));
                vo.setMaintainName(vo.getName());
            }
            fieldInfoList.add(new FieldInfo("maintainPlanName", "维养计划"));
            fieldInfoList.add(new FieldInfo("structureName", "结构物名称"));
            fieldInfoList.add(new FieldInfo("maintainName", "维修项目"));
            fieldInfoList.add(new FieldInfo("maintainTypeName", "维修类型"));
            fieldInfoList.add(new FieldInfo("quantities", "工作量"));
            fieldInfoList.add(new FieldInfo("proposedDate", "计划维修时间"));
            fieldInfoList.add(new FieldInfo("maintainDate", "实际维修时间"));
            fieldInfoList.add(new FieldInfo("creator", "维修人员"));
            fileName = String.format("%s-%s%s", "维修记录卡", now, ".xls");
            return ExcelUtil.createExcel(fileName, sheetName, fieldInfoList, result, MaintainItemVO.class);
        } else {
            List<PlanDetailVO> result;
            if(exportAll == 1){
                result = planDetailExpDao.listInspectionRecord(ThreadLocalMap.getUnitId(), ThreadLocalMap.getRoleId(),
                        power.getInspectionPower(), filter);
            }else{
                result = planDetailExpDao.listInspectionRecordByIds(ids);
            }
            // ->压缩包<-
            //将result中的每个plandetail生成一份报告
//            File zipDir=new File(path+"/巡查报告批量导出");
//            new UnZipAnRar().deleteFile(zipDir);
//            zipDir.mkdirs();
//            File zipToDelete=new File(path+"/"+"巡查报告批量导出.zip");
//            if(zipToDelete.exists()){
//                zipToDelete.delete();
//            }
//            for(PlanDetailVO vo:result) {
//                String toCopyPath=downloadInspectionReport(vo.getId());
//                copyFile(path+toCopyPath,zipDir+"/"+vo.getStructureName()+
//                        new SimpleDateFormat("yyyy-MM-dd").format(vo.getInspectionTime())+
//                        "日常巡查日报表.docx");
//            }
//            try {
//                new InZipUntil().inZip(zipDir.getPath(), new File(path+"/"+"巡查报告批量导出.zip"));
//            }catch (Exception e){
//                e.printStackTrace();
//                Logger logger= LoggerFactory.getLogger(AppUnzipServiceImpl.class);
//                StringBuilder builder=new StringBuilder();
//                for(StackTraceElement element:e.getStackTrace()){
//                    builder.append(element).append("\n");
//                }
//                logger.error(builder.toString());
//            }
//            return path+"/巡查报告批量导出.zip";
            // ->excel<-
//            for(PlanDetailVO vo:result){
//                vo.setInspectionDate(new SimpleDateFormat("yyyy-MM-dd").format(vo.getInspectionTime()));
//            }
//            fieldInfoList.add(new FieldInfo("inspectionDate", "巡查时间"));
//            fieldInfoList.add(new FieldInfo("structureName", "结构物名称"));
//            fieldInfoList.add(new FieldInfo("inspectionPlanType", "巡查类型"));
//            fieldInfoList.add(new FieldInfo("inspector", "巡查人员"));
//            fieldInfoList.add(new FieldInfo("diseaseCount", "巡查病害数"));
//            fileName = String.format("%s-%s%s", "巡查记录卡", now, ".xls");
//            return ExcelUtil.createExcel(fileName, sheetName, fieldInfoList, result, PlanDetailVO.class);

            // ->合并单份word<-
            //将result中的每个plandetail生成一份报告
            NiceXWPFDocument resultWord=null;
            for(PlanDetailVO vo:result) {
                String toCopyPath=downloadInspectionReport(vo.getId());
                NiceXWPFDocument next;
                try {
                    next = new NiceXWPFDocument(new FileInputStream(new File(path+toCopyPath)));
                    if(resultWord!=null){
                        XWPFParagraph p=resultWord.createParagraph();
                        p.setPageBreak(true);
                        resultWord=resultWord.merge(next);
                    }else{
                        resultWord=next;
                    }
                }catch (Exception e){
                    logger.debug(e.getMessage());
                }
            }
            String outputFileName=path + "批量导出合并文档" +".docx";
            File outFile = new File(outputFileName);
            // 如果没有文件夹，则新建
            if(!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            // 如果存在了这个简报，则删除，重新保存
            if(outFile.exists()) {
                outFile.delete();
            }
            // 重新保存
            FileOutputStream out=null;
            try {
                out = new FileOutputStream(outFile);
                if(resultWord!=null) {
                    resultWord.write(out);
                }
                out.close();
            }catch (Exception e){
                logger.debug(e.getMessage());
            }finally {
                if(out!=null) {
                    try {
                        out.close();
                    }catch (Exception e){
                        logger.debug(e.getMessage());
                    }
                }
            }
            /** IO流输出结束 */
            return outputFileName;
        }
    }

    @Override
    public PageInfo<MaintainItemVO> listMaintainRecord(Integer pageNum, Integer pageSize, PlanRecordFilter filter) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(planDetailExpDao.listMaintainRecord(ThreadLocalMap.getUnitId(),
                ThreadLocalMap.getRoleId(), power.getMaintainPower(), filter));
    }

    @Override
    public MaintainItemVO selMaintainItem(Integer maintainItemId) {
        return planDetailExpDao.selMaintainItem(maintainItemId);
    }

    @Override
    public void deleteMaintainItemById(Integer planDetailId) {
        InspectionDiseaseInstance inspectionDiseaseInstance = inspectionDiseaseInstanceDao.findById(
                maintainItemDao.findById(planDetailId).getDiseaseInstanceId());
        if(inspectionDiseaseInstance != null) {
            inspectionDiseaseInstance.setStatus(0);
            inspectionDiseaseInstanceDao.update(inspectionDiseaseInstance);
        }
        PlanInfo toUpdate = planInfoDao.findById(maintainItemDao.findById(planDetailId).getPlanId());
        MaintainItem condition = new MaintainItem();
        condition.setPlanId(toUpdate.getId());
        condition.setStatus(0);
        maintainItemDao.deleteById(planDetailId);
        List<MaintainItem> zeroItems = maintainItemDao.query(condition);
        condition.setStatus(1);
        List<MaintainItem> oneItems = maintainItemDao.query(condition);
        if(zeroItems.size() == 0){
            toUpdate.setStatus(2);
        }else{
            if(oneItems.size() == 0){
                toUpdate.setStatus(0);
            }else{
                toUpdate.setStatus(1);
            }
        }
        planInfoDao.update(toUpdate);
    }

    @Override
    public void addMaintainItem(MaintainItem maintainItem){
        User user = userDao.findById(ThreadLocalMap.getUserId());
        maintainItem.setCreator(user.getRealName());
        Integer projectId = planInfoDao.findById(maintainItem.getPlanId()).getProjectId();
        List<ReceiveTime> receiveTimes=receiveTimeService.getReceiveTime(projectId, false);
        if((ThreadLocalMap.getRoleId() == 0 || ThreadLocalMap.getRoleId() == 1)
                && !receiveTimeService.isInReceiveTime(receiveTimes, maintainItem.getProposedTime())) {
            throw new BusinessException("维养细项拟定维修日期超出指派时间段");
        }
        if(new SimpleDateFormat("yyyy").format(maintainItem.getProposedTime()).equals(new SimpleDateFormat("yyyy").format(new Date()))) {
            if(
                Integer.parseInt(
                    new SimpleDateFormat("MM").format(maintainItem.getProposedTime())
                ) <
                Integer.parseInt(
                    new SimpleDateFormat("MM").format(new Date())
                )
            ){
                maintainItem.setStatus(2);
            }else{
                maintainItem.setStatus(0);
            }
        }else{
            if(
                Integer.parseInt(
                        new SimpleDateFormat("yyyy").format(maintainItem.getProposedTime())
                ) <
                Integer.parseInt(
                        new SimpleDateFormat("yyyy").format(new Date())
                )
            ){
                maintainItem.setStatus(2);
            }else{
                maintainItem.setStatus(0);
            }
        }
        if(maintainItem.getDiseaseInstanceId() != null) {
            Integer diseaseId = maintainItem.getDiseaseInstanceId();
            InspectionDiseaseInstance diseaseInstance = inspectionDiseaseInstanceDao.findById(diseaseId);
            diseaseInstance.setStatus(1);
            inspectionDiseaseInstanceDao.update(diseaseInstance);
        }
        maintainItemDao.save(maintainItem);
        PlanInfo toUpdate=planInfoDao.findById(maintainItem.getPlanId());
        if(toUpdate.getStatus() == 2){
            MaintainItem condition = new MaintainItem();
            condition.setPlanId(maintainItem.getPlanId());
            condition.setStatus(1);
            if(maintainItemDao.query(condition).size() > 0){
                toUpdate.setStatus(1);
            }else{
                toUpdate.setStatus(0);
            }
        }
        planInfoDao.update(toUpdate);
    }

    @Override
    public String downloadMaintainPlan(Integer planId) {
        String filename;
        String sheetName;
        String[][] content;
        String[] title;
        PlanDetailFilter filter = new PlanDetailFilter();
        filter.setPlanId(planId);
        List<MaintainItemVO> result = planDetailExpDao.listMaintainItem(ThreadLocalMap.getUnitId(),
                ThreadLocalMap.getRoleId(), power.getMaintainPower(), filter);
        title = new String[]{"序号", "计划项目", "拟定完成日期"};
        String fileTime = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        filename = "维修计划表-" + fileTime + ".xls";
        sheetName = "sheet1";
        content = new String[result.size()][3];
            try {
                for (int i = 0; i < result.size(); i++) {
                    content[i][0] = i + 1 + "";
                    content[i][1] = result.get(i).getName();
                    content[i][2] = result.get(i).getProposedTime() != null ?
                            new SimpleDateFormat("yyyy-MM-dd").format(result.get(i).getProposedTime()) : "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        try {
            // 导出
            new File(path+"workExcel").mkdirs();
            File file = new File(path+"workExcel/" + filename);
            FileOutputStream fo = new FileOutputStream(file);
            wb.write(fo);
            fo.flush();
            fo.close();
            return "workExcel//" + filename;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String downloadInspectionReport(Integer planDetailId) {
        return downloadReportAsWord(planDetailId, true);
    }

    @Override
    public String downloadMaintainItem(Integer maintainItemId) {
        return downloadReportAsWord(maintainItemId, false);
    }

    @Override
    public String downloadReportAsWord(Integer planId, Boolean isInspection) {
        Date date = new Date();
        // 【一】处理文档Word
        XWPFDocument doc = null;
        FileOutputStream out = null;
        String filename = "";
        // 获取桥梁信息
        Structure structure;
        if(isInspection){
            PlanDetail detail=planDetailDao.findById(planId);
            structure=structureDao.findById(detail.getStructureId());
        }else{
            MaintainItem maintainItem=maintainItemDao.findById(planId);
            structure=structureDao.findById(maintainItem.getStructureId());
        }
        Boolean isBridge=structure.getStructureType()==1;
        Integer bridgeType=isBridge?structure.getBridgeType():0;
        if(isBridge){
            filename = isInspection ? "/城市桥梁日常巡查日报表.docx" : "/桥梁隧道维修养护简报.docx";
        }else{
            filename = isInspection ? "/隧道日常巡查日报表.docx" : "/桥梁隧道维修养护简报.docx";
        }
        String filepath = "/word/" + "report" + daySdf.format(date) + "/" + planId.toString() + filename;
        try {
            if(isInspection) {
                Map<String,Object> inspectionPlaceholderDataMap;
                //真实名称
                String realName = userDao.findById(ThreadLocalMap.getUserId()).getRealName();
                //模板中特殊处理占位符数据
                List<InspectionDiseaseInstanceVO> tablePlaceholderData = inspectionDiseaseExpDao.
                        listDiseaseWithTablePlaceholder(planId, null);
                //表头数据
                List<InspectionDisease> ids=inspectionDiseaseExpDao.selectInspectionDiseaseByStructureBridgeType(bridgeType);
                ids.removeIf(i->(i.getId()>=24&&i.getId()<=26)||i.getId()==64);
                List<?> tableRowData=ids;
                //表中数据
                List<InspectionDiseaseInstanceVO> tableRowLoop = inspectionDiseaseExpDao.
                        listDiseaseWithTableRowLoop(planId, null);
                //模板地址
                String templatePath;
                InspectionTableExcUtil util=new InspectionTableExcUtil();
                InspectionTableExcUtil.isDefault=false;
                if(isBridge){
                    inspectionPlaceholderDataMap =WordMapData.getInstance().
                            getReplaceMapInspection(realName, wordDataDao.getWordDataInspection(planId), tablePlaceholderData);
//                    doc = replaceWordInspection(inspectionPlaceholderDataMap, true);
                    templatePath=inspectionTemplePath;
                    //设置类型
                    ArrayList<Integer> a=new ArrayList<>();
                    a.add(InspectionTableExcUtil.CLASS_OTHER);
                    util.setTableDataClass(a);
                }else{
                    inspectionPlaceholderDataMap=WordMapData.getInstance().
                            getReplaceMapInspectionTunnel(realName, wordDataDao.getWordDataInspection(planId),tablePlaceholderData);
//                    doc = replaceWordInspection(inspectionPlaceholderDataMap, false);
                    templatePath=inspectionTunnelTemplePath;
                    //设置类型
                    ArrayList<Integer> b=new ArrayList<>();
                    b.add(InspectionTableExcUtil.CLASS_TUNNEL);
                    util.setTableDataClass(b);
                }
                //设置输出路径
                util.setOutputPath(path);
                return util.createWord(templatePath, inspectionPlaceholderDataMap,
                        tableRowData,
                        tableRowLoop,
                        filepath,
                        inspectionDiseaseExpDao.getNormalOptionNameByDiseaseId().stream().
                                collect(Collectors.
                                        toMap(DiseaseNormalOptionRel::getDiseaseId,DiseaseNormalOptionRel::getNormalValue)
                                )
                );
            }else{
                doc = replaceWordMaintain(planId,WordMapData.getInstance().getReplaceMapMaintain(planDetailExpDao.selMaintainItem(planId)));
            }

        // 【二】IO流输出
        File outFile = new File(path + filepath);
        // 如果没有文件夹，则新建
        if(!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        // 如果存在了这个简报，则删除，重新保存
        if(outFile.exists()) {
            outFile.delete();
        }
        // 重新保存
        out = new FileOutputStream(outFile);
        doc.write(out);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out != null) {
                try {
                    out.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return filepath;
    }

    @Override
    public String batchExcelExport(KeywordVo keywordVo) {
        String fileName;
        String sheetNameBridge = "桥梁";
        String sheetNameTunnel = "隧道";
        String now = ExcelUtil.formatDate(new Date(), "yyyy-MM-dd");
        List<FieldInfo> fieldInfoList = new ArrayList<>();
        List<InspectionDiseaseInstanceVO> result;
        result = inspectionDiseaseExpDao.listProjectDisease(keywordVo.getKeyword(),
                keywordVo.getStructureType(),
                keywordVo.getProjectId(),
                ThreadLocalMap.getUnitId(),
                ThreadLocalMap.getRoleId(),
                power.getInspectionPower());
        //数据预处理
        for(int i = 0; i < result.size(); i++){
            InspectionDiseaseInstanceVO vo = result.get(i);
            try {
                vo.setInspectionTime(new SimpleDateFormat("yyyy-MM-dd").format(
                        new SimpleDateFormat("yyyy-MM-dd").parse(vo.getInspectionTime())
                ));
                vo.setMaintainTime(new SimpleDateFormat("yyyy-MM-dd").format(
                        new SimpleDateFormat("yyyy-MM-dd").parse(vo.getMaintainTime())
                ));
            }catch (Exception e){
                logger.error(logDataToString(e,"planRecordExport"));
            }
            vo.setStatusString(statusToString(vo.getStatus()));
            vo.setStrategyString(strategyToString(vo.getStrategy()));
            vo.setExceptionTypeString(exceptionTypeToString(vo.getExceptionType()));
            if(vo.getInspectionDiseaseId()<=15){    //桥面系，展示病害数量
                vo.setShowItem(vo.getQuantity() + "(" + vo.getUnit() + ")");
            }
            if(vo.getInspectionDiseaseId() > 15 && vo.getInspectionDiseaseId() <= 23){    //上下部，展示异常部位
                vo.setShowItem(vo.getExceptionPart());
            }
            if(vo.getInspectionDiseaseId() > 64 && vo.getInspectionDiseaseId() <= 73){    //上下部，展示异常部位
                vo.setShowItem(vo.getExceptionPart());
            }
            if(vo.getInspectionDiseaseId() == 24){    //桥区施工展示-
                vo.setShowItem(null);
            }
            if(vo.getInspectionDiseaseId() == 25){    //其他病害，展示选项
                vo.setShowItem(vo.getOptionName());
            }
            if(vo.getInspectionDiseaseId() == 26){    //其他说明
                result.remove(vo);
                i--;
            }
            if(vo.getInspectionDiseaseId()> 26 && vo.getInspectionDiseaseId() <= 63){    //隧道，展示异常部位
                vo.setShowItem(vo.getExceptionPart());
            }
            if(vo.getInspectionDiseaseId() == 64){    //其他说明
                result.remove(vo);
                i--;
            }
        }
        if(keywordVo.getStructureType()==1) {
            //桥梁
            fieldInfoList.add(new FieldInfo("structureName", "结构物"));
            fieldInfoList.add(new FieldInfo("damageType", "病害类型"));
            fieldInfoList.add(new FieldInfo("showItem", "病害程度/异常部位"));
            fieldInfoList.add(new FieldInfo("remarks", "病害或异常说明"));
            fieldInfoList.add(new FieldInfo("strategyString", "处置措施"));
            fieldInfoList.add(new FieldInfo("inspectionTime", "巡查时间"));
            fieldInfoList.add(new FieldInfo("maintainTime", "维修时间"));
            fieldInfoList.add(new FieldInfo("statusString", "病害状况"));
            fileName = String.format("%s-%s%s", "巡查记录卡", now, ".xls");
            return ExcelUtil.createExcel(fileName, sheetNameBridge, fieldInfoList, result, InspectionDiseaseInstanceVO.class);
        }else{
            //隧道
            fieldInfoList.add(new FieldInfo("structureName", "结构物"));
            fieldInfoList.add(new FieldInfo("damageType", "病害类型"));
            fieldInfoList.add(new FieldInfo("showItem", "异常部位"));
            fieldInfoList.add(new FieldInfo("remarks", "异常描述"));
            fieldInfoList.add(new FieldInfo("exceptionTypeString", "判定"));
            fieldInfoList.add(new FieldInfo("strategyString", "养护措施"));
            fieldInfoList.add(new FieldInfo("inspectionTime", "巡查时间"));
            fieldInfoList.add(new FieldInfo("maintainTime", "维修时间"));
            fieldInfoList.add(new FieldInfo("statusString", "病害状况"));
            fileName = String.format("%s-%s%s", "巡查记录卡", now, ".xls");
            return ExcelUtil.createExcel(fileName, sheetNameTunnel, fieldInfoList, result, InspectionDiseaseInstanceVO.class);
        }
    }

    private String statusToString(Integer status){
        if(status==null){
            return "";
        }
        return status==2?"已修":"待修";
    }

    private String strategyToString(Integer strategy){
        if(strategy==null){
            return "";
        }
        switch (strategy){
            case 1:
                return "观察";
            case 2:
                return "报修";
            case 3:
                return "监测";
            case 4:
                return "即修";
            case 5:
                return "更换";
            case 6:
                return "增设";
            case 7:
                return "跟踪监测";
            case 8:
                return "维修处置";
            case 9:
                return "定期或专项检查";
            default:
                return "";
        }
    }

    private String exceptionTypeToString(Integer exceptionType){
        if(exceptionType==null){
            return "";
        }
        return exceptionType==2?"严重异常":"一般异常";
    }

    // word生成L【方法二】利用数据替换
    public  XWPFDocument replaceWordInspection(Map<String, Object> replaceWordMap, Boolean isBridge) throws IOException {
        // 获取模板
        String inputPath;
        if(isBridge) {
            inputPath = inspectionTemplePath;
        }else{
            inputPath = inspectionTunnelTemplePath;
        }
        InputStream input = new FileInputStream(inputPath);
        XWPFDocument doc = new XWPFDocument(input);
        // 【word替换】
        List<IBodyElement> bodyElements = doc.getBodyElements();//获取所有对象内容
        WordTempUtil.replaceInPara(doc, replaceWordMap);
        WordTempUtil.replaceInTable(doc, replaceWordMap);
        return doc;
    }

    public XWPFDocument replaceWordMaintain(Integer targetId, Map<String, Object> replaceWordMap) throws IOException {
        // 获取模板
        String inputPath = maintainTemplePath;
        InputStream input = new FileInputStream(inputPath);
        XWPFDocument doc = new XWPFDocument(input);
        // 【word替换】
        List<IBodyElement> bodyElements = doc.getBodyElements();//获取所有对象内容
        WordTempUtil.replaceInPara(doc, replaceWordMap);
        WordTempUtil.replaceInTable(doc, replaceWordMap);
        // 【添加图片list】
        //获取图片
        Photo condition = new Photo();
        condition.setTargetId(targetId);
        condition.setType(6);
        List<Photo> beforeMaintainPhotos = photoDao.query(condition); // 维养前的照片
        condition.setType(7);
        List<Photo> maintainingPhotos = photoDao.query(condition); // 维养中的照片
        condition.setType(8);
        List<Photo> maintainedPhotos = photoDao.query(condition);  // 维养后的照片

        // 生成图片内容
        XWPFParagraph annexParagraph = doc.createParagraph();
        annexParagraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun annexTitleRun = annexParagraph.createRun();
        annexTitleRun.setText("附件：");
        annexTitleRun.addBreak();
        annexTitleRun.setFontSize(11);
        setFontFamilyForChinese(annexTitleRun);
        setAnnexPhotos(beforeMaintainPhotos, "一、维养前状况", annexParagraph,doc);
        setAnnexPhotos(maintainingPhotos, "二、维养过程", annexParagraph,doc);
        setAnnexPhotos(maintainedPhotos, "三、维养后状况", annexParagraph,doc);
        return doc;
    }

    // 附件图片生成方法
    public void setAnnexPhotos (List<Photo> photos, String Title, XWPFParagraph paragraph, XWPFDocument doc){
        XWPFRun titleRun = paragraph.createRun();
        titleRun.setText(Title); // 设置标题
        titleRun.setFontSize(11);
        setFontFamilyForChinese(titleRun);
        titleRun.addCarriageReturn(); //换行
        if(photos.size() > 0){
            Integer remarkCount = 0; // 备注计数器（转行，写备注，格式处理）
            for (Integer i = 0; i < photos.size(); i++) {
                try {
                    // 处理图片地址
                    String photoPath = path + photos.get(i).getPath(); //【1.最终图片地址】
                    // 放置图片
                    FileInputStream is = new FileInputStream(photoPath);
                    String picId = doc.addPictureData(is, XWPFDocument.PICTURE_TYPE_JPEG);
                    XWPFRun newRun = paragraph.createRun();
                    // 排版，设置空格
                    if (remarkCount % 2 == 0) {
                        newRun.setText("  ");
                    }
                    WordTempUtil.addPictureToRun(newRun, picId, XWPFDocument.PICTURE_TYPE_JPEG, 240, 200);
                    if ((remarkCount > 0 && remarkCount % 2 == 1)||remarkCount == (photos.size() - 1)) {  // 图片插入备注
                        newRun.addBreak();
                        if(remarkCount == (photos.size()-1) && remarkCount % 2 != 1) {
                            String test = photos.get(remarkCount).getRemarks();
                            String test2 = new String();
                            newRun.setText(remarkCenter(test, test2));
                        } else {
                            String testStr1 = photos.get(i - 1).getRemarks();
                            String testStr2 = photos.get(i).getRemarks();
                            newRun.setText(remarkCenter(testStr1, testStr2));
                        }
                        newRun.addCarriageReturn();
                        newRun.addBreak();
                    } else {
                        newRun.setText("      "); // 图片空格处理
                    }
                    remarkCount ++;
                    setFontFamilyForChinese(newRun);
                    newRun.setFontSize(10);
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            XWPFRun run = paragraph.createRun();
            run.addCarriageReturn();
            run.setText("                 "+"(该部分暂未上传图片附件)");
            run.addCarriageReturn();
            run.addCarriageReturn();
            setFontFamilyForChinese(run);
        }
    }
    // 字体处理
    public void setFontFamilyForChinese (XWPFRun run) {
        CTFonts fonts = run.getCTR().addNewRPr().addNewRFonts();
        fonts.setEastAsia("楷体");
    }

    // 压缩简化过长的检查项字段
    public String simplifyCheckItem(String checkItem) {
        String simpleCheckItem = checkItem;
        if (simpleCheckItem.equals("桥墩、桥台、附属物")) {
            simpleCheckItem = "桥墩台附属物";
        } else if (simpleCheckItem.equals("交通标志、标线")) {
            simpleCheckItem = "交通标志、线";
        } else if (simpleCheckItem.equals("吊顶及各种预埋件")) {
            simpleCheckItem = "吊顶及预埋件";
        } else if (simpleCheckItem.equals("各种标志标线轮廓标")) {
            simpleCheckItem = "标志线轮廓标";
        } else if (simpleCheckItem.equals("其他危及行车、行船、行人安全的病害因素")) {
            simpleCheckItem = "其他项";
        }
        return simpleCheckItem;
    }
    // 对图片下的备注进行处理，每个图片下可放置20个字，40个空格，使图片下备注居中
    public String remarkCenter(String photo1Remark,String photo2Remark) {
        String resultString = new String();
        // 【第一个图片备注处理】
        Integer remark1Length = photo1Remark.length();
        for(int i = 0; i < (20 - remark1Length); i++) {
            resultString = resultString + " ";
        }
        resultString = resultString + photo1Remark;
        for(int i = 0; i < (21 - remark1Length); i++) {
            resultString = resultString + " ";
        }
        // 【第二个图片备注处理】
        Integer remark2Length = photo2Remark.length();
        for(int i = 0; i < (20 - remark2Length); i++) {
            resultString = resultString + " ";
        }
        resultString = resultString + photo2Remark;
        for(int i = 0; i < (20 - remark2Length); i++) {
            resultString = resultString + " ";
        }
        return resultString;
    }

    public void copyFile(String in,String out){
        InputStream is = null;
        OutputStream os = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try{
            is = new FileInputStream(in);
            os = new FileOutputStream(out);
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
    }

    private String logDataToString(Exception e, String tag){
        StringBuilder builder=new StringBuilder();
        builder.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        builder.append("\n%");
        builder.append(tag);
        builder.append("%");
        for(StackTraceElement element:e.getStackTrace()){
            builder.append(element);
            builder.append("\n");
        }
        return builder.toString();
    }
}
