package com.adc.idea.common.model;

public class JsonResult {

	private String code;

	private String msg;

	public JsonResult(OperateResult result) {
		this.code = result.getCode();
		this.msg = result.getMessage();
	}

	public JsonResult(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public JsonResult() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
