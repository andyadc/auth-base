package com.adc.idea.sys.service;

import java.util.Map;

import com.adc.idea.common.page.PageModel;
import com.adc.idea.sys.entity.SysUser;

public interface SysUserService {

	SysUser selectByAccount(String account);

	SysUser selectByPk(Integer id);

	void saveUser(SysUser user);

	boolean updatePassword(SysUser user);

	boolean verifyPassword(SysUser user, String oldPassword);
	
	PageModel selectUserPage(Map<String, String> params);
	
	/**
	 * 逻辑删除用户
	 */
	boolean deleteUserLogic(Integer userId);
}
