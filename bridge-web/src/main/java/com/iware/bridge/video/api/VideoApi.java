package com.iware.bridge.video.api;

import com.iware.bridge.model.entity.global.Video;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.bridge.video.service.VideoService;
import com.iware.bridge.video.vo.StructureVideoVO;
import com.iware.common.annotations.CheckRepeat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: HX
 * @date: 2021-6-7
 */

@RestController
@Api(value = "视频接口", tags="视频接口",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value = "/video", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VideoApi {

    @Autowired
    private VideoService videoService;

    @GetMapping(value = "/getAccessToken")
    @ApiOperation(value = "获取萤石云accessToken ", notes = "获取萤石云accessToken ", httpMethod = "GET")
    public String getAccessToken(){
        return videoService.getAccessToken();
    }

    @GetMapping(value = "/getStructureList/{id}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(value = "查询所有结构物及其视频列表", notes = "查询所有结构物及其视频列表", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "项目id（查询全部传0）", dataType = "Integer", required = true, paramType = "path")
    public List<StructureVideoVO> getStructureVideoList(@PathVariable("id") Integer projectId) {
        return videoService.getStructureVideoList(projectId);
    }

    @PostMapping(value = "")
    @CheckRepeat
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(value = "新增视频点", notes = "新增视频点接口", httpMethod = "POST")
    @ApiImplicitParam(name = "video", value = "新增视频属性", dataType = "Video", required = true, paramType = "body")
    public void addVideo(@RequestBody Video video) {
        videoService.addVideo(video);
    }

    @PutMapping(value = "/{id}")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(value = "编辑视频点信息", notes = "编辑视频点信息接口", httpMethod = "PUT")
    @ApiImplicitParam(name = "video", value = "新增视频属性类", dataType = "Video", required = true, paramType = "body")
    public void updVideo(@PathVariable("id") Integer id,@RequestBody Video video) {
        video.setId(id);
        videoService.updVideo(video);
    }

    @DeleteMapping(value = "/{id}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    @ApiOperation(value = "删除视频点", notes = "删除视频点接口", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "视频点ID", dataType = "Integer", required = true, paramType = "path")
    public void delVideo(@PathVariable("id") Integer id) {
        videoService.deleteById(id);
    }
}
