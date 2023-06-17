package com.iware.bridge.info.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.service.ReportService;
import com.iware.bridge.info.vo.ReportVO;
import com.iware.bridge.model.entity.global.Report;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.common.annotations.CheckRepeat;
import com.iware.common.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ZRB
 * @date 2021-7-29
 */

@RestController
@Api(value = "报表管理", tags="报表管理",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/report",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ReportApi {

    @Autowired
    private ReportService reportService;

    @PostMapping("/list")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "获取报表列表", notes = "获取报表列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query",  required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "report", value = "报告",dataType = "Report", paramType = "body", required = true)
    })
    public PageInfo<ReportVO> listByPage(@RequestParam(value = "pageNum") Integer pageNum,
                                         @RequestParam(value = "pageSize") Integer pageSize,
                                         @RequestBody Report report) {
        return reportService.listByPage(pageNum, pageSize, report);
    }

    @PostMapping("/{type}")
    @CheckRepeat
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(value = "添加报告", notes = "添加报告", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型：[1:监测报表 2:巡查报表 3:维养报表]",dataType = "Integer", paramType = "path", required = true),
            @ApiImplicitParam(name = "file", value = "附件", required = true, paramType = "form", dataType = "_file"),
            @ApiImplicitParam(name = "params", value = "附件实体类", required = true, paramType = "form", dataType = "String")
    })
    public void addReport(@PathVariable("type") Integer type, @RequestParam("params") String params,
                                         @RequestParam(value = "file") MultipartFile file) {
        Report report = JSONObject.parseObject(params, Report.class);
        report.setType(type);
        reportService.addReport(file, report);
    }

    @PutMapping(value = "/{reportId}")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(value = "修改报告", notes = "修改报告", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reportId", value = "报告id",dataType = "Integer", paramType = "path", required = true),
            @ApiImplicitParam(name = "file", value = "附件", required = false, paramType = "form", dataType = "__file"),
            @ApiImplicitParam(name = "params", value = "报告实体类", required = true, paramType = "form", dataType = "String")
    })
    public void updateReport(@PathVariable("reportId") Integer reportId,
                             @RequestParam("params") String params,
                             @RequestParam(value = "file", required = false) MultipartFile file){

        Report report = JSONObject.parseObject(params, Report.class);
        report.setId(reportId);
        reportService.updateReport(file, report);
    }

    @DeleteMapping(value = "/{reportId}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    @ApiOperation(value = "删除报告", notes = "删除报告", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reportId", value = "报告id",dataType = "Integer", paramType = "path", required = true)
    })
    public void deleteReport(@PathVariable("reportId") Integer reportId){

        reportService.deleteReport(reportId);
    }

    @GetMapping("/download/{id}")
    @ApiOperation(value = "下载报告", notes = "下载报告", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "报告id", dataType = "Integer", paramType = "path", required = true)
    })
    public void download(@PathVariable("id") Integer id, HttpServletResponse response) {
        Report report = reportService.findById(id);
        FileUtil.pushToWeb(response, report.getPath(), "APPLICATION/OCTET-STREAM");
    }
}
