package com.iware.bridge.info.api;

import com.iware.bridge.info.service.LoginService;
import com.iware.cache.redis.RedisUtil;
import com.iware.common.base.AbstractBaseController;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.properties.CommonProperties;
import com.iware.common.spring.SpringContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(value = "用户登出接口", tags="用户登出接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequestMapping(value="/logout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LogoutApi implements AbstractBaseController {

	private static Logger logger = LoggerFactory.getLogger(LogoutApi.class);
	private CommonProperties commonProperties;

	@Autowired
	private LoginService loginService;

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value="用户登出",notes="用户注销接口",httpMethod="DELETE")
	public void logout(HttpServletRequest request) {
		logger.debug("user logout...");

		String loginType = this.getCommonProperties().getLoginType();
		if("token".equalsIgnoreCase(loginType)){
			// 从 http 请求头中取出 token
			String token = request.getHeader(GlobalConstant.X_AUTH_TOKEN);
			logger.debug("{}==> logout...", token);
			RedisUtil redisUtil = SpringContextHolder.getBean(RedisUtil.class);
			if(redisUtil == null){
				throw new BusinessException(GlobalResultEnum.CACHE_NOT_FOUND);
			}

			redisUtil.del(token);
		}else{
			loginService.logout(request);
		}
	}

	private CommonProperties getCommonProperties(){
		if(commonProperties == null){
			commonProperties = SpringContextHolder.getBean("commonProperties");
		}
		return commonProperties;
	}
}
