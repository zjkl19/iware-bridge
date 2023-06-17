package com.iware.bridge.online.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.online.service.SensorService;
import com.iware.bridge.online.vo.MeansPointVO;
import com.iware.bridge.online.vo.SelectVO;
import com.iware.bridge.online.vo.SensorFilter;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author WJP
 * @date 2021-7-27
 */

@RestController
@Api(value = "传感器管理", tags = "传感器管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/online/sensor", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SensorApi {

    @Autowired
    private SensorService sensorService;

    @PostMapping(value = "/list")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "获取传感器列表", value = "获取传感器列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "filter", value = "条件", required = true, paramType = "body", dataType = "SensorFilter")
    })
    public PageInfo<MeansPointVO> listSensor(@RequestParam(value = "pageNum") Integer pageNum,
                                             @RequestParam(value = "pageSize") Integer pageSize,
                                             @RequestBody SensorFilter filter) {
        return sensorService.listSensor(pageNum,pageSize,filter);
    }

    @PostMapping(value = "")
    @CheckRepeat
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(notes = "新增一条测点信息", value = "新增一条测点信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "附件", required = true, paramType = "form", dataType = "__file"),
            @ApiImplicitParam(name = "params", value = "测点信息", required = false, paramType = "form", dataType = "String")
    })
    public void addSensor(@RequestParam("params") String params,
                          @RequestParam(value = "file", required = false) MultipartFile file) {
        MeansPointVO meansPointVO = JSONObject.parseObject(params, MeansPointVO.class);
        sensorService.addSensor(meansPointVO, file);
    }

    @PutMapping(value = "/{id}")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(notes = "修改一条测点信息", value = "修改一条测点信息", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "测点id", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "params", value = "测点信息", required = true, paramType = "form", dataType = "params"),
            @ApiImplicitParam(name = "file", value = "附件", required = false, paramType = "form", dataType = "__file"),
    })
    public void updSensor(@PathVariable("id") Integer id,
                          @RequestParam("params") String params,
                          @RequestParam(value = "file", required = false) MultipartFile file) {
        MeansPointVO meansPointVO = JSONObject.parseObject(params, MeansPointVO.class);
        meansPointVO.setId(id);
        sensorService.updSensor(meansPointVO, file);
    }

    @DeleteMapping(value = "/{id}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    @ApiOperation(notes = "删除一条测点信息", value = "删除一条传感器", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "传感器id", required = true, paramType = "path", dataType = "integer")
    })
    public void delSensor(@PathVariable("id") Integer id) {
        sensorService.deleteById(id);
    }

    @GetMapping(value = "/listByType/{structureId}/{type}/{queryAll}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "分类获取传感器列表", value = "分类获取传感器列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "type", value = "类型 0:按类型 1:按位置 2:全部", required = true, paramType = "path", dataType = "integer"),
            @ApiImplicitParam(name = "queryAll", value = "0:所有 1:基康非称重传感器", required = true, paramType = "path", dataType = "integer")
    })
    public List<Object> listByType(@PathVariable("structureId") Integer structureId, @PathVariable("type") Integer type,
                                   @PathVariable("queryAll") Integer queryAll) {
        return sensorService.listByType(type, structureId, queryAll);
    }

    @GetMapping(value = "/select/list")
    @ApiOperation(notes = "获取传感器类型/原理/产商/构件", value = "获取传感器类型/原理/产商/构件", httpMethod = "GET")
    @ApiImplicitParams({})
    public SelectVO getSelectList() {
        return sensorService.getSelectList();
    }

}
