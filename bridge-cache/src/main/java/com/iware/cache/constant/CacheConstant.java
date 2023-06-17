package com.iware.cache.constant;

public class CacheConstant {

	//常规登录。30分钟有效
	public static final Integer EXPIRE_TIME = 30;

	//记住我时，7天有效
	public static final Integer EXPIRE_TIME_REMEMBERME = 7*24*60;

	public static final String REDIS_USER_TOKEN_KEY_REMEMBERME = "rememberme:";
}
