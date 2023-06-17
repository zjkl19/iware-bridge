package com.iware.bridge.online.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iware.bridge.app.inspection.service.AppNoticeService;
import com.iware.bridge.info.dao.UserExpDao;
import com.iware.bridge.model.dao.global.ComponentInfoDao;
import com.iware.bridge.model.dao.online.*;
import com.iware.bridge.model.entity.online.MeansPoint;
import com.iware.bridge.model.entity.online.Sensor;
import com.iware.bridge.model.entity.user.User;
import com.iware.bridge.online.dao.SensorExpDao;
import com.iware.bridge.online.dto.SensorTreeDto;
import com.iware.bridge.online.vo.*;
import com.iware.cache.redis.RedisUtil;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.FileTypeEnum;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.enums.SensorStatusEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.pojo.FileData;
import com.iware.common.properties.PowerProperties;
import com.iware.common.spring.SpringContextHolder;
import com.iware.common.utils.DateUtils;
import com.iware.common.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static com.iware.bridge.online.utils.OnlineUtils.*;

/**
 * @author ZRB
 * @date 2021-7-27
 */

@Service
public class SensorServiceImpl implements SensorService {

    @Autowired
    private PowerProperties power;
    @Autowired
    private MeansPointDao meansPointDao;
    @Autowired
    private SensorDao sensorDao;
    @Autowired
    private SensorExpDao sensorExpDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private SensorTypeDao sensorTypeDao;
    @Autowired
    private SensorDetailsDao sensorDetailsDao;
    @Autowired
    private ComponentInfoDao componentInfoDao;
    @Autowired
    private SensorPrincipleDao sensorPrincipleDao;
    @Autowired
    private UserExpDao userExpDao;
    @Autowired
    private MailService mailService;
    @Autowired
    private AppNoticeService appNoticeService;
    @Autowired
    private RedisUtil redisUtil;


    @Override
    public PageInfo<MeansPointVO> listSensor(Integer pageNum, Integer pageSize, SensorFilter filter) {
        PageHelper.startPage(pageNum, pageSize);
        List<MeansPointVO> sensorList = sensorExpDao.selectSensorList(ThreadLocalMap.getRoleId(),
                ThreadLocalMap.getUnitId(), power.getOnlinePower(), filter);
        return new PageInfo<>(sensorList);
    }

    @Override
    @Transactional
    public Integer addSensor(MeansPointVO meansPointVO, MultipartFile file) {

        this.checkSensorExist(meansPointVO);

        List<Sensor> sensorList = new ArrayList<>();
        sensorList.addAll(meansPointVO.getSensorVOList());

        if (file != null && !file.isEmpty()) {
            FileData fileInfo = FileUtil.uploadFile(file, FileTypeEnum.SENSOR_PHOTO.getCode());
            meansPointVO.setPhotoUrl(fileInfo.getFilePath());
        }
        meansPointDao.save(meansPointVO);

        if (!CollectionUtils.isEmpty(sensorList)) {
            sensorList.stream().forEach(item -> {
                    item.setMeansPointId(meansPointVO.getId());
                    item.setStatus(1);
            });
        }

        sensorDao.batchSave(sensorList);
        this.updateRedisSensor(meansPointVO, meansPointVO.getSensorVOList(), 1);
        return meansPointVO.getId();
    }

