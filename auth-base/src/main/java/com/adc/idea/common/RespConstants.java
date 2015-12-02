package com.adc.idea.common;

public enum RespConstants {

	SUCCESS("000", "成功"),
	
	SYS_ERROR("500", "系统异常");
	
	private String code;
	
	private String msg;
	
	private RespConstants(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	public String getMsg(String desc) {
        return msg + ":" + desc;
    }
}
