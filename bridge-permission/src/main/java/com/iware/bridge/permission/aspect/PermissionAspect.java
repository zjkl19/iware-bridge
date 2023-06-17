package com.iware.bridge.permission.aspect;

import com.alibaba.fastjson.JSONObject;
import com.iware.bridge.model.entity.user.Power;
import com.iware.bridge.permission.annotation.Permission;
import com.iware.bridge.permission.enums.ActionTypeEnum;
import com.iware.common.data.ThreadLocalMap;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.utils.ExceptionUtils;
import com.iware.bridge.permission.dao.PermissionDao;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;

@Aspect
@Component
@Order(0)
public class PermissionAspect {

    private final Logger logger = LoggerFactory.getLogger(PermissionAspect.class);

    @Autowired
    private PermissionDao permissionDao;

    @Pointcut("@annotation(com.iware.bridge.permission.annotation.Permission)")
    public void permission() {
        //do nothing
    }

    @Before("permission()")
    public void doBefore(JoinPoint joinPoint) {

        ActionTypeEnum actionTypeEnum = ActionTypeEnum.ACTION_UDY;
        try{
            //从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            logger.debug("log method:{}", method.getName());

            //获取注解对象
            Permission permission = method.getAnnotation(Permission.class);
            logger.debug("log annotation:{}", JSONObject.toJSONString(permission));

            actionTypeEnum = permission.actionType();


        }catch(Exception e){
            logger.error(ExceptionUtils.getErrorStack(e));
        }

        List<Power> powerInfos = permissionDao.getByUserIdAndUrl(ThreadLocalMap.getRoleId(), ThreadLocalMap.getUnitId(),
                actionTypeEnum.getName(),ThreadLocalMap.getRouterUrl());
        if (CollectionUtils.isEmpty(powerInfos)) {
            throw new BusinessException(GlobalResultEnum.UNAUTHORIZED);
        }
    }

}
