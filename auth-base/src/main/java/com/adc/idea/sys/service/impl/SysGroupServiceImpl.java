package com.adc.idea.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adc.idea.common.page.PageHelper;
import com.adc.idea.common.page.PageModel;
import com.adc.idea.sys.entity.SysGroup;
import com.adc.idea.sys.mapper.SysGroupMapper;
import com.adc.idea.sys.service.SysGroupService;

@Service
public class SysGroupServiceImpl implements SysGroupService {

	@Autowired
	private SysGroupMapper groupMapper;
	@Autowired
	private PageHelper pageHelper;

	@Override
	public boolean save(SysGroup group) {

		SysGroup parent = this.getById(group.getParent().getId());
		group.setParentId(parent.getId());
		group.setParentIds(parent.getParentIds() + parent.getId() + ",");

		boolean flag = false;
		if (group.getId() != null) {
			flag = groupMapper.updateByPrimaryKeySelective(group) > 0 ? true : false;
		} else {
			flag = groupMapper.insertSelective(group) > 0 ? true : false;
		}
		return flag;
	}

	@Override
	public SysGroup getById(Integer groupId) {
		return groupMapper.selectByPrimaryKey(groupId);
	}

	@Override
	public List<SysGroup> selectList() {
		return groupMapper.selectList();
	}

	@Override
	public PageModel selectGroupPage(Map<String, String> params) {
		return pageHelper.getPage("com.adc.idea.sys.mapper.SysGroupMapper.selectPage", params);
	}

	@Override
	public SysGroup getParentById(Integer groupId) {
		return groupMapper.selectGroupParentByPrimaryKey(groupId);
	}

	@Override
	public boolean deleteByLogic(Integer groupId) {
		SysGroup group = new SysGroup();
		group.setId(groupId);
		group.setDeleted(SysGroup.GROUP_DELETED);
		return groupMapper.changeDeletedValue(group) > 0 ? true : false;
	}

	@Override
	public List<SysGroup> selectGroupsByRoleId(Integer roleId) {
		return groupMapper.selectGroupsByRoleId(roleId);
	}

}
