package com.iware.bridge.inspection.serivce;

import com.iware.bridge.inspection.dao.ReceiveTimeDao;
import com.iware.bridge.inspection.vo.ReceiveTime;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.exception.BusinessException;
import com.iware.common.properties.PowerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LBX
 * @date 2021-7-29
 */

@Service
public class ReceiveTimeServiceImpl implements ReceiveTimeService{

    @Autowired
    private ReceiveTimeDao receiveTimeDao;
    @Autowired
    private PowerProperties power;

    @Override
    public List<ReceiveTime> getReceiveTime(Integer projectId, Boolean isInspection) {
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();
        if(roleId == null){
            throw new BusinessException("找不到roleId");
        }
        if(roleId == 3 || roleId == 2){
            return null;
        }else{
            if(unitId==null){
                throw new BusinessException("找不到unitId");
            }
            return receiveTimeDao.selectReceiveTimeByUserAndProject(unitId, projectId,
                    isInspection ? power.getInspectionPower() : power.getMaintainPower(),
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
    }

    @Override
    public List<ReceiveTime> getReceiveTime(Integer projectId, Date beginTime, Boolean isInspection) {
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();
        if(roleId == null){
            throw new BusinessException("找不到roleId");
        }
        if(roleId == 3 || roleId == 2){
            return null;
        }else{
            if(unitId==null){
                throw new BusinessException("找不到unitId");
            }
            return receiveTimeDao.selectReceiveTimeByUserAndProject(unitId, projectId,
                    isInspection ? power.getInspectionPower() : power.getMaintainPower(),
                    new SimpleDateFormat("yyyy-MM-dd").format(beginTime));
        }
    }

    @Override
    public List<ReceiveTime> correctQueryTime(List<ReceiveTime> resTime, Date beginTime, Date endTime){
        if(beginTime == null || endTime == null){
            return resTime;
        }
        if(resTime == null){
            ArrayList<ReceiveTime> result = new ArrayList<>();
            result.add(new ReceiveTime(beginTime, endTime));
            return result;
        }
        ArrayList<ReceiveTime> result = new ArrayList<>();
        //根据开始及结束时间，与指派时间段进行与操作
        for(ReceiveTime time : resTime){
            //  |t.b    t.e|  的情况
            if(time.getBeginTime().getTime() >= beginTime.getTime()
                    && time.getEndTime().getTime() <= endTime.getTime()){
                result.add(time);
            }else{
                //  t.b|   t.e|  的情况
                if (time.getBeginTime().getTime() <= beginTime.getTime()
                        && time.getEndTime().getTime() <= endTime.getTime()
                        && time.getEndTime().getTime() >= beginTime.getTime()){
                    result.add(new ReceiveTime(beginTime, time.getEndTime()));
                }
                //  |t.b   |t.e  的情况
                if(time.getEndTime().getTime() >= endTime.getTime()
                        && time.getBeginTime().getTime() >= beginTime.getTime()
                        && time.getBeginTime().getTime() <= endTime.getTime()){
                    result.add(new ReceiveTime(time.getBeginTime(), endTime));
                }
                //  t.b|   |t.e  的情况
                if(time.getBeginTime().getTime() <= beginTime.getTime()
                        && time.getEndTime().getTime() >= endTime.getTime()){
                    result.add(new ReceiveTime(beginTime, endTime));
                }
            }
        }
        return result;
    }

    @Override
    public boolean isInReceiveTime(List<ReceiveTime> receiveTimes, Date datePoint) {
        if(receiveTimes == null){
            return false;
        }
        for(ReceiveTime receiveTime : receiveTimes){
            if(datePoint.getTime() >= receiveTime.getBeginTime().getTime()
                    && datePoint.getTime() <= receiveTime.getEndTime().getTime()){
                return true;
            }
        }
        return false;
    }
}
