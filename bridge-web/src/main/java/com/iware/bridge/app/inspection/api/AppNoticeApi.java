package com.iware.bridge.app.inspection.api;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.app.inspection.service.AppNoticeService;
import com.iware.bridge.inspection.vo.AppNoticeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lbx
 * 2021-10-29
 */

@RestController
@Api(value = "app通知接口", tags="app通知接口",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/app/notice",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AppNoticeApi {

    @Resource
    private AppNoticeService appNoticeService;

    @GetMapping(value = "/size/new")
    @ApiOperation(notes = "获取新通知数量",value = "获取新通知数量",httpMethod = "GET")
    @ApiImplicitParams({
    })
    public Integer getNoticeSizeNew() {
        return appNoticeService.getNoticeSize();
    }

    @GetMapping(value = "/list")
    @ApiOperation(notes = "获取通知",value = "获取通知",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页号", dataType = "integer",required = true,paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "integer",required = true, paramType = "query")
    })
    public PageInfo<AppNoticeVo> getNoticeByUser(@RequestParam("pageNum") Integer pageNum,
                                                 @RequestParam("pageSize") Integer pageSize) {
        return new PageInfo<>(appNoticeService.getNotice(pageNum,pageSize));
    }


}
