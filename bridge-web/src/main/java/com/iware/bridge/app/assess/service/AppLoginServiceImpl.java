package com.iware.bridge.app.assess.service;

import com.iware.bridge.app.assess.dao.AppUserGetDao;
import com.iware.bridge.app.assess.vo.appuser.AppUserVo;
import com.iware.bridge.model.dao.user.UnitDao;
import com.iware.bridge.model.dao.user.UserDao;
import com.iware.bridge.model.entity.user.User;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.utils.TokenUtilJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * App端登录服务
 * @author admin
 *
 */
@Service
public class AppLoginServiceImpl implements AppLoginService {
	@Autowired
	private LoginUserService loginUserServ;
	@Autowired
	private AppUserGetDao assAppUserdDao;

	@Resource
	private UserDao userDao;

	@Resource
	private UnitDao unitDao;

	public Map<String,Object> getTokenOnLogin(String username,String password,String deviceId){
		Logger logger= LoggerFactory.getLogger(AppLoginServiceImpl.class);
		if(username==null || username.equals("") || password ==null || password.equals("")) {
			throw new BusinessException("6001","用户名和密码不能为空");
		}
		if(password != null) {
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
				md.update(password.getBytes("UTF-8"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				StringBuilder builder=new StringBuilder(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				Arrays.stream(e.getStackTrace()).forEach(s->builder.append(s).append("\n"));
				logger.error(builder.toString());
				throw new BusinessException(GlobalResultEnum.INTERNAL_SERVER_ERROR);
			}
			password=new BigInteger(1,md.digest()).toString(16);
		}
		//判断是否绑定账号
		User deviceIdUser=new User();
		deviceIdUser.setDeviceId(deviceId);
		List<User> loginUserByDeviceId=userDao.query(deviceIdUser);
		//若该登录设备下有账号，且账号与当前输入账号不同，则不允许登录
		if(!loginUserByDeviceId.isEmpty()&&!((loginUserByDeviceId.get(0).getUsername()).equals(username))){
			throw new BusinessException("该设备已绑定账号，请联系管理员解除该设备账号绑定");
		}

		User appUser = new User();
		appUser.setUsername(username);
		appUser.setPassword(password);
		List<User> loginUsers =userDao.query(appUser);


		if(loginUsers.isEmpty()) {
			throw new BusinessException(GlobalResultEnum.BRIDGE_PWD_ERROR);
		}
		User loginUser = loginUsers.get(0);

		if(loginUser.getStatus()!=1){
			throw new BusinessException(GlobalResultEnum.ACCOUNT_STATUS_ERROR);
		}

		String loginUserDevice = loginUser.getDeviceId();
		if(loginUserDevice==null || loginUserDevice.equals("")) {
			loginUser.setDeviceId(deviceId);
			userDao.update(loginUser);
		}else {
			if(!loginUserDevice.equals(deviceId)) {
				throw new BusinessException("无法在此设备登录，请联系管理员解除该账号设备绑定");
			}
		}
		Map<String, String> tokenParam = new HashMap<String,String>();
		tokenParam.put("userId", String.valueOf(loginUser.getId()));
		tokenParam.put("username", loginUser.getUsername());
		tokenParam.put("createUserId", String.valueOf(loginUser.getCreateUserId()));
		String token = TokenUtilJWT.createToken(tokenParam,3600);
		HashMap<String, Object> resultData = new HashMap<String,Object>();
		resultData.put("token", token);
		resultData.put("expireTime",3600);
		return resultData;

	}

	/**
	 * 获取登录用户信息
	 * @return
	 */
	@Override
	public AppUserVo getUserInfo() {
		User user = loginUserServ.getUser();
		if(user==null){
			throw new BusinessException("用户不存在");
		}
		AppUserVo userRoleInfo = assAppUserdDao.getUser(user.getId());
		userRoleInfo.setUndertakeUnit(unitDao.findById(userRoleInfo.getUserId()).getName());
		userRoleInfo.setCreateUserName(unitDao.findById(userRoleInfo.getUserId()).getName());
		return userRoleInfo;
	}


}
