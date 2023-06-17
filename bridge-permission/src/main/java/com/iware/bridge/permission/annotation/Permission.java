package com.iware.bridge.permission.annotation;

import com.iware.bridge.permission.enums.ActionTypeEnum;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

    /**
     * 权限操作类型
     * @return
     */
    ActionTypeEnum actionType() default ActionTypeEnum.ACTION_UDY;
}
