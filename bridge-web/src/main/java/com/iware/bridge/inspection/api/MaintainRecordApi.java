package com.iware.bridge.inspection.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.service.PhotoService;
import com.iware.bridge.inspection.serivce.ExcelImportService;
import com.iware.bridge.inspection.serivce.PlanDetailService;
import com.iware.bridge.inspection.vo.ExportFilterVo;
import com.iware.bridge.inspection.vo.MaintainItemVO;
import com.iware.bridge.inspection.vo.PlanRecordFilter;
import com.iware.bridge.model.dao.global.PhotoDao;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.common.annotations.CheckRepeat;
import com.iware.common.exception.BusinessException;
import com.iware.common.result.ResultBody;
import com.iware.common.utils.FileUtil;
import com.iware.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LBX
 * @date 2021-8-2
 */

@RestController
@Api(value = "维养记录接口", tags="维养记录接口",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/maintain/record",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MaintainRecordApi {

    @Autowired
    private PlanDetailService planDetailService;

    @Autowired
    private PhotoService photoService;

    @Resource
    private ExcelImportService excelImportService;

    @Resource
    private PhotoDao photoDao;

    @PostMapping(value = "/list")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取维养记录列表",value="获取维养记录列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "查询条件", required = true, paramType = "body", dataType = "PlanRecordFilter"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "filter", value = "条件", required = true, paramType = "body", dataType = "PlanDetailFilter")
    })
    public PageInfo<MaintainItemVO> listMaintainRecord(@RequestParam(value = "pageNum") Integer pageNum,
                                                       @RequestParam(value = "pageSize") Integer pageSize,
                                                       @RequestBody PlanRecordFilter filter) {
        return planDetailService.listMaintainRecord(pageNum, pageSize, filter);
    }

    @PostMapping(value = "/batch/export")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="批量导出记录列表",value="批量导出记录列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filterVo", value = "导出数据条件", required = true, paramType = "body", dataType = "ExportFilterVo")
    })
    public void batchExport(@RequestBody ExportFilterVo filterVo, HttpServletResponse response) {
        FileUtil.pushToWeb(response, planDetailService.batchExport(2, filterVo.getIds(), filterVo.getFilter(),
                filterVo.getExportAll()), "APPLICATION/OCTET-STREAM");
    }

    @GetMapping(value = "/download/{maintainItemId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="下载简报",value="下载简报",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "maintainItemId", value = "维修记录id", required = true, paramType = "path", dataType = "integer"),
    })
    public void downloadMaintainItem(@PathVariable("maintainItemId") Integer maintainItemId, HttpServletResponse response) {
        FileUtil.pushToWeb(response, planDetailService.downloadMaintainItem(maintainItemId), "APPLICATION/OCTET-STREAM");
    }

    @GetMapping(value = "/{maintainItemId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="查询单条细项",value="查询单条细项",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "maintainItemId", value = "维修记录id", required = true, paramType = "path", dataType = "integer"),
    })
    public MaintainItemVO selMaintainItem(@PathVariable("maintainItemId") Integer maintainItemId) {
        return planDetailService.selMaintainItem(maintainItemId);
    }

    @GetMapping(value = "/date/{date}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取当天工作记录",value="获取当天工作记录",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "维养时间", required = true, paramType = "path", dataType = "string"),
    })
    public List<MaintainItemVO> listMaintainRecordByTime(@PathVariable("date") String date) {
        return planDetailService.listMaintainRecordByTime(date);
    }

    @PostMapping (value = "/importMaiExcel")
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(notes = "导入维养excel",value = "导入维养excel",httpMethod = "POST")
    public ResultBody importExcel(@RequestParam("excel") MultipartFile excel)  {

        return ResultUtil.success("导入成功"+excelImportService.importExcelMaintainRecord(excel)+"条数据");
    }

    @PostMapping(value = "/upload")
    @CheckRepeat
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(value = "上传文件 ", notes = "上传文件", httpMethod = "POST", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "附件实体类", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "files", value = "附件", required = true, paramType = "form", dataType = "__file")
    })
    public void fileUpload(@RequestParam("params") String params,
                           @RequestParam("files") MultipartFile[] files) {
        Photo photo = JSONObject.parseObject(params, Photo.class);
        int afterSize=photoDao.query(photo).size()+ files.length;
        if(afterSize>6){
            throw new BusinessException("只能上传6张图片！");
        }
        photoService.uploadFiles(photo, files);
    }

}
