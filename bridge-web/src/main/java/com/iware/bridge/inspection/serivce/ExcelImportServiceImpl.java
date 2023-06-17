package com.iware.bridge.inspection.serivce;


import com.iware.bridge.inspection.dao.ImportExcelCellNameExistDao;
import com.iware.bridge.inspection.vo.ExcelDiseaseInstanceTableVo;
import com.iware.bridge.inspection.vo.ExcelMaintainItemTableVo;
import com.iware.bridge.inspection.vo.ExcelPlanDetailTableVo;
import com.iware.bridge.inspection.vo.ExcelPlanInfoTableVo;
import com.iware.bridge.model.dao.inspection.*;
import com.iware.bridge.model.entity.inspection.*;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.FileTypeEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.pojo.FileData;
import com.iware.common.properties.PowerProperties;
import com.iware.common.utils.ExcelImportUtil;
import com.iware.common.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LBX
 * @date 2021-10-25
 */

@Service
public class ExcelImportServiceImpl implements ExcelImportService{

    @Value("${file.upload-path}")
    private String path;

    @Autowired
    private PowerProperties power;

    @Resource
    private ImportExcelCellNameExistDao importExcelCellNameExistDao;

    @Resource
    private PlanInfoDao planInfoDao;

    @Resource
    private PlanDetailDao planDetailDao;

    @Resource
    private InspectionDiseaseInstanceDao inspectionDiseaseInstanceDao;

    @Resource
    private InspectionDiseaseOptionDao inspectionDiseaseOptionDao;

    @Resource
    private DiseaseInstanceOptionRelDao diseaseInstanceOptionRelDao;

    @Resource
    private MaintainItemDao maintainItemDao;

    private final String TABLE_NAME_PLAN_INFO="月计划表";
    private final String TABLE_NAME_PLAN_DETAIL="巡查日计划详情表";
    private final String TABLE_NAME_MAINTAIN_ITEM="维养计划详情表";
    private final String TABLE_NAME_INSPECTION_DISEASE="巡查细项病害情况";


