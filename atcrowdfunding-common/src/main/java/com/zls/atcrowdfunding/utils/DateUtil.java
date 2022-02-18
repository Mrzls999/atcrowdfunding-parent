package com.zls.atcrowdfunding.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

	public static String format(Date d, String f) {
		SimpleDateFormat sdf = new SimpleDateFormat(f);
		return sdf.format(d);
	}

	public static String getFormatTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	public static String getFormatTime(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}

}
