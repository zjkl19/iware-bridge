package com.iware.bridge.online.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iware.bridge.model.dao.online.SensorDao;
import com.iware.bridge.model.entity.online.Sensor;
import com.iware.bridge.online.dao.SensorCalculateExpDao;
import com.iware.bridge.online.dao.SensorDataDao;
import com.iware.bridge.online.vo.*;
import com.iware.common.exception.BusinessException;
import com.iware.common.utils.BigDecimalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: wjp
 * @date:
 * @since 1.0
 */
@Service
public class SensorDataAnalysisServiceImpl implements SensorDataAnalysisService {

    @Autowired
    private SensorDataDao sensorDataDao;
    @Autowired
    private SensorDao sensorDao;

    @Autowired
    private SensorCalculateExpDao sensorCalculateExpDao;

    @Override
    public List<SensorCalculateVO> frequency(SensorCalculateFilter sensorCalculateFilter) {
        if (sensorCalculateFilter.getType() == 2) {
            Sensor sensor = new Sensor();
            sensor.setSensorDetailsId(1);
            sensor.setMeansPointId(sensorCalculateFilter.getId());
            List<Sensor> query = sensorDao.query(sensor);
            if (query.isEmpty()) {
                throw new BusinessException("此测点无数据");
            }
            sensorCalculateFilter.setSensorCoding(query.get(0).getSensorCoding());
            sensorCalculateFilter.setSensorDetailsId(1);
        } else if (sensorCalculateFilter.getType() == 3) {
            Sensor sensor = new Sensor();
            sensor.setSensorDetailsId(3);
            sensor.setMeansPointId(sensorCalculateFilter.getId());
            List<Sensor> query = sensorDao.query(sensor);
            if (query.isEmpty()) {
                throw new BusinessException("此测点无数据");
            }
            sensorCalculateFilter.setSensorCoding(query.get(0).getSensorCoding());
            sensorCalculateFilter.setSensorDetailsId(3);
        }
        return sensorCalculateExpDao.selectFrequencyByTime(sensorCalculateFilter);
    }

    @Override
    public List<SensorSpectrumVO> getSpectrum(SensorSpectrumFilter sensorSpectrumFilter) {
        if (sensorSpectrumFilter.getType() == 2) {
            Sensor sensor = new Sensor();
            sensor.setSensorDetailsId(1);
            sensor.setMeansPointId(sensorSpectrumFilter.getId());
            List<Sensor> query = sensorDao.query(sensor);
            if (query.isEmpty()) {
                throw new BusinessException("此测点信息错误");
            }
            sensorSpectrumFilter.setSensorCoding(query.get(0).getSensorCoding());
            sensorSpectrumFilter.setSensorDetailsId(1);
        } else if (sensorSpectrumFilter.getType() == 3) {
            Sensor sensor = new Sensor();
            sensor.setSensorDetailsId(3);
            sensor.setMeansPointId(sensorSpectrumFilter.getId());
            List<Sensor> query = sensorDao.query(sensor);
            if (query.isEmpty()) {
                throw new BusinessException("此测点信息错误");
            }
            sensorSpectrumFilter.setSensorCoding(query.get(0).getSensorCoding());
            sensorSpectrumFilter.setSensorDetailsId(3);
        }
        //获取半小时内的数据
        List<Float> sensorConverges = sensorCalculateExpDao.selectHalfHourData(sensorSpectrumFilter.getSensorCoding(), sensorSpectrumFilter.getTime(), sensorSpectrumFilter.getSensorDetailsId());
        List<SensorSpectrumVO> spectrumData = getSpectrumData(sensorConverges);
        spectrumData.remove(0);
        spectrumData.remove(0);
        return spectrumData;
    }

