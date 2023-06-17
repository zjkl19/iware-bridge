package com.iware.bridge.info.service;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.evaluation.vo.MonitorRecord;
import com.iware.bridge.info.vo.BridgeTunnelDetailVO;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.inspection.vo.MaintainItemVO;
import com.iware.bridge.inspection.vo.PlanDetailVO;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.global.Structure;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-5
 */

public interface BridgeService {

	/** 分页获取桥梁信息 */
	public PageInfo<Structure> getBridgeList(Integer pageNum, Integer pageSize, InfoFilter infoFilter);

	/** 根据项目id查询桥梁 */
	public List<Structure> getBridgeListByProjectId(Integer projectId);

	/** 获取桥梁详细信息 */
	public BridgeTunnelDetailVO getBridgeDetail(Integer structureId);

	/** 添加桥梁 */
	public void addBridge(Structure structure);

	/** 修改桥梁 */
	public void updateBridge(Structure structure);

	/** 修改桥梁详情 */
	public void updateBridgeDetail(BridgeTunnelDetailVO detailVO);

	/** 删除桥梁 */
	public void deleteBridge(Integer structureId);

	/** 获取附件 */
	public PageInfo<Photo> getAnnexList(Integer structureId, Integer pageNum, Integer pageSize);

	/** 详情页获取检测记录 */
    public PageInfo<MonitorRecord> getMonitorRecord(Integer pageNum, Integer pageSize, Integer structureId);

	/** 详情页获取日常巡查记录 */
	public PageInfo<PlanDetailVO> getInspectionRecordList(Integer pageNum, Integer pageSize, Integer structureId);

	/** 详情页获取维修养护记录 */
	public PageInfo<MaintainItemVO> getMaintainRecordList(Integer pageNum, Integer pageSize, Integer structureId);
}
