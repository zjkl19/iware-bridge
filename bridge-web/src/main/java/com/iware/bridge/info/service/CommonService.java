package com.iware.bridge.info.service;

import com.iware.bridge.info.vo.AppointTime;
import com.iware.bridge.info.vo.AreaVO;
import com.iware.bridge.info.vo.StructureFilter;
import com.iware.bridge.info.vo.StructureVO;
import com.iware.bridge.model.entity.evaluation.BridgeType;
import com.iware.bridge.model.entity.global.Project;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.user.Unit;

import java.util.List;

public interface CommonService {

    /** 获取省市区 */
    public List<AreaVO> getAreaList(Integer patentId);

    /** 获取用户桥隧 */
    public List<Structure> listUnitStructure(StructureFilter filter);

    /** 获取用户有传感器/巡查/维养/检测计划的桥隧 */
    public List<Structure> listUnitStructureByModel(StructureFilter filter);

    /** 获取用户项目 */
    public List<Project> listUserProject(Integer powerId, Integer onTime);

    /** 获取用户有传感器/巡查/维养/检测计划的项目*/
    public List<Project> listUserProjectByModel(Integer powerId);

    /** 获取结构物 */
    public StructureVO getStructureById(Integer structureId, Integer type);

    /** 获取承接单位项目指派时间 */
    public List<AppointTime> getUnitAppointTime(Integer projectId, Integer powerId);

    /** 传感器分类 */
    public Integer getSensorType(Integer companyId, Integer sensorDetailsId);

    /** 获取用户结构物地址 */
    public List<AreaVO> listAreaByModel(Integer powerId);

    public List<Unit> listUnitByRoleAndPower(Integer roleId, Integer powerId);

    /** 获取检测评估桥梁类型 */
    public List<BridgeType> getBridgeType();
}
