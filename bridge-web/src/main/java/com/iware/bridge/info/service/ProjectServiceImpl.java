package com.iware.bridge.info.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.dao.ProjectAppointExpDao;
import com.iware.bridge.info.dao.ProjectExpDao;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.info.vo.ProjectAppointVO;
import com.iware.bridge.info.vo.ProjectVO;
import com.iware.bridge.model.dao.global.*;
import com.iware.bridge.model.entity.global.*;
import com.iware.bridge.model.entity.user.Power;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.enums.UserRoleEnum;
import com.iware.common.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author ZRB
 * @date 2021-7-6
 */

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectExpDao projectExpDao;
    @Autowired
    private ProjectStructureRelDao projectStructureRelDao;
    @Autowired
    private ProjectAppointDao projectAppointDao;
    @Autowired
    private ProjectAppointExpDao projectAppointExpDao;
    @Autowired
    private ProjectAppointPowerRelDao projectAppointPowerRelDao;

    @Override
    public List<Structure> listStructureByUnit(Integer unitId) {
        return projectExpDao.listStructureByUnit(unitId);
    }

    @Override
    public PageInfo<ProjectVO> listProjectByPage(Integer pageNum, Integer pageSize, InfoFilter filter) {

        PageHelper.startPage(pageNum, pageSize);
        List<ProjectVO> list = projectExpDao.listProjectByPage(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), filter);
        if (!CollectionUtils.isEmpty(list)) {
            for (ProjectVO project : list) {
                if (!StringUtils.isEmpty(project.getIdStr())) {
                    List<String> ids = Arrays.asList(project.getIdStr().split(","));

                    project.setStructureIds(ids.stream().map(Integer::parseInt).collect(Collectors.toList()));
                }
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    @Transactional
    public void addProject(ProjectVO projectVO) {
        List<Project> projectInfos = projectExpDao.getProjectByName(null, projectVO.getName());
        if (CollectionUtils.isEmpty(projectInfos)) {
            if (UserRoleEnum.OWNER.getCode().equals(ThreadLocalMap.getRoleId())) {
                projectVO.setUnitId(ThreadLocalMap.getUnitId());
            }
            projectVO.setCreateUserId(ThreadLocalMap.getUserId());
            projectDao.save(projectVO);

            //批量添加结构物项目关联
            this.batchSaveProjectStructureRel(projectVO.getId(), projectVO.getStructureIds());
        } else {
            throw new BusinessException(GlobalResultEnum.PROJECT_EXIST_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateProject(ProjectVO projectVO) {
        List<Project> list = projectExpDao.getProjectByName(projectVO.getId(), projectVO.getName());
        if (CollectionUtils.isEmpty(list)) {

            // 删除项目结构物关联后再添加
            projectExpDao.delProjectStructureRel(projectVO.getId());
            this.batchSaveProjectStructureRel(projectVO.getId(), projectVO.getStructureIds());
            // 更新项目表
            projectExpDao.update(projectVO);
        } else {
            throw new BusinessException(GlobalResultEnum.PROJECT_EXIST_ERROR);
        }
    }

    public void batchSaveProjectStructureRel(Integer projectId, List<Integer> structureIds) {

        List<ProjectStructureRel> projectStructureRelList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(structureIds)) {
            for (Integer structureId : structureIds) {
                ProjectStructureRel rel = new ProjectStructureRel();
                rel.setProjectId(projectId);
                rel.setStructureId(structureId);
                projectStructureRelList.add(rel);
            }
            projectStructureRelDao.batchSave(projectStructureRelList);
        }
    }

    @Override
    @Transactional
    public void deleteProject(Integer projectId) {
        //有相关联桥梁时不允许删除
        ProjectStructureRel projectStructureRelQry = new ProjectStructureRel();
        projectStructureRelQry.setProjectId(projectId);
        List<ProjectStructureRel> projectStructureRelList = projectStructureRelDao.query(projectStructureRelQry);
        if (!CollectionUtils.isEmpty(projectStructureRelList)) {
            throw new BusinessException("该项目下存在结构物，不允许删除！");
        }
        projectDao.deleteById(projectId);
    }

    @Override
    public PageInfo<ProjectAppointVO> getAppointList(Integer projectId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProjectAppointVO> list = projectAppointExpDao.getAppointList(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), projectId);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional
    public void addAppoint(ProjectAppointVO appointVO) {

        Integer roleId = ThreadLocalMap.getRoleId();
        Integer unitId = ThreadLocalMap.getUnitId();
        appointVO.setAppointUnitId(unitId);
        appointVO.setCreateUserId(ThreadLocalMap.getUserId());

        ProjectAppointVO mainAppoint = new ProjectAppointVO();

        if (UserRoleEnum.OWNER.getCode().equals(roleId)) {
            //判断业务是否已在该时间内指派
            Integer count = projectAppointExpDao.getDupTimeCount(appointVO);
            if (count > 0) {
                throw new BusinessException(GlobalResultEnum.APPOINT_BUSINESS_DUP_ERROR);
            }

            //判断在该时间内指派的项目中桥梁在其他项目中是否指派相同业务
            count = projectAppointExpDao.getDumpStructureInTimeCount(appointVO);
            if (count > 0) {
                throw new BusinessException(GlobalResultEnum.APPOINT_STRUCTURE_POWER_DUP_ERROR);
            }

            //业主指派给承接单位主用户
            this.saveSingleAppoint(appointVO);

            //如果承接单位主用户已经指派过该项目给承接单位普通用户，延续指派
            List<ProjectAppointVO> appointedList = projectAppointExpDao.getMainLastAppoint(appointVO.getReceiveUnitId(),
                    appointVO.getProjectId(), appointVO.getBusiness());

            BeanUtils.copyProperties(appointVO, mainAppoint);
            mainAppoint.setAppointUnitId(appointVO.getReceiveUnitId());
            mainAppoint.setCreateUserId(appointVO.getCreateUserId());
            if (!CollectionUtils.isEmpty(appointedList)) {
                for (ProjectAppointVO item : appointedList) {

                    //取业务交集
                    List<Integer> intersection = appointVO.getBusiness().stream().filter(
                            powerId -> item.getBusiness().contains(powerId)).collect(toList());

                    mainAppoint.setReceiveUnitId(item.getReceiveUnitId());
                    mainAppoint.setBusiness(intersection);
                    this.saveSingleAppoint(mainAppoint);
                }
            }
        } else if (UserRoleEnum.UNDERTAKING_MAIN.getCode().equals(roleId)) {
            //获取所有业主指派给主用户未过期的并且主用户未指派给普通用户的记录
            List<ProjectAppointVO> unAppointList = projectAppointExpDao.getUnAppointByBusiness(unitId,
                    appointVO.getProjectId(), appointVO.getBusiness());

            if (!CollectionUtils.isEmpty(unAppointList)) {

                //对比未指派业务和勾选业务，勾选业务不在未指派业务内，提示该业务已全部指派
                List<Integer> unAppointBusiness = unAppointList.stream().flatMap(item -> item.getBusiness().stream())
                        .distinct().collect(Collectors.toList());

                List<Integer> reduce = appointVO.getBusiness().stream().filter(
                        powerId -> !unAppointBusiness.contains(powerId)
                ).collect(toList());

                if (!CollectionUtils.isEmpty(reduce)) {
                    throw new BusinessException("所选业务中存在已全部被指派业务");
                }

                mainAppoint.setAppointUnitId(unitId);
                mainAppoint.setCreateUserId(appointVO.getCreateUserId());
                mainAppoint.setProjectId(appointVO.getProjectId());

                //以指派业务分组
                Map<Integer, List<ProjectAppointVO>> groups = unAppointList.stream().collect(Collectors.groupingBy(ProjectAppointVO::getId));

                for (Map.Entry<Integer, List<ProjectAppointVO>> entryProjectAppoint : groups.entrySet()) {
                    List<ProjectAppointVO> itemList = entryProjectAppoint.getValue();

                    List<Integer> intersection = itemList.stream().flatMap(item -> item.getBusiness().stream())
                            .collect(Collectors.toList());
                    mainAppoint.setReceiveUnitId(appointVO.getReceiveUnitId());
                    mainAppoint.setBusiness(intersection);
                    mainAppoint.setStartTime(itemList.get(0).getStartTime());
                    mainAppoint.setEndTime(itemList.get(0).getEndTime());
                    this.saveSingleAppoint(mainAppoint);
                }
            } else {
                throw new BusinessException("该项目业务已有指派信息，请勿重复指派");
            }
        }

    }


    public void saveSingleAppoint(ProjectAppointVO appointVO) {
        List<ProjectAppointPowerRel> relList = new ArrayList<>();
        projectAppointDao.save(appointVO);
        for (Integer powerId : appointVO.getBusiness()) {
            ProjectAppointPowerRel appointRel = new ProjectAppointPowerRel();
            appointRel.setPowerId(powerId);
            appointRel.setProjectAppointId(appointVO.getId());
            relList.add(appointRel);
        }
        projectAppointPowerRelDao.batchSave(relList);
    }

    @Override
    @Transactional
    public void delAppoint(Integer appointId) {

        //业主删除指派承接单位主用户同时删除该项目该时间内指派
        if (UserRoleEnum.OWNER.getCode().equals(ThreadLocalMap.getRoleId())) {
            ProjectAppoint projectAppoint = projectAppointDao.findById(appointId);
            projectAppointExpDao.delMainUserAppointRel(projectAppoint);
            projectAppointExpDao.delMainUserAppoint(projectAppoint);
        }
        projectAppointDao.deleteById(appointId);
        projectAppointExpDao.deletePowerRel(appointId);
    }

    @Override
    public List<Power> getBusiness(Integer projectId) {
        List<Power> list = projectExpDao.getBusinessByUnit(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), projectId);

        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException("该项目合同已到期");
        }
        return list;
    }
}
