package com.adc.idea.sys.service;

import java.util.List;
import java.util.Set;

import com.adc.idea.sys.entity.SysRole;

public interface SysRoleService {

	SysRole selectByPrimaryKey(Integer roleId);
	
	Set<String> findRolesByUserId(Integer userId);
	
	Set<String> findNamesByUserId(Integer userId);
	
	Set<String> findUserIdsByRoleId(Integer roleId);
	
	List<SysRole> selectAll();
	
	List<SysRole> selectRoleListByUserId(Integer userId);
	
	int getCountByName(String name);
	
	int getCountByRole(String role);
	
	void saveRole(SysRole role);
	
	void addRoleMenuList(SysRole role);
	
	void deleteMenuIdsByRoleId(Integer roleId);
	
	boolean insertRole(SysRole role);
	
	boolean updateRole(SysRole role);
	
	void deleteRole(Integer roleId);
	
	boolean deleteUserRoleByRoleId(Integer roleId);
	
}
