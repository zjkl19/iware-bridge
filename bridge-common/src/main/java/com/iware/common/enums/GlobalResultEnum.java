package com.iware.common.enums;

/**
 * 全局接口异常枚举定义
 * @author Administrator
 */

public enum GlobalResultEnum implements EnumInterface {

    SUCCESS("0000", "Success"),

    FAILURE("9999", "Failure"),

    /**
     * 三位数的编码为异常整合httpcode编码
     */
    BAD_REQUEST("400", "请求异常,请联系管理员."),
    UNAUTHORIZED("401", "未授权,请联系管理员."),
    FORBIDDEN("403", "禁止访问,请联系管理员."),
    NOT_FOUND("404", "资源没找到,请联系管理员."),
    METHOD_NOT_ALLOWED("405", "方法不允许,请联系管理员."),
    INVALID_PARAM("406","参数不正确."),
    INTERNAL_SERVER_ERROR("500", "未知错误,请联系管理员."),
    CONNECT_ERROR("600", "请检查网络是否正常,请联系管理员."),
    FILE_TYPE_ERROR("700", "上传的格式不正确"),
    FILE_UPLOAD_ERROR("704", "上传文件失败"),
    FILE_LOAD_ERROR("701", "文件下载失败"),
    FILE_UN_COMPRESS_ERROR("702", "文件解压失败"),
    REPORT_GEN_ERROR("703","报告生出错误."),

    CACHE_NOT_FOUND("6100", "缓存模块未启用"),
    CACHE_ERROR("6101", "缓存模块配置出错"),
    CACHE_EXPIRE("6102", "请重新登录"),


    /**
     * 用户模块编码(6xxx)
     */
    ACCOUNT_PWD_ERROR("6000", "用户名密码错误"),
    ACCOUNT_STATUS_ERROR("6001", "您的帐号已被禁用"),
    BRIDGE_NOT_EXISTED("6002", "用户不存在"),
    BRIDGE_ACCOUNT_SYS_ERROR("6003", "用户无绑定,请绑定用户"),
	BRIDGE_PWD_ERROR("6004", "密码输入错误"),
	USER_EXIST_ERROR("6005", "已存在同名用户，请更换账号"),
    UNIT_EXIST_ERROR("6005", "已存在同名单位，请更换名称"),
	STRUCTURE_NAME_EXIST_ERROR("6006", "已存在同名结构物，请更换名称"),
    STRUCTURE_CODE_EXIST_ERROR("6007", "已存在同代码结构物，请更换代码"),
	SENSOR_CODING_EXIST_ERROR("6012","数据读取编码已被应用，请更换数据读取编码"),
    SENSOR_NAME_EXIST_ERROR("6013","测点编号已被应用，请更换测点编号"),

    PROJECT_EXIST_ERROR("6014", "已存在同名项目，请更换项目名称"),
    APPOINT_BUSINESS_DUP_ERROR("6015", "业务在指定时间内不可重复指派，请重新选择"),
    APPOINT_STRUCTURE_POWER_DUP_ERROR("6016", "项目下桥梁已在其他项目指派相同业务！"),
    APPOINT_BUSINESS_BEYOND_DATE("6017", "指派时间超出自己接受指派时间，请重新选择"),


    DATA_ERROR("2400", "数据出错"),
	/**
	 * 服务调用模块
	 */
	BRIDEG_HTTP_REQUEST_ERROR("6020","调用服务接口失败"),
	BRIDEG_HTTP_URL_NOT_FOUND("6021","服务地址未找到"),

	/**
	 * 日常巡查记录模块
	 * @param code
	 * @param message
	 */
	DAILY_INSPECTION_INSPECTOR_REPEAT_ERROR("6030","该巡查人今日已巡查该桥梁");

    GlobalResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;

    private final String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
