package com.iware.bridge.info.vo;

import com.iware.bridge.model.entity.global.Announcement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "AnnouncementNotice",description = "公告提醒")
public class AnnouncementNotice implements Serializable {

    private static final long serialVersionUID = -1177118102970511032L;

    @ApiModelProperty(value="未读数量")
    private Integer unread;
    @ApiModelProperty(value="未读数量")
    private List<Announcement> announcementList;

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    public List<Announcement> getAnnouncementList() {
        return announcementList;
    }

    public void setAnnouncementList(List<Announcement> announcementList) {
        this.announcementList = announcementList;
    }
}
