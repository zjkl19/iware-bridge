package com.iware.bridge.app.assess.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.app.assess.dao.AppMonitorPlanDao;
import com.iware.bridge.app.assess.dao.PlatUserDao;
import com.iware.bridge.app.assess.vo.monitor.*;
import com.iware.bridge.app.assess.vo.platUser.PlatUserVo;
import com.iware.bridge.model.entity.user.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public  class  AppMonitorPlanServiceImpl implements AppMonitorPlanService{
	
	@Autowired
	private AppMonitorPlanDao appMonitorPlanDao;
	@Autowired
	private PlatUserDao platUserDao;
	@Autowired
	private LoginUserService loginUserServ;

	/**
	 * 获取项目检测列表
	 * @param pageNum
	 * @param pageSize
	 * @param filter
	 * @return
	 */
	public PageInfo<MonitorPlanVo> getMonitorPlanPage(Integer pageNum, Integer pageSize, AppMonitorPlanApiFilterVo filter) {
		
		
		AppMonitorPlanFilterVo monitorPlanFilter = new AppMonitorPlanFilterVo();
		if(filter!=null){
			BeanUtils.copyProperties(filter, monitorPlanFilter);
		}
		User loginUser = loginUserServ.getUser();
		PageHelperObject selector=new PageHelperObject();
		selector.setPageNum(pageNum);
		selector.setPageSize(pageSize);
		selector.setCount(true);
		selector.setReasonable(false);
		selector.setPageSizeZero(false);
		if(loginUser!=null) {
			monitorPlanFilter.setUnitId(loginUser.getUnitId());
		}
		PageHelper.startPage(selector);
		List<AppMontitorPlanDetailVo> planDetailList = new PageInfo<AppMontitorPlanDetailVo>(appMonitorPlanDao.getMonitorPlanList(monitorPlanFilter)).getList();
		
		List<MonitorPlanVo> planList=new ArrayList<MonitorPlanVo>();
		if(!planDetailList.isEmpty()) {
			planDetailList.forEach(detail->{
				MonitorPlanVo plan = new MonitorPlanVo();
				plan.setId(detail.getId());
				plan.setProjectName(detail.getProjectName());
				plan.setStartTime(detail.getStartTime());
				plan.setProjectId(detail.getProjectId());
				Integer parentId = detail.getParentId();
				if(parentId!=null) {
					PlatUserVo platUser = platUserDao.getUser(parentId);
					if(platUser !=null){
						String role = platUser.getRole();
						if(role.equals("业主用户")) {
							plan.setOwnerUnit(platUser.getPetname());
						}
					}
					
				}
				if(plan.getOwnerUnit()==null) {
					Integer pCreateUserId = detail.getpCreateUserId();
					PlatUserVo platUser = platUserDao.getUser(pCreateUserId);
					if(platUser!=null) {
						String role = platUser.getRole();
						if(role.equals("业主用户")) {
							plan.setOwnerUnit(platUser.getPetname());
						}
					}
					
					
					
				}
				planList.add(plan);
			});
		}
		Integer total = appMonitorPlanDao.getMonitorPlanCount(monitorPlanFilter);
		
		Page<MonitorPlanVo> planPage = new Page<MonitorPlanVo>(pageNum,pageSize);
		planPage.setTotal(total);
		planPage.addAll(planList);
		return new PageInfo<MonitorPlanVo>(planPage);
	}

	@Override
	public List<PlatUserVo> getOwnerUnitList() {
		// TODO Auto-generated method stub
		AppMonitorPlanFilterVo monitorPlanFilter = new AppMonitorPlanFilterVo();
		User loginUser = loginUserServ.getUser();
		if(loginUser!=null) {
				monitorPlanFilter.setUnitId(loginUser.getUnitId());
		}
		List<AppMontitorPlanDetailVo> planDetailList = appMonitorPlanDao.getMonitorPlanList(monitorPlanFilter);
		List<Integer> platUserIds=new ArrayList<Integer>();
		if(!planDetailList.isEmpty()) {
			planDetailList.forEach(detail->{
				if(detail.getParentId()!=null) {
					if(platUserIds.contains(detail.getParentId())==false) {
						platUserIds.add(detail.getParentId());
					}
					
				}
				if(detail.getCreateUserId()!=null) {
					if(platUserIds.contains(detail.getCreateUserId())==false) {
						platUserIds.add(detail.getCreateUserId());
					}
					
				}
			});
		}
		if(!platUserIds.isEmpty()) {
			List<PlatUserVo> platUserList = platUserDao.getUserList(platUserIds);
			return (List<PlatUserVo>) platUserList.stream().filter((PlatUserVo user)->user.getRole().equals("业主用户")).collect(Collectors.toList());
		}
		return null;
	}
}
