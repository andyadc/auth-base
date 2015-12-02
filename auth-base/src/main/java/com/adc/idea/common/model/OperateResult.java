package com.adc.idea.common.model;

import org.apache.commons.lang3.StringUtils;

import com.adc.idea.common.RespConstants;

/**
 * 操作返回结果
 * 
 * @author andaicheng
 * 
 */
public class OperateResult {

	public static String SUCCESS = "000";

	private String code;

	private String message;

	private Object object;

	public OperateResult() {
	}

	public OperateResult(String code) {
		this.code = code;
	}

	public OperateResult(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public OperateResult(String code, String message, Object object) {
		this.code = code;
		this.message = message;
		this.object = object;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public boolean isSuccess() {
		if (StringUtils.isNotBlank(this.getCode()))
			return this.getCode().equals(RespConstants.SUCCESS.getCode());
		return false;
	}

}
