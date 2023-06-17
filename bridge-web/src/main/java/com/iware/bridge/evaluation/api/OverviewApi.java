package com.iware.bridge.evaluation.api;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.evaluation.service.EvaluationOverviewService;
import com.iware.bridge.evaluation.vo.BridgeLevelRankFilter;
import com.iware.bridge.evaluation.vo.BridgeLevelRankVO;
import com.iware.bridge.evaluation.vo.BridgeTypeDiseaseFilter;
import com.iware.bridge.evaluation.vo.TechnologyStatusVO;
import com.iware.bridge.model.entity.evaluation.BridgeType;
import com.iware.bridge.model.entity.evaluation.Component;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WJP
 * @date 2021-8-09
 */

@RestController
@Api(value = "检测总览", tags = "检测总览", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/evaluation/overview", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OverviewApi {

    @Autowired
    private EvaluationOverviewService overviewService;

    @GetMapping("/getCount")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "查询检测项目数/检测桥隧数", httpMethod = "GET")
    @ApiImplicitParams({})
    public Map<String, Integer> getInspectionCount() {
        return overviewService.getCount();
    }

    @GetMapping(value = "/listTechnologyStatus")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "列出桥隧技术状况统计", notes = "列出城市和公路技术状况统计", httpMethod = "GET")
    public Map<String, List<TechnologyStatusVO>> listTechnologyStatus() {
        return overviewService.listTechnologyStatus();
    }

    @PostMapping(value = "/getDiseaseCount")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "获取构建病害类型统计", notes = "获取构建病害类型统计", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "桥梁类型id", paramType = "body", dataType = "BridgeTypeDiseaseFilter")
    })
    public List<HashMap<String, Object>> getDiseaseCount(@RequestBody BridgeTypeDiseaseFilter filter) {
        return overviewService.getDiseaseCount(filter);
    }


    @PostMapping(value = "/listBridgeRank")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "获取桥隧评分排名", notes = "获取桥隧评分排名", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "页码、每页数量、等级、年份对象", paramType = "body", dataType = "BridgeLevelRankFilter"),
    })
    public PageInfo<BridgeLevelRankVO> listBridgeRank(@RequestBody BridgeLevelRankFilter filter) {
        return overviewService.listBridgeRank(filter);
    }

    @GetMapping(value = "/listRankYear")
    @ApiOperation(value = "列出排名年份", notes = "列出排名年份", httpMethod = "GET")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public List<String> listRankYear() {
        return overviewService.listRankYear();
    }

    @GetMapping(value = "/listBridgeType")
    @ApiOperation(value = "列出桥型类型", notes = "列出桥型类型用于病害数量统计", httpMethod = "GET")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    public List<BridgeType> listBridgeType() {
        return overviewService.listBridgeType();
    }


    @GetMapping("/getComponent/{id}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "根据桥型获取构件类型", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "桥型id", required = true, paramType = "path", dataType = "integer"),
    })
    public List<Component> getComponent(@PathVariable("id") Integer id) {
        return overviewService.getComponent(id);
    }

}
