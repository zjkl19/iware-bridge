package com.iware.bridge.inspection.api;

import com.iware.bridge.inspection.serivce.AnalyseService;
import com.iware.bridge.inspection.vo.DiseaseRatioVo;
import com.iware.bridge.inspection.vo.MaintainMethodCycleVo;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author LBX
 * @date 2021-7-29
 */

@RestController
@Api(value = "维养效果分析", tags="维养效果分析",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/maintain/analyse",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MaintainAnalyseApi {
    @Autowired
    private AnalyseService analyseService;

    @GetMapping(value = "/accountTrend/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="项目近五年维修费用变化趋势",value="项目近五年维修费用变化趋势",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer")
    })
    public Map<String, Integer> getAccountTrend(@PathVariable("structureId") Integer structureId) {
        return analyseService.getAccountTrend(structureId);
    }

    @GetMapping(value = "/repairTrend/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="近五年维修趋势",value="近五年维修趋势",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer")
    })
    public Map<String, List<DiseaseRatioVo>> getRepairTrend(@PathVariable("structureId") Integer structureId) {
        return analyseService.getRepairTrend(structureId);
    }

    @GetMapping(value = "/itemRepeatAnalyse/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="重复维修项占比及周期分析",value="重复维修项占比及周期分析",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer"),
    })
    public List<MaintainMethodCycleVo> itemRepeatAnalyse(@PathVariable("structureId") Integer structureId) {
        return analyseService.itemRepeatAnalyse(structureId);
    }

    @GetMapping(value = "/maintainRatio/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="当年维修占比",value="当年维修占比",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer"),
    })
    public List<DiseaseRatioVo> maintainRatio(@PathVariable("structureId") Integer structureId) {
        return analyseService.maintainRatio(structureId);
    }

    @GetMapping(value = "/maintainItemStatistics/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="近五年维修项目统计",value="近五年维修项目统计",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer"),
    })
    public Map<String, Integer> maintainItemStatistics(@PathVariable("structureId") Integer structureId) {
        return analyseService.maintainItemStatistics(structureId);
    }
}
