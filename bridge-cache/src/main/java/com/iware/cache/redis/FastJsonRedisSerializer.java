package com.iware.cache.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private Class<T> clazz;

    //添加序列化白名单
    static {
        ParserConfig.getGlobalInstance().addAccept("com.iware.bridge.model.entity.online.Sensor");
        ParserConfig.getGlobalInstance().addAccept("com.iware.bridge.online.vo.SensorVO");
        ParserConfig.getGlobalInstance().addAccept("com.iware.bridge.model.entity.online.MeansPoint");
        ParserConfig.getGlobalInstance().addAccept("com.iware.bridge.online.vo.MeansPointVO");
    }

    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return (T) JSON.parseObject(str, clazz);
    }

}
