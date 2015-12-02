package com.adc.idea.sys.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adc.idea.common.page.PageHelper;
import com.adc.idea.common.page.PageModel;
import com.adc.idea.sys.entity.SysUser;
import com.adc.idea.sys.mapper.SysUserMapper;
import com.adc.idea.sys.security.PasswordHelper;
import com.adc.idea.sys.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private PageHelper pageHelper;
	@Autowired
	private PasswordHelper passwordHelper;

	@Override
	public SysUser selectByAccount(String account) {
		return sysUserMapper.selectByAccount(account);
	}

	@Override
	public SysUser selectByPk(Integer id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public void saveUser(SysUser user) {
		if (StringUtils.isNotBlank(user.getPassword())) {
			passwordHelper.encryptPassword(user);
		}
		if (user.getId() == null) {
			this.sysUserMapper.insertSelective(user);
		} else {
			this.sysUserMapper.updateSysUser(user);
		}
	}

	@Override
	public boolean updatePassword(SysUser user) {
		passwordHelper.encryptPassword(user);
		return this.sysUserMapper.updatePasswordById(user) > 0 ? true : false;
	}

	@Override
	public PageModel selectUserPage(Map<String, String> params) {
		return pageHelper.getPage("com.adc.idea.sys.mapper.SysUserMapper.selectList", params);
	}

	@Override
	public boolean verifyPassword(SysUser user, String oldPassword) {
		return passwordHelper.verifyPassword(user, oldPassword);
	}

	@Override
	public boolean deleteUserLogic(Integer userId) {
		SysUser user = new SysUser(userId);
		user.setDeleted(SysUser.USER_DELETED);
		return this.sysUserMapper.changeDeletedValue(user) > 0 ? true : false;
	}

}
