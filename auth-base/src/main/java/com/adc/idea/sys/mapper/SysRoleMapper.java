package com.adc.idea.sys.mapper;

import java.util.List;
import java.util.Map;

import com.adc.idea.common.mybatis.MyBatisRepository;
import com.adc.idea.sys.entity.SysRole;

@MyBatisRepository
public interface SysRoleMapper {
	
	int deleteByPrimaryKey(Integer id);

	int insertSelective(SysRole record);

	SysRole selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysRole record);

	List<Map<String, String>> selectRolesByUserId(Integer userId);
	
	List<Map<String, String>> selectNamesByUserId(Integer userId);
	
	List<Map<String, String>> selectUserIdsByRoleId(Integer roleId);
	
	List<SysRole> selectAll();
	
	int selectCountByName(String name);
	
	int selectCountByRole(String role);
	
	int deleteUserRoleByRoleId(Integer roleId);
	
	List<SysRole> selectRoleListByUserId(Integer userId);
	
}