package com.iware.bridge.info.service;


import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.vo.AnnouncementNotice;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.model.entity.global.Announcement;

/**
 * @author ZRB
 * @date 2021-7-5
 */

public interface AnnouncementService {

	/** 分页获取公告信息列表 */
	public PageInfo<Announcement> listAnnouncement(Integer pageNum, Integer pageSize,
													  InfoFilter filter);

	/** 添加一条公告 */
	public void addAnnouncement(Announcement announcement);

	/** 修改一条公告 */
	public void updAnnouncement(Announcement announcement);

	/** 删除一条公告 */
    public void deleteById(Integer id);

	/** 发布/下架一条公告 */
	public void publish(Integer id, Integer status);

	/** 首页获取公告消息列表 */
    public AnnouncementNotice getNoticeList();

	/** 修改公告读取状态 */
	public void updateNoticeStatus(Integer announcementId, Integer status);
}
