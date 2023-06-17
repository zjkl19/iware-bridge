package com.iware.bridge.info.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.dao.StructureExpDao;
import com.iware.bridge.info.vo.BridgeTunnelDetailVO;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.model.dao.global.StructureDao;
import com.iware.bridge.model.dao.global.TunnelInfoDao;
import com.iware.bridge.model.entity.global.Photo;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.global.TunnelInfo;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.enums.UserRoleEnum;
import com.iware.common.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-6
 */

@Service
public class TunnelServiceImpl implements TunnelService {

	@Autowired
	private TunnelInfoDao tunnelInfoDao;
	@Autowired
	private PhotoService photoService;
	@Autowired
	private StructureService structureService;
	@Autowired
    private StructureDao structureDao;
	@Autowired
	private StructureExpDao structureExpDao;

	@Override
	public PageInfo<Structure> getTunnelList(Integer pageNum, Integer pageSize,
											 InfoFilter filter) {
		PageHelper.startPage(pageNum, pageSize);
		filter.setStructureType(GlobalConstant.TYPE_TUNNEL);
		List<Structure> list = structureExpDao.getStructureList(ThreadLocalMap.getRoleId(),
				ThreadLocalMap.getUnitId(), filter);
		return new PageInfo<>(list);
	}

	@Override
	@Transactional
	public void addTunnel(Structure structure) {

		structureService.checkStructureExist(structure);
		structure.setStructureType(GlobalConstant.TYPE_TUNNEL);
		structure.setCreateUserId(ThreadLocalMap.getUserId());
		if (UserRoleEnum.OWNER.getCode().equals(ThreadLocalMap.getRoleId())) {
			structure.setUnitId(ThreadLocalMap.getUnitId());
		}
		structureDao.save(structure);

		//添加一条对应的详细信息记录
		TunnelInfo tunnelInfo = new TunnelInfo();
		tunnelInfo.setStructureId(structure.getId());
		tunnelInfoDao.save(tunnelInfo);
	}

	@Override
	@Transactional
	public void updateTunnel(Structure structure) {

		structureService.checkStructureExist(structure);
		structureExpDao.update(structure);
	}

	@Override
	@Transactional
	public void updateTunnelDetail(BridgeTunnelDetailVO detailVO) {

		structureService.checkStructureExist(detailVO);
		structureExpDao.update(detailVO);
		structureExpDao.updateTunnelDetail(detailVO.getTunnelInfo());
	}

	@Override
	@Transactional
	public void delTunnel(Integer structureId) {

		//检查结构物关联
		structureService.checkStructureRel(structureId);

		//删除图片
		photoService.deleteByTypeAndTargetId(structureId, 2);
		//删除附件
		photoService.deleteByTypeAndTargetId(structureId, 6);
		//删除隧道信息
		structureDao.deleteById(structureId);
		structureExpDao.deleteTunnelInfo(structureId);

	}

	@Override
	public BridgeTunnelDetailVO getTunnelDetail(Integer structureId) {
		BridgeTunnelDetailVO detailVO = new BridgeTunnelDetailVO();
		Structure structure = structureDao.findById(structureId);
		BeanUtils.copyProperties(structure, detailVO);

		TunnelInfo tunnelQry = new TunnelInfo();
		tunnelQry.setStructureId(structureId);

		List<TunnelInfo> list = tunnelInfoDao.query(tunnelQry);
		if (!CollectionUtils.isEmpty(list)) {
			if (list.size() > 1) {
				throw new BusinessException(GlobalResultEnum.INTERNAL_SERVER_ERROR);
			}
			detailVO.setTunnelInfo(list.get(0));
		} else {
			throw new BusinessException(GlobalResultEnum.INTERNAL_SERVER_ERROR);
		}
		return detailVO;
	}

	@Override
	public PageInfo<Photo> getAnnexList(Integer structureId, Integer pageNum, Integer pageSize) {
		return photoService.listByPage(pageNum, pageSize, structureId, GlobalConstant.PHOTO_TUNNEL_ANNEX);
	}

}
