package com.adc.idea.sys.service;

import java.util.List;
import java.util.Set;

import com.adc.idea.sys.entity.SysMenu;

public interface SysMenuService {
	
	SysMenu selectByPk(Integer id);
	
	SysMenu selectMenuParent(Integer id);
	
	List<SysMenu> selectAll();
	
	List<SysMenu> selectNavigate(Integer userId);

	List<SysMenu> selectByUserId(Integer userId);
	
	Set<String> findPermissionsByUserId(Integer userId);
	
	void saveMenu(SysMenu menu);
	
	boolean insertMenu(SysMenu menu);
	
	boolean updateMenu(SysMenu menu);
	
	void delete(Integer id);
	
	List<String> getMenuIdByRoleId(Integer roleId);
}
