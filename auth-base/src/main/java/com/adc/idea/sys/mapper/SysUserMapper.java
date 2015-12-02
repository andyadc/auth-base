package com.adc.idea.sys.mapper;

import java.util.List;
import java.util.Map;

import com.adc.idea.common.mybatis.MyBatisRepository;
import com.adc.idea.sys.entity.SysUser;

@MyBatisRepository
public interface SysUserMapper {

	int deleteByPrimaryKey(Integer id);

	int insertSelective(SysUser record);

	SysUser selectByPrimaryKey(Integer id);
	
	SysUser selectByAccount(String account);

	int updateByPrimaryKeySelective(SysUser record);
	
	int updateSysUser(SysUser record);

	List<SysUser> selectList(Map<String, ? extends Object> params);

	int updatePasswordById(SysUser user);
	
	int changeDeletedValue(SysUser user);
}