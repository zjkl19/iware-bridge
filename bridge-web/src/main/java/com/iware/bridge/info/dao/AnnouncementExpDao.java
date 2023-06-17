package com.iware.bridge.info.dao;

import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.model.entity.global.Announcement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-23
 */

@Repository
public interface AnnouncementExpDao {

    /** 分页获取公告信息列表 */
    public List<Announcement> getAnnouncementList(@Param("roleId") Integer roleId,
                                                  @Param("unitId") Integer unitId,
                                                  @Param("filter") InfoFilter filter);

    /** 发布后重新修改本公告提醒(除自己) */
    public void batchUpdateExceptSelf(@Param("announcementId") Integer announcementId,
                                      @Param("userId") Integer userId);

    /** 获取用户提醒公告 */
    public List<Announcement> listNoticeByUser(@Param("userId") Integer userId);

    /** 修改公告读取状态 */
    public void updateNoticeStatus(@Param("userId") Integer userId,
                                   @Param("announcementId") Integer announcementId,
                                   @Param("status") Integer status);

    /** 删除相关通知 */
    public void delRelByAnnouncementId(@Param("announcementId") Integer announcementId);
}
