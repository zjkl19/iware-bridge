package com.iware.bridge.info.api;

import com.iware.bridge.info.service.CommonService;
import com.iware.bridge.info.vo.AppointTime;
import com.iware.bridge.info.vo.AreaVO;
import com.iware.bridge.info.vo.StructureFilter;
import com.iware.bridge.info.vo.StructureVO;
import com.iware.bridge.model.entity.evaluation.BridgeType;
import com.iware.bridge.model.entity.global.Project;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.user.Unit;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-26
 */

@RestController
@Api(value = "通用查询", tags="通用查询",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/common", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CommonApi {

    @Autowired
    private CommonService commonService;

    @GetMapping(value = {"/area/list/{parentId}", "/area/list"})
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="查询省市区",value="查询省市区",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父级id", dataType = "int", paramType = "path"),
    })
    public List<AreaVO> getAreaList(@PathVariable(value = "parentId", required = false) Integer parentId) {
        return commonService.getAreaList(parentId);
    }

    @GetMapping(value = {"/area/listByUser", "/area/listByUser/{powerId}"})
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="查询用户结构物省市区",value="查询用户结构物省市区",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "powerId", value = "模块id", dataType = "int", paramType = "path"),
    })
    public List<AreaVO> getAreaListByUser(@PathVariable(value = "powerId", required = false) Integer powerId) {
        return commonService.listAreaByModel(powerId);
    }

    @GetMapping(value = {"/unit/listByRoleAndPower/{roleId}/{powerId}", "/unit/listByRoleAndPower/{roleId}"})
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取有计划的业主/承接单位主用户",value="获取有计划的业主/承接单位主用户",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "powerId", value = "模块id", dataType = "int", paramType = "path"),
    })
    public List<Unit> listUnitByRoleAndPower(@PathVariable(value = "roleId") Integer roleId,
                                             @PathVariable(value = "powerId", required = false) Integer powerId) {
        return commonService.listUnitByRoleAndPower(roleId, powerId);
    }

    @PostMapping(value = "/structure/listByUnit")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取单位桥梁",value="获取用户单位",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "结构物查询过滤条件", dataType = "StructureFilter", paramType = "body"),
    })
    public List<Structure> listUnitStructure(@RequestBody StructureFilter filter) {
        return commonService.listUnitStructure(filter);
    }

    @PostMapping(value = "/structure/listByModel")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取用户有传感器/巡查/维养/检测计划的桥隧",value="获取用户有传感器/巡查/维养/检测计划的桥隧",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "结构物查询过滤条件", dataType = "StructureFilter", paramType = "body"),
    })
    public List<Structure> listUnitStructureByModel(@RequestBody StructureFilter filter) {
        return commonService.listUnitStructureByModel(filter);
    }

    @GetMapping(value = {"/project/listByUser/{powerId}", "/project/listByUser"})
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取用户项目",value="获取用户项目",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "powerId", value = "权限id", dataType = "integer", paramType = "path"),
    })
    public List<Project> listUserProject(@PathVariable(value = "powerId", required = false) Integer powerId) {
        return commonService.listUserProject(powerId, null);
    }

    @GetMapping(value = {"/project/listByModel/{powerId}"})
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取用户有传感器/巡查/维养/检测计划的项目",value="获取用户有传感器/巡查/维养/检测计划的项目",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "powerId", value = "权限id", dataType = "integer", paramType = "path"),
    })
    public List<Project> listUserProjectByModel(@PathVariable(value = "powerId", required = false) Integer powerId) {
        return commonService.listUserProjectByModel(powerId);
    }

    @GetMapping(value = {"/project/listByOnTime/{powerId}"})
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取用户当前有指派的项目",value="获取用户当前有指派的项目",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "powerId", value = "权限id", dataType = "integer", paramType = "path"),
    })
    public List<Project> listByOnTime(@PathVariable("powerId") Integer powerId) {
        return commonService.listUserProject(powerId, 1);
    }

    @GetMapping("/getStructureById/{structureId}/{type}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="根据id获取结构物",value="根据id获取结构物",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "structureId", value = "结构物id", dataType = "integer", paramType = "path"),
            @ApiImplicitParam(name = "type", value = "0：不需要照片、视频 1：需要照片视频 2：需要照片和详情", dataType = "integer", paramType = "path")
    })
    public StructureVO getStructureById(@PathVariable("structureId")Integer structureId,
                                        @PathVariable("type")Integer type) {
        return commonService.getStructureById(structureId, type);
    }

    @GetMapping("/getUnitAppointTime/{projectId}/{powerId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取用户项目指派时间",value="获取用户项目指派时间",httpMethod="GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目id", dataType = "integer", paramType = "path"),
            @ApiImplicitParam(name = "powerId", value = "权限id", dataType = "integer", paramType = "path"),
    })
    public List<AppointTime> getUnitAppointTime(@PathVariable("projectId")Integer projectId,
                                                @PathVariable("powerId")Integer powerId) {
        return commonService.getUnitAppointTime(projectId, powerId);
    }

    @GetMapping("/getBridgeType")
    @ApiOperation(notes="获取检测评估桥梁类型",value="获取检测评估桥梁类型",httpMethod="GET")
    @ApiImplicitParams({})
    public List<BridgeType> getBridgeType() {
        return commonService.getBridgeType();
    }
}
