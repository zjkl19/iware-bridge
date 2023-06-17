package com.iware.bridge.app.assess.service;


import com.iware.bridge.app.assess.vo.appuser.AppUserVo;

import java.util.Map;

/**
 * App端登录服务
 * @author admin
 *
 */
public interface AppLoginService {
	public Map<String,Object> getTokenOnLogin(String username,String password,String deviceId);
	/**
	 * 获取登录用户信息
	 *
	 */
	public AppUserVo getUserInfo();
}
