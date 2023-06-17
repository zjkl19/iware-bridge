package com.iware.bridge.info.service;

import com.iware.bridge.info.dao.PowerExpDao;
import com.iware.bridge.info.dao.UserExpDao;
import com.iware.bridge.model.dao.user.UserDao;
import com.iware.bridge.model.entity.user.Power;
import com.iware.bridge.model.entity.user.Role;
import com.iware.bridge.model.entity.user.User;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.pojo.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

	private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserExpDao userExpDao;
	@Autowired
	private PowerExpDao powerExpDao;

	public UserToken login(User userLoginInfo) {
		logger.debug("user login...");

		List<User> userList = userDao.query(userLoginInfo);
		if(userList == null || userList.size() == 0){
			throw new BusinessException(GlobalResultEnum.ACCOUNT_PWD_ERROR);
		}

		if(userList.size() > 1){
			throw new BusinessException(GlobalResultEnum.DATA_ERROR);
		}

		User user = userList.get(0);

		if(!GlobalConstant.Y.equals(user.getStatus())){
			throw new BusinessException(GlobalResultEnum.ACCOUNT_STATUS_ERROR);
		}

		//封装用户token信息
		UserToken uToken = new UserToken();
		uToken.setUserInfo(user);

		Role role = userExpDao.getRoleByUserId(user.getId());
        uToken.setRoleInfo(role);

        List<Power> powerList = powerExpDao.getPowerByUser(role.getId(), user.getId());
        uToken.setPowerList(powerList);

		return uToken;
	}

	@Override
	public void logout(HttpServletRequest request) {
		request.getSession().invalidate();
	}
}
