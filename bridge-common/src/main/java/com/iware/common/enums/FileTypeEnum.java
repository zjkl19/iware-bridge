package com.iware.common.enums;

/**
 * 路径定义注意不要以/开头。在根路径已经包含
 *	路径要以/结尾
 * @author Administrator
 */

public enum FileTypeEnum implements EnumInterface {

    BRIDGE_ANNEX_FILE("annexFile/bridge/", "桥梁附件根路径"),
    TUNNEL_ANNEX_FILE("annexFile/tunnel/", "隧道附件根路径"),
    BRIDGE_PHOTO("photo/bridge/", "桥梁图片根路径"),
    TUNNEL_PHOTO("photo/tunnel/", "隧道图片根路径"),

    MAINTAIN_PHOTO("photo/maintain/","维修养护记录下的维修前中后照片路径"),
    INSPECTION_PHOTO("photo/inspection/","日常巡查照片路径"),

    SENSOR_PHOTO("photo/sensor/", "传感器照片路径"),

    ONLINE_REPORT("report/online/","在线监测报告路径"),
    INSPECTION_REPORT("report/inspection/","日常巡查报告路径"),
    MAINTAIN_REPORT("report/maintain/","巡查维养报告路径"),

    EVALUATION_PROGRAMME("evaluation/programme/", "检测计划结构物方案路径"),

    EXCEL_TEMP("excel/tmp/", "excel临时文件夹"),

    ELECTRONIC_FILE("electronic/", "电子档案存储位置")
    ;

    private String code;
    private String message;

    FileTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
