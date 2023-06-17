package com.iware.common.constant;

import java.math.BigDecimal;

public class GlobalConstant {

	public static final BigDecimal SCORE_ZERO = new BigDecimal(0);


	public static final String SESSION_USER_KEY = "user_token";
	public static final String ROUTER_URL = "router_url";
	public static final String SESSION_ID = "session_id";

	/** redis中传感器前缀 key[sensor:meansPointId:sensorCoding:sensorDetailsId] value[Sensor] **/
	public static final String SENSOR_PREFIX = "sensor";
	/** redis中测点前缀 key[means_point:meansPointId] value[MeansPoint] **/
	public static final String MEANS_POINT_PREFIX = "means_point";
	/** redis中传感器频率类型 key[sensor_frequency] value[过期时间(可为空)]**/
	public static final String SENSOR_FREQUENCY = "sensor_frequency";
	/** redis中测点状态 key[means_point_status] value[status] **/
	public static final String SENSOR_STATUS = "means_point_status";
	/** redis中传感器是否失效 key[sensor_expire:meansPointId:sensorCoding:sensorDetailsId] value[expireTime] expire[frequency * 3] **/
	public static final String SENSOR_EXPIRE = "sensor_expire";
	/** redis中传感器上条预警前缀 key[warning:sensorCoding:sensorDetailsId] value[] expire[warningInterval] **/
	public static final String SENSOR_WARNING = "warning";

	/**
	 * 结构物类型
	 */
	public static final Integer TYPE_BRIDGE = 1;
	public static final Integer TYPE_TUNNEL = 2;

	/**
	 * The constant Y.
	 */
	public static final Integer Y = 1;
	public static final Integer N = 0;

	/**
	 * 请求头TOKEN、路由
	 */
	public static final String X_AUTH_TOKEN = "X-AUTH-TOKEN";
	public static final String X_ROUTER_URL = "X-ROUTER-URL";

	/**
	 * 图片类型
	 */
	public static final Integer PHOTO_BRIDGE = 1; //桥梁图片
	public static final Integer PHOTO_TUNNEL = 2; //隧道图片
	public static final Integer PHOTO_BRIDGE_ANNEX = 3; //桥梁附件
	public static final Integer PHOTO_TUNNEL_ANNEX = 4; //隧道附件
	public static final Integer PHOTO_INSPECTION = 5; //巡查病害图片
	public static final Integer PHOTO_MAINTAIN_BEFORE = 6; //维养前
	public static final Integer PHOTO_MAINTAIN_PROCESS = 7; //维养时
	public static final Integer PHOTO_MAINTAIN_AFTER = 8; //维养后

	public static final String SCRIPT_EXPORT_SENSOR_DATA = "export_sensor_data.sh";

	/**
	 * 传感器类型
	 */
	public static final Integer SENSOR_WEIGHT = 7; //称重传感器
	public static final Integer COMPANY_JK = 1; //基康
	public static final Integer COMPANY_DH = 2; //东华

	/**
	 * 时间格式
	 */
	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String HALF_DATE_FORMAT = "yyyy-MM-dd";


	public static final String LOCALHOST_IP = "127.0.0.1";

	public static final class Number {
		public static final Integer NUM_0 = 2 >> 0;
		public static final Integer NUM_1 = 2 >> 1;
		public static final Integer NUM_2 = 2 >> 2;
		public static final Integer NUM_3 = 2 >> 3;
		public static final Integer NUM_4 = 2 >> 4;
		public static final Integer NUM_5 = 2 >> 5;
		public static final Integer NUM_6 = 2 >> 6;
	}

	/**
	 * The class Symbol.
	 *
	 */
	public static final class Symbol {
		private Symbol() {
		}

		public static final String EMPTY = "";
		/**
		 * The constant COMMA.
		 */
		public static final String COMMA = ",";
		public static final String SPOT = ".";
		/**
		 * The constant UNDER_LINE.
		 */
		public final static String UNDER_LINE = "_";
		/**
		 * The constant PER_CENT.
		 */
		public final static String PER_CENT = "%";
		/**
		 * The constant AT.
		 */
		public final static String AT = "@";
		/**
		 * The constant PIPE.
		 */
		public final static String PIPE = "||";
		public final static String SHORT_LINE = "-";
		public final static String SPACE = " ";
		public static final String SLASH = "/";
		public static final String MH = ":";
		public static final String SPLIT_BR = "\n";//换行标识

		public static final String UNKNOW = "unknown";
	}


	/**
	 * 图片压缩高度和宽度
	 */
	public static final int IMAGE_WIDTH = 1920;
	/**
	 * The constant IMAGE_HEIGHT.
	 */
	public static final int IMAGE_HEIGHT = 1280;

}