    @Override
    public void updSensor(MeansPointVO meansPointVO, MultipartFile file) {

        List<Sensor> addList = new ArrayList<>();
        List<Sensor> updList = new ArrayList<>();
        List<Sensor> delList = new ArrayList<>();
        List<SensorVO> sensorVOList = meansPointVO.getSensorVOList();

        this.checkSensorExist(meansPointVO);

        MeansPoint oldMeansPoint = meansPointDao.findById(meansPointVO.getId());
        if (file != null && !file.isEmpty()) {

            if (oldMeansPoint.getPhotoUrl() != null)
                FileUtil.delFileData(oldMeansPoint.getPhotoUrl());

            FileData fileInfo = FileUtil.uploadFile(file, FileTypeEnum.SENSOR_PHOTO.getCode());
            meansPointVO.setPhotoUrl(fileInfo.getFilePath());
        } else if(StringUtils.isEmpty(meansPointVO.getPhotoUrl())) {
            FileUtil.delFileData(oldMeansPoint.getPhotoUrl());
            sensorExpDao.deletePhotoUrl(meansPointVO.getId());
        }

        sensorExpDao.updateMeansPoint(meansPointVO);


        Sensor sensorQry = new Sensor();
        sensorQry.setMeansPointId(meansPointVO.getId());
        List<Sensor> oldSensorList = sensorDao.query(sensorQry);

        //筛选要添加、修改、删除的传感器
        if (!CollectionUtils.isEmpty(oldSensorList) && !CollectionUtils.isEmpty(sensorVOList)) {

            List<Integer> oldIds = oldSensorList.stream().map(Sensor::getId).collect(Collectors.toList());
            List<String> oldCodingList = oldSensorList.stream().map(Sensor::getSensorCoding).collect(Collectors.toList());
            for (SensorVO sensorVO : sensorVOList) {
                sensorVO.setMeansPointId(meansPointVO.getId());
                if (sensorVO.getId() != null && oldIds.contains(sensorVO.getId())) {
                    updList.add(sensorVO);
                } else {
                    if (oldCodingList.contains(sensorVO.getSensorCoding())) {
                        updList.add(sensorVO);
                    } else {
                        addList.add(sensorVO);
                    }
                }
            }

            List<Integer> newIds = sensorVOList.stream().map(Sensor::getId).filter(item -> item != null).collect(Collectors.toList());
            List<String> newCodingList = sensorVOList.stream().map(Sensor::getSensorCoding).collect(Collectors.toList());
            delList = oldSensorList.stream().filter(item -> !newIds.contains(item.getId()) &&
                    !newCodingList.contains(item.getSensorCoding())).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(addList)) {
                sensorDao.batchSave(addList);
            }
            if (!CollectionUtils.isEmpty(delList)) {
                List<Integer> delIds = delList.stream().map(Sensor::getId).collect(Collectors.toList());
                sensorDao.batchDelete(delIds);
            }
            if (!CollectionUtils.isEmpty(updList)) {
                sensorExpDao.batchUpdateSensor(updList);
                if (!CollectionUtils.isEmpty(addList)) {
                    updList.addAll(addList);
                }
            }
        }

