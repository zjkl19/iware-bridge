package com.iware.bridge.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iware.cache.constant.CacheConstant;
import com.iware.cache.redis.RedisUtil;
import com.iware.common.annotations.CheckRepeat;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.interceptor.RequestWrapper;
import com.iware.common.pojo.UserToken;
import com.iware.common.properties.CommonProperties;
import com.iware.common.result.ResultBody;
import com.iware.common.spring.SpringContextHolder;
import com.iware.common.utils.IpUtil;
import com.iware.common.utils.MD5Encrypt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

public class LoginContextInterceptor implements HandlerInterceptor {

	private final Logger logger = LoggerFactory.getLogger(LoginContextInterceptor.class);
	private static CommonProperties commonProperties;
	private static final String REPEAT = "REPEAT";

	private RedisUtil redisUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return true;
		}

		// 如果不是映射到方法直接通过
		if(!(handler instanceof HandlerMethod)){
			return true;
		}

		response.setContentType("text/html;charset=utf-8");

		String type = this.getCommonProperties().getLoginType();
		if(logger.isDebugEnabled()){
			logger.debug("会话类型={}", type);
			logger.debug("user client is {}", IpUtil.getIpAddr(request));
		}

		if("token".equalsIgnoreCase(type)){
			// 从 http 请求头中取出 token
			String token = request.getHeader(GlobalConstant.X_AUTH_TOKEN);
			//如果token为空，在从URL中获取token值
			token = StringUtils.isBlank(token)?request.getParameter(GlobalConstant.X_AUTH_TOKEN):token;
			logger.debug("login token is :{}", token);

			if(StringUtils.isBlank(token)){
				response.getWriter().write(JSONObject.toJSONString(
						new ResultBody<String>(GlobalResultEnum.CACHE_EXPIRE)));
				logger.debug("********************************{} is not allow.", request.getServletPath());
				return Boolean.FALSE;
			}else{
				//从缓存中获取到用户的信息
				redisUtil = SpringContextHolder.getBean(RedisUtil.class);
				Object obj = redisUtil.get(token);
				if(obj != null){
					UserToken uToken = JSON.parseObject(obj.toString(), UserToken.class);
					ThreadLocalMap.put(GlobalConstant.SESSION_ID, token);
					ThreadLocalMap.put(GlobalConstant.SESSION_USER_KEY, uToken);

					redisUtil.expire(token,
							token.startsWith(CacheConstant.REDIS_USER_TOKEN_KEY_REMEMBERME)?
									CacheConstant.EXPIRE_TIME_REMEMBERME:CacheConstant.EXPIRE_TIME);
				}else{
					//根据token获取不到缓存数据时，表示需要重新登录
					response.getWriter().write(JSONObject.toJSONString(
							new ResultBody<String>(GlobalResultEnum.CACHE_EXPIRE)));
					logger.debug("********************************{} is not allow.", request.getServletPath());
					return Boolean.FALSE;
				}
			}
		}else{
			//获取session
			HttpSession session = request.getSession(false);
			if(session == null){
				logger.debug("no session!!!");
				response.getWriter().write(JSONObject.toJSONString(
						new ResultBody<String>(GlobalResultEnum.UNAUTHORIZED)));
				return Boolean.FALSE;
			}else{
				//判断用户ID是否存在，不存在就跳转到登录界面
				Object uToken = session.getAttribute(GlobalConstant.SESSION_USER_KEY);
				if (uToken == null) {
					logger.debug("session no user!!!");
					response.getWriter().write(JSONObject.toJSONString(
							new ResultBody<String>(GlobalResultEnum.UNAUTHORIZED)));
					return Boolean.FALSE;
				}else{
					ThreadLocalMap.put(GlobalConstant.SESSION_ID, session.getId());
					ThreadLocalMap.put(GlobalConstant.SESSION_USER_KEY, uToken);
				}
			}
		}

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			checkRepeat(method, request); //重复提交校验
		}

		String routerUrl = request.getHeader(GlobalConstant.X_ROUTER_URL);
		if(StringUtils.isBlank(routerUrl)){
			response.getWriter().write(JSONObject.toJSONString(
					new ResultBody<String>(GlobalResultEnum.UNAUTHORIZED)));
			logger.debug("********************************{} is not allow.", request.getServletPath());
			return Boolean.FALSE;
		} else {
			ThreadLocalMap.put(GlobalConstant.ROUTER_URL, routerUrl);
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		logger.debug("login after completion...");
		ThreadLocalMap.remove();
	}

	/**
	 * CheckRepeat 注解重复提交处理
	 */
	private boolean checkRepeat(Method method, HttpServletRequest request) {
		//获取注解
		CheckRepeat annotation = method.getAnnotation(CheckRepeat.class);
		//如果没有注解，直接通过
		if (annotation == null) {
			return true;
		}
		//获取注解needCheck,第一次提交则去掉token,并允许交易通过，重复提交则不允许交易通过
		boolean needCheck = annotation.needCheck();
		if (needCheck) {
			String key = "";
			try {
				RequestWrapper requestWrapper = new RequestWrapper(request);
				String body = requestWrapper.getBody();
				key = REPEAT + "-" + MD5Encrypt.MD5(body);
				logger.info("请求参数的key为[{}]", key);
			} catch (Exception e) {
				logger.error("获取请求参数异常:[{}]", e.getMessage());
			}
			boolean flag = this.getRedisUtil().hasKey(key);
			if (!flag) {
				this.getRedisUtil().set(key, "1", 60);
			} else {
				throw new BusinessException("重复请求");
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		logger.debug("login post handle...");
	}

	private CommonProperties getCommonProperties(){
		if(commonProperties == null){
			commonProperties = SpringContextHolder.getBean("commonProperties");
		}
		return commonProperties;
	}

	public RedisUtil getRedisUtil() {
		if(redisUtil == null){
			redisUtil = SpringContextHolder.getBean("redisUtil");
		}
		return redisUtil;
	}
}
