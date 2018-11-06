package com.softeem.dto;


/**
 * 该类是用来存储返回给前端丰富结果集的(状态,相关提示,数据)
 * @author Administrator
 *
 */
public class ResultModel {
	private int code;//状态值 0表示成功 1表示失败
	private String msg;//提示语
	private Object data;//返回查找数据库中的数据
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

