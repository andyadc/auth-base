package com.adc.idea.sys.mapper;

import java.util.List;

import com.adc.idea.common.mybatis.MyBatisRepository;
import com.adc.idea.sys.entity.SysGroup;

@MyBatisRepository
public interface SysGroupMapper {

	int deleteByPrimaryKey(Integer id);

	int insertSelective(SysGroup record);

	SysGroup selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysGroup record);

	List<SysGroup> selectList();

	SysGroup selectGroupParentByPrimaryKey(Integer id);
	
	int changeDeletedValue(SysGroup record);
	
	List<SysGroup> selectGroupsByRoleId(Integer roleId);

}