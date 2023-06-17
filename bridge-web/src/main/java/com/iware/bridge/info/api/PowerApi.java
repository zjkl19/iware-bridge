package com.iware.bridge.info.api;

import com.iware.bridge.info.service.PowerService;
import com.iware.bridge.info.vo.PowerVO;
import com.iware.bridge.info.vo.RolePowerVO;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.common.base.AbstractBaseController;
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

/**
 * @author ZRB
 * @date 2021-010-20
 */

@RestController
@Api(value = "权限接口", tags = "权限接口管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/power",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PowerApi implements AbstractBaseController {

    @Autowired
    private PowerService powerService;


    @GetMapping(value = "/list")
    @ApiOperation(value = "查找所有权限", notes = "查找所有权限", httpMethod = "GET")
    public List<PowerVO> powerList(){
        return powerService.getPowerList();
    }

    @GetMapping(value = "/{unitId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "查找单位权限", notes = "查找单位权限", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "unitId", value = "单位id", paramType = "path", dataType = "Integer")
    })
    public List<Integer> getUnitPowers(@PathVariable("unitId") Integer unitId){
        return powerService.getUnitPowers(unitId);
    }

    @GetMapping(value = "/getPagePowerIds")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "查找页面的权限id", notes = "查找页面的权限id", httpMethod = "GET")
    @ApiImplicitParams({})
    public List<Integer> getPagePowerIds(){
        return powerService.getPagePowerIds();
    }


    @GetMapping(value = "/getRoleDefaultPower/{type}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "查询用户默认权限", notes = "查询用户默认权限接口", httpMethod = "GET")
    @ApiImplicitParam(name = "type", value = "用作什么功能的展示；1-查询，2-新增/修改", dataType = "Integer", required = true, paramType = "path")
    public List<RolePowerVO> getRoleDefaultPower(@PathVariable("type") Integer type) {
        return powerService.getRoleDefaultPower(type);
    }
}
