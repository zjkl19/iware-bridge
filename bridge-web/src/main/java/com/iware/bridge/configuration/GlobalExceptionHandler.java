package com.iware.bridge.configuration;

import com.iware.common.enums.GlobalResultEnum;
import com.iware.common.exception.BusinessException;
import com.iware.common.exception.LoginException;
import com.iware.common.result.ResultBody;
import com.iware.common.utils.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	protected static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = LoginException.class)
    public ResultBody<String> handlerLoginException(HttpServletRequest request,
    		BusinessException exception) {
		logger.error(ExceptionUtils.getErrorStack(exception));
        return new ResultBody<>(GlobalResultEnum.UNAUTHORIZED);
    }

	@ExceptionHandler(value = BusinessException.class)
    public ResultBody<String> handlerBusinessException(HttpServletRequest request,
    		BusinessException exception) {
		logger.error(ExceptionUtils.getErrorStack(exception));
        return new ResultBody<>(exception.getExceptionCode(), exception.getMessage());
    }

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultBody<String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {
            logger.error(ExceptionUtils.getErrorStack(exception));
            return new ResultBody<>(GlobalResultEnum.INVALID_PARAM.getCode(),
                    result.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResultBody<>(GlobalResultEnum.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResultBody<String> handlerException(HttpServletRequest request,
                                           Exception exception) {
    	logger.error(ExceptionUtils.getErrorStack(exception));
        return new ResultBody<>(GlobalResultEnum.INTERNAL_SERVER_ERROR);
    }
}
