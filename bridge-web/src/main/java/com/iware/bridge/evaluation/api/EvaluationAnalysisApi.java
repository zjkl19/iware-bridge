package com.iware.bridge.evaluation.api;

import com.iware.bridge.evaluation.service.EvaluationAnalysisService;
import com.iware.bridge.evaluation.vo.*;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WJP
 * @date 2021-8-13
 */
@Api(value = "检测数据分析", tags = "检测数据分析相关")
@RestController
@RequestMapping(value = "/evaluation/monitorAnalysis")
public class EvaluationAnalysisApi {

    @Autowired
    private EvaluationAnalysisService evaluationAnalysisService;

    @PostMapping(value = "/newProject")
    @ApiOperation(value = "获取最新的项目/结构物/时间数据", notes = "获取最新的项目/结构物/时间数据 ", httpMethod = "POST")
    @ApiImplicitParam(name = "monitorAnalysisFilterVO", value = "项目结构物id时间过滤类", dataType = "MonitorAnalysisFilterVO", paramType = "body")
    public EvaluationAnalysisVO newProject(@RequestBody MonitorAnalysisFilterVO monitorAnalysisFilterVO) {
        return evaluationAnalysisService.getNewProject(monitorAnalysisFilterVO);
    }

    @PostMapping(value = "/getPageInfo")
    @ApiOperation(value = "获取页面信息", notes = "检测次数/检测病害数/结构病害数量统计/构件病害数量统计 ", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "monitorAnalysisFilterVO", value = "项目结构物id时间过滤类", dataType = "MonitorAnalysisFilterVO", paramType = "body")
    })
    public MonitorAnalysisResultVO getPageInfo(@RequestBody MonitorAnalysisFilterVO monitorAnalysisFilterVO) {
        return evaluationAnalysisService.getPageInfo(monitorAnalysisFilterVO);
    }

    @PostMapping(value = "/getComponentDiseaseType")
    @ApiOperation(value = "获取构件病害类型统计", notes = "构件病害类型统计接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "monitorAnalysisFilterVO", value = "项目结构物id时间过滤类", dataType = "MonitorAnalysisFilterVO", paramType = "body")
    })
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public List<ComponentDiseaseTypeNumber> getComponentDiseaseType(@RequestBody MonitorAnalysisFilterVO monitorAnalysisFilterVO) {
        return evaluationAnalysisService.getComponentDiseaseType(monitorAnalysisFilterVO);
    }

    @PostMapping(value = "/getBCIBSI")
    @ApiOperation(value = "获取BCIBSI评分", notes = "获取BCIBSI评分", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "monitorAnalysisFilterVO", value = "项目结构物id时间过滤类", dataType = "MonitorAnalysisFilterVO", paramType = "body")
    })
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public List<ScoreComparison> getBciBsi(@RequestBody MonitorAnalysisFilterVO monitorAnalysisFilterVO) {
        return evaluationAnalysisService.getBciBsi(monitorAnalysisFilterVO);
    }

    @GetMapping(value = "/getStructureTimeList/{id}/{project}")
    @ApiOperation(value = "获取时间", notes = "获取时间 ", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "结构物id", dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "project", value = "项目id", dataType = "Integer", paramType = "path")
    })
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public List<String> getStructureTimeList(@PathVariable(value = "id") Integer id,@PathVariable(value = "project") Integer project) {
        return evaluationAnalysisService.selectStructureTimeByStructureId(id,project);
    }
}
