package com.iware.bridge.online.service;

import com.iware.bridge.model.dao.online.MeansPointDao;
import com.iware.bridge.model.entity.global.ProjectAppoint;
import com.iware.bridge.model.entity.global.Structure;
import com.iware.bridge.model.entity.online.MeansPoint;
import com.iware.bridge.online.dao.SensorWeightExpDao;
import com.iware.bridge.online.dto.SensorWeightDto;
import com.iware.bridge.online.vo.*;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.properties.PowerProperties;
import com.iware.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: wjp
 * @date: 2021年8月19日14:15:37
 * @since 1.0
 */
@Service
public class SensorWeightDataServiceImpl implements SensorWeightDataService {

    @Autowired
    private PowerProperties power;
    @Autowired
    private SensorWeightExpDao sensorWeightExpDao;
    @Autowired
    private MeansPointDao meansPointDao;


    @Override
    public List<MeansPoint> getSensorWeightCarNo(Integer structureId, Integer type) {
        MeansPoint meansPoint = new MeansPoint();
        meansPoint.setStructureId(structureId);
        if (type == 1) {
            meansPoint.setSensorTypeId(7);
        } else if (type == 2) {
            meansPoint.setSensorTypeId(1);
        } else if (type == 3) {
            meansPoint.setSensorTypeId(3);
        }
        return meansPointDao.query(meansPoint);
    }

