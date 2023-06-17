package com.iware.bridge.inspection.api;

import com.iware.bridge.inspection.serivce.*;
import com.iware.bridge.inspection.vo.*;
import com.iware.bridge.model.dao.global.ProjectStructureRelDao;
import com.iware.bridge.model.dao.global.StructureDao;
import com.iware.bridge.model.entity.global.ProjectStructureRel;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.common.result.ResultBody;
import com.iware.common.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LBX
 * @date 2021-8-2
 */

@RestController
@Api(value = "巡查记录接口", tags="巡查记录接口",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/inspection/record",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class InspectionRecordApi {


    @Autowired
    private PlanService planService;
    @Autowired
    private PlanDetailService planDetailService;
    @Autowired
    private InspectionDiseaseService diseaseService;

    @Resource
    private StructureDao structureDao;

    @Resource
    private ExcelImportService excelImportService;

    @Value("${file.upload-path}")
    private String path;
    @Resource
    private ProjectStructureRelDao projectStructureRelDao;

    @PostMapping(value = "/list/{projectId}/{year}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取巡查计划列表",value="获取巡查计划列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "year", value = "年份：0全部", required = true, paramType = "path", dataType = "integer")
    })
    public List<PlanStructureVO> listPlanStructure(@PathVariable("projectId") Integer projectId,
                                                   @PathVariable("year") Integer year) {
        return planService.listPlanStructure(projectId, year);
    }

    @PostMapping(value = "/detail/list")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取巡查记录列表",value="获取巡查记录列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "查询条件", required = true, paramType = "body", dataType = "PlanRecordFilter")
    })
    public List<PlanDetailVO> listInspectionRecord(@RequestBody PlanRecordFilter filter) {
        return planDetailService.listInspectionRecord(filter);
    }

    @PostMapping(value = "/disease/list/{planDetailId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取病害情况",value="获取病害情况",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planDetailId", value = "计划详情id", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "keyword", value = "查询条件", required = false, paramType = "body", dataType = "KeywordVo")
    })
    public List<InspectionDiseaseInstanceVO> listDisease(@PathVariable("planDetailId") Integer planDetailId,
                                                         @RequestBody KeywordVo keyword) {
        List<InspectionDiseaseInstanceVO> result=diseaseService.listDisease(planDetailId, keyword.getKeyword(),keyword.getStructureType(),keyword.getProjectId());
        for(int i = 0; i < result.size(); i++){
            InspectionDiseaseInstanceVO vo = result.get(i);
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
        return result;
    }

    @PostMapping(value = "/batch/export")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="批量导出记录列表",value="批量导出记录列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filterVo", value = "导出数据条件", required = true, paramType = "body", dataType = "ExportFilterVo")
    })
    public void batchExport(@RequestBody ExportFilterVo filterVo,
                                  HttpServletResponse response) {
        String path = planDetailService.batchExport(1, filterVo.getIds(), filterVo.getFilter(), filterVo.getExportAll());
        FileUtil.pushToWeb(response, path, "APPLICATION/OCTET-STREAM");
    }

    @GetMapping(value = "/download/{planDetailId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="下载桥梁/隧道简报",value="下载桥梁/隧道简报",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planDetailId", value = "巡查计划细项id", required = true, paramType = "path", dataType = "integer")
    })
    public void downloadReport(@PathVariable("planDetailId") Integer planDetailId,
                               HttpServletResponse response) {
        String path = planDetailService.downloadInspectionReport(planDetailId);
        FileUtil.pushToWeb(response, path, "APPLICATION/OCTET-STREAM");
    }

    @GetMapping(value = "/date/{date}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取当天工作记录",value="获取当天工作记录",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "巡查时间", required = true, paramType = "path", dataType = "string"),
    })
    public List<PlanDetailVO> listInspectionRecordByTime(@PathVariable("date") String date) {
        return planDetailService.listInspectionRecordByTime(date);
    }

    @GetMapping(value = "/inspectionDisease/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "桥梁隧道巡检病害表全部查询",value = "桥梁隧道巡检病害表全部查询",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer")
    })
    public HashMap<String,List<InspectionDiseaseTableCellVo>> listInspectionRecordByTime(@PathVariable("structureId") Integer structureId)  {
        Structure structure=structureDao.findById(structureId);
        int bridgeType;
        if(structure.getStructureType()==2){
            bridgeType=0;
        }else{
            bridgeType=structure.getBridgeType();
        }
        return diseaseService.selectInspectionDiseaseByStructureBridgeType(bridgeType);
    }

    @PostMapping (value = "/importInsExcel")
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(notes = "导入巡查excel",value = "导入巡查excel",httpMethod = "POST")
    public ResultBody importExcel(@RequestParam("excel") MultipartFile excel)  {
        return ResultUtil.success("导入成功"+excelImportService.importExcelInspectionRecord(excel)+"条数据");
    }

    @GetMapping(value = "/testImportExcel")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "测试导入excel",value = "测试导入excel",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filePath", value = "文件地址", required = true, paramType = "query", dataType = "String")
    })
    public Map<String,List<T>> testImportExcel(@Param("filePath") String filePath)  {
        try {
            return new ExcelImportUtil() {
                @Override
                protected HashMap<Integer, String> colWithPropertyName(String sheetName) {
                    switch (sheetName){
                        case "巡查日计划详情表":
                            HashMap<Integer,String> dayMap=new HashMap<>();
                            dayMap.put(0,"Id");
                            dayMap.put(1,"PlanId");
                            dayMap.put(2,"StructureName");
                            dayMap.put(3,"Inspector");
                            dayMap.put(4,"InspectionTime");
                            dayMap.put(5,"Weather");
                            dayMap.put(6,"Location");
                            dayMap.put(7,"InspectionUnit");
                            dayMap.put(8,"Status");
                            return dayMap;
                        case "月计划表":
                            HashMap<Integer,String> monthMap=new HashMap<>();
                            monthMap.put(0,"Id");
                            monthMap.put(1,"Name");
                            monthMap.put(2,"ProjectName");
                            monthMap.put(3,"PlanTime");
                            monthMap.put(4,"Budget");
                            monthMap.put(5,"Expenditure");
                            monthMap.put(6,"Status");
                            return monthMap;
                        case "巡查细项病害情况":
                            HashMap<Integer,String> diseaseMap=new HashMap<>();
                            diseaseMap.put(0,"Id");
                            diseaseMap.put(1,"PlanDetailId");
                            diseaseMap.put(2,"InspectionDisease");
                            diseaseMap.put(3,"Quantity");
                            diseaseMap.put(4,"Strategy");
                            diseaseMap.put(5,"Remark");
                            diseaseMap.put(6,"Status");
                            diseaseMap.put(7,"ExceptionPart");
                            diseaseMap.put(8,"ExceptionType");
                            return diseaseMap;
                        default:
                            return null;
                    }
                }

                @Override
                protected HashMap<Integer, Integer> colWithPropertyType(String sheetName) {
                    HashMap<Integer,Integer> result=new HashMap<>();
                    switch (sheetName){
                        case "巡查日计划详情表":
                            result.put(0,EXCEL_CELL_TYPE_INT);
                            result.put(1,EXCEL_CELL_TYPE_INT);
                            result.put(2,EXCEL_CELL_TYPE_STRING);
                            result.put(3,EXCEL_CELL_TYPE_STRING);
                            result.put(4,EXCEL_CELL_TYPE_DATE);
                            result.put(5,EXCEL_CELL_TYPE_STRING);
                            result.put(6,EXCEL_CELL_TYPE_STRING);
                            result.put(7,EXCEL_CELL_TYPE_STRING);
                            result.put(8,EXCEL_CELL_TYPE_INT);
                            return result;
                        case "月计划表":
                            result.put(0,EXCEL_CELL_TYPE_INT);
                            result.put(1,EXCEL_CELL_TYPE_STRING);
                            result.put(2,EXCEL_CELL_TYPE_STRING);
                            result.put(3,EXCEL_CELL_TYPE_MONTH);
                            result.put(4,EXCEL_CELL_TYPE_BIGDATA);
                            result.put(5,EXCEL_CELL_TYPE_BIGDATA);
                            result.put(6,EXCEL_CELL_TYPE_INT);
                            return result;
                        case "巡查细项病害情况":
                            result.put(0,EXCEL_CELL_TYPE_INT);
                            result.put(1,EXCEL_CELL_TYPE_INT);
                            result.put(2,EXCEL_CELL_TYPE_STRING);
                            result.put(3,EXCEL_CELL_TYPE_INT);
                            result.put(4,EXCEL_CELL_TYPE_INT);
                            result.put(5,EXCEL_CELL_TYPE_STRING);
                            result.put(6,EXCEL_CELL_TYPE_INT);
                            result.put(7,EXCEL_CELL_TYPE_STRING);
                            result.put(8,EXCEL_CELL_TYPE_INT);
                            return result;
                        default:
                            return null;
                    }
                }

                @Override
                protected String getRowClassName(String sheetName) {
                    switch (sheetName){
                        case "巡查日计划详情表":
                            return ExcelPlanDetailTableVo.class.getName();
                        case "月计划表":
                            return ExcelPlanInfoTableVo.class.getName();
                        case "巡查细项病害情况":
                            return ExcelDiseaseInstanceTableVo.class.getName();
                        default:
                            return null;
                    }
                }
            }.importExcel(new File(filePath));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/batch/export/project/{projectId}/{year}/{month}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="批量打包项目巡查报告",value="批量打包项目巡查报告",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "month", value = "月份", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "path", dataType = "integer")
    })
    public String batchExportByProject(@PathVariable("year") Integer year,@PathVariable("month") Integer month,@PathVariable("projectId") Integer projectId) {
        PlanRecordFilter filter=new PlanRecordFilter();
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DAY_OF_MONTH,1);
        Date beginTime=c.getTime();
        filter.setStartTime(beginTime);
        c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endTime=c.getTime();
        filter.setEndTime(endTime);
        File zipDir=new File(path+"/巡查报告批量导出");
        new UnZipAnRar().deleteFile(zipDir);
        zipDir.mkdirs();
        File zipToDelete=new File(path+"/"+"巡查报告批量导出.zip");
        if(zipToDelete.exists()){
            zipToDelete.delete();
        }
        ProjectStructureRel condition=new ProjectStructureRel();
        condition.setProjectId(projectId);
        List<ProjectStructureRel> result=projectStructureRelDao.query(condition);
        for(ProjectStructureRel stru:result) {
            filter.setStructureId(stru.getStructureId());
            String outputPath = planDetailService.batchExport(1, new ArrayList<>(),filter ,1);
            copyFile(outputPath,zipDir+"/"+
                    structureDao.findById(stru.getStructureId()).getName() +
                    year.toString() + "年" +
                    month.toString() + "月" +
                    "批量导出合并文档.docx");
            new File(outputPath).delete();
        }
        try {
            new InZipUntil().inZip(zipDir.getPath(), new File(path+"/"+"巡查报告批量导出.zip"));
        }catch (Exception e){
            e.printStackTrace();
            Logger logger= LoggerFactory.getLogger(InspectionRecordApi.class);
            StringBuilder builder=new StringBuilder();
            for(StackTraceElement element:e.getStackTrace()){
                builder.append(element).append("\n");
            }
            logger.error(builder.toString());
        }
        return path+"/巡查报告批量导出.zip";
    }

    @PostMapping("/batch/excel/export")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="批量导出项目记录列表",value="批量导出项目记录列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keywordVo", value = "导出数据条件", required = true, paramType = "body", dataType = "KeywordVo")
    })
    public void batchExcelExport(@RequestBody KeywordVo keywordVo,
                                 HttpServletResponse response) {
        String path = planDetailService.batchExcelExport(keywordVo);
        FileUtil.pushToWeb(response, path, "APPLICATION/OCTET-STREAM");
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
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }

}
