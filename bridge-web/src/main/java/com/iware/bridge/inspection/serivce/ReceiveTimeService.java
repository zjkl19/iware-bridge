package com.iware.bridge.inspection.serivce;

import com.iware.bridge.inspection.vo.ReceiveTime;

import java.util.Date;
import java.util.List;

/**
 * @author LBX
 * @date 2021-7-29
 */

public interface ReceiveTimeService {
    public List<ReceiveTime> getReceiveTime(Integer projectId,Boolean isInspection);

    public List<ReceiveTime> getReceiveTime(Integer projectId,Date beginTime,Boolean isInspection);

    public List<ReceiveTime> correctQueryTime(List<ReceiveTime> resTime, Date beginTime, Date endTime);

    public boolean isInReceiveTime(List<ReceiveTime> receiveTimes,Date datePoint);
}
