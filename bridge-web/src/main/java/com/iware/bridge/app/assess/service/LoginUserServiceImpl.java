package com.iware.bridge.app.assess.service;

import com.auth0.jwt.interfaces.Claim;
import com.iware.bridge.app.assess.dao.PlatUserDao;
import com.iware.bridge.model.dao.user.UserDao;
import com.iware.bridge.model.entity.user.User;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.utils.TokenUtilJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class LoginUserServiceImpl implements LoginUserService{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PlatUserDao platUserDao;

	public User getUser() {

		// TODO Auto-generated method stub

		String token = request.getHeader(GlobalConstant.X_AUTH_TOKEN);
		Map<String, Claim> tokenData = TokenUtilJWT.verifyToken(token);
		Integer userId = tokenData.get("userId").as(Integer.class);
		String username=tokenData.get("username").asString();
		User user = userDao.findById(userId);
		return user;
	}

}
