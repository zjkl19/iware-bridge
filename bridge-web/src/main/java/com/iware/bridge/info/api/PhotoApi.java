package com.iware.bridge.info.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.service.PhotoService;
import com.iware.bridge.model.entity.global.Photo;
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
 * @date 2021-7-6
 */

@RestController
@Api(value = "图片管理相关接口", tags = "图片管理相关接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/photo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PhotoApi {

    @Autowired
    private PhotoService photoService;

    @GetMapping("/list/{targetId}/{type}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "获取图片列表", notes = "获取图片列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", example = "1", defaultValue = "1", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "int", paramType = "query", example = "10", defaultValue = "10", required = false),
            @ApiImplicitParam(name = "targetId", value = "关联表Id", dataType = "Integer", paramType = "path", required = true),
            @ApiImplicitParam(name = "type", value = "类型：[1:桥梁图片，2：隧道图片, 3:桥梁附件，4：隧道附件，5:日常巡检，6、7、8:维修养护]",
                    dataType = "Integer", paramType = "path", required = true)
    })
    public PageInfo<Photo> listByPage(@PathVariable("targetId") Integer targetId,
                                      @PathVariable("type") Integer type,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return photoService.listByPage(pageNum, pageSize, targetId, type);
    }

    @PostMapping(value = "/upload/{type}")
    @CheckRepeat
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(value = "上传文件 ", notes = "上传文件", httpMethod = "POST", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型：[1:桥梁图片，2：隧道图片, 3:桥梁附件，4：隧道附件，5:日常巡检，6、7、8:维修养护]",
                    dataType = "Integer", paramType = "path", required = true),
            @ApiImplicitParam(name = "files", value = "附件", required = true, paramType = "form", dataType = "__file"),
            @ApiImplicitParam(name = "params", value = "附件实体类", required = true, paramType = "form", dataType = "String")
    })
    public void fileUpload(@PathVariable("type") Integer type, @RequestParam("params") String params,
                           @RequestParam("files") MultipartFile[] files) {
        Photo photo = JSONObject.parseObject(params, Photo.class);
        photo.setType(type);
        photoService.uploadFiles(photo, files);
    }

    @PostMapping(value = "/model/upload/{type}")
    @CheckRepeat
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(value = "上传模型文件 ", notes = "上传模型文件", httpMethod = "POST", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "附件", required = true, paramType = "form", dataType = "__file"),
            @ApiImplicitParam(name = "attach", value = "附件实体类", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "3：桥梁附件 4:隧道附件", required = true, paramType = "path", dataType = "Integer"),
    })
    public void modelUpload(@RequestParam String attach, @RequestParam MultipartFile file,
                            @PathVariable("type") Integer type) {
        Photo photo = JSONObject.parseObject(attach, Photo.class);
        photo.setType(type);
        photoService.modelUpload(file, photo);
    }

    @PutMapping(value = "/model/update/{photoId}")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(value = "更新模型信息", notes = "更新模型信息", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "photoId", value = "图片id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "file", value = "附件", required = true, paramType = "form", dataType = "__file"),
            @ApiImplicitParam(name = "attach", value = "附件实体类", required = true, paramType = "form", dataType = "String")
    })
    public void updateModel(@PathVariable("photoId") Integer photoId,
                            @RequestParam String attach, @RequestParam MultipartFile file){

        Photo photo = JSONObject.parseObject(attach, Photo.class);
        photo.setId(photoId);
        photoService.updateModel(file, photo);
    }

    @DeleteMapping("/{photoId}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    @ApiOperation(value = "删除文件", notes = "删除文件", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "photoId", value = "文件Id", dataType = "Integer", paramType = "path", required = true)
    })
    public void deletePhoto(@PathVariable("photoId") Integer photoId) {
        photoService.delete(photoId);
    }

    @GetMapping("/download/{id}")
    @ApiOperation(value = "下载模型", notes = "下载模型", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "模型id", dataType = "Integer", paramType = "path", required = true)
    })
    public void download(@PathVariable("id") Integer id, HttpServletResponse response) {
        Photo photo = photoService.findById(id);
        FileUtil.pushToWeb(response, photo.getPath(), "APPLICATION/OCTET-STREAM");
    }

}
