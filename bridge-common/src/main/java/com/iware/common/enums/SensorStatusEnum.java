package com.iware.common.enums;

public enum SensorStatusEnum {

    OFFLINE(0, "离线"),
    ONLINE(1, "在线"),
    FAULT(2, "故障");

    SensorStatusEnum(Integer code, String message) {
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
