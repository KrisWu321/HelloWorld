package com.softeem.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.softeem.encrypt.MD5;

/**
 * ������
 * @author Mr.Zhou
 *
 */
public class Tools {
	
	/**��ȡUUID
	 * @return UUID
	 */
	public static String getUID(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * ���������ַ���ʹ��MD5����
	 * @param str ��Ҫ���ܵ��ַ���
	 * @return MD5���ܺ���ַ���
	 */
	public static String getMD5(String str){
		return new MD5().getMD5ofStr(str);
	}
	
	/**
	 * ��������date���ݰ��ո����ĸ�ʽתΪ�ַ���
	 * @param date   dateʱ��
	 * @param str    ת����ʽ
	 * @return   ʱ���ʽ�ַ���
	 */
	public static String getDateToString(Date date,String str){
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(date);
	}
	

}

