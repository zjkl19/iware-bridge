package com.iware.bridge.app.assess.controller;

import com.iware.bridge.app.assess.service.AppLoginService;
import com.iware.bridge.app.assess.vo.appuser.AppUserVo;
import com.iware.bridge.app.assess.vo.login.LoginVo;
import com.iware.common.result.ResultBody;
import com.iware.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zhuqiang
 */
@Api(value = "APP用户登录", tags = "APP用户登录")
@RestController
@RequestMapping(value = "/app")
public class AppLoginApi {
	
	@Autowired
	private AppLoginService appLoginServ;
	
	@PostMapping(value = "/login")
    @ApiOperation(value = "登录 ", notes = "登录 ", httpMethod = "POST")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "login", dataType = "LoginVo", paramType = "body"),
    })
	public ResultBody getAppUserPage(@RequestBody(required = true) LoginVo login) {
		 try {
			 	
			 	Map<String, Object> resultData = appLoginServ.getTokenOnLogin(login.getUsername(), login.getPassword(),login.getDeviceId());
				return ResultUtil.success(resultData);
			}catch (Exception e) {
				// TODO: handle exception
				return ResultUtil.error(e.getMessage());
			}
		 
	 }

	@GetMapping(value = "/user")
	@ApiOperation(value = "获取登录用户信息 ", notes = "获取登录用户信息 ", httpMethod = "GET")
	@ApiImplicitParams({
			
	})
	public ResultBody getAppUserInfo() {
		try {

			AppUserVo userInfo = appLoginServ.getUserInfo();

			return ResultUtil.success(userInfo);
		}catch (Exception e) {
			// TODO: handle exception
			return ResultUtil.error(e.getMessage());
		}

	}
}
