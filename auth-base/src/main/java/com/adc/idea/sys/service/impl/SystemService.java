package com.adc.idea.sys.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adc.idea.sys.entity.SysMenu;
import com.adc.idea.sys.entity.SysOrganization;
import com.adc.idea.sys.entity.SysRole;
import com.adc.idea.sys.entity.SysUser;
import com.adc.idea.sys.mapper.SysUserMapper;
import com.adc.idea.sys.service.SysMenuService;
import com.adc.idea.sys.service.SysOrganizationService;
import com.adc.idea.sys.service.SysRoleService;

@Service("systemService")
public class SystemService {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysOrganizationService sysOrganizationService;
	
	public SysUser getSysUserByPk(Integer userId) {
		return sysUserMapper.selectByPrimaryKey(userId);
	}

	public SysUser selectSysUserByAccount(String account) {
		return this.sysUserMapper.selectByAccount(account);
	}

	public Set<String> findPermissionsByUserId(Integer userId) {
		return this.sysMenuService.findPermissionsByUserId(userId);
	}

	public Set<String> findRolesByUserId(Integer userId) {
		return this.sysRoleService.findRolesByUserId(userId);
	}
	
	public List<SysMenu> selectMenuListByUserId(Integer userId){
		return sysMenuService.selectByUserId(userId);
	}
	
	public List<SysMenu> selectAllMenuList() {
		return sysMenuService.selectAll();
	}
	
	public List<SysMenu> selectNavigate(Integer userId) {
		return sysMenuService.selectNavigate(userId);
	}
	
	public List<SysRole> selectAllRoleList() {
		return sysRoleService.selectAll();
	}
	
	public List<SysOrganization> selectAllOrgList() {
		return sysOrganizationService.selectAll(null);
	}
}
