package com.iware.bridge.app.assess.controller;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.app.assess.service.AppMonitorPlanService;
import com.iware.bridge.app.assess.vo.monitor.AppMonitorPlanApiFilterVo;
import com.iware.bridge.app.assess.vo.monitor.MonitorPlanVo;
import com.iware.bridge.app.assess.vo.platUser.PlatUserVo;
import com.iware.common.result.ResultBody;
import com.iware.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhuqiang
 */
@Api(value = "APP端项目相关", tags = "APP端项目相关")
@RestController
@RequestMapping(value = "/app/monitor")
public class AppMonitorPlanApi {
	
	@Autowired
	private AppMonitorPlanService monitorServ;


	@PostMapping(value = "/getlist")
    @ApiOperation(value = "获取检测项目列表 ", notes = "获取检测项目列表 ", httpMethod = "POST")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "pageNum", dataType = "int64", paramType = "query",defaultValue="1",required=true),
    	@ApiImplicitParam(name = "pageSize", dataType = "int64", paramType = "query",defaultValue="1",required=true),
    	@ApiImplicitParam(name = "filter", dataType = "AppMonitorPlanApiFilterVo", paramType = "body"),
    })
	public ResultBody getMonitorList(@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",defaultValue="1") Integer pageSize,
			@RequestBody(required = false) AppMonitorPlanApiFilterVo filter) {
		 try {
			 	PageInfo<MonitorPlanVo> planList = monitorServ.getMonitorPlanPage(pageNum, pageSize, filter);
				return ResultUtil.success(planList);
			}catch (Exception e) {
				// TODO: handle exception
				return ResultUtil.error(e.getMessage());
			}
		 
	 }
	
	
	@GetMapping(value = "/getOwnerUnitList")
    @ApiOperation(value = "获取检测项目业主列表 ", notes = "获取检测项目业主列表 ", httpMethod = "GET")
    @ApiImplicitParams({
    	
    })
	public ResultBody getOwnerUnitList() {
		 try {
			 	List<PlatUserVo> userList = monitorServ.getOwnerUnitList();
				return ResultUtil.success(userList);
			}catch (Exception e) {
				// TODO: handle exception
				return ResultUtil.error(e.getMessage());
			}
		 
	 }

}
