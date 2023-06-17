package com.iware.bridge.online.api;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.model.entity.online.SensorLog;
import com.iware.bridge.model.entity.online.SensorRecord;
import com.iware.bridge.online.service.SensorLogService;
import com.iware.bridge.online.vo.SensorLogFilter;
import com.iware.bridge.online.vo.SensorLogVO;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.common.annotations.CheckRepeat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author WJP
 * @date 2021-7-28
 */

@RestController
@Api(value = "维护日志/维护记录", tags="维护日志/维护记录",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/online",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SensorLogApi {

    @Autowired
    private SensorLogService sensorLogService;

    @PostMapping(value = "/log/list")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取维护日志列表",value="获取维护日志列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "filter", value = "条件", required = true, paramType = "body", dataType = "SensorLogFilter")
    })
    public PageInfo<SensorLogVO> listSensorLog(@RequestParam(value = "pageNum") Integer pageNum,
                                               @RequestParam(value = "pageSize") Integer pageSize,
                                               @RequestBody SensorLogFilter filter) {
        return sensorLogService.listSensorLog(pageNum, pageSize, filter);
    }

    @PostMapping(value = "/log")
    @CheckRepeat
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(notes="新增一条维护日志",value="新增一条维护日志",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sensorLog", value = "维护日志", required = true, paramType = "body", dataType = "SensorLog")
    })
    public void addSensorLog(@RequestBody SensorLog sensorLog) {

        sensorLogService.addSensorLog(sensorLog);
    }

    @PutMapping(value = "/log/{sensorLogId}")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(notes="修改一条维护日志",value="修改一条维护日志",httpMethod="PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sensorLogId", value = "维护日志id", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "sensorLog", value = "维护日志", required = true, paramType = "body", dataType = "SensorLog")
    })
    public void updSensorLog(@PathVariable("sensorLogId") Integer sensorLogId,
                                @RequestBody SensorLog sensorLog) {
        sensorLog.setId(sensorLogId);
        sensorLogService.updSensorLog(sensorLog);
    }

    @DeleteMapping(value = "/log/{logId}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    @ApiOperation(notes="删除一条维护日志",value="删除一条维护日志",httpMethod="DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logId", value = "维护日志id", required = true, paramType = "path", dataType = "integer")
    })
    public void delSensorLog(@PathVariable("logId") Integer logId) {
        sensorLogService.delSensorLog(logId);
    }

    @PostMapping(value = "/record/{meansPointId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取维护记录列表",value="获取维护记录列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "filter", value = "条件", required = true, paramType = "body", dataType = "InfoFilter")
    })
    public PageInfo<SensorRecord> listSensorRecord(@PathVariable("meansPointId") Integer meansPointId,
                                                   @RequestParam(value = "pageNum") Integer pageNum,
                                                   @RequestParam(value = "pageSize") Integer pageSize,
                                                   @RequestBody InfoFilter filter) {
        return sensorLogService.listSensorRecord(pageNum, pageSize, meansPointId, filter);
    }

    @PostMapping(value = "/record")
    @CheckRepeat
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(notes="新增一条维护记录",value="新增一条维护记录",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sensorRecord", value = "维护记录", required = true, paramType = "body", dataType = "SensorRecord")
    })
    public void addSensorRecord(@RequestBody SensorRecord sensorRecord) {

        sensorLogService.addSensorRecord(sensorRecord);
    }

    @PutMapping(value = "/record/{recordId}")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(notes="修改一条维护记录",value="修改一条维护记录",httpMethod="PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recordId", value = "维护记录id", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "sensorLog", value = "维护日志", required = true, paramType = "body", dataType = "SensorRecord")
    })
    public void updSensorRecord(@PathVariable("recordId") Integer recordId,
                             @RequestBody SensorRecord sensorRecord) {
        sensorRecord.setId(recordId);
        sensorLogService.updSensorRecord(sensorRecord);
    }

    @DeleteMapping(value = "/record/{recordId}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    @ApiOperation(notes="删除一条维护记录",value="删除一条维护记录",httpMethod="DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recordId", value = "维护记录id", required = true, paramType = "path", dataType = "integer")
    })
    public void delSensorRecord(@PathVariable("recordId") Integer recordId) {
        sensorLogService.delSensorRecord(recordId);
    }
}
