package com.iware.bridge.online.service;

import com.iware.bridge.online.vo.*;

import java.util.List;

public interface SensorDataAnalysisService {

    /** 频率计算 */
    public List<SensorCalculateVO> frequency(SensorCalculateFilter sensorCalculateFilter);

    /** 获取频谱图 **/
    public List<SensorSpectrumVO> getSpectrum(SensorSpectrumFilter sensorSpectrumFilter);

    /** 相关性分析 **/
    public CorrelationAnalysisVO correlationAnalysis(SensorDataFilter sensorDataFilter);

    /** 箱型图分析 */
    public List<BoxData> boxAnalyse(SensorDataFilter filter);

}