        this.updateRedisSensor(meansPointVO,  updList, 2);
    }


    public void checkSensorExist(MeansPointVO meansPointVO) {

        List<SensorVO> sensorList = meansPointVO.getSensorVOList();

        String meansPointName = sensorExpDao.getMeansPointName(meansPointVO.getName(), meansPointVO.getId());
        if(!StringUtils.isEmpty(meansPointName)) {
            throw new BusinessException(GlobalResultEnum.SENSOR_NAME_EXIST_ERROR);
        }

        if (!CollectionUtils.isEmpty(sensorList)) {
            for (Sensor sensor : sensorList) {
                String sensorCoding = sensorExpDao.getSensorCoding(sensor.getSensorCoding(), sensor.getId());
                if(!StringUtils.isEmpty(sensorCoding)) {
                    throw new BusinessException(GlobalResultEnum.SENSOR_CODING_EXIST_ERROR);
                }
            }
        } else {
            throw new BusinessException("传感器细项列表不得为空");
        }

    }


    @Override
    @Transactional
    public void deleteById(Integer id) {
        MeansPoint meansPoint = meansPointDao.findById(id);
        meansPointDao.deleteById(id);
        sensorExpDao.deleteSensorByMeansPointId(id);
        this.updateRedisSensor(meansPoint,null, 3);
    }

    /**
     * 修改redis缓存中传感器列表
     * type： 1新增 2修改 3删除
     */
    private void updateRedisSensor(MeansPoint meansPoint, List<? extends Sensor> sensorList, Integer type) {

        String name;
        Boolean resetFrequency = Boolean.FALSE;
        Boolean isOnline = Boolean.TRUE;

        String meansPointName = getMeansPointKey(meansPoint.getId().toString());

        if (type < 3) {
            Integer meansStatus = (Integer) redisUtil.hget(GlobalConstant.SENSOR_STATUS, meansPoint.getId().toString());

            if (!SensorStatusEnum.ONLINE.getCode().equals(meansStatus)) {
                isOnline = Boolean.FALSE;
            }

            redisUtil.hset(GlobalConstant.SENSOR_STATUS, meansPoint.getId().toString(), meansPoint.getStatus());

            Long time = this.getTimeByFrequency(meansPoint.getSamplingFrequency());

            if (type == 1 && time != null) {
                resetFrequency = Boolean.TRUE;
            }

            if (type == 2){
                MeansPoint oldMeansPoint = (MeansPoint) redisUtil.get(meansPointName);
                Long oldTime =  this.getTimeByFrequency(oldMeansPoint.getSamplingFrequency());
                if (!Objects.equals(time, oldTime)) {

                    if (time != null) {
                        resetFrequency = Boolean.TRUE;
                    }

                    //修改了频率，并且频率为空或从小于1变成大于等于1
                    if (oldTime != null && time == null) {
                        redisUtil.batchDelete(getExpireKey(meansPoint.getId().toString(), "*"));
                        redisUtil.hset(GlobalConstant.SENSOR_FREQUENCY, meansPoint.getId().toString(), 1);
                    }
                }
            }

            redisUtil.set(meansPointName, meansPoint);
            for (Sensor sensor : sensorList) {
                name = getSensorKey(meansPoint.getId().toString(), sensor.getSensorCoding(),
                        sensor.getSensorDetailsId().toString());
                redisUtil.set(name, sensor);
                //频率改为小于1，并且设备在线
                if (resetFrequency && isOnline) {
                    redisUtil.set(getExpireKey(meansPoint.getId().toString(), sensor.getSensorCoding(),
                            sensor.getSensorDetailsId().toString()), time, time);
                }
            }
            redisUtil.hset(GlobalConstant.SENSOR_FREQUENCY, meansPoint.getId().toString(), time);
        } else {
            redisUtil.del(meansPointName);
            redisUtil.hdel(GlobalConstant.SENSOR_STATUS, meansPoint.getId().toString());
            redisUtil.hdel(GlobalConstant.SENSOR_FREQUENCY, meansPoint.getId().toString());
            redisUtil.batchDelete(getExpireKey(meansPoint.getId().toString(), "*"));
            //批量删除redis中此测点传感器信息
            redisUtil.batchDelete(getSensorKey(meansPoint.getId().toString(), "*"));
        }
    }

    @Override
    public List<Object> listByType(Integer type, Integer structureId,Integer queryAll) {
        List<Object> list = new ArrayList<>();
        List<SensorTreeDto> sensorTreeDTOList = sensorExpDao.selectSensorByType(type, structureId, queryAll);
        if (type != 2) {
            list.addAll(sensorTreeDTOList);
        } else {
            if (!CollectionUtils.isEmpty(sensorTreeDTOList)) {
                for (SensorTreeDto sensorTreeDto : sensorTreeDTOList) {
                    if (!CollectionUtils.isEmpty(sensorTreeDto.getSensorVOList())) {
                        list.addAll(sensorTreeDto.getSensorVOList());
                    }
                }
            }
        }
        return list;
    }

    @Override
    public SelectVO getSelectList() {
        SelectVO selectVO = new SelectVO();
        selectVO.setCompanyList(companyDao.findAll());
        selectVO.setSensorTypeList(sensorTypeDao.findAll());
        selectVO.setSensorDetailsList(sensorDetailsDao.findAll());
        selectVO.setSensorPrincipleList(sensorPrincipleDao.findAll());
        selectVO.setComponentInfoList(componentInfoDao.findAll());
        return selectVO;
    }

    @Override
    public void updateStatusByFrequency() {

        String time = DateUtils.formatDate(new Date(), GlobalConstant.FULL_DATE_FORMAT);
        List<MeansPointStatusVO> meansPointList = sensorExpDao.getMeansPointStatus();
        if (!CollectionUtils.isEmpty(meansPointList)) {

            //离线的测点列表(以结构物分组)
            List<MeansPointStatusVO> offLineMeansPoint = meansPointList.stream()
                    .filter(item -> item.getStatus() > item.getCurrentStatus()).collect(Collectors.toList());
            Map<Integer, List<MeansPointStatusVO>> offLine = offLineMeansPoint.stream()
                    .collect(Collectors.groupingBy(MeansPointStatusVO::getStructureId));

            //redis中状态批量更新
            if (!CollectionUtils.isEmpty(offLineMeansPoint)) {
                Map<String, Object> map = new HashMap<>();
                for (MeansPointStatusVO meansPointVO : offLineMeansPoint) {
                    map.put(meansPointVO.getId().toString(), SensorStatusEnum.OFFLINE.getCode());
                }
                redisUtil.hmset(GlobalConstant.SENSOR_STATUS, map);
            }


            for (Map.Entry<Integer, List<MeansPointStatusVO>> entry : offLine.entrySet()) {

                //获取此结构物的承接单位列表
                ProjectUserVO projectUserVO = userExpDao.getUserListByStructure(entry.getKey(), power.getOnlinePower());

                //邮件
                if (projectUserVO != null && !CollectionUtils.isEmpty(projectUserVO.getUserList())) {
                    List<User> userList = projectUserVO.getUserList();
                    StringBuilder content = new StringBuilder();
                    for (MeansPointStatusVO meansPoint : entry.getValue()) {
                        content.append("【离线通知】");
                        content.append(time).append("，\"").append(projectUserVO.getName())
                                .append("项目\" 中的 \"").append(meansPoint.getStructureName())
                                .append("结构物\" ，测点编号为 \"").append(meansPoint.getName())
                                .append("\" 的传感器近一个小时无数据，疑似离线，请及时检查和处理。");

                        appNoticeService.saveNoticeByUserList(userList, content.toString());

                        //邮件通知
                        content.append("——桥隧管养一体化平台");
                        List<String> emails = userList.stream().filter(item -> !StringUtils.isEmpty(item.getEmail()))
                                .map(User::getEmail).distinct().collect(Collectors.toList());

                        mailService.batchSendMessage(emails, "离线通知", content.toString());
                        content.delete(0, content.length());
                    }
                }
            }

            sensorExpDao.batchUpdateStatus(meansPointList);
        }
    }

    @Override
    public void loadSensorList() {

        //批量删除redis中传感器信息
        redisUtil.batchDelete(getSensorKey("*"));
        redisUtil.batchDelete(getMeansPointKey("*"));
        redisUtil.batchDelete(getExpireKey("*"));
        redisUtil.del(GlobalConstant.SENSOR_STATUS);
        List<MeansPointBO> meansPointList = sensorExpDao.getAllMeansPoint();

        if (!CollectionUtils.isEmpty(meansPointList)) {
            MeansPoint meansPoint = new MeansPoint();
            for (MeansPointBO meansPointBO : meansPointList) {
                BeanUtils.copyProperties(meansPointBO, meansPoint);
                redisUtil.set(getMeansPointKey(meansPointBO.getId().toString()), meansPoint);

                redisUtil.hset(GlobalConstant.SENSOR_STATUS, meansPointBO.getId().toString(), meansPointBO.getStatus());
                List<Sensor> sensorVOList = meansPointBO.getSensorList();
                if (!CollectionUtils.isEmpty(sensorVOList)) {

                    for (Sensor sensor : sensorVOList) {

                        redisUtil.set(getSensorKey(meansPoint.getId().toString(), sensor.getSensorCoding(),
                                sensor.getSensorDetailsId().toString()), sensor);
                    }
                    Long time = this.getTimeByFrequency(meansPointBO.getSamplingFrequency());
                    redisUtil.hset(GlobalConstant.SENSOR_FREQUENCY, meansPointBO.getId().toString(), time);
                }
            }
        }
    }

    @Override
    public void updateSensorStatus(Integer meansPointId, Integer status) {
        MeansPoint meansPoint = new MeansPoint();
        meansPoint.setId(meansPointId);
        meansPoint.setStatus(status);

        meansPointDao.update(meansPoint);
    }

    /**
     * 传感器分数频率转时间,单位为秒(频率小于1HZ的)
     */

    public static Long getTimeByFrequency(String frequency) {
        Long time = null;

        String pattern = "1/[0-9]+";

        if (Pattern.matches(pattern, frequency == null ? "": frequency)) {
            frequency = frequency.substring(frequency.lastIndexOf("/") + 1);
            time = Long.parseLong(frequency) * 3;
        }
        return time;
    }

}
