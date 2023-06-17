package com.iware.bridge.video.vo;

import java.util.Map;

/**
 * 萤石云token变量
 */
public class YingshiYunToken {
    /**
     * 获取萤石云的accessToken
     * param accessToken ,expiretime
     * @return
     */

    public static final String EXPIRE_TIME = "expireTime";

    public static final String ACCESS_TOKEN = "accessToken";

    public static Map<String,String> YingYunAcessToken;

    public static String get(String key) {
        return YingYunAcessToken.get(key);
    }


    public static void put(String key, String value) {
        YingYunAcessToken.put(key, value);
    }
}
