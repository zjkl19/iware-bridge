package com.iware.common.utils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * 生成文件名
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UUIDUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
