package com.iware.bridge.info.service;

import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.vo.BridgeTunnelDetailVO;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.global.Structure;

/**
 * @author ZRB
 * @date 2021-7-6
 */


public interface TunnelService {

	/** 查询用户可看隧道列表 */
	public PageInfo<Structure> getTunnelList(Integer pageNum, Integer pageSize, InfoFilter filter);

	/** 添加隧道 */
	public void addTunnel(Structure structure);

	/** 修改隧道 */
	public void updateTunnel(Structure structure);

	/** 修改隧道详情 */
	public void updateTunnelDetail(BridgeTunnelDetailVO detailVO);

	/** 删除隧道 */
	public void delTunnel(Integer structureId);

	/** 获取隧道详情 */
	public BridgeTunnelDetailVO getTunnelDetail(Integer structureId);

	/** 获取附件列表 */
	public PageInfo<Photo> getAnnexList(Integer structureId, Integer pageNum, Integer pageSize);

}
