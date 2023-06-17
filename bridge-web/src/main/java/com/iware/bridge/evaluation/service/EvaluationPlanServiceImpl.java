package com.iware.bridge.evaluation.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.evaluation.dao.MonitorPlanExpDao;
import com.iware.bridge.evaluation.dao.MonitorPlanStructureRelExpDao;
import com.iware.bridge.evaluation.vo.*;
import com.iware.bridge.info.dao.PowerExpDao;
import com.iware.bridge.inspection.vo.ReceiveTime;
import com.iware.bridge.model.dao.evaluation.MonitorPlanDao;
import com.iware.bridge.model.dao.evaluation.MonitorPlanStructureRelDao;
import com.iware.bridge.model.entity.evaluation.MonitorPlan;
import com.iware.bridge.model.entity.evaluation.MonitorPlanStructureRel;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.FileTypeEnum;
import com.iware.common.pojo.FileData;
import com.iware.common.properties.PowerProperties;
import com.iware.common.utils.FileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WJP
 * @date 2021-8-16
 */

@Service
public class EvaluationPlanServiceImpl implements EvaluationPlanService {

    @Autowired
    private MonitorPlanExpDao monitorPlanExpDao;
    @Autowired
    private MonitorPlanDao monitorPlanDao;
    @Autowired
    private MonitorPlanStructureRelDao monitorPlanStructureRelDao;
    @Autowired
    private MonitorPlanStructureRelExpDao monitorPlanStructureRelExpDao;
    @Autowired
    private EvaluationAnalysisService evaluationAnalysisService;
    @Value("${file.upload-path}")
    private String path;

    @Autowired
    private PowerExpDao powerExpDao;
    @Autowired
    private PowerProperties powerProperties;

    @Override
    public PageInfo<MonitorPlanListVO> listPlan(Integer pageNum, Integer pageSize, PlanFilter filter) {
        List<AppointProjectVO> projects = evaluationAnalysisService.getProjects();
        PageHelper.startPage(pageNum, pageSize);
        List<MonitorPlanListVO> list = new ArrayList<>();
        list = monitorPlanExpDao.selectPlanList(ThreadLocalMap.getUnitId(), ThreadLocalMap.getRoleId(), filter, projects);
        return new PageInfo<>(list);
    }

    @Override
    public List<MonitorPlanStructureRel> getStructureRel(Integer planId) {
        List<MonitorPlanStructureRel> monitorPlanStructureRels = monitorPlanStructureRelExpDao.selectNoUrl(planId);
        return monitorPlanStructureRels;
    }

    @Override
    public void addMonitorPlan(MonitorPlanVO planVO, MultipartFile[] files) {
        planVO.setCreateTime(new Date());
        planVO.setCreateUserId(ThreadLocalMap.getUserId());
        Date date = new Date(); //取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1); //把日期往后增加一天,整数  往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果

        if (planVO.getEndTime().before(date)) {
            planVO.setStatus(4);
        } else {
            planVO.setStatus(1);
        }
        monitorPlanDao.save(planVO);
        Integer index = 0;
        for (MonitorStructureRelVO monitorStructureRelVO : planVO.getStructureRelList()) {
            if (monitorStructureRelVO.getFileList().size() > 0) {
                if ("-".equals(monitorStructureRelVO.getFileName())) {
                    continue;
                }
                MonitorPlanStructureRel monitorPlanStructureRel = new MonitorPlanStructureRel();
                monitorPlanStructureRel = uploadFiles(files[index]);
                monitorStructureRelVO.setFileUrl(monitorPlanStructureRel.getFileUrl());
                index++;
            }
            monitorStructureRelVO.setMonitorPlanId(planVO.getId());
            monitorPlanStructureRelDao.save(monitorStructureRelVO);
        }
    }

