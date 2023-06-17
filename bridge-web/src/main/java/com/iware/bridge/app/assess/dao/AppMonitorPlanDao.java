package com.iware.bridge.app.assess.dao;

import com.iware.bridge.app.assess.vo.monitor.AppMonitorPlanFilterVo;
import com.iware.bridge.app.assess.vo.monitor.AppMontitorPlanDetailVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppMonitorPlanDao {
	public List<AppMontitorPlanDetailVo> getMonitorPlanList(AppMonitorPlanFilterVo MonitorPlanFilter);
	
	public Integer getMonitorPlanCount(AppMonitorPlanFilterVo MonitorPlanFilter);
}
