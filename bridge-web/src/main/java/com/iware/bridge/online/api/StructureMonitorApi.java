package com.iware.bridge.online.api;

import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.online.dto.WarningTimeDto;
import com.iware.bridge.online.service.MonitorOverviewService;
import com.iware.bridge.online.service.StructureMonitorService;
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
 * @author WJP
 * @date 2021-7-27
 */

@RestController
@Api(value = "结构物监测", tags = "结构物监测", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/online/monitor", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StructureMonitorApi {

    @Autowired
    private StructureMonitorService monitorService;
    @Autowired
    private MonitorOverviewService overviewService;

    @GetMapping("/getCount/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "查询结构物在线、离线传感器数/今日预警数/今日待处理", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer")
    })
    public Map<String, Integer> getCount(@PathVariable("structureId") Integer structureId) {
        return monitorService.getCount(structureId);
    }

    @GetMapping("/warningRank/{structureId}/{type}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "测点预警排行", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "type", value = "类型[0:全部 1:今日 2:本月 3:今年]", required = true, paramType = "path", dataType = "integer")
    })
    public List<EchartMap> getWarningRank(@PathVariable("structureId") Integer structureId,
                                                     @PathVariable("type") Integer type) {
        return monitorService.getWarningRank(structureId, type);
    }

    @GetMapping("/sensorList/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "传感器统计", value = "传感器统计", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer")
    })
    public List<EchartMap> getSensorList(@PathVariable("structureId") Integer structureId) {
        return overviewService.getSensorTypeList(structureId);
    }

    @GetMapping("/warningTend/{type}/{structure}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "预警变化趋势", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型[0:全部 1:今日 2:本月 3:今年]", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "structure", value = "结构物id", required = true, paramType = "path", dataType = "integer")
    })
    public List<WarningTimeDto> getWarningTend(@PathVariable("type") Integer type, @PathVariable("structure") Integer structure) {
        return monitorService.getWarningTend(type, structure);
    }
}
