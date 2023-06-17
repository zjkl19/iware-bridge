package com.iware.bridge.evaluation.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.evaluation.service.EvaluationPlanService;
import com.iware.bridge.evaluation.vo.MonitorPlanListVO;
import com.iware.bridge.evaluation.vo.MonitorPlanVO;
import com.iware.bridge.evaluation.vo.MonitorStructureRelVO;
import com.iware.bridge.evaluation.vo.PlanFilter;
import com.iware.bridge.inspection.vo.ReceiveTime;
import com.iware.bridge.model.entity.evaluation.MonitorPlanStructureRel;
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
import java.util.List;

/**
 * @author WJP
 * @date 2021-8-13
 */

@RestController
@Api(value = "检测计划", tags = "检测计划", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/evaluation/plan", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EvaluationPlanApi {

    @Autowired
    private EvaluationPlanService planService;

    @PostMapping("/list")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "获取计划列表", notes = "获取计划列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "filter", value = "条件", dataType = "PlanFilter", paramType = "body")
    })
    public PageInfo<MonitorPlanListVO> listPlan(@RequestParam("pageNum") Integer pageNum,
                                                @RequestParam("pageSize") Integer pageSize,
                                                @RequestBody PlanFilter filter) {
        return planService.listPlan(pageNum, pageSize, filter);

    }

    @GetMapping("/getStructureRel/{planId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "获取计划详情", notes = "获取计划详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "检测计划id", dataType = "int", paramType = "path"),
    })
    public List<MonitorPlanStructureRel> getStructureRel(@PathVariable("planId") Integer planId) {
        return planService.getStructureRel(planId);
    }

    @PostMapping("")
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @CheckRepeat
    @ApiOperation(value = "新增计划", notes = "新增计划", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "计划实体类", dataType = "MonitorPlanVO", paramType = "body"),
            @ApiImplicitParam(name = "files", value = "附件", required = true, paramType = "form", dataType = "__file")
    })
    public void addMonitorPlan(@RequestParam("params") String params,
                               @RequestParam("files") MultipartFile[] files) {
        MonitorPlanVO monitorPlanVO = JSONObject.parseObject(params, MonitorPlanVO.class);
        planService.addMonitorPlan(monitorPlanVO, files);
    }

    @PutMapping("/{planId}")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(value = "修改计划", notes = "修改计划", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "检测计划id", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "params", value = "计划实体类", dataType = "MonitorPlanVO", paramType = "body"),
            @ApiImplicitParam(name = "files", value = "附件", required = true, paramType = "form", dataType = "__file")
    })
    public void updMonitorPlan(@PathVariable("planId") Integer planId, @RequestParam("params") String params,
                               @RequestParam("files") MultipartFile[] file) {
        MonitorPlanVO monitorPlanVO = JSONObject.parseObject(params, MonitorPlanVO.class);
        monitorPlanVO.setId(planId);
        planService.updMonitorPlan(monitorPlanVO, file);

    }

    @DeleteMapping("/{planId}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    @ApiOperation(value = "刪除计划", notes = "刪除计划", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "检测计划id", dataType = "int", paramType = "path")
    })
    public void delMonitorPlan(@PathVariable("planId") Integer planId) {
        planService.delMonitorPlan(planId);
    }

    @PostMapping(value = "/upload")
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @CheckRepeat
    @ApiOperation(value = "上传文件 ", notes = "上传文件", httpMethod = "POST", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "附件", required = true, paramType = "form", dataType = "__file")
    })
    public MonitorPlanStructureRel fileUpload(@RequestParam("file") MultipartFile file) {
        return planService.uploadFiles(file);
    }


    @PostMapping("/structure")
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @CheckRepeat
    @ApiOperation(value = "新增计划里的结构物数据", notes = "新增计划里的结构物数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "计划结构物实体类", dataType = "MonitorStructureRelVO", paramType = "body"),
            @ApiImplicitParam(name = "files", value = "附件", required = true, paramType = "form", dataType = "__file")
    })
    public void addStructureRel(@RequestParam("params") String params, @RequestParam("file") MultipartFile file) {
        MonitorStructureRelVO structureRelVO = JSONObject.parseObject(params, MonitorStructureRelVO.class);
        planService.addStructureRel(structureRelVO, file);
    }

    @PutMapping("structure/{structureRelId}")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(value = "修改计划里的结构物数据", notes = "修改计划里的结构物数据", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureRelId", value = "检测计划里的结构物主键id", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "params", value = "计划结构物实体类", dataType = "MonitorStructureRelVO", paramType = "body"),
            @ApiImplicitParam(name = "files", value = "附件", required = true, paramType = "form", dataType = "__file"),
    })
    public void updStructureRel(@PathVariable("structureRelId") Integer structureRelId, @RequestParam("params") String params, @RequestParam("file") MultipartFile file) {
        MonitorStructureRelVO monitorPlanStructureRel = JSONObject.parseObject(params, MonitorStructureRelVO.class);
        monitorPlanStructureRel.setId(structureRelId);
        planService.updStructureRel(monitorPlanStructureRel, file);
    }

    @DeleteMapping("structure/{structureRelId}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    @ApiOperation(value = "删除计划里的结构物数据", notes = "删除计划里的结构物数据", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureRelId", value = "检测计划里的结构物主键id", dataType = "int", paramType = "path")
    })
    public void delStructureRel(@PathVariable("structureRelId") Integer structureRelId) {
        planService.delStructureRel(structureRelId);
    }

    @GetMapping("download/{id}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "下载", notes = "下载", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "结构物计划id", dataType = "int", paramType = "path"),
    })
    public void download(@PathVariable("id") Integer id, HttpServletResponse response) {
        MonitorPlanStructureRel monitorPlanStructureRel = planService.selectFilePath(id);
        if (monitorPlanStructureRel != null) {
            FileUtil.pushToWeb(response, monitorPlanStructureRel.getFileUrl(), "APPLICATION/OCTET-STREAM");
        }
    }

    @GetMapping("getAppointTime/{projectId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "获取项目的指派时间", notes = "获取项目的指派时间", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "int", paramType = "path"),
    })
    public List<ReceiveTime> getAppointTime(@PathVariable("projectId") Integer projectId) {
        return planService.getAppointTime(projectId);
    }
}
