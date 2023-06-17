package com.iware.common.utils;

import com.alibaba.fastjson.JSON;
import com.iware.common.enums.EnumInterface;
import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.pojo.CustomPageInfo;
import com.iware.common.result.ResultBody;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultUtil {

    private static final Logger logger = LoggerFactory.getLogger(ResultUtil.class);
    public static ResultBody success(){
        return vo(GlobalResultEnum.SUCCESS.getCode(), GlobalResultEnum.SUCCESS.getMessage(), null);
    }
    public static ResultBody success(String msg){
        return vo(GlobalResultEnum.SUCCESS.getCode(), msg, null);
    }

    public static ResultBody success(Object data){
        return vo(GlobalResultEnum.SUCCESS.getCode(), "请求成功", data);
    }

    public static ResultBody success(String msg, Object data){
        return vo(GlobalResultEnum.SUCCESS.getCode(), msg, data);
    }

    public static ResultBody error(){
        return vo(GlobalResultEnum.FAILURE.getCode(), null, null);
    }

    public static ResultBody error(String msg){
        return vo(GlobalResultEnum.FAILURE.getCode(), msg, null);
    }

    public static ResultBody error(Object data){
        return vo(GlobalResultEnum.FAILURE.getCode(), null, data);
    }

    public static ResultBody error(String msg, Object data){
        return vo(GlobalResultEnum.FAILURE.getCode(), msg, data);
    }

    public static ResultBody error(GlobalResultEnum code){
        return vo(code.getCode(), code.getMessage(), null);
    }

    public static ResultBody error(GlobalResultEnum code, Object data){
        return vo(code.getCode(), code.getMessage(), data);
    }

    public static ResultBody custom(EnumInterface code){
        return vo(code.getCode(), code.getMessage(), null);
    }

    public static ResultBody custom(EnumInterface code, Object data){
        return vo(code.getCode(), code.getMessage(), data);
    }

    public static CustomPageInfo pageInfo(List<?> list, Long total){
        return new CustomPageInfo(list,null,null, total);
    }

    public static CustomPageInfo pageInfo(List<?> list, Integer pageSize, Integer pageNum, Long total){
        return new CustomPageInfo(list,pageSize,pageNum, total);
    }

    public static ResultBody vo(String code, String message, Object data) {
        return new ResultBody<>(code,message,data);
    }

    public static void resultOut(ServletRequest request, ServletResponse response, GlobalResultEnum resultEnum) {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse) response;
        try {
            resp.setHeader("Access-Control-Allow-Origin",  req.getHeader("Origin"));
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            resp.setContentType("application/json; charset=utf-8");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(JSON.toJSON(ResultUtil.error(resultEnum)));
            resp.getWriter().flush();
            resp.getWriter().close();
        } catch (Exception e) {
            logger.info("KickoutSessionFilter.class 输出JSON异常，可以忽略。");
        }finally {
            try {
                if(resp.getWriter() != null){
                    resp.getWriter().close();
                }
            } catch (IOException e) {
                logger.error(ExceptionUtils.getErrorStack(e));
            }
        }
    }
}