    @Override
    @Transactional
    public Integer importExcelInspectionRecord(MultipartFile excel) {
        int count=0;
        StringBuilder errorMessage=new StringBuilder();//错误信息，统一做提示
        //导入数据
        Logger logger= LoggerFactory.getLogger(ExcelImportServiceImpl.class);
        FileTypeEnum fileTypeEnum = FileTypeEnum.EXCEL_TEMP;
        FileData fileInfo = FileUtil.uploadFile(excel, fileTypeEnum,null);
        String filePath=fileInfo.getFilePath();//相对路径
        Map<String, List<Object>> toImportData;
        try {
            toImportData=new ExcelImportUtil() {
                @Override
                protected HashMap<Integer, String> colWithPropertyName(String sheetName) {
                    HashMap<Integer,String> result=new HashMap<>();
                    switch (sheetName){
                        case TABLE_NAME_PLAN_DETAIL:
                            result.put(0,"Id");
                            result.put(1,"PlanId");
                            result.put(2,"StructureName");
                            result.put(3,"Inspector");
                            result.put(4,"InspectionTime");
                            result.put(5,"Weather");
                            result.put(6,"Location");
                            result.put(7,"InspectionUnit");
                            return result;
                        case TABLE_NAME_PLAN_INFO:
                            result.put(0,"Id");
                            result.put(1,"Name");
                            result.put(2,"ProjectName");
                            result.put(3,"PlanTime");
                            result.put(4,"Budget");
                            result.put(5,"Expenditure");
                            result.put(6,"Status");
                            return result;
                        case TABLE_NAME_INSPECTION_DISEASE:
                            result.put(0,"Id");
                            result.put(1,"PlanDetailId");
                            result.put(2,"InspectionDisease");
                            result.put(3,"Quantity");
                            result.put(4,"Strategy");
                            result.put(5,"Remark");
                            result.put(6,"Status");
                            result.put(7,"ExceptionPart");
                            result.put(8,"ExceptionType");
                            result.put(9,"Options");
                            return result;
                        default:
                            return null;
                    }
                }

                @Override
                protected HashMap<Integer, Integer> colWithPropertyType(String sheetName) {
                    HashMap<Integer,Integer> result=new HashMap<>();
                    switch (sheetName){
                        case TABLE_NAME_PLAN_DETAIL:
                            result.put(0,EXCEL_CELL_TYPE_INT);
                            result.put(1,EXCEL_CELL_TYPE_INT);
                            result.put(2,EXCEL_CELL_TYPE_STRING);
                            result.put(3,EXCEL_CELL_TYPE_STRING);
                            result.put(4,EXCEL_CELL_TYPE_DATE);
                            result.put(5,EXCEL_CELL_TYPE_STRING);
                            result.put(6,EXCEL_CELL_TYPE_STRING);
                            result.put(7,EXCEL_CELL_TYPE_STRING);
                            return result;
                        case TABLE_NAME_PLAN_INFO:
                            result.put(0,EXCEL_CELL_TYPE_INT);
                            result.put(1,EXCEL_CELL_TYPE_STRING);
                            result.put(2,EXCEL_CELL_TYPE_STRING);
                            result.put(3,EXCEL_CELL_TYPE_MONTH);
                            result.put(4,EXCEL_CELL_TYPE_BIGDATA);
                            result.put(5,EXCEL_CELL_TYPE_BIGDATA);
                            result.put(6,EXCEL_CELL_TYPE_INT);
                            return result;
                        case TABLE_NAME_INSPECTION_DISEASE:
                            result.put(0,EXCEL_CELL_TYPE_INT);
                            result.put(1,EXCEL_CELL_TYPE_INT);
                            result.put(2,EXCEL_CELL_TYPE_STRING);
                            result.put(3,EXCEL_CELL_TYPE_INT);
                            result.put(4,EXCEL_CELL_TYPE_INT);
                            result.put(5,EXCEL_CELL_TYPE_STRING);
                            result.put(6,EXCEL_CELL_TYPE_INT);
                            result.put(7,EXCEL_CELL_TYPE_STRING);
                            result.put(8,EXCEL_CELL_TYPE_INT);
                            result.put(9,EXCEL_CELL_TYPE_STRING);
                            return result;
                        default:
                            return null;
                    }
                }

                @Override
                protected String getRowClassName(String sheetName) {
                    switch (sheetName){
                        case TABLE_NAME_PLAN_DETAIL:
                            return ExcelPlanDetailTableVo.class.getName();
                        case TABLE_NAME_PLAN_INFO:
                            return ExcelPlanInfoTableVo.class.getName();
                        case TABLE_NAME_INSPECTION_DISEASE:
                            return ExcelDiseaseInstanceTableVo.class.getName();
                        default:
                            return null;
                    }
                }
            }.importExcel(new File(path+filePath));
        }catch (IOException e){//文件关闭/开启异常
            logger.error(logDataToString(e,"linIOE"));
            throw new BusinessException("6039","文件损坏,关闭/开启异常,请联系管理员");
        }catch (ClassNotFoundException e){
            logger.error(logDataToString(e,"linCnfE"));
            throw new BusinessException("6039","表名不符合规范");
        }catch (NoSuchMethodException e){
            logger.error(logDataToString(e,"linNsmE"));
            throw new BusinessException("6039","传入属性有误，请联系管理员");
        }catch (IllegalAccessException e){
            logger.error(logDataToString(e,"linIaE"));
            throw new BusinessException("6039","数据处理异常，请联系管理员");
        }catch (InvocationTargetException e){
            logger.error(logDataToString(e,"linItE"));
            throw new BusinessException("6039","数据处理异常，请联系管理员");
        }catch (InstantiationException e){
            logger.error(logDataToString(e,"linIE"));
            throw new BusinessException("6039","数据处理异常，请联系管理员");
        }catch (ParseException e){
            logger.error(logDataToString(e,"linIE"));
            throw new BusinessException("6039","日期格式有误,月格式为 [年/月] 日格式为[年/月/日]");
        }catch (NullPointerException e){
            logger.error(logDataToString(e,"linNpE"));
            throw new BusinessException("6039","请检查表名或表数量，表名不可修改且最后一个表不可删除");
        }catch (IllegalStateException e){
            logger.error(logDataToString(e,"linIsE"));
            throw new BusinessException("6039","请检查表格单元格格式，通常为文本格输入纯数字，需要修改为文本");
        }catch (TypeNotPresentException e){
            logger.error(logDataToString(e,"linTNPE"));
            throw new BusinessException("6039",e.typeName());
        }

        //处理数据
        if(toImportData==null||toImportData.isEmpty()){
            return 0;
        }
        List<Object> planInfoList=toImportData.get(TABLE_NAME_PLAN_INFO);
        if(planInfoList==null||planInfoList.isEmpty()){
            return 0;
        }
        List<Object> planDetailList=toImportData.get(TABLE_NAME_PLAN_DETAIL);
        if(planDetailList==null||planDetailList.isEmpty()){
            return 0;
        }else{
            count+=planDetailList.size();
        }
        HashMap<Integer,List<PlanDetail>> planDetailMap=new HashMap<>();
        List<Object> inspectionDiseaseList=toImportData.get(TABLE_NAME_INSPECTION_DISEASE);
        if(inspectionDiseaseList==null||inspectionDiseaseList.isEmpty()){

        }
        HashMap<Integer,List<InspectionDiseaseInstance>> diseaseInstanceMap=new HashMap<>();
        HashMap<Integer,List<DiseaseInstanceOptionRel>> optionMap=new HashMap<>();
        //检查数据合法性
        HashSet<String> projectNames=new HashSet<>();
        HashSet<String> structureNames=new HashSet<>();
        HashSet<String> inspectors=new HashSet<>();
        HashSet<Integer> planInfoIdSet=new HashSet<>();
        HashSet<Integer> planDetailIdSet=new HashSet<>();
        for(Object o:planInfoList){
            projectNames.add(((ExcelPlanInfoTableVo)o).getProjectName());
        }
        for(Object o:planDetailList){
            ExcelPlanDetailTableVo detailTableVo=(ExcelPlanDetailTableVo)o;
            structureNames.add(detailTableVo.getStructureName());
            inspectors.add(detailTableVo.getInspector());
            if(planDetailIdSet.contains(detailTableVo.getId())){
                if(errorMessage.indexOf("部分日计划编号重复,请检查")==-1) {
                    errorMessage.append("部分日计划编号重复,请检查\n");
                }
            }
            planDetailIdSet.add(detailTableVo.getId());
        }
        //0.项目名称合法性
        if(projectNames.size()!=importExcelCellNameExistDao.countProjectNameExist(
                ThreadLocalMap.getUnitId(),
                ThreadLocalMap.getRoleId(),
                power.getInspectionPower(),
                new ArrayList<String>(projectNames)
        )){
            errorMessage.append("部分项目不存在，请检查\n");
        }
        //1.桥梁名称合法性
        if(structureNames.size()!=importExcelCellNameExistDao.countStructureNameExist(
                ThreadLocalMap.getUnitId(),
                ThreadLocalMap.getRoleId(),
                power.getInspectionPower(),
                new ArrayList<String>(structureNames)
        )){//查询桥梁名称，结果数与表格中数量不相等
            errorMessage.append("部分桥梁不存在，请检查\n");
        }
        //2.巡查人员合法性
        if(inspectors.size()!=importExcelCellNameExistDao.countUserNameExist(
                new ArrayList<String>(inspectors)
        )){//查询用户真实名称，结果数与表格中数量不相等
            errorMessage.append("部分巡检人员账户不存在，请检查\n");
        }
        //3.巡查日计划与月计划关联性
        for(Object o:planInfoList){
            if(planInfoIdSet.contains(((ExcelPlanInfoTableVo)o).getId())){
                if(errorMessage.indexOf("部分月计划编号重复,请检查")==-1) {
                    errorMessage.append("部分月计划编号重复,请检查\n");
                }
            }
            planInfoIdSet.add(((ExcelPlanInfoTableVo)o).getId());
        }
        HashMap<Integer,HashSet<String>> planDetailTimeExist=new HashMap<>();
        for(Object o:planDetailList){
            ExcelPlanDetailTableVo detailTableVo=(ExcelPlanDetailTableVo)o;
            if(!planInfoIdSet.contains(detailTableVo.getPlanId())){
                errorMessage.append("部分日计划关联月计划编号不存在,请检查\n");
                break;
            }
            PlanDetail planDetail=new PlanDetail();
            planDetail.setId(detailTableVo.getId());
            planDetail.setStructureId(importExcelCellNameExistDao.
                    selectStructureIdByName(detailTableVo.getStructureName()));
            planDetail.setInspector(detailTableVo.getInspector());
            HashSet<String> now=planDetailTimeExist.getOrDefault(detailTableVo.getPlanId(),new HashSet<String>());
            if(now.contains(new SimpleDateFormat("yyyy-MM-dd").format(detailTableVo.getInspectionTime()))){
                errorMessage.append("部分日计划巡查日期重复,请检查\n");
                break;
            }else{
                now.add(new SimpleDateFormat("yyyy-MM-dd").format(detailTableVo.getInspectionTime()));
            }
            planDetailTimeExist.put(detailTableVo.getPlanId(),now);
            planDetail.setInspectionTime(detailTableVo.getInspectionTime());
            if(detailTableVo.getWeather()!=null&&detailTableVo.getWeather().length()>8){
                errorMessage.append("部分日计划天气长度超过8,请检查\n");
                break;
            }
            planDetail.setWeather(detailTableVo.getWeather());
            if(detailTableVo.getLocation()!=null&&detailTableVo.getLocation().length()>64){
                errorMessage.append("部分日计划位置信息长度超过64,请检查\n");
                break;
            }
            planDetail.setLocation(detailTableVo.getLocation());
            if(detailTableVo.getInspectionUnit()!=null&&detailTableVo.getInspectionUnit().length()>32){
                errorMessage.append("部分日计划巡查单位长度超过32,请检查\n");
                break;
            }
            planDetail.setInspectionUnit(detailTableVo.getInspectionUnit());
            planDetail.setStatus(detailTableVo.getStatus());
            List<PlanDetail> ori=planDetailMap.getOrDefault(detailTableVo.getPlanId(),new ArrayList<>());
            ori.add(planDetail);
            planDetailMap.put(detailTableVo.getPlanId(),ori);
        }
        //4.巡查细项病害情况与日计划关联
        //5.巡查细项病害类型合法性
        if(inspectionDiseaseList!=null) {
            for (Object o : inspectionDiseaseList) {
                if (o == null) {
                    continue;
                }
                ExcelDiseaseInstanceTableVo instanceTableVo = (ExcelDiseaseInstanceTableVo) o;
                if (!planDetailIdSet.contains(instanceTableVo.getPlanDetailId())) {
                    errorMessage.append("部分病害细项关联日计划编号不存在,请检查\n");
                    break;
                }
                InspectionDiseaseInstance instance = new InspectionDiseaseInstance();
                String[] diseaseSplitResult = instanceTableVo.getInspectionDisease().split("-");
                if (diseaseSplitResult.length < 3) {
                    errorMessage.append("部分病害细项填写格式不规范,请检查\n");
                    break;
                }
                String diseasePart = diseaseSplitResult[0];
                String checkItem = diseaseSplitResult[1];
                String damageType = diseaseSplitResult[2];
                if (diseasePart == null ||
                        diseasePart.isEmpty() ||
                        checkItem == null ||
                        checkItem.isEmpty() ||
                        damageType == null ||
                        damageType.isEmpty()) {
                    errorMessage.append("部分病害细项填写格式不规范,请检查\n");
                    break;
                }
                Integer diseaseId = importExcelCellNameExistDao.selectInstanceIdByInspectionDiseaseName(diseasePart,
                        checkItem, damageType);
                if (diseaseId == null) {
                    errorMessage.append("部分病害细项填写部位或检查项或病害类型不存在,请检查\n");
                    break;
                }
                instance.setId(instanceTableVo.getId());
                instance.setInspectionDiseaseId(diseaseId);
                instance.setQuantity(instanceTableVo.getQuantity());
                Integer instanceStrategy = instanceTableVo.getStrategy();
                if (instanceStrategy == null || instanceStrategy <= 0 || instanceStrategy > 9) {
                    errorMessage.append("部分病害细项处置措施填写不规范,请检查\n");
                    break;
                }
                instance.setStrategy(instanceStrategy);
                if (instanceTableVo.getRemark() != null && instanceTableVo.getRemark().length() > 256) {
                    errorMessage.append("部分病害细项备注长度超过256,请检查\n");
                    break;
                }
                instance.setRemarks(instanceTableVo.getRemark());
                Integer instanceStatus = instanceTableVo.getStatus();
                if (instanceStatus == null || (instanceStatus != 0 && instanceStatus != 2)) {
                    errorMessage.append("部分病害细项状态填写不规范,请检查\n");
                    break;
                }
                instance.setStatus(instanceStatus);
                if (instanceTableVo.getExceptionPart() != null && instanceTableVo.getExceptionPart().length() > 64) {
                    errorMessage.append("部分病害细项异常部位长度超过64,请检查\n");
                    break;
                }
                instance.setExceptionPart(instanceTableVo.getExceptionPart());
                instance.setExceptionType(instanceTableVo.getExceptionType());
                List<InspectionDiseaseInstance> ori = diseaseInstanceMap.
                        getOrDefault(instanceTableVo.getPlanDetailId(), new ArrayList<>());
                ori.add(instance);
                diseaseInstanceMap.put(instanceTableVo.getPlanDetailId(), ori);
                //选项处理
                InspectionDiseaseOption optionCondition = new InspectionDiseaseOption();
                optionCondition.setInspectionDiseaseId(instance.getInspectionDiseaseId());
                optionCondition.setOptionStatus(0);
                List<InspectionDiseaseOption> options = inspectionDiseaseOptionDao.query(optionCondition);
                List<DiseaseInstanceOptionRel> rels = optionMap.getOrDefault(instance.getId(), new ArrayList<>());
                if (options.size() > 0) {
                    if (options.size() == 1) {
                        DiseaseInstanceOptionRel optionRel = new DiseaseInstanceOptionRel();
                        optionRel.setInspectionDiseaseOptionId(options.get(0).getId());
                        rels.add(optionRel);
                    } else {
                        if (instanceTableVo.getOptions().contains("A")) {
                            DiseaseInstanceOptionRel optionRel = new DiseaseInstanceOptionRel();
                            optionRel.setInspectionDiseaseOptionId(options.get(3).getId());
                            rels.add(optionRel);
                        }
                        if (instanceTableVo.getOptions().contains("B")) {
                            DiseaseInstanceOptionRel optionRel = new DiseaseInstanceOptionRel();
                            optionRel.setInspectionDiseaseOptionId(options.get(2).getId());
                            rels.add(optionRel);
                        }
                        if (instanceTableVo.getOptions().contains("C")) {
                            DiseaseInstanceOptionRel optionRel = new DiseaseInstanceOptionRel();
                            optionRel.setInspectionDiseaseOptionId(options.get(1).getId());
                            rels.add(optionRel);
                        }
                        if (instanceTableVo.getOptions().contains("D")) {
                            DiseaseInstanceOptionRel optionRel = new DiseaseInstanceOptionRel();
                            optionRel.setInspectionDiseaseOptionId(options.get(0).getId());
                            rels.add(optionRel);
                        }
                    }
                    optionMap.put(instance.getId(), rels);
                }
            }
        }
        //检查完毕，抛出异常或写入数据库
        if(errorMessage.toString().length()>0){
            throw new BusinessException("6039",errorMessage.toString());
        }
        //数据写入数据库
        SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM");
        for(Object o:planInfoList){
            ExcelPlanInfoTableVo infoTableVo=(ExcelPlanInfoTableVo)o;
            PlanInfo info=new PlanInfo();
            info.setName(infoTableVo.getName());
            info.setProjectId(importExcelCellNameExistDao.selectProjectIdByName(infoTableVo.getProjectName()));
            PlanInfo queryTime=new PlanInfo();
            queryTime.setPlanTime(infoTableVo.getPlanTime());
            queryTime.setProjectId(info.getProjectId());
            queryTime.setType(1);
            List<PlanInfo> queryTimeResult=planInfoDao.query(queryTime);
            if(queryTimeResult!=null&&!queryTimeResult.isEmpty()){
                throw new BusinessException("6039","部分月计划日期重复,请检查\n");
            }
            info.setPlanTime(infoTableVo.getPlanTime());
            if(infoTableVo.getBudget()!=null&&infoTableVo.getBudget().toString().length()>10){
                throw new BusinessException("6039","部分月计划预算长度超过10,请检查\n");
            }
            info.setBudget(infoTableVo.getBudget());
            if(infoTableVo.getExpenditure()!=null&&infoTableVo.getExpenditure().toString().length()>10){
                throw new BusinessException("6039","部分月计划实际金额长度超过10,请检查\n");
            }
            info.setExpenditure(infoTableVo.getExpenditure());
            info.setStatus(infoTableVo.getStatus());
            info.setType(1);
            info.setUserId(ThreadLocalMap.getUserId());
            planInfoDao.save(info);
            String MonthTime=formater.format(info.getPlanTime());
            for(PlanDetail detail:planDetailMap.getOrDefault(infoTableVo.getId(),new ArrayList<>())){
                if(!MonthTime.equals(formater.format(detail.getInspectionTime()))){
                    throw new BusinessException("6039","部分计划细项与月计划时间不对应,请检查\n");
                }
                Integer diseaseKey=detail.getId();
                detail.setPlanId(info.getId());
                detail.setStatus(1);
                planDetailDao.save(detail);
                for(InspectionDiseaseInstance instance:diseaseInstanceMap.getOrDefault(diseaseKey,new ArrayList<>())){
                    Integer optionKey=instance.getId();
                    instance.setPlanDetailId(detail.getId());
                    inspectionDiseaseInstanceDao.save(instance);
                    for(DiseaseInstanceOptionRel rel:optionMap.getOrDefault(optionKey,new ArrayList<>())){
                        rel.setInspectionDiseaseInstanceId(instance.getId());
                        diseaseInstanceOptionRelDao.save(rel);
                    }
                }
            }
        }
        return count;
    }

