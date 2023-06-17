package com.iware.bridge.evaluation.api;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.evaluation.service.EvaluationRecordBciService;
import com.iware.bridge.evaluation.service.EvaluationRecordService;
import com.iware.bridge.evaluation.vo.*;
import com.iware.bridge.model.entity.evaluation.Attachment;
import com.iware.bridge.model.entity.evaluation.BridgeRoad;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.common.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author WJP
 * @date 2021-8-13
 */
@RestController
@Api(value = "检测记录", tags = "检测记录", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/evaluation/record", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EvaluationRecordApi {

    private static Logger logger = LoggerFactory.getLogger(EvaluationRecordApi.class);

    @Autowired
    private EvaluationRecordService evaluationRecordService;
    @Autowired
    private EvaluationRecordBciService evaluationRecordBciService;

    @PostMapping(value = "/getRecordList")
    @ApiOperation(value = "获取检测列表(最外层)", notes = "获取检测列表", httpMethod = "POST")
    @ApiImplicitParam(name = "assessRecordFilter", value = "查询列表条件对象", paramType = "body", dataType = "AssessRecordFilter")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public List<AssessRecordVO> getRecordList(@RequestBody AssessRecordFilter assessRecordFilter) {

        return evaluationRecordService.getRecordList(assessRecordFilter);
    }

    @GetMapping(value = "/delMonitorStructure")
    @ApiOperation(value = "删除监测结构物（最外层）", notes = "删除监测结构物", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "监测结构物id", paramType = "Integer", dataTypeClass = Integer.class)
    })
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    public void delMonitorStructure(@RequestParam(value = "id", required = false) Integer id) {
        evaluationRecordService.delMonitorStructure(id);
    }

    @PostMapping(value = "/getBCIEvaluationList")
    @ApiOperation(value = "获取BCI评价列表（BCI评价）", notes = "获取BCI评价列表", httpMethod = "POST")
    @ApiImplicitParam(name = "assessRecordFilter", value = "查询列表条件对象", paramType = "body", dataType = "AssessRecordFilter")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public List<BCIEvaluation> getBCIEvaluationList(@RequestBody AssessRecordFilter assessRecordFilter) {
        return evaluationRecordService.getBCIEvaluationList(assessRecordFilter);
    }

    @PostMapping(value = "/getBridgeSurveyDetail")
    @ApiOperation(value = "获取桥梁结构树形结构（桥梁概况）", notes = "获取桥梁结构树形结构 ", httpMethod = "POST")
    @ApiImplicitParam(name = "bridgeSurveyDetailListFilter", value = "桥梁概况查询条件", paramType = "body", dataType = "BridgeSurveyDetailListFilter")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public List<BridgeSurveyFirstTierVO> getBridgeSurveyDetail(@RequestBody BridgeSurveyDetailListFilter bridgeSurveyDetailListFilter) {
        return evaluationRecordService.getBridgeSurveyDetail(bridgeSurveyDetailListFilter);
    }

    @GetMapping(value = "/isOriginalRecordByStructure/{id}")
    @ApiOperation(value = "是否导出原始记录（全局） ", notes = "是否导出原始记录 ", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "结构物计划id", dataType = "Integer", paramType = "path")
    public boolean isOriginalRecordByStructure(@PathVariable(value = "id") Integer id) {
        return evaluationRecordService.isOriginalRecordByStructure(id);
    }

    @PostMapping(value = "/getPictures")
    @ApiOperation(value = "获取桥梁结构图片（桥梁概况） ", notes = "获取桥梁结构图片 ", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structPhotoFilter", value = "桥梁结构图片查询过滤器", paramType = "body", dataType = "StructPhotoFilter")
    })
    public List<StructPhoto> getPhotos(@RequestBody StructPhotoFilter structPhotoFilter) {
        return evaluationRecordService.getBridgeStructPhoto(structPhotoFilter);
    }

    @PostMapping(value = "/getDiseaseList")
    @ApiOperation(value = "桥梁病害获取数据（桥梁病害） ", notes = "桥梁病害获取数据 ", httpMethod = "POST")
    @ApiImplicitParam(name = "diseaseBridgeListFilterVO", value = "检测记录-桥梁病害查询过滤器", paramType = "body", dataType = "DiseaseBridgeListFilterVO")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public List<DiseaseBridge> getDiseaseList(@RequestBody DiseaseBridgeListFilterVO diseaseBridgeListFilterVO) {

        return evaluationRecordService.getDiseaseList(diseaseBridgeListFilterVO);
    }

    @PostMapping(value = "/getDiseaseDetail")
    @ApiOperation(value = "桥梁概况获取详细数据（桥梁概况） ", notes = "桥梁概况获取详细数据 ", httpMethod = "POST")
    @ApiImplicitParam(name = "situationFilter", value = "检测记录-桥梁病害详细数据条件", paramType = "body", dataType = "BridgeSituationFilter")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public Map<String, Object> getDiseaseList(@RequestBody BridgeSituationFilter situationFilter) {

        return evaluationRecordService.getBSInformation(situationFilter);
    }

    @PostMapping(value = "/uploadPicture", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "上传桥梁结构图片 （桥梁概况）", notes = "上传桥梁结构图片 ", httpMethod = "POST", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", dataType = "file", paramType = "file"),
            @ApiImplicitParam(name = "type", value = "附件类型(1：线路图片 2：桥垮图片 3：部位图片 4：构件图片 5：病害图片)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "partType", value = "部位类型(1：桥面系 2：上部结构 3：下部结构)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "targetId", value = "目标id", dataType = "int", paramType = "query")
    })
    public Attachment uploadPhotos(@RequestParam MultipartFile file,
                                   @RequestParam Integer type,
                                   @RequestParam Integer partType,
                                   @RequestParam Integer targetId) {
        return evaluationRecordService.addStructPhoto(file, type, partType, targetId);
    }

    @GetMapping(value = "/getPhotoUrl/{id}")
    @ApiOperation(value = "照片地址 （桥梁病害）", notes = "照片地址 ", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "病害实例id", dataType = "integer", paramType = "path"),
    })
    public List<PhotoUrl> getPhotoUrl(@PathVariable("id") Integer id) {
        return evaluationRecordService.getPhotoUrl(id);
    }


    @PostMapping(value = "/uploadPhotoUrl")
    @ApiOperation(value = "上传病害照片 （桥梁病害）", notes = "上传照片 ", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", dataType = "file", paramType = "file"),
            @ApiImplicitParam(name = "map", value = "参数", dataType = "Map", paramType = "query"),
    })
    public void uploadPhotoUrl(@RequestParam Map<String, String> map, @RequestParam MultipartFile file) {
        evaluationRecordService.addPhotoUrl(map, file);

    }

    @DeleteMapping(value = "/deletePhoto/{id}")
    @ApiOperation(value = "删除照片（桥梁病害） ", notes = "删除照片 ", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "照片id", dataType = "Integer", paramType = "path")
    public void deletePhoto(@PathVariable("id") Integer id) {
        evaluationRecordService.deletePhoto(id);
    }

    @PutMapping(value = "/updatePictureStatus/{id}")
    @ApiOperation(value = "选择结构默认图片（桥梁概况）", notes = "选择结构默认图片 ", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "照片id", dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "attachment", value = "照片实例", paramType = "body", dataType = "Attachment")
    })
    public void selectPhoto(@PathVariable("id") Integer id,
                            @RequestBody Attachment attachment) {
        attachment.setId(id);
        evaluationRecordService.selectDefaultStructPhoto(attachment);
    }

    @PostMapping(value = "/pictures/download")
    @ApiOperation(value = "下载桥梁结构图片（桥梁概况） ", notes = "下载桥梁结构图片 ", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structPhotoFilter", value = "桥梁结构图片查询过滤器", paramType = "body", dataType = "StructPhotoFilter")
    })
    public String downloadPhotos(@RequestBody StructPhotoFilter structPhotoFilter, HttpServletResponse response) {
        return evaluationRecordService.downloadStructPhoto(structPhotoFilter);
    }

    @PostMapping(value = "/updatePhoto")
    @ApiOperation(value = "修改检测照片（桥梁病害）", notes = "修改检测照片 ", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attachment", value = "照片实例", paramType = "body", dataType = "Attachment")

    })
    public void updatePhoto(@RequestBody Attachment attachment) {
        evaluationRecordService.updatePhoto(attachment);
    }


    @PostMapping(value = "/getDiseaseRecordList")
    @ApiOperation(value = "查看记录数据列表（桥梁病害）", notes = "查看记录数据列表 ", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recordListFilter", value = "检测记录-病害修改记录分页过滤器", paramType = "body", dataType = "RecordListFilter")
    })
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public PageInfo<RecordVO> getRecordList(@RequestBody RecordListFilter recordListFilter) {
        return evaluationRecordService.getDiseaseRecordList(recordListFilter);
    }

    @PostMapping(value = "/getRecyclingList")
    @ApiOperation(value = "回收站获取数据（桥梁病害） ", notes = "回收站获取数据 ", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recyclingListFilter", value = "检测记录-桥梁病害-回收站分页过滤器", paramType = "body", dataType = "RecyclingListFilter")

    })
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public PageInfo<RecyclingVO> getRecyclingList(@RequestBody RecyclingListFilter recyclingListFilter) {
        return evaluationRecordService.getRecyclingList(recyclingListFilter);
    }

    @PostMapping(value = "/replaceRecord")
    @ApiOperation(value = "替换记录（桥梁病害） ", notes = "替换记录 ", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recordDetailListFilter", value = "检测记录-桥梁病害-回收站-记录详情过滤器", paramType = "body", dataType = "RecordDetailListFilter")
    })
    public void replaceRecord(@RequestBody RecordDetailListFilter recordDetailListFilter) {
        evaluationRecordService.replaceRecord(recordDetailListFilter);
    }

    @PostMapping(value = "/getRecordDetailList")
    @ApiOperation(value = "记录详情获取数据（桥梁病害） ", notes = "记录详情获取数据 ", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recordDetailListFilter", value = "检测记录-桥梁病害-回收站-记录详情过滤器", paramType = "body", dataType = "RecordDetailListFilter")
    })
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public List<DiseaseBridge> getRecordDetailList(@RequestBody RecordDetailListFilter recordDetailListFilter) {
        return evaluationRecordService.getRecordDetailList(recordDetailListFilter);
    }

    @DeleteMapping(value = "/pictures/{id}")
    @ApiOperation(value = "删除结构图片（桥梁概况）", notes = "删除结构图片 ", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "图片id", dataType = "int", paramType = "path", required = true)
    })
    public void deletePhotoId(@PathVariable("id") Integer id) {
        evaluationRecordService.deleteStrunctPhoto(id);
    }

    @GetMapping(value = "/getExportRecord/{id}")
    @ApiOperation(value = "获取检测记录表信息（原始记录） ", notes = "生成记录 ", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "计划结构物id", dataType = "Integer", paramType = "path"),
    })
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public List<ShowManage> getExportRecord(@PathVariable("id") Integer id) {
        return evaluationRecordService.getRecordDetail(id);
    }


    @GetMapping(value = "/getOriginalRecord/{id}")
    @ApiOperation(value = "获取结构物原始记录（原始记录） ", notes = "获取结构物原始记录 ", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "计划结构物id", dataType = "Integer", paramType = "path")
    })
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public OriginalRecord getOriginalRecord(@PathVariable("id") Integer id) {
        return evaluationRecordService.getOriginalRecord(id);
    }

    @GetMapping(value = "/exportRecord/{id}")
    @ApiOperation(value = "导出记录（原始记录） ", notes = "导出记录 ", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "计划结构物id", dataType = "Integer", paramType = "path")
    })
    public String exportRecord(@PathVariable("id") Integer id) {
        return evaluationRecordService.exportRecord(id);

    }

    @PostMapping(value = "/insertRecord")
    @ApiOperation(value = "生成记录（原始记录） ", notes = "生成记录 ", httpMethod = "POST")
    @ApiImplicitParam(name = "originalRecord", value = "原始记录", paramType = "body", dataType = "OriginalRecord")
    public void insertRecord(@RequestBody OriginalRecord originalRecord) {
        evaluationRecordService.insertRecord(originalRecord);

    }

    @PostMapping(value = "/generateReport")
    @ApiOperation(value = "生成报告（检测报告） ", notes = "生成报告 ", httpMethod = "POST")
    @ApiImplicitParam(name = "generateReportVO", value = "生成报告", paramType = "body", dataType = "GenerateReportVO")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public Integer generateReport(@RequestBody GenerateReportVO generateReportVO) {
        int ret = -1;
        ret = evaluationRecordService.generateReport(generateReportVO);

        return ret;
    }

    @GetMapping(value = "/getRoadList/{id}")
    @ApiOperation(value = "获取列表数据（检测报告） ", notes = "获取列表数据 ", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "计划结构物id", dataType = "Integer", paramType = "path")
    })
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public List<RoadBridge> getRoadList(@PathVariable("id") Integer id) {
        return evaluationRecordService.selectRoadList(id);
    }

    @PostMapping(value = "/selBCIEvaluationDetail")
    @ApiOperation(value = "查找BCI评价详情(BCI评价)", notes = "查找BCI评价详情", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bciDetailVO", value = "BCI详情参数对象",
                    paramType = "body", dataType = "BCIDetailVO")
    })
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public BCIEvaluationDetailDTO selBCIEvaluationDetail(@RequestBody BCIDetailVO bciDetailVO) {
        return evaluationRecordBciService.selBCIEvaluationDetail(bciDetailVO);
    }


    @PostMapping(value = "/countBCI")
    @ApiOperation(value = "计算BCI评价(BCI评价)", notes = "计算BCI评价", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bciEvaluationDetailDTO", value = "BCI详情对象",
                    paramType = "body", dataType = "BCIEvaluationDetailDTO")
    })
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public BCIEvaluationDetailDTO countBCI(@RequestBody BCIEvaluationDetailDTO bciEvaluationDetailDTO, HttpServletRequest request, HttpServletResponse response) {
        return evaluationRecordBciService.countBCI(bciEvaluationDetailDTO, request);

    }

    @GetMapping(value = "/calculateDeduct/{id}")
    @ApiOperation(value = "计算病害扣分值 （BCI评价）", notes = "计算病害扣分值 ", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "线路id", dataType = "Integer", paramType = "path")
    })
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public void calculateDeduct(@PathVariable("id") Integer id) {
        evaluationRecordService.calculateDeduct(id);
    }

    @GetMapping(value = "/getDiseaseSummaryList/{id}")
    @ApiOperation(value = "查询线路病害（BCI评价的扣分病害数据） ", notes = "查询线路病害 ", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "线路id", dataType = "Integer", paramType = "path")
    })
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public List<DiseaseSummary> getDiseaseSummaryList(@PathVariable("id") Integer id) {
        return evaluationRecordService.selectDiseaseSummary(id);
    }

    @GetMapping("downloadTestReport/{id}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "下载(检测报告)", notes = "下载", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "线路id", dataType = "int", paramType = "path"),
    })
    public void downloadTestReport(@PathVariable("id") Integer id, HttpServletResponse response) {
        List<BridgeRoad> bridgeRoads = evaluationRecordService.downloadTestReport(id);
        if (bridgeRoads.size() != 0) {
            FileUtil.pushToWeb(response, bridgeRoads.get(0).getReportPath(), "APPLICATION/OCTET-STREAM");
        }
    }

}
