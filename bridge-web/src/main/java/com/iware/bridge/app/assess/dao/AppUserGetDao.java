package com.iware.bridge.app.assess.dao;

import com.iware.bridge.app.assess.vo.appuser.AppUserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserGetDao {
    public AppUserVo getUser(@Param("id") Integer id);
}
