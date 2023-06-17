package com.iware.bridge.app.assess.dao;

import com.iware.bridge.app.assess.vo.platUser.PlatUserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatUserDao {
	public PlatUserVo getUser(@Param(value = "id") Integer id);
	
	public List<PlatUserVo> getUserList(@Param(value = "ids") List<Integer> ids);
}
