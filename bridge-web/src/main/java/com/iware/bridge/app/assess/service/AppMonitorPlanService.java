package com.iware.bridge.app.assess.service;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.app.assess.vo.monitor.AppMonitorPlanApiFilterVo;
import com.iware.bridge.app.assess.vo.monitor.MonitorPlanVo;
import com.iware.bridge.app.assess.vo.platUser.PlatUserVo;

import java.util.List;

public  interface  AppMonitorPlanService {
	/**
	 * 获取APP端检测计划项目列表
	 * @param PageNum
	 * @param PageSize
	 * @param filter
	 * @return
	 */
	 public PageInfo<MonitorPlanVo> getMonitorPlanPage(Integer PageNum, Integer PageSize, AppMonitorPlanApiFilterVo filter);
	 /**
	  * 获取业主列表
	  * @return
	  */
	 public List<PlatUserVo> getOwnerUnitList();
}
