package com.softeem.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.softeem.encrypt.MD5;

/**
 * 工具类
 * @author Mr.Zhou
 *
 */
public class Tools {
	
	/**获取UUID
	 * @return UUID
	 */
	public static String getUID(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 将给定的字符串使用MD5加密
	 * @param str 需要加密的字符串
	 * @return MD5加密后的字符串
	 */
	public static String getMD5(String str){
		return new MD5().getMD5ofStr(str);
	}
	
	/**
	 * 将给定的date数据按照给定的格式转为字符串
	 * @param date   date时间
	 * @param str    转换格式
	 * @return   时间格式字符串
	 */
	public static String getDateToString(Date date,String str){
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(date);
	}
	

}

