package com.iware.common.enums;

public enum UserRoleEnum {

    ADMIN(3, "admin超级用户"),

    OWNER(2, "业主用户"),
    UNDERTAKING_MAIN(1, "承接单位主用户"),
    UNDERTAKING_NORMAL(0, "承接单位普通用户");

    UserRoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private final Integer code;

    private final String message;

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