    @Override
    public void updMonitorPlan(MonitorPlanVO planVO, MultipartFile[] files) {
        Date date = new Date(); //取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1); //把日期往后增加一天,整数  往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果

        if (planVO.getEndTime().before(date)) {
            planVO.setStatus(4);
        } else {
            planVO.setStatus(1);
        }
        monitorPlanDao.update(planVO);
        MonitorPlanStructureRel query = new MonitorPlanStructureRel();
        query.setMonitorPlanId(planVO.getId());
        List<MonitorPlanStructureRel> list = monitorPlanStructureRelDao.query(query);
        List<Integer> newList = planVO.getStructureRelList().stream().map(MonitorPlanStructureRel::getId).collect(Collectors.toList());
        List<Integer> oldList = list.stream().map(MonitorPlanStructureRel::getId).collect(Collectors.toList());
        for (MonitorPlanStructureRel monitorPlanStructureRel : list) {
            //删除
            if (!newList.contains(monitorPlanStructureRel.getId())) {
                monitorPlanStructureRelDao.deleteById(monitorPlanStructureRel.getId());
                //删除文件
                delFile(monitorPlanStructureRel.getFileUrl());
            }
        }
        for (MonitorStructureRelVO monitorStructureRelVO : planVO.getStructureRelList()) {
            //添加
            if (!oldList.contains(monitorStructureRelVO.getId())) {
                if (!"-".equals(monitorStructureRelVO.getFileName())) {
                    //添加文件
                    for (MultipartFile file : files) {
                        if (monitorStructureRelVO.getFileName().equals(file.getOriginalFilename())) {
                            String path = null;
                            try {
                                path = uploadFile(file, monitorStructureRelVO.getPath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            monitorStructureRelVO.setFileUrl(path);
                            break;
                        }
                    }
                }
                monitorStructureRelVO.setMonitorPlanId(planVO.getId());
                monitorPlanStructureRelDao.save(monitorStructureRelVO);
                //修改
            } else {
                for (MonitorPlanStructureRel monitorPlanStructureRel : list) {
                    if (monitorStructureRelVO.getStructureName().equals(monitorPlanStructureRel.getStructureName()) && !"-".equals(monitorStructureRelVO.getFileName())) {
                        if (monitorStructureRelVO.getPath() == null) {
                            continue;
                        }
                        //替换
                        String[] str = null;
                        if (monitorPlanStructureRel.getFileUrl() != null) {
                            str = monitorPlanStructureRel.getFileUrl().split("/");
                        }
                        if (!monitorStructureRelVO.getFileName().equals(monitorPlanStructureRel.getFileName())) {
                            //删除文件
                            delFile(monitorPlanStructureRel.getFileUrl());
                            //添加文件
                            for (MultipartFile file : files) {
                                if (monitorStructureRelVO.getFileName().equals(file.getOriginalFilename())) {
                                    String path = null;
                                    try {
                                        path = uploadFile(file, monitorStructureRelVO.getPath());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    monitorStructureRelVO.setFileUrl(path);
                                    break;
                                }
                            }
                            monitorPlanStructureRelDao.update(monitorStructureRelVO);
                        } else if (str != null && str.length > 1) {
                            if (!str[str.length - 1].equals(monitorStructureRelVO.getPath())) {
                                //删除文件
                                delFile(monitorPlanStructureRel.getFileUrl());
                                //添加文件
                                for (MultipartFile file : files) {
                                    if (monitorStructureRelVO.getFileName().equals(file.getOriginalFilename())) {
                                        String path = null;
                                        try {
                                            path = uploadFile(file, monitorStructureRelVO.getPath());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        monitorStructureRelVO.setFileUrl(path);
                                        break;
                                    }
                                }
                                monitorPlanStructureRelDao.update(monitorStructureRelVO);
                            }
                        }

                    }
                }
            }
        }
    }

    @Override
    public void delMonitorPlan(Integer planId) {
        monitorPlanDao.deleteById(planId);
        monitorPlanStructureRelExpDao.deleteStructure(planId);
    }

    @Override
    public MonitorPlanStructureRel uploadFiles(MultipartFile file) {
        MonitorPlanStructureRel rel = new MonitorPlanStructureRel();
        FileData fileInfo = FileUtil.uploadFile(file, FileTypeEnum.EVALUATION_PROGRAMME, null);
        rel.setFileName(fileInfo.getName());
        rel.setFileUrl(fileInfo.getFilePath());
        return rel;
    }

    @Override
    public void addStructureRel(MonitorStructureRelVO monitorStructureRelVO, MultipartFile file) {
        if (monitorStructureRelVO.getFileList().size() > 0) {
            MonitorPlanStructureRel monitorPlanStructureRel = uploadFiles(file);
            monitorStructureRelVO.setFileUrl(monitorPlanStructureRel.getFileUrl());
        }
        MonitorPlanStructureRel monitorPlanStructureRel = new MonitorPlanStructureRel();
        BeanUtils.copyProperties(monitorStructureRelVO, monitorPlanStructureRel);
        monitorPlanStructureRelDao.save(monitorPlanStructureRel);
    }

    @Override
    public void updStructureRel(MonitorStructureRelVO monitorStructureRelVO, MultipartFile file) {
        if (monitorStructureRelVO.getFileList().size() > 0) {
            MonitorPlanStructureRel monitorPlanStructureRel = uploadFiles(file);
            monitorStructureRelVO.setFileUrl(monitorPlanStructureRel.getFileUrl());
        }
        MonitorPlanStructureRel monitorPlanStructureRel = new MonitorPlanStructureRel();
        BeanUtils.copyProperties(monitorStructureRelVO, monitorPlanStructureRel);
        monitorPlanStructureRelDao.update(monitorPlanStructureRel);
    }

    @Override
    public void delStructureRel(Integer id) {
        monitorPlanStructureRelDao.deleteById(id);
    }

    @Override
    public MonitorPlanStructureRel selectFilePath(Integer structureRelId) {
        MonitorPlanStructureRel monitorPlanStructureRel = new MonitorPlanStructureRel();
        monitorPlanStructureRel.setId(structureRelId);
        List<MonitorPlanStructureRel> query = monitorPlanStructureRelDao.query(monitorPlanStructureRel);
        if (query.size() != 0) {
            return query.get(0);
        }
        return null;
    }

    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        //存储地址
        String str = "evaluation/programme/" + format + "/" + fileName;
        String dest = path + str;
        File newFile = new File(dest);
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        File tempFile = null;
        tempFile = new File(dest);
        //生成文件
        FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
        return str;
    }

    public void delFile(String filePath) {
        File file = new File(path + filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public List<ReceiveTime> getAppointTime(Integer projectId) {
        Integer roleId = ThreadLocalMap.getRoleId();
        List<ReceiveTime> appointTime = new ArrayList<>();
        if (roleId == 0 || roleId == 1) {
            appointTime = powerExpDao.getAppointTime(ThreadLocalMap.getUnitId(), projectId, powerProperties.getEvaluationPower());
        }
        return appointTime;
    }

    @Override
    public void planTimeout() {
        MonitorPlan monitorPlan = new MonitorPlan();
        monitorPlan.setStatus(1);
        List<MonitorPlan> monitorPlanList = monitorPlanDao.query(monitorPlan);
        for (MonitorPlan plan : monitorPlanList) {
            Date endTime = plan.getEndTime();
            Date date = new Date(); //取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, -1); //把日期往后增加一天,整数  往后推,负数往前移动
            date = calendar.getTime(); //这个时间就是日期往后推一天的结果


            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            //set hour min
            int hour = 23;
            int min = 59;
            int sec = 59;
            calendar.set(year,month,day,hour,min,sec);
            Date setDate = calendar.getTime();
            if (endTime.before(setDate)) {
                plan.setStatus(4);
                monitorPlanDao.update(plan);
            }
        }
    }
}
