package com.adc.idea.sys.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class PasswordDTO implements Serializable {

	private static final long serialVersionUID = -5925506919564581036L;

	private String oldPassword;
	private String newPassword;
	private String confirmNewPassword;

	public boolean checkPwd() {
		if (StringUtils.isBlank(this.getOldPassword()) || StringUtils.isBlank(this.getNewPassword())
				|| StringUtils.isBlank(this.getConfirmNewPassword())) {
			return false;
		}
		if (!this.getNewPassword().equals(this.getConfirmNewPassword())) {
			return false;
		}
		return true;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

}
