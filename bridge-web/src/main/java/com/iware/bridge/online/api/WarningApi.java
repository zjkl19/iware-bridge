package com.iware.bridge.online.api;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.model.entity.online.SensorConverge;
import com.iware.bridge.online.service.WarningService;
import com.iware.bridge.online.vo.ProcessFilter;
import com.iware.bridge.online.vo.WarningRatio;
import com.iware.bridge.online.vo.WarningRecordFilter;
import com.iware.bridge.online.vo.WarningRecordVO;
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
 * @author WJP
 * @date 2021-7-27
 */

@RestController
@Api(value = "预警管理", tags = "预警管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/online/warning", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WarningApi {

    @Autowired
    private WarningService warningService;

    @PostMapping(value = "/list")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "获取预警列表", value = "获取预警列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "filter", value = "条件", required = true, paramType = "body", dataType = "WarningRecordFilter")
    })
    public PageInfo<WarningRecordVO> listWarningRecord(@RequestParam(value = "pageNum") Integer pageNum,
                                                       @RequestParam(value = "pageSize") Integer pageSize,
                                                       @RequestBody WarningRecordFilter filter) {
        return warningService.listWarningRecord(pageNum, pageSize, filter);
    }

    @PutMapping(value = "/processing")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(notes = "处理/批处理预警", value = "处理/批处理预警", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "处理参数", required = true, paramType = "body", dataType = "ProcessFilter")
    })
    public void batchProcessing(@RequestBody ProcessFilter filter) {
        warningService.batchProcessing(filter);
    }

    @GetMapping({"/statistics/{type}", "/statistics/{structureId}/{type}"})
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "预警统计", value = "预警统计", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型[0:全部 1:今日 2:本月 3:今年]", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer")
    })
    public List<WarningRatio> getWarningStatistics(@PathVariable("type") Integer type,
                                                   @PathVariable(value = "structureId", required = false) Integer structureId) {
        return warningService.getWarningStatistics(type, structureId);
    }

    @PostMapping(value = "/getSensorData")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "获取预警信息点附近折线数据", value = "获取预警信息点附近折线数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sensorConverge", value = "sensorCoding和warningTime", required = true, paramType = "body", dataType = "SensorConverge")
    })
    public Map<String, String> getSensorData(@RequestBody SensorConverge sensorConverge) {
        return warningService.getSensorData(sensorConverge);
    }

    @GetMapping("/getOnlineNotice")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取是否有未处理预警、故障传感器",value="获取是否有未处理预警、故障传感器",httpMethod="GET")
    @ApiImplicitParams({})
    public Map<String, Integer> getOnlineNotice() {
        return warningService.getOnlineNotice();
    }

}