    @Override
    @Transactional
    public Integer importExcelMaintainRecord(MultipartFile excel) {
        int count=0;
        StringBuilder errorMessage=new StringBuilder();//错误信息，统一做提示
        //导入数据
        Logger logger= LoggerFactory.getLogger(ExcelImportServiceImpl.class);
        FileTypeEnum fileTypeEnum = FileTypeEnum.EXCEL_TEMP;
        FileData fileInfo = FileUtil.uploadFile(excel, fileTypeEnum,null);
        String filePath=fileInfo.getFilePath();//相对路径
        Map<String, List<Object>> toImportData;
        try {
            toImportData=new ExcelImportUtil() {
                @Override
                protected HashMap<Integer, String> colWithPropertyName(String sheetName) {
                    HashMap<Integer,String> result=new HashMap<>();
                    switch (sheetName){
                        case TABLE_NAME_MAINTAIN_ITEM:
                            result.put(0,"Id");
                            result.put(1,"PlanId");
                            result.put(2,"StructureName");
                            result.put(3,"Name");
                            result.put(4,"Type");
                            result.put(5,"ProposedTime");
                            result.put(6,"MaintainTime");
                            result.put(7,"Creator");
                            result.put(8,"Content");
                            result.put(9,"Method");
                            result.put(10,"Result");
                            result.put(11,"MaintenanceUnit");
                            result.put(12,"Quantities");
                            result.put(13,"Status");
                            return result;
                        case TABLE_NAME_PLAN_INFO:
                            result.put(0,"Id");
                            result.put(1,"Name");
                            result.put(2,"ProjectName");
                            result.put(3,"PlanTime");
                            result.put(4,"Budget");
                            result.put(5,"Expenditure");
                            result.put(6,"Status");
                            return result;
                        default:
                            return null;
                    }
                }

                @Override
                protected HashMap<Integer, Integer> colWithPropertyType(String sheetName) {
                    HashMap<Integer,Integer> result=new HashMap<>();
                    switch (sheetName){
                        case TABLE_NAME_MAINTAIN_ITEM:
                            result.put(0,EXCEL_CELL_TYPE_INT);
                            result.put(1,EXCEL_CELL_TYPE_INT);
                            result.put(2,EXCEL_CELL_TYPE_STRING);
                            result.put(3,EXCEL_CELL_TYPE_STRING);
                            result.put(4,EXCEL_CELL_TYPE_INT);
                            result.put(5,EXCEL_CELL_TYPE_DATE);
                            result.put(6,EXCEL_CELL_TYPE_DATE);
                            result.put(7,EXCEL_CELL_TYPE_STRING);
                            result.put(8,EXCEL_CELL_TYPE_STRING);
                            result.put(9,EXCEL_CELL_TYPE_STRING);
                            result.put(10,EXCEL_CELL_TYPE_STRING);
                            result.put(11,EXCEL_CELL_TYPE_STRING);
                            result.put(12,EXCEL_CELL_TYPE_STRING);
                            result.put(13,EXCEL_CELL_TYPE_INT);
                            return result;
                        case TABLE_NAME_PLAN_INFO:
                            result.put(0,EXCEL_CELL_TYPE_INT);
                            result.put(1,EXCEL_CELL_TYPE_STRING);
                            result.put(2,EXCEL_CELL_TYPE_STRING);
                            result.put(3,EXCEL_CELL_TYPE_MONTH);
                            result.put(4,EXCEL_CELL_TYPE_BIGDATA);
                            result.put(5,EXCEL_CELL_TYPE_BIGDATA);
                            result.put(6,EXCEL_CELL_TYPE_INT);
                            return result;
                        default:
                            return null;
                    }
                }

                @Override
                protected String getRowClassName(String sheetName) {
                    switch (sheetName){
                        case TABLE_NAME_MAINTAIN_ITEM:
                            return ExcelMaintainItemTableVo.class.getName();
                        case TABLE_NAME_PLAN_INFO:
                            return ExcelPlanInfoTableVo.class.getName();
                        default:
                            return null;
                    }
                }
            }.importExcel(new File(path+filePath));
        }catch (IOException e){//文件关闭/开启异常
            logger.error(logDataToString(e,"linIOE"));
            throw new BusinessException("6039","文件损坏,关闭/开启异常,请联系管理员");
        }catch (ClassNotFoundException e){
            logger.error(logDataToString(e,"linCnfE"));
            throw new BusinessException("6039","表名不符合规范");
        }catch (NoSuchMethodException e){
            logger.error(logDataToString(e,"linNsmE"));
            throw new BusinessException("6039","传入属性有误，请联系管理员");
        }catch (IllegalAccessException e){
            logger.error(logDataToString(e,"IaE"));
            throw new BusinessException("6039","数据处理异常，请联系管理员");
        }catch (InvocationTargetException e){
            logger.error(logDataToString(e,"ItE"));
            throw new BusinessException("6039","数据处理异常，请联系管理员");
        }catch (InstantiationException e){
            logger.error(logDataToString(e,"IE"));
            throw new BusinessException("6039","数据处理异常，请联系管理员");
        }catch (ParseException e){
            logger.error(logDataToString(e,"IE"));
            throw new BusinessException("6039","日期格式有误,月格式为 [年/月] 日格式为[年/月/日]");
        }catch (NullPointerException e){
            logger.error(logDataToString(e,"NpE"));
            throw new BusinessException("6039","请检查表名或表数量，表名不可修改且最后一个表不可删除");
        }catch (IllegalStateException e){
            logger.error(logDataToString(e,"linIsE"));
            throw new BusinessException("6039","请检查表格单元格格式，通常为文本格输入纯数字，需要修改为文本");
        }catch (TypeNotPresentException e){
            logger.error(logDataToString(e,"linTNPE"));
            throw new BusinessException("6039",e.typeName());
        }

        //处理数据
        if(toImportData==null||toImportData.isEmpty()){
            return 0;
        }
        List<Object> planInfoList=toImportData.get(TABLE_NAME_PLAN_INFO);
        if(planInfoList==null||planInfoList.isEmpty()){
            return 0;
        }
        List<Object> maintainItemList=toImportData.get(TABLE_NAME_MAINTAIN_ITEM);
        if(maintainItemList==null||maintainItemList.isEmpty()){
            return 0;
        }else{
            count+=maintainItemList.size();
        }
        HashMap<Integer,List<MaintainItem>> maintainItemMap=new HashMap<>();
        //检查数据合法性
        HashSet<String> projectNames=new HashSet<>();
        HashSet<String> structureNames=new HashSet<>();
        HashSet<String> creators=new HashSet<>();
        HashSet<Integer> planInfoIdSet=new HashSet<>();
        for(Object o:planInfoList){
            projectNames.add(((ExcelPlanInfoTableVo)o).getProjectName());
        }
        for(Object o:maintainItemList){
            ExcelMaintainItemTableVo maintainItemTableVo=(ExcelMaintainItemTableVo)o;
            structureNames.add(maintainItemTableVo.getStructureName());
            creators.add(maintainItemTableVo.getCreator());
        }
        //0.项目名称合法性
        if(projectNames.size()!=importExcelCellNameExistDao.countProjectNameExist(
                ThreadLocalMap.getUnitId(),
                ThreadLocalMap.getRoleId(),
                power.getMaintainPower(),
                new ArrayList<String>(projectNames)
        )){
            errorMessage.append("部分项目不存在，请检查\n");
        }
        //1.桥梁名称合法性
        if(structureNames.size()!=importExcelCellNameExistDao.countStructureNameExist(
                ThreadLocalMap.getUnitId(),
                ThreadLocalMap.getRoleId(),
                power.getMaintainPower(),
                new ArrayList<String>(structureNames)
        )){//查询桥梁名称，结果数与表格中数量不相等
            errorMessage.append("部分桥梁不存在，请检查\n");
        }
        //2.巡查人员合法性
        if(creators.size()!=importExcelCellNameExistDao.countUserNameExist(
                new ArrayList<String>(creators)
        )){//查询用户真实名称，结果数与表格中数量不相等
            errorMessage.append("部分维修人员账户不存在，请检查\n");
        }
        //3.维养细项与月计划关联性
        for(Object o:planInfoList){
            if(planInfoIdSet.contains(((ExcelPlanInfoTableVo)o).getId())){
                if(errorMessage.indexOf("部分月计划编号重复,请检查")==-1){
                    errorMessage.append("部分月计划编号重复,请检查\n");
                }
            }
            planInfoIdSet.add(((ExcelPlanInfoTableVo)o).getId());
        }
        for(Object o:maintainItemList){
            ExcelMaintainItemTableVo maintainItemTableVo=(ExcelMaintainItemTableVo)o;
            if(!planInfoIdSet.contains(maintainItemTableVo.getPlanId())){
                errorMessage.append("部分维养细项关联月计划编号不存在,请检查\n");
                break;
            }
            MaintainItem maintainItem=new MaintainItem();
            maintainItem.setStructureId(importExcelCellNameExistDao.
                    selectStructureIdByName(maintainItemTableVo.getStructureName()));
            if(maintainItemTableVo.getName()!=null&&maintainItemTableVo.getName().length()>32){
                errorMessage.append("部分维养细项维修项名称长度大于32,请检查\n");
                break;
            }
            maintainItem.setName(maintainItemTableVo.getName());
            Integer maintainType=maintainItemTableVo.getType();
            if(maintainType==null||maintainType<0||maintainType>3){
                errorMessage.append("部分维养细项填写维修类型不规范,请检查\n");
                break;
            }
            maintainItem.setType(maintainType);
            maintainItem.setProposedTime(maintainItemTableVo.getProposedTime());
            maintainItem.setMaintainTime(maintainItemTableVo.getMaintainTime());
            maintainItem.setCreator(maintainItemTableVo.getCreator());
            if(maintainItemTableVo.getContent()!=null&&maintainItemTableVo.getContent().length()>256){
                errorMessage.append("部分维养细项维修内容长度大于256,请检查\n");
                break;
            }
            maintainItem.setContent(maintainItemTableVo.getContent());
            if(maintainItemTableVo.getMethod()!=null&&maintainItemTableVo.getMethod().length()>256){
                errorMessage.append("部分维养细项维修方法长度大于256,请检查\n");
                break;
            }
            maintainItem.setMethod(maintainItemTableVo.getMethod());
            if(maintainItemTableVo.getResult()!=null&&maintainItemTableVo.getResult().length()>256){
                errorMessage.append("部分维养细项维修结果长度大于256,请检查\n");
                break;
            }
            maintainItem.setResult(maintainItemTableVo.getResult());
            if(maintainItemTableVo.getMaintenanceUnit()!=null&&maintainItemTableVo.getMaintenanceUnit().length()>32){
                errorMessage.append("部分维养细项维修单位长度大于32,请检查\n");
                break;
            }
            maintainItem.setMaintenanceUnit(maintainItemTableVo.getMaintenanceUnit());
            if(maintainItemTableVo.getQuantities()!=null&&maintainItemTableVo.getQuantities().length()>32){
                errorMessage.append("部分维养细项工程量长度大于32,请检查\n");
                break;
            }
            maintainItem.setQuantities(maintainItemTableVo.getQuantities());
            maintainItem.setStatus(maintainItemTableVo.getStatus());
            List<MaintainItem> ori=maintainItemMap.getOrDefault(maintainItemTableVo.getPlanId(),new ArrayList<>());
            ori.add(maintainItem);
            maintainItemMap.put(maintainItemTableVo.getPlanId(),ori);
        }

        //检查完毕，抛出异常或写入数据库
        if(errorMessage.toString().length()>0){
            throw new BusinessException("6039",errorMessage.toString());
        }
        //数据写入数据库
        SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM");
        for(Object o:planInfoList){
            ExcelPlanInfoTableVo infoTableVo=(ExcelPlanInfoTableVo)o;
            PlanInfo info=new PlanInfo();
            info.setName(infoTableVo.getName());
            info.setProjectId(importExcelCellNameExistDao.selectProjectIdByName(infoTableVo.getProjectName()));
            PlanInfo queryTime=new PlanInfo();
            queryTime.setPlanTime(infoTableVo.getPlanTime());
            queryTime.setProjectId(info.getProjectId());
            queryTime.setType(4);
            List<PlanInfo> queryTimeResult=planInfoDao.query(queryTime);
            if(queryTimeResult!=null&&!queryTimeResult.isEmpty()){
                throw new BusinessException("6039","部分月计划日期重复,请检查\n");
            }
            info.setPlanTime(infoTableVo.getPlanTime());
            if(infoTableVo.getBudget()!=null&&infoTableVo.getBudget().toString().length()>10){
                throw new BusinessException("6039","部分月计划预算长度超过10,请检查\n");
            }
            info.setBudget(infoTableVo.getBudget());
            if(infoTableVo.getExpenditure()!=null&&infoTableVo.getExpenditure().toString().length()>10){
                throw new BusinessException("6039","部分月计划实际金额长度超过10,请检查\n");
            }
            info.setExpenditure(infoTableVo.getExpenditure());
            info.setStatus(infoTableVo.getStatus());
            info.setType(4);
            info.setUserId(ThreadLocalMap.getUserId());
            planInfoDao.save(info);
            String monthTime=formater.format(info.getPlanTime());
            for(MaintainItem item:maintainItemMap.getOrDefault(infoTableVo.getId(),new ArrayList<>())){
                if(!monthTime.equals(formater.format(item.getMaintainTime()))){
                    throw new BusinessException("6039","部分维修细项与月计划时间不对应,请检查\n");
                }
                item.setPlanId(info.getId());
                maintainItemDao.save(item);
            }
        }
        return count;
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
