package com.iware.bridge.info.service;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.info.vo.ProjectAppointVO;
import com.iware.bridge.info.vo.ProjectVO;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.user.Power;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-6
 */

public interface ProjectService {

	/** 新建项目时获取当前可选隧道 */
	public List<Structure> listStructureByUnit(Integer unitId);

	/** 获取项目列表(包含分页，模糊查询) */
	public PageInfo<ProjectVO> listProjectByPage(Integer pageNum, Integer pageSize, InfoFilter filter);

	/** 新增项目 */
	public void addProject(ProjectVO projectVO);

	/** 修改项目 */
	public void updateProject(ProjectVO projectVO);

	/** 删除项目 */
	public void deleteProject(Integer projectId);

	/** 查询指派记录 */
	public PageInfo<ProjectAppointVO> getAppointList(Integer projectId, Integer pageNum, Integer pageSize);

	/** 添加指派记录 */
	public void addAppoint(ProjectAppointVO appointVO);

	/** 删除指派记录 */
	public void delAppoint(Integer appointId);

    /** 获取业务 */
	public List<Power> getBusiness(Integer projectId);

}
