package com.iware.bridge.evaluation.api;

import com.iware.bridge.evaluation.service.StructureEvaluateService;
import com.iware.bridge.evaluation.vo.BasicVO;
import com.iware.bridge.evaluation.vo.OverviewVO;
import com.iware.bridge.evaluation.vo.ScoreVO;
import com.iware.bridge.info.vo.EchartMap;
import com.iware.bridge.model.entity.evaluation.Component;
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
 * @date 2021-8-12
 */

@RestController
@Api(value = "结构物检测", tags="结构物检测",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/evaluation/monitor",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StructureEvaluateApi {

    @Autowired
    private StructureEvaluateService structureEvaluateService;

    @GetMapping("/getNewestScore/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value="获取桥梁最近一次评分情况", notes="获取桥梁最近一次评分情况", httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "桥梁id", dataType = "int",paramType = "path"),
    })
    public Map<String, String> getNewestScore(@PathVariable("structureId") Integer structureId) {

        return structureEvaluateService.getNewestScore(structureId);
    }

    @GetMapping("/getScoreTrend/{structureId}/{partType}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value="历年评分变化", notes="历年评分变化", httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "桥梁id", dataType = "int",paramType = "path"),
            @ApiImplicitParam(name = "partType", value = "部位id 2:全桥 3:桥面系 4：上部结构 5：下部结构", dataType = "int",paramType = "path"),
    })
    public List<ScoreVO> getScoreTrend(@PathVariable("structureId") Integer structureId,
                                       @PathVariable("partType") Integer partType) {
        return structureEvaluateService.getScoreTrend(structureId, partType);
    }

    @GetMapping("/getDiseaseByComponent/{structureId}/{componentId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value="构件病害数排行", notes="构件病害数排行", httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "桥梁id", dataType = "int",paramType = "path"),
            @ApiImplicitParam(name = "componentId", value = "构件id", dataType = "int",paramType = "path")
    })
    public List<EchartMap> getDiseaseByComponent(@PathVariable("structureId") Integer structureId,
                                                 @PathVariable("componentId") Integer componentId) {
        return structureEvaluateService.getDiseaseByComponent(structureId, componentId);
    }

    @GetMapping("/getEvaluationInfo/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value="检测概况", notes="检测概况", httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "桥梁id", dataType = "int",paramType = "path")
    })
    public List<OverviewVO> getEvaluationInfo(@PathVariable("structureId") Integer structureId){
        return structureEvaluateService.getEvaluationInfo(structureId);
    }


    @GetMapping("/getBasic/{structureId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value="查询基本信息和图片", notes="查询基本信息和图片", httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "桥梁id", dataType = "int",paramType = "path"),

    })
    public BasicVO getBasic(@PathVariable("structureId") Integer structureId) {
        return structureEvaluateService.getBasic(structureId);
    }

    @GetMapping("/getComponent")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value="获取构件类型", notes="获取构件类型", httpMethod="GET")
    @ApiImplicitParams({})
    public List<Component> getComponent() {
        return structureEvaluateService.getComponent();
    }

}
