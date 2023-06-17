package com.iware.bridge.info.service;


import com.iware.bridge.model.entity.user.User;
import com.iware.common.pojo.UserToken;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    /** 登录 */
	public UserToken login(User userLoginInfo);

	/** 登出 */
    public void logout(HttpServletRequest request);
}
