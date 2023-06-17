package com.iware.bridge.inspection.api;

import com.iware.bridge.inspection.serivce.AnalyseService;
import com.iware.bridge.inspection.vo.DiseaseRatioVo;
import com.iware.bridge.inspection.vo.DiseaseRepairVo;
import com.iware.bridge.inspection.vo.DiseaseTypeVo;
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
@Api(value = "巡查效果分析", tags="巡查效果分析",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/inspection/analyse",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class InspectionAnalyseApi {

    @Autowired
    private AnalyseService analyseService;

    @GetMapping(value = "/getDiseaseType/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="病害类型统计",value="病害类型统计",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer")
    })
    public List<DiseaseTypeVo> getDiseaseType(@PathVariable("structureId") Integer structureId) {
        return analyseService.getDiseaseType(structureId);
    }

    @GetMapping(value = "/compDiseaseRank/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="构件病害数排行榜",value="构件病害数排行榜",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer")
    })
    public List<DiseaseRatioVo> compDiseaseRank(@PathVariable("structureId") Integer structureId) {
        return analyseService.compDiseaseRank(structureId);
    }

    @GetMapping(value = "/diseaseMonthFrequency/{structureId}/{month}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="构件病害月频率",value="构件病害月频率",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "month", value = "月份", required = true, paramType = "path", dataType = "integer")
    })
    public List<DiseaseRatioVo> diseaseMonthFrequency(@PathVariable("structureId") Integer structureId,
                                                @PathVariable("month") Integer month) {
        return analyseService.diseaseMonthFrequency(structureId, month);
    }

    @GetMapping(value = "/diseaseRepair/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="构件病害报修情况",value="构件病害报修情况",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer"),
    })
    public List<DiseaseRepairVo> diseaseRepair(@PathVariable("structureId") Integer structureId) {
        return analyseService.diseaseRepair(structureId);
    }

    @GetMapping(value = "/diseaseYearRank/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="近五年构件病害数对比",value="近五年构件病害数对比",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer"),
    })
    public Map<String,List<DiseaseRatioVo>> diseaseYearRank(@PathVariable("structureId") Integer structureId) {
        return analyseService.diseaseYearRank(structureId);
    }
}
