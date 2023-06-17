package com.iware.bridge.online.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iware.bridge.online.service.DataProcessService;
import com.iware.bridge.online.service.SensorDataService;
import com.iware.bridge.online.service.SensorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Api(value = "数据接入接口", tags = "数据接入接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/onBridgeMonitoring", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataAccessApi {

    @Autowired
    private DataProcessService dataProcessService;

    @PostMapping(value = "/getDeviceData")
    @ApiOperation(notes = "接收东华传感器数据的接口（对外暴露）", value = "接收东华传感器数据的接口（对外暴露）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "数据", required = true, paramType = "body", dataType = "String")
    })
    public void addDeviceData(HttpServletResponse response, @RequestBody String body) throws IOException {

        JSONObject res = JSON.parseObject(body);
        JSONObject resJson = new JSONObject();
        if ("anheng@123".equals(res.getString("token"))) {
            dataProcessService.addSensorData(res.getJSONArray("data"));
            resJson.put("code", "0000");
        } else {
            resJson.put("code", "秘钥有误");
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain");
        response.getWriter().write(resJson.toString());
    }

    @PostMapping(value = "/getWeightData")
    @ApiOperation(notes = "接收称重传感器数据的接口（对外暴露）", value = "接收称重传感器数据的接口（对外暴露）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "数据", required = true, paramType = "body", dataType = "String")
    })
    public void addWeighData(HttpServletResponse response, @RequestBody String body) throws IOException {

        JSONArray res = JSON.parseArray(body);
        JSONObject resJson = new JSONObject();
        dataProcessService.addWeighData(res);

        resJson.put("code", "0000");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain");
        response.getWriter().write(resJson.toString());
    }
}
