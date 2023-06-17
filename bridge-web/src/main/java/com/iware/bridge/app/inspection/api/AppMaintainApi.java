package com.iware.bridge.app.inspection.api;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.app.inspection.service.AppInspectionService;
import com.iware.bridge.app.inspection.service.AppMaintainService;
import com.iware.bridge.inspection.serivce.PlanDetailService;
import com.iware.bridge.inspection.serivce.PlanService;
import com.iware.bridge.inspection.vo.MaintainItemVO;
import com.iware.bridge.inspection.vo.PlanDetailFilter;
import com.iware.bridge.inspection.vo.PlanFilter;
import com.iware.bridge.inspection.vo.PlanVO;
import com.iware.bridge.model.entity.inspection.MaintainItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author linbinxiang
 * @date 2021-8-9
 */

@RestController
@Api(value = "app维养接口", tags="app维养接口",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/app/maintain",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AppMaintainApi {

    @Resource
    private PlanService planService;

    @Resource
    private PlanDetailService planDetailService;

    @Resource
    private AppMaintainService appMaintainService;

    @Resource
    private AppInspectionService appInspectionService;

    @PostMapping(value = "/plan/list")
    @ApiOperation(notes="获取计划列表",value="获取计划列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "条件", required = true, paramType = "body", dataType = "PlanFilter")
    })
    public List<PlanVO> listPlan(@RequestBody PlanFilter filter) {
        filter.setType(2);
        return appInspectionService.listPlan(filter);
    }

    @PostMapping(value = "/plan/detail/list")
    @ApiOperation(notes="获取维养计划详情列表",value="获取维养计划详情列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "filter", value = "条件", required = true, paramType = "body", dataType = "PlanDetailFilter")
    })
    public PageInfo<MaintainItemVO> listMaintainItem(@RequestParam(value = "pageNum") Integer pageNum,
                                                     @RequestParam(value = "pageSize") Integer pageSize,
                                                     @RequestBody PlanDetailFilter filter) {
        return planDetailService.listMaintainItem(pageNum, pageSize, filter,true);
    }

    @PostMapping(value = "/plan/detail")
    @ApiOperation(notes = "新增维养计划细项",value = "新增维养计划细项",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "maintainItem", value = "细项内容", required = true, paramType = "body", dataType = "MaintainItem")
    })
    public void addPlanDetail(@RequestBody MaintainItem maintainItem){
        maintainItem.setMaintainTime(new Date());
        maintainItem.setStatus(0);
        appMaintainService.addMaintainItem(maintainItem);
    }

    @PostMapping(value = "/plan/detail/update")
    @ApiOperation(notes = "修改维养计划细项",value = "修改维养计划细项",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "附件", required = true, paramType = "form", dataType = "__file"),
            @ApiImplicitParam(name = "params", value = "MaintainItemVO类json", required = true, paramType = "form", dataType = "String")
    })
    public void updPlanDetail(@RequestParam("params") String params,
                              @RequestParam("files") MultipartFile[] files, MultipartRequest request){
        MaintainItemVO maintainItem = JSONObject.parseObject(params,MaintainItemVO.class);
        maintainItem.setMaintainTime(new Date());
        ArrayList<MultipartFile> images=new ArrayList<>();
        for(int i=0;i<maintainItem.getBeforeList().size();i++){
            MultipartFile getFile=null;
            if((getFile=request.getFile(maintainItem.getBeforeList().get(i).getName()))!=null) {
                images.add(getFile);
            }
        }
        for(int i=0;i<maintainItem.getProgressList().size();i++){
            MultipartFile getFile=null;
            if((getFile=request.getFile(maintainItem.getProgressList().get(i).getName()))!=null) {
                images.add(getFile);
            }
        }
        for(int i=0;i<maintainItem.getAfterList().size();i++){
            MultipartFile getFile=null;
            if((getFile=request.getFile(maintainItem.getAfterList().get(i).getName()))!=null) {
                images.add(getFile);
            }
        }
        appMaintainService.updMaintainItem(maintainItem,images);
    }

}
