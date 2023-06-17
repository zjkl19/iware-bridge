package com.iware.common.exception;

import com.iware.common.enums.EnumInterface;

/**
 * 登录异常
 * @author Administrator
 */

public class LoginException extends IllegalArgumentException{

	private static final long serialVersionUID = -591186551288279221L;

	protected EnumInterface errorCode;

    public LoginException(EnumInterface errorCode) {
        super(formatMsg(errorCode));
        this.errorCode = errorCode;
    }

    public EnumInterface getErrorCode() {
        return this.errorCode;
    }

    public static final String formatMsg(EnumInterface errorCode) {
        return String.format("%s:%s", errorCode.getCode(), errorCode.getMessage());
    }
}
