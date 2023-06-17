package com.iware.bridge.inspection.api;

import com.iware.bridge.inspection.serivce.OverviewService;
import com.iware.bridge.inspection.vo.MaintainAccountMonthVo;
import com.iware.bridge.inspection.vo.MaintainTypeVo;
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
@Api(value = "维养任务总览", tags="维养任务总览",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/maintain/overview",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MaintainOverviewApi {
    @Autowired
    private OverviewService overviewService;

    @GetMapping("/getCount")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value="查询维养桥隧数/待处理病害数/已处理病害数/年度维养总金额",httpMethod="GET")
    @ApiImplicitParams({})
    public Map<String, Double> getMaintainCount() {
        return overviewService.getMaintainCount();
    }

    @GetMapping("/workRecord/{year}/{month}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="日历工作记录表",value="日历工作记录表",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "month", value = "月份", required = true, paramType = "path", dataType = "integer")
    })
    public List<Date> getWorkRecord(@PathVariable("year")Integer year,
                                    @PathVariable("month")Integer month) {
        return overviewService.getWorkRecord(2, year, month);
    }

    @GetMapping("/diseaseCount")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="维修病害数",value="维修病害数",httpMethod="GET")
    @ApiImplicitParams({})
    public Map<String, Integer> getDiseaseRatio() {
        return overviewService.getDiseaseCount();
    }

    @GetMapping("/typeRatio")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="维修类型分布",value="维修类型分布",httpMethod="GET")
    @ApiImplicitParams({})
    public List<MaintainTypeVo> getTypeRatio() {
        return overviewService.getTypeRatio();
    }

    @GetMapping("/accountTrend/{year}/{month}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="维修费用趋势",value="维修费用趋势",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "month", value = "月份", required = true, paramType = "path", dataType = "integer")
    })
    public List<MaintainAccountMonthVo> getDiseaseTrend(@PathVariable("year")Integer year,
                                                        @PathVariable("month")Integer month) {
        return overviewService.getAccountTrend(year, month);
    }
}