    public List<SensorSpectrumVO> getSpectrumData(List<Float> list) {
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
            List<SensorSpectrumVO> returnList = new ArrayList<>();
            for (int i = 0; i < x.size(); i++) {
                SensorSpectrumVO sensorSpectrumVO = new SensorSpectrumVO();
                sensorSpectrumVO.setFrequency(Float.parseFloat(x.get(i)));
                sensorSpectrumVO.setAmplitude(Float.parseFloat(y.get(i)));
                returnList.add(sensorSpectrumVO);
            }
            return returnList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public CorrelationAnalysisVO correlationAnalysis(SensorDataFilter sensorDataFilter) {
        if (sensorDataFilter.getSensorList().isEmpty()) {
            throw new BusinessException("至少要选择2个传感器细项");
        }
        if (sensorDataFilter.getSensorList().size() != 2) {
            throw new BusinessException("至少要选择2个传感器细项");
        }
//        if (sensorDataFilter.getSensorList().get(0).getCompanyId() != 1 || sensorDataFilter.getSensorList().get(1).getCompanyId() != 1) {
//            throw new BusinessException("选择的传感器需要为基康设备");
//        }
//        MeansPoint meansPoint = meansPointDao.findById(sensorDataFilter.getSensorList().get(0).getMeansPointId());
//        MeansPoint meansPoint2 = meansPointDao.findById(sensorDataFilter.getSensorList().get(1).getMeansPointId());
        if (!sensorDataFilter.getSensorList().get(0).getSamplingFrequency().equals(sensorDataFilter.getSensorList().get(1).getSamplingFrequency())) {
            throw new BusinessException("传感器的采样频率不一致");
        }


        CorrelationAnalysisPython correlationAnalysisPython = new CorrelationAnalysisPython();
        List<Float> oneFloat = new ArrayList<>();
        List<Float> twoFloat = new ArrayList<>();
        for (int i = 0; i < sensorDataFilter.getSensorList().size(); i++) {
            SensorVO sensorVO = sensorDataFilter.getSensorList().get(i);
            List<Float> floatData = new ArrayList<>();
            if (sensorVO.getSensorDetailsId() == 7) {
                floatData = sensorCalculateExpDao.getPythonDataCZ(sensorDataFilter.getStartTime(), sensorDataFilter.getEndTime(), sensorVO.getSensorCoding(), sensorVO.getSensorDetailsId());
            } else if (sensorVO.getCompanyId() == 2) {
                floatData = sensorCalculateExpDao.getPythonDataDH(sensorDataFilter.getStartTime(), sensorDataFilter.getEndTime(), sensorVO.getSensorCoding(), sensorVO.getSensorDetailsId());
            } else if (sensorVO.getCompanyId() == 1) {
                floatData = sensorCalculateExpDao.getPythonDataJK(sensorDataFilter.getStartTime(), sensorDataFilter.getEndTime(), sensorVO.getSensorCoding());
            } else {
                throw new BusinessException("暂无数据");
            }
            if (i == 0) {
                oneFloat = floatData;
            } else {
                twoFloat = floatData;
            }
        }

        if (oneFloat.isEmpty() || twoFloat.isEmpty()) {
            CorrelationAnalysisVO correlationAnalysisVO = new CorrelationAnalysisVO();
            correlationAnalysisVO.setAllX(new ArrayList<>());
            return correlationAnalysisVO;
        }


        if (oneFloat.size() > twoFloat.size()) {
            List<Float> newFloatData = new ArrayList<>();
            for (int i = 0; i < twoFloat.size(); i++) {
                Float value = oneFloat.get(i);
                newFloatData.add(value);
            }
            correlationAnalysisPython.setOne(newFloatData);
            correlationAnalysisPython.setTwo(twoFloat);
        } else if (oneFloat.size() < twoFloat.size()) {
            List<Float> newFloatData = new ArrayList<>();
            for (int i = 0; i < oneFloat.size(); i++) {
                Float value = twoFloat.get(i);
                newFloatData.add(value);
            }
            correlationAnalysisPython.setTwo(newFloatData);
            correlationAnalysisPython.setOne(oneFloat);
        } else {
            correlationAnalysisPython.setTwo(twoFloat);
            correlationAnalysisPython.setOne(oneFloat);
        }

        if (correlationAnalysisPython.getOne().size() < 3){
            throw new BusinessException("至少要有三条数据才能进行相关性分析");
        }

        CorrelationAnalysisVO correlationAnalysisVO = getAnalysisData(correlationAnalysisPython);
        correlationAnalysisVO.setAllX(correlationAnalysisPython.getOne());
        correlationAnalysisVO.setAllY(correlationAnalysisPython.getTwo());
        return correlationAnalysisVO;
    }


    public CorrelationAnalysisVO getAnalysisData(CorrelationAnalysisPython correlationAnalysisPython) {
        try {
            URL url = new URL("http://127.0.0.1:8000/analysis");
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;
            //请求方式
//            conn.setRequestMethod("POST");
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
            out.print(JSON.toJSONString(correlationAnalysisPython));
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

            CorrelationAnalysisVO correlationAnalysisVO = new CorrelationAnalysisVO();
            JSONArray objects = JSON.parseArray(stringList + "");
            for (int i = 0; i < objects.size(); i++) {
                List<Float> aFloat = JSONObject.parseArray(objects.get(i) + "", Float.class);
                if (i == 0) {
                    correlationAnalysisVO.setMiddleFirstX(aFloat.get(0));
                    correlationAnalysisVO.setMiddleFirstY(aFloat.get(1));
                } else if (i == 1) {
                    correlationAnalysisVO.setMiddleSecondX(aFloat.get(0));
                    correlationAnalysisVO.setMiddleSecondY(aFloat.get(1));
                } else if (i == 2) {
                    correlationAnalysisVO.setCorrelationCoefficient(aFloat.get(0));
                } else if (i == 3) {
                    correlationAnalysisVO.setRegressionEquation("y = " + aFloat.get(0) + "*x + " + aFloat.get(1));
                } else if (i == 4) {
                    correlationAnalysisVO.setDispersion(aFloat.get(0));
                } else if (i == 5) {
                    correlationAnalysisVO.setUpFirstX(aFloat.get(0));
                    correlationAnalysisVO.setUpFirstY(aFloat.get(1));
                } else if (i == 6) {
                    correlationAnalysisVO.setUpSecondX(aFloat.get(0));
                    correlationAnalysisVO.setUpSecondY(aFloat.get(1));
                } else if (i == 7) {
                    correlationAnalysisVO.setDownFirstX(aFloat.get(0));
                    correlationAnalysisVO.setDownFirstY(aFloat.get(1));
                } else if (i == 8) {
                    correlationAnalysisVO.setDownSecondX(aFloat.get(0));
                    correlationAnalysisVO.setDownSecondY(aFloat.get(1));
                }
            }
            return correlationAnalysisVO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BoxData> boxAnalyse(SensorDataFilter filter) {

        List<SensorVO> sensorList = filter.getSensorList();
        List<BoxData> boxDataList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sensorList)) {
            for (SensorVO sensor : sensorList) {
                List<Float> dataList = sensorDataDao.boxDiagramByJK(sensor.getSensorCoding(),
                        filter.getStartTime(), filter.getEndTime());
                if (!CollectionUtils.isEmpty(dataList) && dataList.size() > 5) {
                    BoxData boxData = new BoxData();
                    boxData.setName(sensor.getSensorName());
                    dataList = dataList.stream().sorted().collect(Collectors.toList());
                    this.boxCalculate(boxData, dataList);
                    boxDataList.add(boxData);
                }
            }
        }
        return boxDataList;
    }

    private void boxCalculate(BoxData boxData, List<Float> dataList) {
        List<Float> warningList;
        List<Float> calculate = new ArrayList<>();
        Float firstQuartile = getQuartile(dataList, 0.25f);
        Float thirdQuartile = getQuartile(dataList, 0.75f);
        Float midValue, bound, minValue, maxValue;
        if (dataList.size() % 2 == 0) {
            midValue = (dataList.get(dataList.size() / 2) + dataList.get(dataList.size() / 2 - 1)) / 2;
        } else {
            midValue = dataList.get((dataList.size() - 1) / 2);
        }
        bound = BigDecimalUtils.sub(thirdQuartile, firstQuartile).multiply(BigDecimal.valueOf(1.5)).floatValue();
        minValue = Math.max(dataList.get(0), BigDecimalUtils.sub(firstQuartile, bound).floatValue());
        maxValue = Math.min(dataList.get(dataList.size() - 1), BigDecimalUtils.add(thirdQuartile, bound).floatValue());

        warningList = dataList.stream().filter(value -> (value > maxValue || value < minValue))
                .collect(Collectors.toList());

        calculate.add(minValue);
        calculate.add(firstQuartile);
        calculate.add(midValue);
        calculate.add(thirdQuartile);
        calculate.add(maxValue);
        boxData.setCalculate(calculate);
        boxData.setWarningList(warningList);
    }

    private Float getQuartile(List<Float> list, Float position) {
        double H = (list.size() - 1) * position + 1;
        double h = Math.floor(H);
        BigDecimal sub = BigDecimalUtils.sub(H, h);

        float v = list.get((int) h - 1);
        if (BigDecimal.ZERO.equals(sub)) {
            return v;
        } else {
            // v + (H - h) * (list[h] - v)
            return new BigDecimal(v).add(sub.multiply(BigDecimalUtils.sub(list.get((int) h), v))).floatValue();
        }
    }


}
