package com.adc.idea.sys.mapper;

import java.util.List;

import com.adc.idea.common.mybatis.MyBatisRepository;
import com.adc.idea.sys.entity.SysOrganization;

@MyBatisRepository
public interface SysOrganizationMapper {
	
	int deleteByPrimaryKey(Integer id);

	int insertSelective(SysOrganization record);

	SysOrganization selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysOrganization record);

	List<SysOrganization> selectList(SysOrganization organization);
}