package com.iware.bridge.online.service;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.iware.bridge.model.dao.online.*;
import com.iware.bridge.model.entity.online.*;
import com.iware.bridge.online.dao.SensorCalculateExpDao;
import com.iware.bridge.online.dao.SensorDataDao;
import com.iware.bridge.online.dto.SensorHistoryDto;
import com.iware.bridge.online.vo.SensorDataFilter;
import com.iware.bridge.online.vo.SensorHistoryVO;
import com.iware.bridge.online.vo.SensorVO;
import com.iware.common.constant.GlobalConstant;
import com.iware.common.enums.FileTypeEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.utils.FileUtil;
import com.iware.common.utils.ProcessUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZRB
 * @date 2021-7-27
 */


@Service
public class SensorDataServiceImpl implements SensorDataService {

    private static Logger logger = LoggerFactory.getLogger(SensorDataServiceImpl.class);

    @Autowired
    private SensorDataDao sensorDataDao;
    @Autowired
    private AxleTypeModelRelDao axleTypeModelRelDao;

    @Value("${file.script-path}")
    private String scriptPath;

    @Autowired
    private SensorCalculateExpDao sensorCalculateExpDao;

    private static final String PROCESS_RESULT_REGEX = "(?<=result:)(-)?\\d+";

    @Override
    public void addSensorDay() {
        sensorDataDao.addSensorDay();
    }

    @Override
    public void addSensorHour() {
        sensorDataDao.addSensorHour();
    }

    @Override
    public void addSensorMinute() {
        sensorDataDao.addSensorMinute();
    }

    @Override
    public void addSensorSecond() {
        sensorDataDao.addSensorSecond();
    }

    @Override
    public void addSensorSecondOne() {
        sensorDataDao.addSensorSecondOne();
    }

