package com.iware.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckRepeat {
    /**
     * 需要防重复功能的表单入口URL对应的controller方法需要添加的注解，用于生成token（默认为uuid）
     * @return
     */
    boolean repeat() default false;

    /**
     * 防重复表单提交表单到后台对应的URL的controller方法需要添加的注解，用于第一次成功提交后remove掉token
     * @return
     */
    boolean needCheck() default true;
}
