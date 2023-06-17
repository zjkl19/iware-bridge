package com.iware.bridge.info.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.service.ElectronicService;
import com.iware.bridge.info.vo.ElectronicArchivesVO;
import com.iware.bridge.model.entity.global.ElectronicArchives;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.common.annotations.CheckRepeat;
import com.iware.common.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ZRB
 * @date 2021-7-29
 */

@RestController
@Api(value = "电子档案资料", tags="电子档案资料",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/electronic",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ElectronicApi {

    @Autowired
    private ElectronicService electronicService;

    @PostMapping("/list")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "获取电子档案列表", notes = "获取电子档案列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query",  required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "electronicArchives", value = "电子档案",dataType = "ElectronicArchives", paramType = "body", required = true)
    })
    public PageInfo<ElectronicArchivesVO> listByPage(@RequestParam(value = "pageNum") Integer pageNum,
                                                     @RequestParam(value = "pageSize") Integer pageSize,
                                                     @RequestBody ElectronicArchives electronicArchives) {
        return electronicService.listByPage(pageNum, pageSize, electronicArchives);
    }

    @PostMapping("")
    @CheckRepeat
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(value = "添加电子档案", notes = "添加电子档案", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "附件", required = true, paramType = "form", dataType = "_file"),
            @ApiImplicitParam(name = "params", value = "附件实体类", required = true, paramType = "form", dataType = "String")
    })
    public void addElectronic(@RequestParam("params") String params, @RequestParam(value = "file") MultipartFile file) {
        ElectronicArchives electronicArchives = JSONObject.parseObject(params, ElectronicArchives.class);
        electronicService.addElectronic(file, electronicArchives);
    }

    @PutMapping(value = "/{electronicId}")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(value = "修改电子档案", notes = "修改电子档案", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "electronicId", value = "电子档案id",dataType = "Integer", paramType = "path", required = true),
            @ApiImplicitParam(name = "file", value = "附件", required = false, paramType = "form", dataType = "__file"),
            @ApiImplicitParam(name = "params", value = "档案实体类", required = true, paramType = "form", dataType = "String")
    })
    public void updElectronic(@PathVariable("electronicId") Integer electronicId,
                             @RequestParam("params") String params,
                             @RequestParam(value = "file", required = false) MultipartFile file){

        ElectronicArchives electronicArchives =  JSONObject.parseObject(params, ElectronicArchives.class);
        electronicArchives.setId(electronicId);
        electronicService.updElectronic(file, electronicArchives);
    }

    @DeleteMapping(value = "/{electronicId}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    @ApiOperation(value = "删除电子档案", notes = "删除电子档案", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reportId", value = "电子档案id",dataType = "Integer", paramType = "path", required = true)
    })
    public void delElectronic(@PathVariable("electronicId") Integer electronicId){

        electronicService.delElectronic(electronicId);
    }

    @GetMapping("/download/{id}")
    @ApiOperation(value = "下载电子档案", notes = "下载电子档案", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "电子档案id", dataType = "Integer", paramType = "path", required = true)
    })
    public void download(@PathVariable("id") Integer id, HttpServletResponse response) {
        ElectronicArchives electronicArchives = electronicService.findById(id);
        FileUtil.pushToWeb(response, electronicArchives.getPath(), "APPLICATION/OCTET-STREAM");
    }
}
