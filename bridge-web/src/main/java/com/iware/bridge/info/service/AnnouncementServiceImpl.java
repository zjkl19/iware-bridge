package com.iware.bridge.info.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.info.dao.AnnouncementExpDao;
import com.iware.bridge.info.vo.AnnouncementNotice;
import com.iware.bridge.info.vo.InfoFilter;
import com.iware.bridge.model.dao.global.AnnouncementDao;
import com.iware.bridge.model.dao.global.UserAnnouncementRelDao;
import com.iware.bridge.model.dao.user.UserDao;
import com.iware.bridge.model.entity.global.Announcement;
import com.iware.bridge.model.entity.global.UserAnnouncementRel;
import com.iware.bridge.model.entity.user.User;
import com.iware.common.data.ThreadLocalMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZRB
 * @date 2021-7-5
 */

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

	@Autowired
	private AnnouncementDao announcementDao;
	@Autowired
	private AnnouncementExpDao announcementExpDao;
	@Autowired
	private UserAnnouncementRelDao announcementRelDao;
	@Autowired
	private UserDao userDao;

	@Override
	public AnnouncementNotice getNoticeList() {
		AnnouncementNotice notice = new AnnouncementNotice();

		notice.setUnread(0);
		List<Announcement> announcementList = announcementExpDao.listNoticeByUser(ThreadLocalMap.getUserId());
		notice.setAnnouncementList(announcementList);

		if (!CollectionUtils.isEmpty(announcementList)) {
			int unread = (int)announcementList.stream().filter(item -> item.getStatus().equals(0)).count();
			notice.setUnread(unread);
		}
		return notice;
	}

	@Override
	public void updateNoticeStatus(Integer announcementId, Integer status) {
		announcementExpDao.updateNoticeStatus(ThreadLocalMap.getUserId(), announcementId, status);
	}

	@Override
	public PageInfo<Announcement> listAnnouncement(Integer pageNum, Integer pageSize,
													  InfoFilter filter) {
		PageHelper.startPage(pageNum, pageSize);
		List<Announcement> list = announcementExpDao.getAnnouncementList(ThreadLocalMap.getRoleId(),
				ThreadLocalMap.getUnitId(), filter);
		return new PageInfo<>(list);
	}

	@Override
	@Transactional
	public void addAnnouncement(Announcement announcement) {
		String realName = userDao.findById(ThreadLocalMap.getUserId()).getRealName();
		List<User> userList = userDao.findAll();
		announcement.setCreator(realName);
		announcement.setCreateUserId(ThreadLocalMap.getUserId());
		announcement.setUnitId(ThreadLocalMap.getUnitId());
		announcement.setStatus(0); //默认未发布
		announcementDao.save(announcement);

		//遍历用户生成通知
		List<UserAnnouncementRel> relList = new ArrayList<>();
		for (User user : userList) {
			UserAnnouncementRel rel = new UserAnnouncementRel();
			rel.setAnnouncementId(announcement.getId());
			rel.setUserId(user.getId());

			if (user.getId().equals(ThreadLocalMap.getUserId())) {
				rel.setStatus(1);
			} else {
				rel.setStatus(0);
			}
			relList.add(rel);
		}
		announcementRelDao.batchSave(relList);
	}

	@Override
	public void updAnnouncement(Announcement announcement) {
		announcement.setStatus(0); //修改公告状态改为未发布
		announcementDao.update(announcement);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		announcementDao.deleteById(id);

		//删除相关通知
		announcementExpDao.delRelByAnnouncementId(id);
	}

	@Override
	@Transactional
	public void publish(Integer id, Integer status) {
		Announcement announcement = new Announcement();
		announcement.setId(id);
		announcement.setStatus(status);
		announcementDao.update(announcement);

		//发布后重新修改本公告提醒(除自己)
		announcementExpDao.batchUpdateExceptSelf(id, ThreadLocalMap.getUserId());
	}
}
