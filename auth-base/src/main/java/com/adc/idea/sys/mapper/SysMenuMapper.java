package com.adc.idea.sys.mapper;

import java.util.List;
import java.util.Map;

import com.adc.idea.common.mybatis.MyBatisRepository;
import com.adc.idea.sys.entity.SysMenu;
import com.adc.idea.sys.entity.SysRole;

@MyBatisRepository
public interface SysMenuMapper {
	
	int deleteByPrimaryKey(Integer id);
	
	int deleteByParentId(Integer id);

	int insertSelective(SysMenu record);

	SysMenu selectByPrimaryKey(Integer id);
	
	SysMenu selectMenuParentByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysMenu record);
	
	int updateParentIds(SysMenu menu);
	
	int updateSort(SysMenu menu);
	
	List<SysMenu> selectAll();
	
	List<SysMenu> selectNavigate(Integer userId);

	List<SysMenu> selectByUserId(Integer userId);
	
	List<Map<String, String>> selectPermissionsByUserId(Integer userId);
	
	List<Map<String, String>> selectMenuIdByRoleId(Integer roleId);
	
	int deleteMenuIdByRoleId(Integer roleId);
	
	int insertRoleMenu(SysRole role);
}