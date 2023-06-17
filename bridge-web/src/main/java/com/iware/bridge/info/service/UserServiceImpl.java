package com.iware.bridge.info.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.dao.UserExpDao;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.info.vo.UserVO;
import com.iware.bridge.model.dao.user.*;
import com.iware.bridge.model.entity.user.*;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZRB
 * @date 2021-06-22
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserExpDao userExpDao;
	@Autowired
	private UserRoleRelDao userRoleRelDao;
	@Autowired
	private UserAppRoleRelDao userAppRoleRelDao;
	@Autowired
	private PowerService powerService;
	@Autowired
	private AppRoleDao appRoleDao;

	@Override
	@Transactional
	public void addUser(UserVO userVO) {

		userVO.setCreateUserId(ThreadLocalMap.getUserId());
		userVO.setStatus(1);

		//查询是否存在同名用户
		User user = userExpDao.existUser(userVO.getUsername(), null);

		if (user != null) {
			throw new BusinessException(GlobalResultEnum.USER_EXIST_ERROR);
		}
		userDao.save(userVO);

		//保存角色信息
		UserRoleRel userRoleRel = new UserRoleRel();
		userRoleRel.setRoleId(userVO.getRoleId());
		userRoleRel.setUserId(userVO.getId());
		userRoleRelDao.save(userRoleRel);

		//保存app权限
		this.batchSaveAppRoleRel(userVO.getId(), userVO.getAppRoleList());
	}

	@Override
	@Transactional
	public void editUser(UserVO userVO) {
		//查询是否存在同名用户
		User user = userExpDao.existUser(userVO.getUsername(), userVO.getId());
		if (user != null) {
			throw new BusinessException(GlobalResultEnum.USER_EXIST_ERROR);
		}

		userExpDao.update(userVO);
		userDao.update(userVO);

		//更新用户app权限
		userExpDao.delAppRoleRelByUserId(userVO.getId());
		this.batchSaveAppRoleRel(userVO.getId(), userVO.getAppRoleList());
	}

	public void batchSaveAppRoleRel(Integer userId, List<Integer> appRoleList) {

		List<UserAppRoleRel> userAppRoleRelList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(appRoleList)) {
			for (Integer appRoleId : appRoleList) {
				UserAppRoleRel userAppRoleRel = new UserAppRoleRel();
				userAppRoleRel.setUserId(userId);
				userAppRoleRel.setAppRoleId(appRoleId);
				userAppRoleRelList.add(userAppRoleRel);
			}
			userAppRoleRelDao.batchSave(userAppRoleRelList);
		}
	}


	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void updateStatus(User user) {
		userDao.update(user);
	}

	@Override
	@Transactional
	public void delUser(Integer userId) {
		userDao.deleteById(userId);
		userExpDao.deleteRoleRelByUserId(userId);
		userExpDao.delAppRoleRelByUserId(userId);
		powerService.delPowerByUnitId(userId);
	}

	@Override
	public PageInfo<UserVO> getUserList(Integer pageNum, Integer pageSize, InfoFilter filter) {

		PageHelper.startPage(pageNum, pageSize);
		List<UserVO> list = userExpDao.getUserList(ThreadLocalMap.getRoleId(), ThreadLocalMap.getUnitId(),
				ThreadLocalMap.getUserId(), filter);
		if (!CollectionUtils.isEmpty(list)) {
			for (UserVO user : list) {
				if (!StringUtils.isEmpty(user.getAppRoleStr())) {
					List<String> ids = Arrays.asList(user.getAppRoleStr().split(","));

					user.setAppRoleList(ids.stream().map(Integer::parseInt).collect(Collectors.toList()));
				}
			}
		}
		return new PageInfo<>(list);
	}

	@Override
	public List<User> getByRoleId(Integer roleId) {
		return userExpDao.getUserListByRole(roleId);
	}

	@Override
	public List<AppRole> getAppRoleList() {
		return appRoleDao.findAll();
	}

	@Override
	public void removeBindDevice(Integer userId) {
		userExpDao.updateDeviceIdNull(userId);
	}
}
