package com.iware.bridge.online.api;

import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.online.MeansPoint;
import com.iware.bridge.online.dto.SensorWeightDto;
import com.iware.bridge.online.service.SensorWeightDataService;
import com.iware.bridge.online.vo.SensorWeightFilter;
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
 * @author: wjp
 * @date: 2021年8月19日14:13:14
 * @since 1.0
 */
@RestController
@Api(value = "在线监测（统计数据）", tags = "在线监测（统计数据）", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/online/weightSensor", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SensorWeightDataApi {

    @Autowired
    private SensorWeightDataService sensorWeightDataService;

    @PostMapping(value = "/trafficFlow")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "获取车流量时程", value = "获取车流量时程", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "条件", paramType = "body", dataType = "SensorWeightFilter", required = true)
    })
    public Map<String, List<SensorWeightDto>> getTrafficFlow(@RequestBody SensorWeightFilter filter) {

        return sensorWeightDataService.getTrafficFlow(filter);
    }

    @PostMapping(value = "/distribute")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "获取车型、车速分布", value = "获取车型、车速分布", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "选择的传感器数据", paramType = "body", dataType = "SensorWeightFilter", required = true)
    })
    public Map<String, List<SensorWeightDto>> getDistribute(@RequestBody SensorWeightFilter filter) {

        return sensorWeightDataService.getCarModelSpeed(filter);
    }

    @PostMapping(value = "/maxAlexWeight")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "获取最大车重", value = "获取最大车重", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "选择的传感器数据", paramType = "body", dataType = "SensorWeightFilter", required = true)
    })
    public Map<String, List<SensorWeightDto>> maxAlexWeight(@RequestBody SensorWeightFilter filter) {

        return sensorWeightDataService.maxAlexWeight(filter);
    }

    @PostMapping(value = "/carWeight")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "获取车重分布", value = "获取车重分布", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "选择的传感器数据", paramType = "body", dataType = "SensorWeightFilter", required = true)
    })
    public Map<String, List<SensorWeightDto>> carWeight(@RequestBody SensorWeightFilter filter) {

        return sensorWeightDataService.carWeight(filter);
    }

    @PostMapping(value = "/countCarWeight")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "重车统计", value = "重车统计", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "选择的传感器数据", paramType = "body", dataType = "SensorWeightFilter", required = true)
    })
    public Map<String, List<SensorWeightDto>> countCarWeight(@RequestBody SensorWeightFilter filter) {

        return sensorWeightDataService.countCarWeight(filter);
    }

    @PostMapping(value = "/transfiniteCar")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "超重车统计", value = "超重车统计", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "选择的传感器数据", paramType = "body", dataType = "SensorWeightFilter", required = true)
    })
    public Map<String, Integer> transfiniteCar(@RequestBody SensorWeightFilter filter) {

        return sensorWeightDataService.transfiniteCar(filter);
    }

    @GetMapping(value = "/sensorWeightCarNo/{structureId}/{type}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "查询称重传感器结构物下的车道", value = "查询称重传感器结构物下的车道", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", paramType = "path", dataType = "integer", required = true),
            @ApiImplicitParam(name = "type", value = "型id（1：称重，2：加速度，3：索力，4：相关性，5：箱线）", paramType = "path", dataType = "integer", required = true)
    })
    public List<MeansPoint> sensorWeightCarNo(@PathVariable("structureId") Integer structureId,@PathVariable("type") Integer type) {
        return sensorWeightDataService.getSensorWeightCarNo(structureId,type);
    }

    @GetMapping(value = "/sensorStructure")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "查询有称重传感器的结构物", value = "查询有称重传感器的结构物", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型id（1：称重，2：加速度，3：索力，4：相关性，5：箱线）", paramType = "path", dataType = "integer", required = true),
    })
    public List<Structure> sensorStructure(Integer type) {
        return sensorWeightDataService.sensorStructure(type);
    }

}
