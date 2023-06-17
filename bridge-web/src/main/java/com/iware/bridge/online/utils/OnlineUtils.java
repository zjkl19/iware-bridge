package com.iware.bridge.online.utils;

import com.iware.common.constant.GlobalConstant;

public class OnlineUtils {


    /**
     * 格式 【sensor:meansPointId:sensorCoding:sensorDetailsId】
     */
    public static String getSensorKey(String... args) {
        return String.format("%s%s", GlobalConstant.SENSOR_PREFIX, getResultByArgs(args));
    }

    /**
     * 格式 【means_point:meansPointId】
     */
    public static String getMeansPointKey(String... args) {
        return String.format("%s%s", GlobalConstant.MEANS_POINT_PREFIX, getResultByArgs(args));
    }

    /**
     * 格式 【sensor_expire:meansPointId:sensorCoding:sensorDetailsId】
     */
    public static String getExpireKey(String... args) {
        return String.format("%s%s", GlobalConstant.SENSOR_EXPIRE, getResultByArgs(args));
    }

    /**
     * 格式 【warning:sensorCoding:sensorDetailsId】
     */
    public static String getWarningKey(String... args) {
        return String.format("%s%s", GlobalConstant.SENSOR_WARNING, getResultByArgs(args));
    }

    public static String getResultByArgs(String... args) {
        StringBuilder sb = new StringBuilder();
        if (args != null && args.length > 0) {
            for (String arg : args) {
                sb.append(GlobalConstant.Symbol.MH).append(arg);
            }
        }
        return sb.toString();
    }

}
