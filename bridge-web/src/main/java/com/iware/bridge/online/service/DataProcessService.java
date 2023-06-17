package com.iware.bridge.online.service;

import com.alibaba.fastjson.JSONArray;

public interface DataProcessService {

    /** 保存东华传感器数据 */
    public void addSensorData(JSONArray data);

    /** 接收称重传感器数据的接口 */
    public void addWeighData(JSONArray res);

    /** 其他传感器数据 */
    public void addSocketData(String message);
}
