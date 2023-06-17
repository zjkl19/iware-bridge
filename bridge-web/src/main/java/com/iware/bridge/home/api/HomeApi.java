package com.iware.bridge.home.api;

import com.iware.bridge.home.service.HomeService;
import com.iware.bridge.home.vo.HomeBase;
import com.iware.bridge.home.vo.MeansPointVideoVO;
import com.iware.bridge.home.vo.StructureType;
import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.info.vo.StructureFilter;
import com.iware.bridge.model.entity.online.WarningRecord;
import com.iware.bridge.online.service.WarningService;
import com.iware.bridge.online.vo.WarningRatio;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author ZRB
 * @date 2021-8-4
 */

@RestController
@Api(value = "首页接口",tags="首页接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/home",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HomeApi {

    @Autowired
    private HomeService homeService;
    @Autowired
    private WarningService warningService;

    @PostMapping(value = "/listStructure")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="首页获取用户桥梁",value="首页获取用户桥梁",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "结构物查询过滤条件", dataType = "StructureFilter", paramType = "body"),
    })
    public StructureType listStructure(@RequestBody StructureFilter filter) {
        return homeService.listStructure(filter);
    }

    @GetMapping(value = "/getBaseInfo")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取基础情况",value="获取基础情况",httpMethod="GET")
    @ApiImplicitParams({})
    public HomeBase getBaseInfo() {
        return homeService.getBaseInfo();
    }

    @GetMapping(value = "/getWarningInfo/{type}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取预警统计",value="获取预警统计",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型[0:全部 1:今日 2:本月 3:今年]", required = true, paramType = "path", dataType = "integer"),
    })
    public List<WarningRatio> getWarningInfo(@PathVariable("type") Integer type) {
        return warningService.getWarningStatistics(type, null);
    }

    @GetMapping(value = "/getInspMainInfo/{type}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取巡查维养信息",value="获取巡查维养信息",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型[0:全部 1:今日 2:本月 3:今年]", required = true, paramType = "path", dataType = "integer"),
    })
    public Map<String, Integer> getInspMainInfo(@PathVariable("type") Integer type) {
        return homeService.getInspMainInfo(type);
    }

    @GetMapping(value = {"/getComprehensive", "/getComprehensive/{structureId}"})
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取桥隧综合评价指数",value="获取桥隧综合评价指数",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = false, paramType = "path", dataType = "integer"),
    })
    public List<Double> getComprehensive(@PathVariable(value = "structureId", required = false) Integer structureId) {
        return homeService.getComprehensive(structureId);
    }

    @GetMapping(value = {"/detail/getEvaluationInfo/{structureId}"})
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取首页详情检测概况",value="获取首页详情检测概况",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer"),
    })
    public Map<String, String> getEvaluationInfo(@PathVariable("structureId") Integer structureId) {
        return homeService.getEvaluationInfo(structureId);
    }

    @GetMapping(value = "/detail/getMaintainInfo/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="首页详情页获取巡查维养概况",value="首页详情页获取巡查维养概况",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer"),
    })
    public Map<String, String> getLastInspMainInfo(@PathVariable("structureId") Integer structureId) {
        return homeService.getLastInspMainInfo(structureId);
    }

    @GetMapping(value = "/detail/getSensorInfo/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="首页详情页获取监测概况",value="首页详情页获取监测概况",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer"),
    })
    public Map<String, Integer> getSensorInfo(@PathVariable("structureId") Integer structureId) {
        return homeService.getSensorInfo(structureId);
    }

    @GetMapping(value = "/detail/getMainEvaHistory/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="首页详情页获取历史检测维养",value="首页详情页获取历史检测维养",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer"),
    })
    public List<EchartMap> getMainEvaHistory(@PathVariable("structureId") Integer structureId) {
        return homeService.getMainEvaHistory(structureId);
    }


    @GetMapping(value = "/detail/getSensorAndVideo/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取传感器和视频",value="获取传感器和视频",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer")
    })
    public MeansPointVideoVO getSensorAndVideo(@PathVariable("structureId") Integer structureId) {
        return homeService.getSensorAndVideo(structureId);
    }

    @GetMapping(value = "/getNewestWarning/{meansPointId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "获取传感器最后一条预警信息", value = "获取传感器最后一条预警信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "meansPointId", value = "测点id", required = true, paramType = "path", dataType = "Integer")
    })
    public WarningRecord getNewestWarning(@PathVariable("meansPointId") Integer meansPointId) {
        return homeService.getNewestWarning(meansPointId);
    }

    @GetMapping(value = "/getSensorData/{meansPointId}/{sensorId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "获取用户最新传感器数据", value = "获取用户最新传感器数据", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "meansPointId", value = "测点id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "sensorId", value = "传感器id", required = true, paramType = "path", dataType = "Integer")
    })
    public Map<String, String> getNewestSensorData(@PathVariable("meansPointId") Integer meansPointId,
                                                   @PathVariable("sensorId") Integer sensorId) {
        return homeService.getNewestSensorData(meansPointId, sensorId);
    }
}
