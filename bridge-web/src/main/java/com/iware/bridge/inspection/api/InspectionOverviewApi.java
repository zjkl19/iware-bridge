package com.iware.bridge.inspection.api;

import com.iware.bridge.inspection.serivce.OverviewService;
import com.iware.bridge.inspection.vo.DiseaseRatioVo;
import com.iware.bridge.inspection.vo.InspectionDiseaseDateVo;
import com.iware.bridge.inspection.vo.StructureGradeRatioVo;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LBX
 * @date 2021-7-29
 */

@RestController
@Api(value = "巡查任务总览", tags="巡查任务总览",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/inspection/overview",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class InspectionOverviewApi {

    @Autowired
    private OverviewService overviewService;

    @GetMapping("/getCount")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value="查询总巡查次数/当月巡查次数/巡查病害数/巡查桥隧数",httpMethod="GET")
    @ApiImplicitParams({})
    public Map<String, Integer> getInspectionCount() {
        return overviewService.getInspectionCount();
    }

    @GetMapping("/workRecord/{year}/{month}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="日历工作记录表",value="日历工作记录表",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "month", value = "月份", required = true, paramType = "path", dataType = "integer")
    })
    public List<Date> getWorkRecord(@PathVariable("year")Integer year, @PathVariable("month")Integer month) {
        return overviewService.getWorkRecord(1, year, month);
    }

    @GetMapping("/diseaseRatio")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="主要病害占比",value="主要病害占比",httpMethod="GET")
    @ApiImplicitParams({})
    public List<DiseaseRatioVo> getDiseaseRatio() {
        return overviewService.getDiseaseRatio();
    }

    @GetMapping("/maintainGradeRatio")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="养护等级占比",value="养护等级占比",httpMethod="GET")
    @ApiImplicitParams({})
    public List<StructureGradeRatioVo> getMaintainGradeRatio() {
        return overviewService.getMaintainGradeRatio();
    }

    @GetMapping("/diseaseTrend/{year}/{month}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="巡查病害数变化",value="巡查病害数变化",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "month", value = "月份", required = true, paramType = "path", dataType = "integer")
    })
    public List<InspectionDiseaseDateVo> getDiseaseTrend(@PathVariable("year")Integer year,
                                                         @PathVariable("month")Integer month) {
        return overviewService.getDiseaseTrend(year, month);
    }

}
