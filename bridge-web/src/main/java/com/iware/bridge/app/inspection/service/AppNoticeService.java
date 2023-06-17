package com.iware.bridge.app.inspection.service;

import com.iware.bridge.inspection.vo.AppNoticeVo;
import com.iware.bridge.model.entity.user.User;

import java.util.List;

public interface AppNoticeService {

    public List<AppNoticeVo> getNotice(Integer pageNum,Integer pageSize);

    public Integer getNoticeSize();

    public void saveNoticeByUserList(List<User> userList, String content);
}
