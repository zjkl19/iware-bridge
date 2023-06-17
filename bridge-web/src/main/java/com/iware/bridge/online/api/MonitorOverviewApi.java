package com.iware.bridge.online.api;

import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.online.dto.MonitoringSituationDto;
import com.iware.bridge.online.service.MonitorOverviewService;
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
@Api(value = "监测总览", tags = "监测总览", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/online/overview", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MonitorOverviewApi {

    @Autowired
    private MonitorOverviewService overviewService;

    @GetMapping("/getCount")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "查询监测桥隧数/传感器数/摄像头/当日预警数/昨日预警数", httpMethod = "GET")
    @ApiImplicitParams({})
    public Map<String, Integer> getCount() {
        return overviewService.getCount();
    }

    @GetMapping("/getSensorTypeList")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "传感器统计", value = "传感器统计", httpMethod = "GET")
    @ApiImplicitParams({})
    public List<EchartMap> getSensorTypeList() {
        return overviewService.getSensorTypeList(null);
    }

    @GetMapping("/scoreRank/{id}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "监测评分排行", value = "监测评分排行", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "项目id", dataType = "int")
    })
    public List<MonitoringSituationDto> getScoreRank(@PathVariable(value = "id") Integer id) {
        return overviewService.getScoreRank(id);
    }
}
