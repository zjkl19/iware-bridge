package com.iware.bridge.inspection.api;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.inspection.serivce.PlanDetailService;
import com.iware.bridge.inspection.serivce.PlanService;
import com.iware.bridge.inspection.vo.*;
import com.iware.bridge.model.entity.inspection.PlanDetail;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.common.annotations.CheckRepeat;
import com.iware.common.result.ResultBody;
import com.iware.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LBX
 * @date 2021-7-30
 */

@RestController
@Api(value = "巡查计划接口", tags="巡查计划接口",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/inspection/plan",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class InspectionPlanApi {

    @Autowired
    private PlanService planService;
    @Autowired
    private PlanDetailService planDetailService;

    @PostMapping(value = "/list")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取计划列表",value="获取计划列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "filter", value = "条件", required = true, paramType = "body", dataType = "PlanFilter")
    })
    public PageInfo<PlanVO> listPlan(@RequestParam(value = "pageNum") Integer pageNum,
                                       @RequestParam(value = "pageSize") Integer pageSize,
                                       @RequestBody PlanFilter filter) {
        filter.setType(1);
        return new PageInfo<>(planService.listPlan(pageNum, pageSize, filter));
    }

    @PostMapping(value = "")
    @CheckRepeat
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(notes="新增巡查计划",value="新增巡查计划",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planVO", value = "计划信息", required = true, paramType = "body", dataType = "PlanVO")
    })
    public ResultBody addInspectionPlan(@RequestBody PlanVO planVO) {
        String result=planService.addInspectionPlan(planVO);
        if(result==null||result.isEmpty()){
            return ResultUtil.success();
        }else{
            return ResultUtil.vo("6039","",result);
        }
    }

    @PutMapping(value = "/{planId}")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(notes="修改巡查计划",value="修改巡查计划",httpMethod="PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planVO", value = "计划信息", required = true, paramType = "body", dataType = "PlanVO")
    })
    public ResultBody updInspectionPlan(@PathVariable("planId") Integer planId,
                                        @RequestBody PlanVO planVO) {
        planVO.setId(planId);
        String result=planService.updInspectionPlan(planVO);
        if(result==null||result.isEmpty()){
            return ResultUtil.success();
        }else{
            return ResultUtil.vo("6039","",result);
        }
    }

    @DeleteMapping(value = "/{planId}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    @ApiOperation(notes="删除巡查计划",value="删除巡查计划",httpMethod="DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planId", value = "计划id", dataType = "integer", paramType = "path", required = true),
    })
    public void delPlan(@PathVariable("planId") Integer planId) {
        planService.delInspectionPlan(planId);
    }

    @PostMapping(value = "/detail/list")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes="获取巡查计划详情列表",value="获取巡查计划详情列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "filter", value = "条件", required = true, paramType = "body", dataType = "PlanDetailFilter")
    })
    public PageInfo<PlanDetailVO> listPlanDetail(@RequestParam(value = "pageNum") Integer pageNum,
                                                 @RequestParam(value = "pageSize") Integer pageSize,
                                                 @RequestBody PlanDetailFilter filter) {
        return planDetailService.listPlanDetail(1, 999, filter,false);
    }

    @PostMapping(value = "/detail")
    @CheckRepeat
    @Permission(actionType = ActionTypeEnum.ACTION_ADD)
    @ApiOperation(notes="新增巡查计划细项",value="新增巡查计划细项",httpMethod="POST")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "detail", value = "细项", required = true, paramType = "body", dataType = "PlanDetail")
    })
    public void addPlanDetail(@RequestBody PlanDetail detail) {
        planDetailService.addPlanDetail(detail);
    }

    @DeleteMapping(value = "/detail/{planDetailId}")
    @Permission(actionType = ActionTypeEnum.ACTION_DEL)
    @ApiOperation(notes="删除巡查计划细项",value="删除巡查计划细项",httpMethod="DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planDetailId", value = "细项id", required = true, paramType = "body", dataType = "integer")
    })
    public void delPlanDetail(@PathVariable("planDetailId") Integer planDetailId) {
        planDetailService.delPlanDetail(planDetailId);
    }

    @PutMapping(value = "/detail")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(notes = "修改巡查计划细项",value = "修改巡查计划细项",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planDetailVO", value = "细项内容", required = true, paramType = "body", dataType = "PlanDetailVO")
    })
    public void updPlanDetail(@RequestBody PlanDetailVO planDetailVO){
        planDetailService.updPlanDetail(planDetailVO);
    }

    @GetMapping(value = "/detail/{planDetailId}")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "查询单个巡查计划细项",value = "查询单个巡查计划细项",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planDetailId", value = "细项id", required = true, paramType = "path", dataType = "integer")
    })
    public PlanDetailVO selPlanDetail(@PathVariable("planDetailId") Integer planDetailId){
        return planDetailService.selPlanDetail(planDetailId);
    }

    @PostMapping(value = "/firstDate")
    @Permission(actionType = ActionTypeEnum.ACTION_QRY)
    @ApiOperation(notes = "获取当月首次巡查时间",value = "获取当月首次巡查时间",httpMethod = "POST")
    @ApiImplicitParams({
    })
    public Map<String,String> selPlanFirstDate(@RequestBody FirstDataFilter filter){
        Map<String,String> result = new HashMap<>();
        try {
            for(Integer structure : filter.getStructureId()){
                if(structure==-1){
                    continue;
                }
                Date firstDate=planService.selPlanFirstDate(structure,
                        filter.getProjectId(),
                        new SimpleDateFormat("yyyy-MM").parse(filter.getMonth()));
                result.put(structure + "", new SimpleDateFormat("yyyy-MM-dd").format(firstDate));
            }
        }catch (Exception e){

        }
        return result;
    }

    @PutMapping(value = "/updatePlanAmount")
    @Permission(actionType = ActionTypeEnum.ACTION_UPD)
    @ApiOperation(notes = "修改金额",value = "修改金额",httpMethod = "PUT")
    @ApiImplicitParams({
    })
    public void updatePlanAmount(@RequestBody PlanAmountMap map){
        planService.updatePlanAmount(map);
    }
}
