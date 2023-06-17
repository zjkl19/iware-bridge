package com.iware.bridge.info.api;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.service.UnitService;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.info.vo.UnitVO;
import com.iware.bridge.model.entity.user.Unit;
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

import java.util.List;

/**
 * @author ZRB
 * @date 2021-10-19
 */

@RestController
@Api(value = "单位接口", tags = "单位接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/unit",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UnitApi {

    @Autowired
    private UnitService unitService;

    @PostMapping(value = "")
    @CheckRepeat
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(value = "新增单位", notes = "新增单位接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "unitVO", value = "新增的单位信息", dataType = "UnitVO", required = true, paramType = "body")
    })
    public void addUnit(@RequestBody UnitVO unitVO) {
        unitService.addUnit(unitVO);
    }

    @PutMapping(value = "/{id}")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(value = "修改单位", notes = "修改单位接口", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "unitVO", value = "修改的单位信息", dataType = "UnitVO", required = true, paramType = "body")
    })
    public void editUnit(@PathVariable("id") Integer id, @RequestBody UnitVO unitVO) {
        unitVO.setId(id);
        unitService.editUnit(unitVO);
    }

    @DeleteMapping(value = "/{unitId}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    @ApiOperation(value = "删除单位", notes = "删除单位", httpMethod = "DELETE")
    @ApiImplicitParam(name = "unitId", value = "单位Id", dataType = "Integer", required = true, paramType = "path")
    public void delUnit(@PathVariable("unitId") Integer unitId) {
        unitService.delUnit(unitId);
    }

    @PostMapping(value = "/list")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "查询单位", notes = "查询单位", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", example = "1", defaultValue = "1", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", example = "10", defaultValue = "10", required = false),
            @ApiImplicitParam(name = "infoFilter", value = "过滤条件", required = true, paramType = "body", dataType = "InfoFilter")
    })
    public PageInfo<UnitVO> getUnitList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                        @RequestBody InfoFilter filter) {
        return unitService.getUnitList(pageNum, pageSize, filter);
    }

    @GetMapping(value = "/getUserUnitList")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "创建用户获取单位列表", notes = "创建用户获取单位列表", httpMethod = "GET")
    @ApiImplicitParams({})
    public List<Unit> getUserUnitList() {
        return unitService.getUserUnitList();
    }

    @GetMapping("/getUnitByRole/{roleId}/{parentId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "根据角色/父级单位查询单位", notes = "根据角色/父级单位查询单位", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "权限id", dataType = "Integer", required = true, paramType = "path"),
            @ApiImplicitParam(name = "parentId", value = "父级单位, 0不查询此属性", dataType = "Integer", required = true, paramType = "path")
    })
    public List<Unit> getUnitByRole(@PathVariable("roleId") Integer roleId,
                                    @PathVariable("parentId") Integer parentId) {
        return unitService.getUnitByRole(roleId, parentId);
    }
}
