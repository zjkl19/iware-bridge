package com.iware.bridge.info.api;

import com.alibaba.fastjson.JSONObject;
import com.iware.bridge.info.service.LoginService;
import com.iware.bridge.model.entity.user.User;
import com.iware.cache.constant.CacheConstant;
import com.iware.cache.redis.RedisUtil;
import com.iware.common.base.AbstractBaseController;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.pojo.TokenVO;
import com.iware.common.pojo.UserToken;
import com.iware.common.properties.CommonProperties;
import com.iware.common.spring.SpringContextHolder;
import com.iware.common.utils.UUIDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Api(value = "用户登录接口", tags = "用户登录接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/login",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginApi implements AbstractBaseController {

	private static Logger logger = LoggerFactory.getLogger(LoginApi.class);
	private CommonProperties commonProperties;

	@Autowired
	private LoginService loginService;

	@PostMapping(value={""})
	@ApiOperation(notes="用户登录",value="用户登录",httpMethod="POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "user",value = "用户数据对象",dataType = "User",required = true,paramType = "body")
	})
	public TokenVO userLogin(HttpServletRequest request, @RequestBody User user) {

		UserToken uToken = loginService.login(user);
		logger.debug("用户token信息==>{}", JSONObject.toJSONString(uToken));

		TokenVO token = new TokenVO();
		token.setUserInfo(uToken.getUserInfo());
		token.setRoleInfo(uToken.getRoleInfo());
		Integer time = CacheConstant.EXPIRE_TIME;
		String loginType = this.getCommonProperties().getLoginType();
		if("token".equalsIgnoreCase(loginType)){
			RedisUtil redisUtil = SpringContextHolder.getBean(RedisUtil.class);
			if(redisUtil == null){
				throw new BusinessException(GlobalResultEnum.CACHE_NOT_FOUND);
			}
			//根据UUID生成uuid，作为token主体信息
			String uuid = UUIDUtils.getUUID();
			String key = uuid;
			if(!redisUtil.set(key, JSONObject.toJSONString(uToken))){
				throw new BusinessException(GlobalResultEnum.CACHE_ERROR);
			}
			redisUtil.expire(key, time);
			token.setToken(key);
			token.setPowerList(uToken.getPowerList());
		}else{
			//放入session
			HttpSession session = request.getSession(true);
			logger.debug("获取session....");
			session.setMaxInactiveInterval(time * 60);
			session.setAttribute(GlobalConstant.SESSION_USER_KEY, uToken);
			logger.debug("放入用户信息....");
		}

		return token;
	}

	private CommonProperties getCommonProperties(){
		if(commonProperties == null){
			commonProperties = SpringContextHolder.getBean("commonProperties");
		}
		return commonProperties;
	}
}
