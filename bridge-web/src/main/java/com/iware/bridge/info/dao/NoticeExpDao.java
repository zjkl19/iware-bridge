package com.iware.bridge.info.dao;

import com.iware.bridge.inspection.vo.AppNoticeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lbx
 * 2021-10-29
 */

@Repository
public interface NoticeExpDao {

    public List<AppNoticeVo> selectNoticeByUserId(@Param("userId") Integer userId,@Param("status") Integer status);

    public Integer selectNoticeSizeByUserId(@Param("userId") Integer userId,@Param("status") Integer status);

    public void updateRelStatus(@Param("userId") Integer userId);

}
