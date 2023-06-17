package com.iware.bridge.online.api;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.model.entity.online.AxleTypeModelRel;
import com.iware.bridge.online.dto.SensorHistoryDto;
import com.iware.bridge.online.service.SensorDataAnalysisService;
import com.iware.bridge.online.service.SensorDataService;
import com.iware.bridge.online.vo.*;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.common.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author WJP
 * @date 2021-7-27
 */

@RestController
@Api(value = "传感器数据接口", tags = "传感器数据接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/online/sensorData", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SensorDataApi {

    @Autowired
    private SensorDataService dataService;

    @Autowired
    private SensorDataAnalysisService sensorDataAnalysisService;

    @GetMapping(value = "/list/axleType")
    @ApiOperation(notes = "车型关联数据", value = "车型关联数据", httpMethod = "GET")
    @ApiImplicitParams({})
    public List<AxleTypeModelRel> listAxleType() {
        return dataService.listAxleType();
    }


    @PostMapping(value = "/getLastTen")
    @ApiOperation(notes = "获取传感器最新10条数据", value = "获取传感器最新10条数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sensorVOList", value = "选择的传感器数据（称重）", paramType = "body", dataType = "SensorVO", required = true)
    })
    public Map<String, List<SensorHistoryDto>> getLastTen(@RequestBody List<SensorVO> sensorVOList) {
        return dataService.getLastTen(sensorVOList);
    }

    @PostMapping(value = "/history")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "传感器历史数据", value = "传感器历史数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "选择的传感器数据", paramType = "body", dataType = "SensorFilter", required = true)
    })
    public List<SensorHistoryVO> getHistory(@RequestBody SensorDataFilter filter) {
        return dataService.getHistory(filter);
    }

    @PostMapping(value = "/boxAnalyse")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "箱型图分析", value = "箱型图分析", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "选择的传感器数据", paramType = "body", dataType = "SensorFilter", required = true)
    })
    public List<BoxData> boxAnalyse(@RequestBody SensorDataFilter filter) {
        return sensorDataAnalysisService.boxAnalyse(filter);
    }

    @PostMapping(value = "/list")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "数据列表", value = "数据列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", example = "1", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", example = "10", defaultValue = "10"),
            @ApiImplicitParam(name = "filter", value = "选择的传感器数据", paramType = "body", dataType = "SensorFilter", required = true)
    })
    public PageInfo<SensorHistoryDto> listData(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize,
                                               @RequestBody SensorDataFilter filter) {
        return dataService.listData(pageNum, pageSize, filter);
    }

    @PostMapping(value = "/batch/export")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "传感器数据批量导出", value = "传感器数据批量导出", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "选择的传感器数据", paramType = "body", dataType = "SensorFilter", required = true)
    })
    public void batchExport(@RequestBody SensorDataFilter filter,
                            HttpServletResponse response) {
        String path = dataService.createExcel(filter);
        FileUtil.pushToWeb(response, path, "APPLICATION/OCTET-STREAM");
        FileUtil.delFileData(path);
    }

    @PostMapping(value = "/frequency")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "查询频率/索力", value = "查询频率/索力", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sensorCalculateFilter", value = "查询条件", paramType = "body", dataType = "SensorCalculateFilter", required = true)
    })
    public List<SensorCalculateVO> frequency(@RequestBody SensorCalculateFilter sensorCalculateFilter) {
        return sensorDataAnalysisService.frequency(sensorCalculateFilter);
    }

    @PostMapping(value = "/frequencyPoint")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "查询某个频率时间点的频谱图", value = "查询某个频率时间点的频谱图", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sensorSpectrumFilter", value = "查询条件", paramType = "body", dataType = "SensorSpectrumFilter", required = true)
    })
    public List<SensorSpectrumVO> frequencyPoint(@RequestBody SensorSpectrumFilter sensorSpectrumFilter) {
        return sensorDataAnalysisService.getSpectrum(sensorSpectrumFilter);
    }

    @PostMapping(value = "/correlationAnalysis")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "相关性分析", value = "相关性分析", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sensorDataFilter", value = "查询条件", paramType = "body", dataType = "SensorDataFilter", required = true)
    })
    public CorrelationAnalysisVO analysis(@RequestBody SensorDataFilter sensorDataFilter) {
        return sensorDataAnalysisService.correlationAnalysis(sensorDataFilter);
    }

}
