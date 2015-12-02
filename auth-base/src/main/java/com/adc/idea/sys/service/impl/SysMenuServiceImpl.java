package com.adc.idea.sys.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adc.idea.sys.entity.SysMenu;
import com.adc.idea.sys.mapper.SysMenuMapper;
import com.adc.idea.sys.service.SysMenuService;
import com.adc.idea.sys.utils.SystemUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Service
public class SysMenuServiceImpl implements SysMenuService {
	
	@Autowired
	private SysMenuMapper menuMapper;
	
	@Override
	public SysMenu selectByPk(Integer id) {
		return menuMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SysMenu> selectByUserId(Integer userId) {
		return menuMapper.selectByUserId(userId);
	}

	@Override
	public Set<String> findPermissionsByUserId(Integer userId) {
		List<Map<String, String>> list = menuMapper.selectPermissionsByUserId(userId);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		Set<String> permissions = Sets.newHashSet();
		for (Map<String, String> map : list) {
			if (CollectionUtils.isNotEmpty(map.values())) {
				permissions.addAll(map.values());
			}
		}
		return permissions;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveMenu(SysMenu menu) {
		
		SysMenu parent = this.selectByPk(menu.getParent().getId());
		menu.setParentId(parent.getId());
		menu.setParentIds(parent.getParentIds() + parent.getId() + "," );
		
		boolean flag = false;
		if (menu.getId() == null) {
			flag = this.insertMenu(menu);
		}else {
			flag = this.updateMenu(menu);
		}
		
		if (flag) {
			SystemUtils.removeAllMenuList();
			SystemUtils.removeMenuList();
		}
	}

	@Override
	public boolean insertMenu(SysMenu menu) {
		return this.menuMapper.insertSelective(menu) > 0 ? true : false;
	}

	@Override
	public boolean updateMenu(SysMenu menu) {
		return this.menuMapper.updateByPrimaryKeySelective(menu) > 0 ? true : false;
	}

	@Override
	public List<SysMenu> selectAll() {
		return this.menuMapper.selectAll();
	}

	@Override
	public SysMenu selectMenuParent(Integer id) {
		return this.menuMapper.selectMenuParentByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		this.menuMapper.deleteByParentId(id);
		this.menuMapper.deleteByPrimaryKey(id);
		
		SystemUtils.removeMenuList();
		SystemUtils.removeAllMenuList();
	}

	@Override
	public List<String> getMenuIdByRoleId(Integer roleId) {
		List<Map<String, String>> menuMaps = menuMapper.selectMenuIdByRoleId(roleId);
		List<String> menuIds = Lists.newArrayList();
		for (Map<String, String> menuMap : menuMaps) {
			menuIds.addAll(menuMap.values());
		}
		return menuIds;
	}

	@Override
	public List<SysMenu> selectNavigate(Integer userId) {
		return this.menuMapper.selectNavigate(userId);
	}

}
