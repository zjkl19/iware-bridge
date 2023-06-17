package com.iware.bridge.inspection.vo;

import java.util.Date;

public class ReceiveTime {
    private Date beginTime;
    private Date endTime;

    public ReceiveTime() {

    }

    public ReceiveTime(Date beginTime, Date endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