    @Override
    public Map<String, List<SensorWeightDto>> getTrafficFlow(SensorWeightFilter sensorWeightFilter) {

        Float day = 0f;
        day = DateUtils.betweenDays(sensorWeightFilter.getStartTime(), sensorWeightFilter.getEndTime());
        List<MeansPoint> sensorWeightCarNo = new ArrayList<>();
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer powerId = power.getOnlinePower();
        Map<String, List<SensorWeightDto>> map = new HashMap<>();
        if (sensorWeightFilter.getCarNo() == null) {
            sensorWeightCarNo = getSensorWeightCarNo(sensorWeightFilter.getStructureId(), 1);
        } else {
            MeansPoint meansPoint = meansPointDao.findById(sensorWeightFilter.getCarNo());
            sensorWeightCarNo.add(meansPoint);
        }

        List<ProjectAppoint> appoints = new ArrayList<>();
        if (roleId == 1 || roleId == 0) {
            appoints = sensorWeightExpDao.selectQuery(unitId, powerId, sensorWeightFilter.getStructureId());
        }
        List<SensorWeightDto> list = new ArrayList<>();

        if (day <= 1) {

            if (roleId == 2 || roleId == 3 || appoints.size() != 0) {
                list = sensorWeightExpDao.selectTrafficFlowHour(sensorWeightFilter, appoints);
            }
            addData(1, sensorWeightCarNo, map, list, sensorWeightFilter);
        } else if (day <= 90) {

            if (roleId == 2 || roleId == 3 || appoints.size() != 0) {
                list = sensorWeightExpDao.selectTrafficFlowDay(sensorWeightFilter, appoints);
            }
            addData(2, sensorWeightCarNo, map, list, sensorWeightFilter);
        } else {

            if (roleId == 2 || roleId == 3 || appoints.size() != 0) {
                list = sensorWeightExpDao.selectTrafficFlowMonth(sensorWeightFilter, appoints);
            }
            addData(3, sensorWeightCarNo, map, list, sensorWeightFilter);
        }
        map = map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldVal, newVal) -> oldVal,
                LinkedHashMap::new
        ));
        return map;
    }

    @Override
    public Map<String, List<SensorWeightDto>> getCarModelSpeed(SensorWeightFilter sensorWeightFilter) {
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer powerId = power.getOnlinePower();
        Map<String, List<SensorWeightDto>> map = new HashMap<>();


        List<ProjectAppoint> appoints = new ArrayList<>();
        if (roleId == 1 || roleId == 0) {
            appoints = sensorWeightExpDao.selectQuery(unitId, powerId, sensorWeightFilter.getStructureId());
        }
        SensorWeightModel sensorWeightModel = sensorWeightExpDao.selectCarModel(sensorWeightFilter, appoints);
        List<SensorWeightDto> list1 = new ArrayList<>();
        Float two = 0f;
        Float three = 0f;
        Float four = 0f;
        Float five = 0f;
        Float six = 0f;
        Float sixUp = 0f;
        if (sensorWeightModel != null) {
            two = Float.parseFloat("" + sensorWeightModel.getTwoModel());
            three = Float.parseFloat("" + sensorWeightModel.getThreeModel());
            four = Float.parseFloat("" + sensorWeightModel.getFourModel());
            five = Float.parseFloat("" + sensorWeightModel.getFiveModel());
            six = Float.parseFloat("" + sensorWeightModel.getSixModel());
            sixUp = Float.parseFloat("" + sensorWeightModel.getSixUpModel());
        }
        list1.add(new SensorWeightDto("二轴车", null, two, 1));
        list1.add(new SensorWeightDto("三轴车", null, three, 2));
        list1.add(new SensorWeightDto("四轴车", null, four, 3));
        list1.add(new SensorWeightDto("五轴车", null, five, 4));
        list1.add(new SensorWeightDto("六轴车", null, six, 5));
        list1.add(new SensorWeightDto("六轴以上车", null, sixUp, 6));
        map.put("model", list1);


        List<SensorWeightDto> list2 = new ArrayList<>();
        SensorWeightSpeed sensorWeightSpeed = sensorWeightExpDao.selectCarSpeed(sensorWeightFilter, appoints);
        Float twentyDown = 0f;
        Float forty = 0f;
        Float sixty = 0f;
        Float eighty = 0f;
        Float hundred = 0f;
        Float hundredUp = 0f;
        if (sensorWeightSpeed != null) {
            twentyDown = Float.parseFloat("" + sensorWeightSpeed.getTwentySpeed());
            forty = Float.parseFloat("" + sensorWeightSpeed.getTwentyFortySpeed());
            sixty = Float.parseFloat("" + sensorWeightSpeed.getFortySixtySpeed());
            eighty = Float.parseFloat("" + sensorWeightSpeed.getSixtyEightySpeed());
            hundred = Float.parseFloat("" + sensorWeightSpeed.getEightyHundredSpeed());
            hundredUp = Float.parseFloat("" + sensorWeightSpeed.getHundredSpeed());
        }
        list2.add(new SensorWeightDto("<20 km/h", null, twentyDown, null));
        list2.add(new SensorWeightDto("20-40 km/h", null, forty, null));
        list2.add(new SensorWeightDto("40-60 km/h", null, sixty, null));
        list2.add(new SensorWeightDto("60-80 km/h", null, eighty, null));
        list2.add(new SensorWeightDto("80-100 km/h", null, hundred, null));
        list2.add(new SensorWeightDto(">100 km/h", null, hundredUp, null));
        map.put("carSpeed", list2);
        return map;
    }

    @Override
    public Map<String, List<SensorWeightDto>> maxAlexWeight(SensorWeightFilter sensorWeightFilter) {
        Float day = 0f;
        day = DateUtils.betweenDays(sensorWeightFilter.getStartTime(), sensorWeightFilter.getEndTime());

        List<MeansPoint> sensorWeightCarNo = new ArrayList<>();
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer powerId = power.getOnlinePower();
        Map<String, List<SensorWeightDto>> map = new HashMap<>();
        if (sensorWeightFilter.getCarNo() == null) {
            sensorWeightCarNo = getSensorWeightCarNo(sensorWeightFilter.getStructureId(), 1);
        } else {
            MeansPoint meansPoint = meansPointDao.findById(sensorWeightFilter.getCarNo());
            sensorWeightCarNo.add(meansPoint);
            sensorWeightCarNo.add(meansPoint);
        }
        List<ProjectAppoint> appoints = new ArrayList<>();
        if (roleId == 1 || roleId == 0) {
            appoints = sensorWeightExpDao.selectQuery(unitId, powerId, sensorWeightFilter.getStructureId());
        }
        List<SensorWeightDto> list = new ArrayList<>();
        if (day <= 1) {

            if (roleId == 2 || roleId == 3 || appoints.size() != 0) {
                list = sensorWeightExpDao.selectMaxWeightHour(sensorWeightFilter, appoints);
            }
            addData(1, sensorWeightCarNo, map, list, sensorWeightFilter);
        } else if (day <= 90) {

            if (roleId == 2 || roleId == 3 || appoints.size() != 0) {
                list = sensorWeightExpDao.selectMaxWeightDay(sensorWeightFilter, appoints);
            }
            addData(2, sensorWeightCarNo, map, list, sensorWeightFilter);
        } else {

            if (roleId == 2 || roleId == 3 || appoints.size() != 0) {
                list = sensorWeightExpDao.selectMaxWeightMonth(sensorWeightFilter, appoints);
            }
            addData(3, sensorWeightCarNo, map, list, sensorWeightFilter);
        }
        for (String s : map.keySet()) {
            for (SensorWeightDto sensorWeightDto : map.get(s)) {
                if (sensorWeightDto.getCount() == null) {
                    sensorWeightDto.setCount(Float.parseFloat("0"));
                }
            }
        }
        map = map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldVal, newVal) -> oldVal,
                LinkedHashMap::new
        ));
        return map;
    }

    @Override
    public Map<String, List<SensorWeightDto>> carWeight(SensorWeightFilter sensorWeightFilter) {
        Map<String, List<SensorWeightDto>> map = new HashMap<>();
        List<MeansPoint> sensorWeightCarNo = new ArrayList<>();
        if (sensorWeightFilter.getCarNo() == null) {
            sensorWeightCarNo = getSensorWeightCarNo(sensorWeightFilter.getStructureId(), 1);
        } else {
            MeansPoint meansPoint = meansPointDao.findById(sensorWeightFilter.getCarNo());
            sensorWeightCarNo.add(meansPoint);
        }
        for (MeansPoint meansPoint : sensorWeightCarNo) {
            List<SensorWeightDto> list = new ArrayList<>();
            list.add(new SensorWeightDto("0-10 t", null, 0f, null));
            list.add(new SensorWeightDto("10-30 t", null, 0f, null));
            list.add(new SensorWeightDto("30-50 t", null, 0f, null));
            list.add(new SensorWeightDto("50 t以上", null, 0f, null));
            map.put(meansPoint.getName(), list);
        }
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer powerId = power.getOnlinePower();
        List<ProjectAppoint> appoints = new ArrayList<>();
        if (roleId == 1 || roleId == 0) {
            appoints = sensorWeightExpDao.selectQuery(unitId, powerId, sensorWeightFilter.getStructureId());
        }
        List<SensorWeightWeight> sensorWeightWeights = sensorWeightExpDao.selectCarWeight(sensorWeightFilter, appoints);
        for (SensorWeightWeight sensorWeightWeight : sensorWeightWeights) {
            List<SensorWeightDto> list = new ArrayList<>();
            list.add(new SensorWeightDto("0-10 t", null, Float.parseFloat("" + sensorWeightWeight.getTenWeight()), null));
            list.add(new SensorWeightDto("10-30 t", null, Float.parseFloat("" + sensorWeightWeight.getTenThirtyWeight()), null));
            list.add(new SensorWeightDto("30-50 t", null, Float.parseFloat("" + sensorWeightWeight.getThirtyFiftyWeight()), null));
            list.add(new SensorWeightDto("50 t以上", null, Float.parseFloat("" + sensorWeightWeight.getFiftyWeight()), null));
            map.put(sensorWeightWeight.getName(), list);
        }

        map = map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldVal, newVal) -> oldVal,
                LinkedHashMap::new
        ));
        return map;
    }

    @Override
    public Map<String, List<SensorWeightDto>> countCarWeight(SensorWeightFilter sensorWeightFilter) {
        sensorWeightFilter.setWeight(sensorWeightFilter.getWeight() * 1000);
        Float day = 0f;
        day = DateUtils.betweenDays(sensorWeightFilter.getStartTime(), sensorWeightFilter.getEndTime());
        List<MeansPoint> sensorWeightCarNo = new ArrayList<>();
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer powerId = power.getOnlinePower();
        Map<String, List<SensorWeightDto>> map = new HashMap<>();
        if (sensorWeightFilter.getCarNo() == null) {
            sensorWeightCarNo = getSensorWeightCarNo(sensorWeightFilter.getStructureId(), 1);
        } else {
            MeansPoint meansPoint = meansPointDao.findById(sensorWeightFilter.getCarNo());
            sensorWeightCarNo.add(meansPoint);
        }
        List<ProjectAppoint> appoints = new ArrayList<>();
        if (roleId == 1 || roleId == 0) {
            appoints = sensorWeightExpDao.selectQuery(unitId, powerId, sensorWeightFilter.getStructureId());
        }
        List<SensorWeightDto> list = new ArrayList<>();
        if (day <= 1) {
            if (roleId == 2 || roleId == 3 || appoints.size() != 0) {
                list = sensorWeightExpDao.selectCarWeightHour(sensorWeightFilter, appoints);
            }
            addData(1, sensorWeightCarNo, map, list, sensorWeightFilter);
        } else if (day <= 90) {

            if (roleId == 2 || roleId == 3 || appoints.size() != 0) {
                list = sensorWeightExpDao.selectCarWeightDay(sensorWeightFilter, appoints);
            }
            addData(2, sensorWeightCarNo, map, list, sensorWeightFilter);
        } else {

            if (roleId == 2 || roleId == 3 || appoints.size() != 0) {
                list = sensorWeightExpDao.selectCarWeightMonth(sensorWeightFilter, appoints);
            }
            addData(3, sensorWeightCarNo, map, list, sensorWeightFilter);
        }
        map = map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldVal, newVal) -> oldVal,
                LinkedHashMap::new
        ));
        return map;
    }


    @Override
    public Map<String, Integer> transfiniteCar(SensorWeightFilter sensorWeightFilter) {
        if (sensorWeightFilter.getAxleId() == null) {
            sensorWeightFilter.setAxleId(1);
        }
        Map<String, Integer> map = new HashMap<>();
        Integer unitId = ThreadLocalMap.getUnitId();
        Integer roleId = ThreadLocalMap.getRoleId();
        Integer powerId = power.getOnlinePower();
        List<ProjectAppoint> appoints = new ArrayList<>();
        if (roleId == 1 || roleId == 0) {
            appoints = sensorWeightExpDao.selectQuery(unitId, powerId, sensorWeightFilter.getStructureId());
        }
        SensorWeightWeight sensorWeightWeight = sensorWeightExpDao.selecttransfiniteCar(sensorWeightFilter, appoints);
        Integer normal = 0;
        Integer noNormal = 0;
        if (sensorWeightWeight != null) {
            if (sensorWeightFilter.getAxleId() == 1) {
                normal = sensorWeightWeight.getTwoModelWeight();
                noNormal = sensorWeightWeight.getTwoModelWeightNot();
            } else if (sensorWeightFilter.getAxleId() == 2) {
                normal = sensorWeightWeight.getThreeModelWeight();
                noNormal = sensorWeightWeight.getThreeModelWeightNot();
            } else if (sensorWeightFilter.getAxleId() == 3) {
                normal = sensorWeightWeight.getFourModelWeight();
                noNormal = sensorWeightWeight.getFourModelWeightNot();
            } else if (sensorWeightFilter.getAxleId() == 4) {
                normal = sensorWeightWeight.getFiveModelWeight();
                noNormal = sensorWeightWeight.getFiveModelWeightNot();
            } else if (sensorWeightFilter.getAxleId() == 5) {
                normal = sensorWeightWeight.getSixModelWeight();
                noNormal = sensorWeightWeight.getSixModelWeightNot();
            } else if (sensorWeightFilter.getAxleId() == 6) {
                normal = sensorWeightWeight.getSixUpModelWeight();
                noNormal = sensorWeightWeight.getSixUpModelWeightNot();
            }
        }

        map.put("未超重", noNormal);
        map.put("超重", normal);
        return map;
    }


    @Override
    public List<Structure> sensorStructure(Integer type) {
        return sensorWeightExpDao.selectSensorWeightStructure(ThreadLocalMap.getUnitId(), ThreadLocalMap.getRoleId(), power.getOnlinePower(), type);
    }

    public void addData(Integer type, List<MeansPoint> sensorWeightCarNo, Map<String, List<SensorWeightDto>> map, List<SensorWeightDto> list, SensorWeightFilter sensorWeightFilter) {
        for (MeansPoint sensorWeightDto : sensorWeightCarNo) {
            if (!map.containsKey(sensorWeightDto.getName())) {
                map.put(sensorWeightDto.getName(), new ArrayList<>());
            }
        }

        for (SensorWeightDto sensorWeightDto : list) {
            map.get(sensorWeightDto.getName()).add(sensorWeightDto);
        }

        List<String> stringList = sensorWeightExpDao.selectTimeZero(type, sensorWeightFilter);
        //时间集合
        Map<String, List<String>> mapTime = new HashMap<>();
        for (String s : map.keySet()) {
            if (!mapTime.containsKey(s)) {
                mapTime.put(s, new ArrayList<>());
                for (SensorWeightDto sensorWeightDto : map.get(s)) {
                    mapTime.get(s).add(sensorWeightDto.getTime());
                }
            }
        }


        //补全时间
        Map<String, List<String>> addTime = new HashMap<>();
        for (String s : stringList) {
            for (String s1 : mapTime.keySet()) {
                if (!mapTime.get(s1).contains(s)) {
                    if (!addTime.containsKey(s1)) {
                        addTime.put(s1, new ArrayList<>());
                        addTime.get(s1).add(s);
                    } else {
                        addTime.get(s1).add(s);
                    }
                }
            }
        }

        //添加进返回对象
        for (String s : addTime.keySet()) {
            for (String s1 : addTime.get(s)) {
                SensorWeightDto sensorWeightDto = new SensorWeightDto();
                sensorWeightDto.setCount(Float.parseFloat("0"));
                sensorWeightDto.setName(s);
                sensorWeightDto.setTime(s1);
                map.get(s).add(sensorWeightDto);
            }
        }

        //排序
        for (String s : map.keySet()) {
            map.put(s, map.get(s).stream().sorted(Comparator.comparing(SensorWeightDto::getTime)).collect(Collectors.toList()));

        }
    }

    @Override
    public void weightHourData() {
        //获取当前时间
        Calendar rightNow = Calendar.getInstance();
        Integer year = rightNow.get(Calendar.YEAR);
        Integer month = rightNow.get(Calendar.MONTH);
        Integer day = rightNow.get(rightNow.DAY_OF_MONTH);
        Integer hour24 = rightNow.get(rightNow.HOUR_OF_DAY) - 1;
        Integer min = rightNow.get(rightNow.MINUTE);
        rightNow.set(year, month, day, hour24, min);
        year = rightNow.get(Calendar.YEAR);
        month = rightNow.get(Calendar.MONTH) + 1;
        day = rightNow.get(rightNow.DAY_OF_MONTH);
        hour24 = rightNow.get(rightNow.HOUR_OF_DAY);
        String startTime = year + "-" + month + "-" + day + " " + hour24 + ":00:00";
        String endTime = year + "-" + month + "-" + day + " " + hour24 + ":59:59";
        List<SensorWeightWeight> sensorWeightWeights = sensorWeightExpDao.selectHourWeightData(startTime, endTime);
        List<SensorWeightModel> sensorWeightModels = sensorWeightExpDao.selectHourModelData(startTime, endTime);
        List<SensorWeightSpeed> sensorWeightSpeeds = sensorWeightExpDao.selectHourSpeedData(startTime, endTime);
        if (!sensorWeightModels.isEmpty()) {
            sensorWeightExpDao.addHourModel(sensorWeightModels);
        }
        if (!sensorWeightSpeeds.isEmpty()) {
            sensorWeightExpDao.addHourSpeed(sensorWeightSpeeds);
        }
        if (!sensorWeightWeights.isEmpty()) {
            sensorWeightExpDao.addHourWeight(sensorWeightWeights);
        }

    }
}
