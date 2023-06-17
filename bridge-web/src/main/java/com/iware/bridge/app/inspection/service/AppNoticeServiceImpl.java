package com.iware.bridge.app.inspection.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.app.assess.service.LoginUserService;
import com.iware.bridge.info.dao.NoticeExpDao;
import com.iware.bridge.inspection.vo.AppNoticeVo;
import com.iware.bridge.model.dao.global.AppNoticeDao;
import com.iware.bridge.model.dao.global.UserNoticeRelDao;
import com.iware.bridge.model.entity.global.AppNotice;
import com.iware.bridge.model.entity.global.UserNoticeRel;
import com.iware.bridge.model.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lbx
 * 2021-10-29
 */

@Service
public class AppNoticeServiceImpl implements AppNoticeService{

    @Autowired
    private LoginUserService loginUserServ;
    @Autowired
    private AppNoticeDao appNoticeDao;
    @Autowired
    private UserNoticeRelDao userNoticeRelDao;
    @Resource
    private NoticeExpDao noticeExpDao;

    @Override
    public List<AppNoticeVo> getNotice(Integer pageNum,Integer pageSize) {
        User loginUser = loginUserServ.getUser();
        Integer userId=loginUser.getId();
        PageHelper.startPage(pageNum,pageSize);
        noticeExpDao.updateRelStatus(userId);
        List<AppNoticeVo> result=new PageInfo<>(noticeExpDao.selectNoticeByUserId(userId,null)).getList();
        for(AppNoticeVo vo:result){
            String oriContent=vo.getNoticeContent();
            String offlineTime=oriContent.substring(oriContent.indexOf("【离线通知】")+6,oriContent.indexOf("，\""));
            String projectName=oriContent.substring(oriContent.indexOf("，\"")+2,oriContent.indexOf("项目\" 中的 \""));
            String structureName=oriContent.substring(oriContent.indexOf("项目\" 中的 \"")+8,oriContent.indexOf("结构物\" ，测点编号为 \""));
            String sensorCode=oriContent.substring(oriContent.indexOf("测点编号为 \"")+7,oriContent.indexOf("\" 的传感器"));
            String resContent=oriContent.substring(oriContent.indexOf("传感器近"));
            vo.setOfflineTime(offlineTime);
            vo.setProjectName(projectName);
            vo.setStructureName(structureName);
            vo.setSensorCode(sensorCode);
            vo.setNoticeContent(resContent);
        }
        return result;
    }

    @Override
    public Integer getNoticeSize() {
        User loginUser = loginUserServ.getUser();
        Integer userId=loginUser.getId();
        return noticeExpDao.selectNoticeSizeByUserId(userId,0);
    }

    @Override
    @Transactional
    public void saveNoticeByUserList(List<User> userList, String content) {

        List<UserNoticeRel> userNoticeRelList = new ArrayList<>();
        //离线测点通知
        AppNotice notice=new AppNotice();
        notice.setTitle("离线通知");
        notice.setStatus(1);
        notice.setContent(content);

        if (!CollectionUtils.isEmpty(userList)) {
            appNoticeDao.save(notice);
            for (User user : userList) {
                UserNoticeRel userNoticeRel=new UserNoticeRel();
                userNoticeRel.setStatus(0);
                userNoticeRel.setNoticeId(notice.getId());
                userNoticeRel.setUserId(user.getId());
                userNoticeRelList.add(userNoticeRel);
            }
            userNoticeRelDao.batchSave(userNoticeRelList);
        }
    }
}
