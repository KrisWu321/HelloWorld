package com.softeem.dto;


/**
 * �����������洢���ظ�ǰ�˷ḻ�������(״̬,�����ʾ,����)
 * @author Administrator
 *
 */
public class ResultModel {
	private int code;//״ֵ̬ 0��ʾ�ɹ� 1��ʾʧ��
	private String msg;//��ʾ��
	private Object data;//���ز������ݿ��е�����
	public ResultModel() {
		super();
	}
	public ResultModel(int code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}

