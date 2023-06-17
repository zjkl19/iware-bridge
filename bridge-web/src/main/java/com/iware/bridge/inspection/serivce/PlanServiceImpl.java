package com.iware.bridge.inspection.serivce;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.inspection.dao.PlanDetailExpDao;
import com.iware.bridge.inspection.dao.PlanExpDao;
import com.iware.bridge.inspection.vo.*;
import com.iware.bridge.model.dao.global.StructureDao;
import com.iware.bridge.model.dao.inspection.InspectionDiseaseInstanceDao;
import com.iware.bridge.model.dao.inspection.MaintainItemDao;
import com.iware.bridge.model.dao.inspection.PlanDetailDao;
import com.iware.bridge.model.dao.inspection.PlanInfoDao;
import com.iware.bridge.model.dao.user.UserDao;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.inspection.InspectionDiseaseInstance;
import com.iware.bridge.model.entity.inspection.MaintainItem;
import com.iware.bridge.model.entity.inspection.PlanDetail;
import com.iware.bridge.model.entity.inspection.PlanInfo;
import com.iware.bridge.model.entity.user.User;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.exception.BusinessException;
import com.iware.common.properties.PowerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LBX
 * @date 2021-7-29
 */

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanExpDao planExpDao;
    @Autowired
    private PlanInfoDao planInfoDao;
    @Autowired
    private PlanDetailDao planDetailDao;
    @Autowired
    private PowerProperties power;
    @Autowired
    ReceiveTimeService receiveTimeService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PlanDetailExpDao planDetailExpDao;
    @Autowired
    private InspectionDiseaseInstanceDao inspectionDiseaseInstanceDao;
    @Autowired
    MaintainItemDao maintainItemDao;
    @Autowired
    StructureDao structureDao;

    @Override
    public List<PlanVO> listPlan(Integer pageNum, Integer pageSize, PlanFilter filter) {
        if(pageNum!=null&&pageSize!=null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        Integer powerId;
        if (filter.getType().equals(1)) {
            powerId = power.getInspectionPower();
        } else {
            powerId = power.getMaintainPower();
        }
        List<PlanVO> result=new PageInfo<>(planExpDao.listPlan(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), powerId, filter)).getList();
        for(PlanVO vo:result){
            if (filter.getType().equals(1)) {
                List<PlanDetail> queryResult= planDetailExpDao.selectPlanDetailOrderByTime(vo.getId());
                vo.setPlanDetailList(queryResult);
            }else{
                PlanDetailFilter detailFilter=new PlanDetailFilter();
                detailFilter.setPlanId(vo.getId());
                List<MaintainItemVO> queryResult=planDetailExpDao.
                        listMaintainItem(ThreadLocalMap.getUnitId(),
                                ThreadLocalMap.getRoleId(),
                                power.getMaintainPower(),
                                detailFilter);
                for(int i = 0; i < queryResult.size(); i++){
                    MaintainItemVO maintainItemVO = queryResult.get(i);
                    if(maintainItemVO.getInspectionDiseaseId() == null){
                        continue;
                    }
                    if(maintainItemVO.getInspectionDiseaseId() <= 15){
                        maintainItemVO.setShowItem(maintainItemVO.getQuantity() + "(" + maintainItemVO.getUnit() + ")");
                    }
                    if(maintainItemVO.getInspectionDiseaseId()>15 && maintainItemVO.getInspectionDiseaseId() <= 23){
                        maintainItemVO.setShowItem(maintainItemVO.getExceptionPart());
                    }
                    if(maintainItemVO.getInspectionDiseaseId() == 24){
                        maintainItemVO.setShowItem(null);
                    }
                    if(maintainItemVO.getInspectionDiseaseId() == 25){
                        maintainItemVO.setShowItem(maintainItemVO.getOptionName());
                    }
                    if(maintainItemVO.getInspectionDiseaseId() == 26){
                        queryResult.remove(maintainItemVO);
                        i--;
                    }
                }
                List<MaintainItemVO> zero = new ArrayList<>(queryResult);
                vo.setItemList(zero);
            }
        }
        return result;
    }

    @Transactional
    @Override
    public String addInspectionPlan(PlanVO planVO) {
        boolean isAdd=false;
        StringBuilder structureNoGrade=new StringBuilder();
        Long nowTime=new Date().getTime()-1000*60*60*24;
        PlanInfo condition=new PlanInfo();
        condition.setProjectId(planVO.getProjectId());
        condition.setName(planVO.getName());
        List<PlanInfo> queryList=new ArrayList<>();
        condition.setType(1);
        queryList.addAll(planInfoDao.query(condition));
        condition.setType(2);
        queryList.addAll(planInfoDao.query(condition));
        condition.setType(3);
        queryList.addAll(planInfoDao.query(condition));
        if(!queryList.isEmpty()) {
            throw new BusinessException("该项目当月已有计划");
        }
        planVO.setStatus(0);
        planVO.setUserId(ThreadLocalMap.getUserId());
        planInfoDao.save(planVO);
        if(planVO.getPlanDetailList()!=null&&!planVO.getPlanDetailList().isEmpty()) {
            for(int i=0;i<planVO.getPlanDetailList().size();i++) {
                Structure structure=structureDao.findById(planVO.getPlanDetailList().get(i).getStructureId());
                Integer grade = structure.getMaintainGrade();
                if(grade==null&&structure.getStructureType()==1){//桥梁，且无养护等级
                    if(structureNoGrade.length()!=0) {
                        structureNoGrade.append("、");
                    }
                    structureNoGrade.append(structure.getName());
                    continue;
                }
                isAdd=true;
                PlanDetail toSave = planVO.getPlanDetailList().get(i);
                Structure structureResult=structureDao.findById(toSave.getStructureId());
                if(structureResult!=null){
                    toSave.setLongitude(structureResult.getLongitude());
                    toSave.setLatitude(structureResult.getLatitude());
                }
                toSave.setPlanId(planVO.getId());
                if(toSave.getInspectionTime().getTime()<nowTime){
                    toSave.setStatus(-1);
                }else {
                    toSave.setStatus(0);
                }
                grade=((grade==null)?1:grade);  //处理隧道的情况，默认日期间隔为1
                Integer interval = 7;
                switch (grade) {
                    case 1:
                        interval = 1;
                        break;
                    case 2:
                        interval = 3;
                        break;
                    case 3:
                        interval = 7;
                        break;
                    default:
                }
                Date endTime = null;
                if(ThreadLocalMap.getRoleId()==2){//业主，不考虑指派时间
                    planDetailDao.save(toSave);
                    toSave.setInspectionTime(new Date(toSave.getInspectionTime().getTime() + (long) 24 * 60 * 60 * 1000 * (long) interval));
                    while (new SimpleDateFormat("yyyy-MM").format(toSave.getInspectionTime()).
                            equals(new SimpleDateFormat("yyyy-MM").format(planVO.getPlanTime()))) {
                        if (toSave.getInspectionTime().getTime() < nowTime) {
                            toSave.setStatus(-1);
                        } else {
                            toSave.setStatus(0);
                        }
                        planDetailDao.save(toSave);
                        toSave.setInspectionTime(new Date(toSave.getInspectionTime().getTime() + (long) 24 * 60 * 60 * 1000 * (long) interval));
                    }
                }else {//承接
                    List<ReceiveTime> receiveTimes = receiveTimeService.getReceiveTime(planVO.getProjectId(), toSave.getInspectionTime(), true);
                    if (receiveTimes != null && !receiveTimes.isEmpty()) {
                        endTime = receiveTimes.get(0).getEndTime();
                    }
                    if (endTime == null) {
                        throw new BusinessException("超过指派时间段");
                    }
                    if (endTime != null && toSave.getInspectionTime().getTime() >= endTime.getTime()) {
                        throw new BusinessException("本月首次巡查日期超过指派时间段");
                    }
                    planDetailDao.save(toSave);
                    toSave.setInspectionTime(new Date(toSave.getInspectionTime().getTime() + (long) 24 * 60 * 60 * 1000 * (long) interval));
                    while (new SimpleDateFormat("yyyy-MM").format(toSave.getInspectionTime()).
                            equals(new SimpleDateFormat("yyyy-MM").format(planVO.getPlanTime()))) {
                        if (endTime != null && toSave.getInspectionTime().getTime() > endTime.getTime()) {   //超出指派时间
                            if (receiveTimes.size() > 1 &&
                                    new SimpleDateFormat("yyyy-MM").format(receiveTimes.get(1).getBeginTime())
                                            .equals(new SimpleDateFormat("yyyy-MM").format(toSave.getInspectionTime()))) {
                                while (receiveTimes.get(1).getBeginTime().getTime() > toSave.getInspectionTime().getTime()) {
                                    toSave.setInspectionTime(new Date(toSave.getInspectionTime().getTime() + (long) 24 * 60 * 60 * 1000 * (long) interval));
                                }
                            } else {
                                break;
                            }
                        }
                        if (toSave.getInspectionTime().getTime() < nowTime) {
                            toSave.setStatus(-1);
                        } else {
                            toSave.setStatus(0);
                        }
                        planDetailDao.save(toSave);
                        toSave.setInspectionTime(new Date(toSave.getInspectionTime().getTime() + (long) 24 * 60 * 60 * 1000 * (long) interval));
                    }
                }
            }
        }
        if(!isAdd){
            planInfoDao.deleteById(planVO.getId());
        }
        if(structureNoGrade.length()!=0){
            structureNoGrade.append("\n无养护等级信息,无法生成细项计划");
        }
        return structureNoGrade.toString();
    }

    @Override
    public String updInspectionPlan( PlanVO planVO) {
        boolean isAdd=false;
        StringBuilder structureNoGrade=new StringBuilder();
        Long nowTime=new Date().getTime()-1000*60*60*24;
        PlanInfo condition=new PlanInfo();
        condition.setProjectId(planVO.getProjectId());
        condition.setName(planVO.getName());
        List<PlanInfo> queryList=new ArrayList<>();
        condition.setType(1);
        queryList.addAll(planInfoDao.query(condition));
        condition.setType(2);
        queryList.addAll(planInfoDao.query(condition));
        condition.setType(3);
        queryList.addAll(planInfoDao.query(condition));
        if(!queryList.isEmpty()) {
            for(PlanInfo planInfo:queryList){
                if(!planInfo.getId().equals(planVO.getId())){
                    throw new BusinessException("该项目当月已有计划");
                }
            }
        }
        if(planVO.getBudget()==null){
            planVO.setBudget(new BigDecimal(0));
        }
        if(planVO.getExpenditure()==null){
            planVO.setExpenditure(new BigDecimal(0));
        }
        PlanDetail condition1=new PlanDetail();
        condition1.setPlanId(planVO.getId());
        List<PlanDetail> toDelete=planDetailDao.query(condition1);
        if(planVO.getPlanDetailList()!=null&&!planVO.getPlanDetailList().isEmpty()) {
            for(int i=0;i<planVO.getPlanDetailList().size();i++) {
                Structure structure=structureDao.findById(planVO.getPlanDetailList().get(i).getStructureId());
                Integer grade = structure.getMaintainGrade();
                if(grade==null&&structure.getStructureType()==1){//桥梁，且无养护等级
                    if(structureNoGrade.length()!=0) {
                        structureNoGrade.append("、");
                    }
                    structureNoGrade.append(structure.getName());
                    continue;
                }
                isAdd=true;
                PlanDetail toSave = planVO.getPlanDetailList().get(i);
                Structure structureResult = structureDao.findById(toSave.getStructureId());
                if (structureResult != null) {
                    toSave.setLongitude(structureResult.getLongitude());
                    toSave.setLatitude(structureResult.getLatitude());
                }
                toSave.setPlanId(planVO.getId());
                if (toSave.getInspectionTime().getTime() < nowTime) {
                    toSave.setStatus(-1);
                } else {
                    toSave.setStatus(0);
                }
                grade=((grade==null)?1:grade);  //处理隧道的情况，默认日期间隔为1
                Integer interval = 7;
                switch (grade) {
                    case 1:
                        interval = 1;
                        break;
                    case 2:
                        interval = 3;
                        break;
                    case 3:
                        interval = 7;
                        break;
                    default:
                }
                Date endTime = null;
                if (ThreadLocalMap.getRoleId() == 2) {//业主，不考虑指派时间
                    planDetailDao.save(toSave);
                    toSave.setInspectionTime(new Date(toSave.getInspectionTime().getTime() + (long) 24 * 60 * 60 * 1000 * (long) interval));
                    while (new SimpleDateFormat("yyyy-MM").format(toSave.getInspectionTime()).
                            equals(new SimpleDateFormat("yyyy-MM").format(planVO.getPlanTime()))) {
                        if (toSave.getInspectionTime().getTime() < nowTime) {
                            toSave.setStatus(-1);
                        } else {
                            toSave.setStatus(0);
                        }
                        planDetailDao.save(toSave);
                        toSave.setInspectionTime(new Date(toSave.getInspectionTime().getTime() + (long) 24 * 60 * 60 * 1000 * (long) interval));
                    }
                } else {
                    List<ReceiveTime> receiveTimes = receiveTimeService.getReceiveTime(planVO.getProjectId(), toSave.getInspectionTime(), true);
                    if (receiveTimes != null && !receiveTimes.isEmpty()) {
                        endTime = receiveTimes.get(0).getEndTime();
                    }
                    if (endTime == null) {
                        throw new BusinessException("超过指派时间段");
                    }
                    if (endTime != null && toSave.getInspectionTime().getTime() >= endTime.getTime()) {
                        throw new BusinessException("本月首次巡查日期超过指派时间段");
                    }
                    planDetailDao.save(toSave);
                    toSave.setInspectionTime(new Date(toSave.getInspectionTime().getTime() + (long) 24 * 60 * 60 * 1000 * (long) interval));
                    while (new SimpleDateFormat("yyyy-MM").format(toSave.getInspectionTime()).
                            equals(new SimpleDateFormat("yyyy-MM").format(planVO.getPlanTime()))) {
                        if (endTime != null && toSave.getInspectionTime().getTime() > endTime.getTime()) {   //超出指派时间
                            if (receiveTimes.size() > 1 &&
                                    new SimpleDateFormat("yyyy-MM").format(receiveTimes.get(1).getBeginTime())
                                            .equals(new SimpleDateFormat("yyyy-MM").format(toSave.getInspectionTime()))) {
                                while (receiveTimes.get(1).getBeginTime().getTime() > toSave.getInspectionTime().getTime()) {
                                    toSave.setInspectionTime(new Date(toSave.getInspectionTime().getTime() + (long) 24 * 60 * 60 * 1000 * (long) interval));
                                }
                            } else {
                                break;
                            }
                        }
                        if (toSave.getInspectionTime().getTime() < nowTime) {
                            toSave.setStatus(-1);
                        } else {
                            toSave.setStatus(0);
                        }
                        planDetailDao.save(toSave);
                        toSave.setInspectionTime(new Date(toSave.getInspectionTime().getTime() + (long) 24 * 60 * 60 * 1000 * (long) interval));
                    }
                }
            }
        }
        if(isAdd) {
            for (PlanDetail del : toDelete) {
                planDetailDao.deleteById(del.getId());
            }
            planInfoDao.update(planVO);
        }
        if(structureNoGrade.length()!=0){
            structureNoGrade.append("\n无养护等级信息,无法生成细项计划");
        }
        return structureNoGrade.toString();
    }

    @Override
    public void delInspectionPlan(Integer planId) {
        planInfoDao.deleteById(planId);
        PlanDetail condition=new PlanDetail();
        condition.setPlanId(planId);
        List<PlanDetail> toDelete=planDetailDao.query(condition);
        for(PlanDetail del:toDelete){
            planDetailDao.deleteById(del.getId());
        }
    }

    @Override
    public void addMaintainPlan(PlanVO planVO) {
        PlanInfo condition=new PlanInfo();
        condition.setProjectId(planVO.getProjectId());
        condition.setName(planVO.getName());
        condition.setType(4);
        List<PlanInfo> queryList=planInfoDao.query(condition);
        if(!queryList.isEmpty()){
            throw new BusinessException("该项目当月已有计划");
        }
        planVO.setStatus(0);
        planVO.setType(4);
        planVO.setUserId(ThreadLocalMap.getUserId());
        planInfoDao.save(planVO);
        User user=userDao.findById(ThreadLocalMap.getUserId());
        for(MaintainItem maintainItem:planVO.getItemList()){
            maintainItem.setPlanId(planVO.getId());
            maintainItem.setCreator(user.getRealName());
            if(new SimpleDateFormat("yyyy").format(maintainItem.getProposedTime()).equals(new SimpleDateFormat("yyyy").format(new Date()))) {
                if(
                        Integer.parseInt(
                                new SimpleDateFormat("MM").format(maintainItem.getProposedTime())
                        ) <
                                Integer.parseInt(
                                        new SimpleDateFormat("MM").format(new Date())
                                )
                ){
                    maintainItem.setStatus(2);
                }else{
                    maintainItem.setStatus(0);
                }
            }else{
                if(
                        Integer.parseInt(
                                new SimpleDateFormat("yyyy").format(maintainItem.getProposedTime())
                        ) <
                                Integer.parseInt(
                                        new SimpleDateFormat("yyyy").format(new Date())
                                )
                ){
                    maintainItem.setStatus(2);
                }else{
                    maintainItem.setStatus(0);
                }
            }
            List<ReceiveTime> receiveTimes=receiveTimeService.getReceiveTime(planVO.getProjectId(),false);
            if((ThreadLocalMap.getRoleId()==0||ThreadLocalMap.getRoleId()==1)&&!receiveTimeService.isInReceiveTime(receiveTimes,maintainItem.getProposedTime())){
                planInfoDao.deleteById(planVO.getId());
                throw new BusinessException("维养细项拟定维修日期超出指派时间段");
            }
            if(maintainItem.getDiseaseInstanceId()!=null) {
                Integer diseaseId = maintainItem.getDiseaseInstanceId();
                InspectionDiseaseInstance diseaseInstance = inspectionDiseaseInstanceDao.findById(diseaseId);
                diseaseInstance.setStatus(1);
                inspectionDiseaseInstanceDao.update(diseaseInstance);
            }
            maintainItemDao.save(maintainItem);
        }
    }

    @Override
    public void updMaintainPlan(PlanVO planVO) {
        if(planVO.getBudget()==null){
            planVO.setBudget(new BigDecimal(0));
        }
        if(planVO.getExpenditure()==null){
            planVO.setExpenditure(new BigDecimal(0));
        }
        planInfoDao.update(planVO);
        MaintainItem condition=new MaintainItem();
        condition.setPlanId(planVO.getId());
        List<MaintainItem> toDeleteMaintain=maintainItemDao.query(condition);
        for(MaintainItem item:toDeleteMaintain){
            InspectionDiseaseInstance instance=new InspectionDiseaseInstance();
            instance.setId(item.getDiseaseInstanceId());
            instance.setStatus(0);
            inspectionDiseaseInstanceDao.update(instance);
            maintainItemDao.deleteById(item.getId());
        }
        for(MaintainItem maintainItem:planVO.getItemList()){
            maintainItem.setPlanId(planVO.getId());
            if(new SimpleDateFormat("yyyy").format(maintainItem.getProposedTime()).equals(new SimpleDateFormat("yyyy").format(new Date()))) {
                if(
                        Integer.parseInt(
                                new SimpleDateFormat("MM").format(maintainItem.getProposedTime())
                        ) <
                                Integer.parseInt(
                                        new SimpleDateFormat("MM").format(new Date())
                                )
                ){
                    maintainItem.setStatus(2);
                }else{
                    maintainItem.setStatus(0);
                }
            }else{
                if(
                        Integer.parseInt(
                                new SimpleDateFormat("yyyy").format(maintainItem.getProposedTime())
                        ) <
                                Integer.parseInt(
                                        new SimpleDateFormat("yyyy").format(new Date())
                                )
                ){
                    maintainItem.setStatus(2);
                }else{
                    maintainItem.setStatus(0);
                }
            }
            if(ThreadLocalMap.getRoleId()==2||ThreadLocalMap.getRoleId()==3) {

            }else {
                if (maintainItem.getProposedTime().getTime() > receiveTimeService.getReceiveTime(planVO.getProjectId(), false).get(0).getEndTime().getTime()) {
                    throw new BusinessException("维养细项拟定维修日期超出指派时间段");
                }
            }
            if(maintainItem.getDiseaseInstanceId() != null) {
                Integer diseaseId = maintainItem.getDiseaseInstanceId();
                InspectionDiseaseInstance diseaseInstance = inspectionDiseaseInstanceDao.findById(diseaseId);
                diseaseInstance.setStatus(1);
                inspectionDiseaseInstanceDao.update(diseaseInstance);
            }
            maintainItemDao.save(maintainItem);
        }
    }

    @Override
    public void delMaintainPlan(Integer planId) {
        planInfoDao.deleteById(planId);
        MaintainItem condition=new MaintainItem();
        condition.setPlanId(planId);
        List<MaintainItem> toDeleteMaintain=maintainItemDao.query(condition);
        for(MaintainItem item:toDeleteMaintain){
            InspectionDiseaseInstance instance=new InspectionDiseaseInstance();
            instance.setId(item.getDiseaseInstanceId());
            instance.setStatus(0);
            inspectionDiseaseInstanceDao.update(instance);
            maintainItemDao.deleteById(item.getId());
        }
    }

    @Override
    public List<PlanStructureVO> listPlanStructure(Integer projectId, Integer year) {
        Integer unitId;
        Integer roleId;
        Integer powerId;
        unitId=ThreadLocalMap.getUnitId();
        roleId=ThreadLocalMap.getRoleId();
        powerId=power.getInspectionPower();
        if(roleId == null){
            throw new BusinessException("登录信息缺失");
        }else{
            if ((roleId==0||roleId==1)&&unitId == null){
                throw new BusinessException("登录信息缺失");
            }
        }
        String conditionYear=year+"";
        if(year==0){
            conditionYear=null;
        }
        List<PlanStructureVO> result= planExpDao.listPlanStructure(projectId,conditionYear,unitId,roleId,powerId);
        return result;
    }

    @Override
    public Date selPlanFirstDate(Integer structureId,Integer projectId, Date month) {
        Date lastDate=planExpDao.selPlanLastDate(structureId,new SimpleDateFormat("yyyy-MM").format(month));
        Structure structure=structureDao.findById(structureId);
        if(structure==null){
            throw new BusinessException("寻找不到该桥梁");
        }
        Integer grade= structure.getMaintainGrade();
        Date result=lastDate;
        Integer interval=7;
        grade = (grade==null?1:grade);
        switch (grade){
            case 1:
                interval=1;
                break;
            case 2:
                interval=3;
                break;
            case 3:
                interval=7;
                break;
            default:
        }
        List<ReceiveTime> receiveTime=receiveTimeService.getReceiveTime(projectId,month,true);
        if(result!=null) {
            while (!new SimpleDateFormat("yyyy-MM").format(result).
                    equals(new SimpleDateFormat("yyyy-MM").format(month))) {
                result.setTime(result.getTime() + (long) 24 * 60 * 60 * 1000 * (long) interval);
            }
            if(ThreadLocalMap.getRoleId()==0||ThreadLocalMap.getRoleId()==1) {
                if (receiveTime.size()==0){
                    return null;
                }
                if (result.getTime() > receiveTime.get(0).getEndTime().getTime()) {
                    result = receiveTime.get(0).getEndTime();
                }
            }
        }
        if(result==null){
            result=month;
        }
        if(ThreadLocalMap.getRoleId()==2||ThreadLocalMap.getRoleId()==3){
            return result;
        }
        if(result.getTime()<receiveTime.get(0).getBeginTime().getTime()){
            return new SimpleDateFormat("yyyy-MM")
                    .format(receiveTime.get(0).getBeginTime()
                    )
                    .equals(new SimpleDateFormat("yyyy-MM")
                            .format(month)
                    )?receiveTime.get(0).getBeginTime():null;
        }
        return result;
    }

    @Override
    public void resetPlanStatus(){
        resetPlanDetailStatus();
        resetMaintainItemStatus();
        resetPlanInfoStatus();
    }

    @Override
    public void resetPlanDetailStatus(){
        planDetailExpDao.resetPlanDetailStatus(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    @Override
    public void resetMaintainItemStatus(){
        planDetailExpDao.resetMaintainItemStatus(new SimpleDateFormat("yyyy-MM").format(new Date()));
    }

    @Override
    public void resetPlanInfoStatus() {
        planExpDao.resetPlanInfoStatus();
    }

    @Override
    public void updatePlanAmount(PlanAmountMap map) {
        PlanInfo toUpdate=new PlanInfo();
        toUpdate.setId(map.getPlanId());
        toUpdate.setBudget(map.getBudget());
        toUpdate.setExpenditure(map.getExpenditure());
        planInfoDao.update(toUpdate);
    }
}
