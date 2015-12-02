package com.adc.idea.sys.service;

import java.util.List;
import java.util.Map;

import com.adc.idea.common.page.PageModel;
import com.adc.idea.sys.entity.SysGroup;

public interface SysGroupService {

	boolean save(SysGroup group);

	SysGroup getById(Integer groupId);

	SysGroup getParentById(Integer groupId);

	List<SysGroup> selectList();

	PageModel selectGroupPage(Map<String, String> params);

	boolean deleteByLogic(Integer groupId);
	
	List<SysGroup> selectGroupsByRoleId(Integer roleId);
}
