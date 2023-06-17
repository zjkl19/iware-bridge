package com.iware.bridge.online.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iware.bridge.model.dao.online.MeansPointDao;
import com.iware.bridge.model.dao.online.SensorConvergeDao;
import com.iware.bridge.model.dao.online.SensorWeightDao;
import com.iware.bridge.model.dao.online.WarningRecordDao;
import com.iware.bridge.model.entity.online.*;
import com.iware.bridge.online.client.SensorDetailsEnum;
import com.iware.bridge.online.client.SocketData;
import com.iware.bridge.online.vo.SensorWeightVO;
import com.iware.bridge.websocket.WebSocketServer;
import com.iware.cache.redis.RedisUtil;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.enums.SensorStatusEnum;
import com.iware.common.spring.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

import static com.iware.bridge.online.utils.OnlineUtils.*;

@Service
public class DataProcessServiceImpl implements DataProcessService {

    private static Logger logger = LoggerFactory.getLogger(DataProcessServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SensorWeightDao sensorWeightDao;
    @Autowired
    private SensorConvergeDao sensorConvergeDao;
    @Autowired
    private WarningRecordDao warningRecordDao;
    @Autowired
    private SensorDataService sensorDataService;
    @Autowired
    private MeansPointDao meansPointDao;

    @Override
    @Transactional
    public void addSensorData(JSONArray data) {

        for (int i = 0; i < data.size(); i++) {
            JSONObject jsonObject = data.getJSONObject(i);
            String sensorCoding = jsonObject.getString("sensorCodeing");

            Sensor sensor = null;
            Set<String> keys = redisUtil.keys(getSensorKey("*", sensorCoding, "*"));
            if (!CollectionUtils.isEmpty(keys)) {
                sensor = (Sensor) redisUtil.get(keys.iterator().next());
            }

            Integer meansPointStatus = (Integer) redisUtil.hget(GlobalConstant.SENSOR_STATUS,
                    sensor.getMeansPointId().toString());
            //如果传感器状态为非故障，才记录数据
            if (sensor != null && !SensorStatusEnum.FAULT.getCode().equals(meansPointStatus)) {

                this.sendMessage(sensorCoding, jsonObject);

                List<Date> samplingTimeList = jsonObject.getJSONArray("samplingTime").toJavaList(Date.class);
                List<Float> actualFloat = jsonObject.getJSONArray("actual").toJavaList(Float.class);

                if (!CollectionUtils.isEmpty(actualFloat)) {

                    List<SensorConverge> dataList = new ArrayList<>();
                    for (int j = 0; j < actualFloat.size(); j++) {
                        SensorConverge item = new SensorConverge();
                        item.setSamplingTime(samplingTimeList.get(j));
                        item.setSensorCoding(sensorCoding);
                        item.setValue(actualFloat.get(j));
                        item.setSensorDetailsId(sensor.getSensorDetailsId());
                        dataList.add(item);
                    }
                    sensorConvergeDao.batchSave(dataList);

                    //判断是否频率小于1
                    Object object = redisUtil.hget(GlobalConstant.SENSOR_FREQUENCY,
                            sensor.getMeansPointId().toString());
                    if (object != null) {
                        Long frequency = StringUtils.isEmpty(object) ? null : Long.parseLong(object.toString());
                        this.handlerByFrequency(sensor, meansPointStatus, frequency);
                    }

                    //判断预警
                    this.judgeWarning(dataList, sensor);

                    if (!CollectionUtils.isEmpty(dataList)) {
                        sensorDataService.calculatePower(new Date(), Calendar.getInstance(), sensorCoding, sensor.getSensorDetailsId());
                    }
                }
            }
        }
    }

    @Override
    public void addWeighData(JSONArray res) {
        for (int i = 0; i < res.size(); i++) {

            JSONObject jsonObject = res.getJSONObject(i);
            String sensorCoding = res.getJSONObject(i).getString("sensorCoding");

            Sensor sensor = this.getSensorByCoding(sensorCoding);
            Integer meansPointStatus = (Integer) redisUtil.hget(GlobalConstant.SENSOR_STATUS,
                    sensor.getMeansPointId().toString());
            //如果传感器状态为非故障，才记录数据
            if (sensor != null && !SensorStatusEnum.FAULT.getCode().equals(meansPointStatus)) {

                this.sendMessage(sensorCoding, jsonObject);

                SensorWeightVO sensorWeightVO = ((JSON) res.get(i)).toJavaObject(SensorWeightVO.class);

                SensorWeight sensorWeight = new SensorWeight();
                BeanUtils.copyProperties(sensorWeightVO, sensorWeight);
                sensorWeight.setSensorDetailsId(sensorWeightVO.getId());
                sensorWeight.setLeftWheelOne(sensorWeightVO.getLeft1());
                sensorWeight.setLeftWheelTwo(sensorWeightVO.getLeft2());
                sensorWeight.setLeftWheelThree(sensorWeightVO.getLeft3());
                sensorWeight.setLeftWheelFour(sensorWeightVO.getLeft4());
                sensorWeight.setLeftWheelFive(sensorWeightVO.getLeft5());
                sensorWeight.setLeftWheelSix(sensorWeightVO.getLeft6());
                sensorWeight.setLeftWheelSeven(sensorWeightVO.getLeft7());
                sensorWeight.setLeftWheelEight(sensorWeightVO.getLeft8());
                sensorWeight.setRightWheelOne(sensorWeightVO.getRight1());
                sensorWeight.setRightWheelTwo(sensorWeightVO.getRight2());
                sensorWeight.setRightWheelThree(sensorWeightVO.getRight3());
                sensorWeight.setRightWheelFour(sensorWeightVO.getRight4());
                sensorWeight.setRightWheelFive(sensorWeightVO.getRight5());
                sensorWeight.setRightWheelSix(sensorWeightVO.getRight6());
                sensorWeight.setRightWheelSeven(sensorWeightVO.getRight7());
                sensorWeight.setRightWheelEight(sensorWeightVO.getRight8());
                sensorWeight.setSpacingOneTwo(sensorWeightVO.getSpacing12());
                sensorWeight.setSpacingTwoThree(sensorWeightVO.getSpacing23());
                sensorWeight.setSpacingThreeFour(sensorWeightVO.getSpacing34());
                sensorWeight.setSpacingFourFive(sensorWeightVO.getSpacing45());
                sensorWeight.setSpacingFiveSix(sensorWeightVO.getSpacing56());
                sensorWeight.setSpacingSixSeven(sensorWeightVO.getSpacing67());
                sensorWeight.setSpacingSevenEight(sensorWeightVO.getSpacing78());

                String licensePlate = sensorWeightVO.getLicensePlate();
                String licenseColor = sensorWeightVO.getLincenseColor();
                if (!StringUtils.isEmpty(licenseColor)) {
                    sensorWeight.setLicenseColor(licenseColor.replaceAll("\u0000", ""));
                }
                if (!StringUtils.isEmpty(licensePlate)) {
                    sensorWeight.setLicensePlate(licensePlate.replaceAll("\u0000", ""));
                }
                sensorWeightDao.save(sensorWeight);
                this.judgeWeightWarning(sensorWeight, sensor);
            }
        }
    }

    @Override
    public void addSocketData(String message) {

        SocketData socketData = JSON.parseObject(message, SocketData.class);

        if (socketData != null) {
            Float value = socketData.getCurrentValue();
            Long date = socketData.getDate();
            String direction = socketData.getDirection();
            if (value != null && date != null) {
                SensorConverge sensorConverge = new SensorConverge();
                if ("X".equals(direction)) {
                    sensorConverge.setSensorDetailsId(SensorDetailsEnum.INCLINATION_X.getSensorDetailsId());
                    sensorConverge.setSensorCoding(socketData.getMpName() + "_X");
                } else if ("Y".equals(direction)) {
                    sensorConverge.setSensorDetailsId(SensorDetailsEnum.INCLINATION_Y.getSensorDetailsId());
                    sensorConverge.setSensorCoding(socketData.getMpName() + "_Y");
                } else {
                    sensorConverge.setSensorDetailsId(socketData.getMonitorType() == null ? null :
                            SensorDetailsEnum.getDetailsIdByTypeName(socketData.getMonitorType()));
                    sensorConverge.setSensorCoding(socketData.getMpName());
                }

                Sensor sensor = null;
                Set<String> keys = redisUtil.keys(getSensorKey("*", sensorConverge.getSensorCoding(), "*"));
                if (!CollectionUtils.isEmpty(keys)) {
                    sensor = (Sensor) redisUtil.get(keys.iterator().next());
                }

                Integer meansPointStatus = (Integer) redisUtil.hget(GlobalConstant.SENSOR_STATUS,
                        sensor.getMeansPointId().toString());

                //如果传感器状态为非故障，才记录数据
                if (sensor != null && !SensorStatusEnum.FAULT.getCode().equals(meansPointStatus)) {
                    sensorConverge.setValue(value);
                    sensorConverge.setSamplingTime(new Date(date * 1000));
                    sensorConvergeDao.save(sensorConverge);

                    //判断是否频率小于1
                    Object object = redisUtil.hget(GlobalConstant.SENSOR_FREQUENCY,
                            sensor.getMeansPointId().toString());
                    if (object != null) {
                        Long frequency = StringUtils.isEmpty(object) ? null : Long.parseLong(object.toString());
                        this.handlerByFrequency(sensor, meansPointStatus, frequency);
                    }

                    //预警判断
                    List<SensorConverge> list = new ArrayList<>();
                    list.add(sensorConverge);
                    this.judgeWarning(list, sensor);
                }
            }
        }
    }

    /**
     * 筛选添加传感器预警数据
     */
    private void judgeWarning(List<SensorConverge> list, Sensor sensor) {

        Integer sensorDetailsId = sensor.getSensorDetailsId();
        String warningName = getWarningKey(sensor.getSensorCoding(), sensorDetailsId.toString());
        Object lastSensorWarning = redisUtil.get(warningName);

        MeansPoint meansPoint = (MeansPoint) redisUtil.get(
                getMeansPointKey(sensor.getMeansPointId().toString())
        );
        if (lastSensorWarning != null) {//此传感器对应细项上次产生预警还在预警间隔内
            return;
        }

        if (meansPoint != null) {

            lastSensorWarning = redisUtil.get(warningName);
            if (lastSensorWarning != null) {
                return;
            }

            SensorConverge result = null;
            Integer level = null;

            if (list.size() == 1) {
                result = list.get(0);
                level = this.getWarningLevel(result.getValue(), sensor);
            } else {
                SensorConverge max = list.stream().filter(item -> item.getValue() != null)
                        .max(Comparator.comparing(SensorConverge::getValue)).orElse(new SensorConverge());
                SensorConverge min = list.stream().filter(item -> item.getValue() != null)
                        .min(Comparator.comparing(SensorConverge::getValue)).orElse(new SensorConverge());

                Integer maxLevel = this.getWarningLevel(max.getValue(), sensor);
                Integer minLevel = this.getWarningLevel(min.getValue(), sensor);

                if (maxLevel != null || minLevel != null) {
                    level = maxLevel == null ? minLevel :
                            (minLevel == null ? maxLevel : Math.max(minLevel, maxLevel));
                    result = level > 0 ? max : min;
                }
            }


            if (level != null) {

                WarningRecord warningRecord = new WarningRecord();

                warningRecord.setLevel(Math.abs(level));
                warningRecord.setValue(result.getValue());
                warningRecord.setSensorId(sensor.getId());
                warningRecord.setSensorName(meansPoint.getName());
                warningRecord.setSensorDetailsId(sensorDetailsId);
                warningRecord.setStructureId(meansPoint.getStructureId());
                warningRecord.setComponentId(meansPoint.getComponentId());
                warningRecord.setWarningTime(result.getSamplingTime());
                warningRecord.setStatus(0); //未处理
                warningRecord.setSensorCoding(sensor.getSensorCoding());

                warningRecordDao.save(warningRecord);
                redisUtil.set(warningName, result.getSamplingTime(), sensor.getWarningInterval());
            }
        }

    }

    /**
     * 筛选添加称重传感器预警数据
     */
    private void judgeWeightWarning(SensorWeight sensorWeight, Sensor sensor) {

        MeansPoint meansPoint = (MeansPoint) redisUtil.get(
                getMeansPointKey(sensor.getMeansPointId().toString())
        );

        if (meansPoint != null) {

            Float totalWeight = sensorWeight.getTotalWeight().floatValue();
            Integer level = this.getWarningLevel(totalWeight, sensor);
            if (level != null) {
                WarningRecord warningRecord = new WarningRecord();
                warningRecord.setLevel(Math.abs(level));
                warningRecord.setValue(totalWeight);
                warningRecord.setSensorId(sensor.getId());
                warningRecord.setSensorName(meansPoint.getName());
                warningRecord.setSensorDetailsId(sensorWeight.getSensorDetailsId());
                warningRecord.setStructureId(meansPoint.getStructureId());
                warningRecord.setComponentId(meansPoint.getComponentId());
                warningRecord.setWarningTime(sensorWeight.getSamplingTime());
                warningRecord.setStatus(0); //未处理
                warningRecord.setSensorCoding(sensor.getSensorCoding());

                warningRecordDao.save(warningRecord);
            }

        }
    }

    public Integer getWarningLevel(Float value, Sensor sensor) {

        Integer level = null;
        if (sensor.getThirdWarningUpper() != null && value >= sensor.getThirdWarningUpper()) {
            level = 3;
        }
        if (sensor.getSecondWarningUpper() != null && value >= sensor.getSecondWarningUpper()) {
            level = 2;
        }
        if (sensor.getFirstWarningUpper() != null && value >= sensor.getFirstWarningUpper()) {
            level = 1;
        }
        if (sensor.getThirdWarningLower() != null && value <= sensor.getThirdWarningLower()) {
            level = -3;
        }
        if (sensor.getSecondWarningLower() != null && value <= sensor.getSecondWarningLower()) {
            level = -2;
        }
        if (sensor.getFirstWarningLower() != null && value <= sensor.getFirstWarningLower()) {
            level = -1;
        }
        return level;
    }

    private void sendMessage(String sensorCoding, JSONObject jsonObject) {

        WebSocketServer webSocketServer = new WebSocketServer();
        Map<String, List<Session>> codingMap = WebSocketServer.getCodingMap();
        if (!CollectionUtils.isEmpty(codingMap) && codingMap.containsKey(sensorCoding)) {

            // 判断传来的传感器coding是否有用户在查看
            List<Session> sessionList = codingMap.get(sensorCoding);// 查看都有哪些用户
            for (Session session : sessionList) {
                try {
                    webSocketServer.sendMessage(jsonObject.toJSONString(), session);
                } catch (IOException e) {
                    logger.error("websocket IO异常");
                }
            }

        }
    }

    public Sensor getSensorByCoding(String sensorCoding) {
        Sensor sensor = null;
        Set<String> keys = redisUtil.keys(getSensorKey("*", sensorCoding, "*"));
        if (!CollectionUtils.isEmpty(keys)) {
            sensor = (Sensor) redisUtil.get(keys.iterator().next());
        }
        return sensor;
    }

    /**
     * 处理频率小于1的状态及redis
     * @param sensor 传感器
     * @param meansPointStatus 测点状态
     */
    public void handlerByFrequency(Sensor sensor, Integer meansPointStatus, Long frequency) {

        String meansPointId = sensor.getMeansPointId().toString();
        //刷新过期时间
        String expireKey = getExpireKey(meansPointId ,sensor.getSensorCoding(),
                sensor.getSensorDetailsId().toString());
        this.refreshExpireTime(expireKey, frequency);

        //如果之前是离线，判断是否所有细项已经在线，如果是则设置为在线
        if (SensorStatusEnum.OFFLINE.getCode().equals(meansPointStatus)) {

            Set<String> expireKeys = redisUtil.keys(getExpireKey(meansPointId, "*"));
            Set<String> keys = redisUtil.keys(getSensorKey(meansPointId, "*"));

            if (Objects.equals(keys.size(), expireKeys.size())) {
                MeansPoint meansPoint = new MeansPoint();
                meansPoint.setId(sensor.getMeansPointId());
                meansPoint.setStatus(SensorStatusEnum.ONLINE.getCode());
                meansPointDao.update(meansPoint);
                redisUtil.hset(GlobalConstant.SENSOR_STATUS, meansPointId,
                        SensorStatusEnum.ONLINE.getCode());
            }
        }
    }

    /**
     * 刷新传感器频率过期时间
     */
    public void refreshExpireTime(String expireKey, Long frequency) {
        if (frequency != null) {
            redisUtil.set(expireKey, frequency, frequency);
        }
    }

}
