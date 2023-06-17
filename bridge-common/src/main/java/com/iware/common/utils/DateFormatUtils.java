package com.iware.common.utils;

import java.util.Date;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * The Class DateFormateUtils.
 * @author hli
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateFormatUtils {

    /** The ymdhms. */
    public static final DateTimeFormatter ymdhms = DateTimeFormat.forPattern("yyMMddhhmmss");

    /** The ymdhmsByBar. */
    public static final DateTimeFormatter ymdhmsByBar = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    /** The y_m_d formatter. */
    public static final DateTimeFormatter ymdByBar = DateTimeFormat.forPattern("yyyy-MM-dd");

    /** The ymd formatter. */
    public static final DateTimeFormatter ymd = DateTimeFormat.forPattern("yyyyMMdd");
    /** The y_m_dhm formatter*/
    public static final DateTimeFormatter ymdhm = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");


    public static Date toDate(String dateStr, DateTimeFormatter format){
    	if(format == null || StringUtils.isBlank(dateStr)){
    		return null;
    	}
    	return format.parseDateTime(dateStr).toDate();
    }

    public static String toString(Date date, DateTimeFormatter format){
    	if(date == null || format == null){
    		return null;
    	}
    	return format.print(date.getTime());
    }

    /**
     * To ymdhms.
     *
     * @param timeMills the time mills
     * @return the string
     */
    public static String toYMDHMS(long timeMills) {
        return ymdhms.print(timeMills);
    }

    /**
     * To ymd.
     *
     * @param timeMills the time mills
     * @return the string
     */
    public static String toYMD(long timeMills) {
        return ymd.print(timeMills);
    }

    /**
     * To y_ m_ d.
     *
     * @param timeMills the time mills
     * @return the string
     */
    public static String toYMDByBar(long timeMills) {
        return ymdByBar.print(timeMills);
    }

    /**
     * To y_ m_ dhms.
     *
     * @param timeMills the time mills
     * @return the string
     */
    public static String toYMDHMSByBar(long timeMills) {
        return ymdhmsByBar.print(timeMills);
    }

    /**
     * To y_ m_ dhm
     *
     * @param timeMills the time mills
     * @return the string
     */
    public static String toYMDHMByBar(long timeMills){
    	return ymdhm.print(timeMills);
    }
}