    /**
     * 获取传感器最后10条
     */
    @Override
    public Map<String, List<SensorHistoryDto>> getLastTen(List<SensorVO> sensorVOList) {

        Map<String, List<SensorHistoryDto>> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(sensorVOList)) {
            for (SensorVO sensor : sensorVOList) {
                List<SensorHistoryDto> list = new ArrayList<>();
                if (GlobalConstant.SENSOR_WEIGHT.equals(sensor.getSensorDetailsId())) { //称重传感器
                    list = sensorDataDao.getLastTen(sensor.getSensorCoding());
                }
                map.put(sensor.getSensorCoding(), list);
            }
        }
        return map;
    }

    @Override
    public List<SensorHistoryVO> getHistory(SensorDataFilter filter) {

        Date startDate = filter.getStartTime();
        Date endDate = filter.getEndTime();

        long second = (endDate.getTime() - startDate.getTime()) / 1000;//时间差(s)

        ArrayList<SensorHistoryVO> historyData = new ArrayList<>();
        List<SensorVO> sensorList = filter.getSensorList();

        if (!CollectionUtils.isEmpty(sensorList)) {
            for (SensorVO sensor : sensorList) {
                SensorHistoryVO sensorHistoryVO = new SensorHistoryVO();
                List<SensorHistoryDto> dataList = new ArrayList<>();
                String sensorCoding = sensor.getSensorCoding();
                if (GlobalConstant.SENSOR_WEIGHT.equals(sensor.getSensorDetailsId())) { //称重传感器
                    dataList = sensorDataDao.historyDataByWeight(sensorCoding, startDate, endDate);
                } else if (GlobalConstant.COMPANY_JK.equals(sensor.getCompanyId())) { //基康传感器
                    dataList = sensorDataDao.historyDataByJK(sensorCoding, startDate, endDate);
                } else { //其他传感器

                    //根据时间获取不同间隔表数据 0原始表 1:1s 2:3s 3:1m 4:10m 5:1h
                    Integer type = 0; //默认查所有
                    if (second > 132000) {
                        type = 5;
                    } else if (second > 22000) {
                        type = 4;
                    } else if (second > 6000) {
                        type = 3;
                    } else if (second > 1560) {
                        type = 2;
                    } else if (second > 5) {
                        type = 1;
                    }
                    dataList = sensorDataDao.historyDataByDH(sensorCoding, type, startDate, endDate);
                }

                float max = 0, min = 0, avg = 0, variance = 0;
                float varianceMolecule = 0;
                if (!CollectionUtils.isEmpty(dataList)) {
                    if (GlobalConstant.SENSOR_WEIGHT.equals(sensor.getSensorDetailsId()) ||
                            GlobalConstant.COMPANY_JK.equals(sensor.getCompanyId())) {
                        max = dataList.stream().filter(item -> item.getValue() != null)
                                .max(Comparator.comparing(SensorHistoryDto::getValue))
                                .orElse(new SensorHistoryDto()).getValue();
                        min = dataList.stream().filter(item -> item.getValue() != null)
                                .min(Comparator.comparing(SensorHistoryDto::getValue))
                                .orElse(new SensorHistoryDto()).getValue();
                    } else {
                        max = dataList.stream().filter(item -> item.getValue() != null)
                                .max(Comparator.comparing(SensorHistoryDto::getMaxValue))
                                .orElse(new SensorHistoryDto()).getMaxValue();
                        min = dataList.stream().filter(item -> item.getValue() != null)
                                .min(Comparator.comparing(SensorHistoryDto::getMinValue))
                                .orElse(new SensorHistoryDto()).getMinValue();
                    }
                    avg = dataList.stream().map(SensorHistoryDto::getValue).map(BigDecimal::new)
                            .reduce((a, b) -> a.add(b)).map(BigDecimal::floatValue).orElse(0F) / dataList.size();

                    for (SensorHistoryDto data : dataList) {
                        varianceMolecule += (data.getValue() - avg) * (data.getValue() - avg);
                    }
                    variance = varianceMolecule / dataList.size();
                }


                sensorHistoryVO.setId(sensor.getId());
                sensorHistoryVO.setSensorTypeId(sensor.getSensorTypeId());
                sensorHistoryVO.setMax(this.formatValue(max, 6));
                sensorHistoryVO.setMin(this.formatValue(min, 6));
                sensorHistoryVO.setAvg(this.formatValue(avg, 6));
                sensorHistoryVO.setVariance(this.formatValue(variance, 6));
                sensorHistoryVO.setUnit(sensor.getUnit());
                sensorHistoryVO.setList(dataList);
                historyData.add(sensorHistoryVO);
            }
        }

        return historyData;
    }

    public Float formatValue(Float value, Integer bit) {
        return new BigDecimal(value).setScale(bit, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    @Override
    public PageInfo<SensorHistoryDto> listData(Integer pageNum, Integer pageSize, SensorDataFilter filter) {

        PageInfo<SensorHistoryDto> pageInfo = new PageInfo<>();
        List<SensorHistoryDto> list = this.getSensorDataList(filter);

        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        if (!CollectionUtils.isEmpty(list)) {
            Integer currentRow = pageNum * pageSize;
            pageInfo.setPages(list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1);
            pageInfo.setTotal(list.size());
            pageInfo.setList(list.subList((pageNum - 1) * pageSize,
                    currentRow > list.size() ? list.size() : currentRow));
        }

        return pageInfo;
    }

    public List<SensorHistoryDto> getSensorDataList(SensorDataFilter filter) {
        List<SensorHistoryDto> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(filter.getSensorList())) {

            List<String> JKSensor = new ArrayList<>();
            List<String> DHSensor = new ArrayList<>();
            List<String> weightSensor = new ArrayList<>();

            for (SensorVO sensor : filter.getSensorList()) {
                if (GlobalConstant.SENSOR_WEIGHT.equals(sensor.getSensorDetailsId())) {
                    weightSensor.add(sensor.getSensorCoding());
                } else {
                    if (GlobalConstant.COMPANY_JK.equals(sensor.getCompanyId())) {
                        JKSensor.add(sensor.getSensorCoding());
                    } else {
                        DHSensor.add(sensor.getSensorCoding());
                    }
                }
            }
            result = sensorDataDao.sensorHistoryData(weightSensor, JKSensor, DHSensor,
                    filter.getStartTime(), filter.getEndTime());
        }
        return result;
    }

    @Override
    public String createExcel(SensorDataFilter filter) {

        List<String> params = new ArrayList<>();
        String filePath = String.format("%s%s%s", FileTypeEnum.EXCEL_TEMP.getCode(), new Date().getTime() / 1000, ".csv");
        String fileFullPath = FileUtil.genFileFullPath(filePath);
        if (CollectionUtils.isEmpty(filter.getSensorList())) {
            throw new BusinessException("请选择传感器!");
        }

        for (int i = 0; i < filter.getSensorList().size(); i++) {
            SensorVO sensor = filter.getSensorList().get(i);
            params.add(sensor.getSensorCoding());
            if (GlobalConstant.SENSOR_WEIGHT.equals(sensor.getSensorDetailsId())) {
                params.add("3");
            } else {
                if (GlobalConstant.COMPANY_JK.equals(sensor.getCompanyId())) {
                    params.add("1");
                } else {
                    params.add("2");
                }
            }
        }
        if (params.size() == 2) {
            params.add("-1");
            params.add("-1");
        }

        params.add(String.valueOf(filter.getStartTime().getTime() / 1000));
        params.add(String.valueOf(filter.getEndTime().getTime() / 1000));
        params.add(fileFullPath);
        String content = this.doProcessResult(String.format("%s%s", scriptPath,
                GlobalConstant.SCRIPT_EXPORT_SENSOR_DATA), params);

        if (org.apache.commons.lang3.StringUtils.isNotEmpty(content)) {
            Matcher m = Pattern.compile(PROCESS_RESULT_REGEX).matcher(content);
            boolean flag = m.find();
            if (flag) {
                Integer result = Integer.parseInt(m.group());
                if (result == -1) {
                    throw new BusinessException("文件导出失败");
                }
            } else {
                throw new BusinessException("文件导出失败");
            }
        } else {
            throw new BusinessException("文件导出失败,执行脚本失败");
        }
        return filePath;
    }

    private String doProcessResult(String filePath, List<String> paramList) {
        logger.debug(String.format("执行脚本[%s],参数为:[%s]", filePath, Joiner.on(" ").join(paramList)));
        String content = ProcessUtils.processScript(filePath, paramList);
        logger.debug("结尾为:" + content);
        return content;
    }

    /**
     * 东华传感器计算半小时内的功率 索力3 加速度1
     **/
    public void calculatePower(Date date, Calendar cal, String sensorCoding, Integer detailId) {
        Integer minter = cal.get(cal.MINUTE);
        if (minter != 00 && minter != 30) {
            return;
        }
        //根据传感器编号查询是否为索力和加速度传感器
        if (detailId != 3 && detailId != 1) {
            return;
        }
        //判断当前传感器编号和细项是否已经计算过
        List<Float> frequencyList = sensorCalculateExpDao.selectFrequency(sensorCoding, date, detailId);
        if (frequencyList.size() != 0) {
            return;
        }
        //获取半小时内的数据
        List<Float> sensorConverges = sensorCalculateExpDao.selectHalfHourData(sensorCoding, date, detailId);
        //获取测点编号
        MeansPoint meansPoint = sensorCalculateExpDao.selectDescribe(sensorCoding, detailId);
        //发送请求到python获取频率和功率谱数组并且获取最大的功率的频率
        Float frequency = 0f;

        //索力传感器的索力
        Float cableForce = 0f;
        if (sensorConverges.size() >= 1025) {
            //最大频率
            frequency = getPythonData(sensorConverges, meansPoint.getReferenceFrequency(), meansPoint.getFloatRange());

            if (detailId == 3 && frequency != -1f) {
                cableForce = calculateCableForce(frequency, meansPoint.getCableLength(), meansPoint.getUnitCableLength());
                logger.debug("索力" + cableForce);
            } else {
                cableForce = null;
            }
        } else {
            return;
        }

        if (frequency == -1f) {
            frequency = null;
            cableForce = null;
        }
//        //存入数据库
        sensorCalculateExpDao.addCacul(sensorCoding, date, detailId, frequency, cableForce);
    }


    public Float getPythonData(List<Float> list, Float referenceFrequency, Float floatRange) {
        try {
            URL url = new URL("http://127.0.0.1:8000/get_json");
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;
            //请求方式
//          conn.setRequestMethod("POST");
//           //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数即数据
            out.print(list);
            //缓冲数据
            out.flush();
            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";

            List<String> stringList = new ArrayList<>();

            while ((str = br.readLine()) != null) {
                stringList.add(str);
            }
            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();

            String[] strArr = stringList.get(0).split(",");

            //频率
            List<String> x = new ArrayList<>();
            //功率谱
            List<String> y = new ArrayList<>();

            for (int i = 0; i < strArr.length; i++) {
                if (i < strArr.length / 2) {
                    y.add(strArr[i]);
                } else {
                    x.add(strArr[i]);
                }
            }


            List<String> yRequest = new ArrayList<>();
            List<String> xRequest = new ArrayList<>();
            if (referenceFrequency != null) {
                Float maxF = referenceFrequency + referenceFrequency * floatRange * 0.01f;
                Float minF = referenceFrequency - referenceFrequency * floatRange * 0.01f;
                for (int i = 0; i < x.size(); i++) {
                    Float value = Float.parseFloat(x.get(i));
                    if (minF <= value && value <= maxF) {
                        yRequest.add(y.get(i));
                        xRequest.add(x.get(i));
                    }
                }
            }


            //均值
            Float avgTotal = 0f;
            for (int i = 0; i < yRequest.size(); i++) {
                avgTotal += Float.parseFloat(yRequest.get(i));
            }
            Float avg = avgTotal / yRequest.size();


            Double d = 0.0;
            Integer index = 0;
            //获取最大值
            for (int i = 0; i < yRequest.size(); i++) {
                Double e = Double.parseDouble(yRequest.get(i));
                if (e > d) {
                    d = e;
                    index = i;
                }
            }


            //峰值功率谱
            if (yRequest.size() > 0) {
                logger.debug("峰值功率谱对应的频率" + xRequest.get(index));
                //峰值功率谱对应的频率
                logger.debug("峰值功率谱" + yRequest.get(index));
                Float result = Float.parseFloat(xRequest.get(index));

                if (Float.parseFloat(yRequest.get(index)) / avg < 2) {
                    return -1f;
                }
                return result;
            } else {
                return -1f;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<AxleTypeModelRel> listAxleType() {
        return axleTypeModelRelDao.findAll();
    }

    public Float calculateCableForce(Float frequency, Float cableLength, Float unitCableLength) {
        return 4 * unitCableLength * cableLength * cableLength * frequency * frequency / 1000;
    }

}
