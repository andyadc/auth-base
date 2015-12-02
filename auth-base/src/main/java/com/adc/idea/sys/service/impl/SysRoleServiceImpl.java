package com.adc.idea.sys.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adc.idea.sys.entity.SysRole;
import com.adc.idea.sys.mapper.SysMenuMapper;
import com.adc.idea.sys.mapper.SysRoleMapper;
import com.adc.idea.sys.service.SysRoleService;
import com.adc.idea.sys.utils.SystemUtils;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleMapper roleMapper;
	@Autowired
	private SysMenuMapper menuMapper;

	@Override
	public SysRole selectByPrimaryKey(Integer roleId) {
		return roleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public Set<String> findRolesByUserId(Integer userId) {
		List<Map<String, String>> rolesList = roleMapper.selectRolesByUserId(userId);
		if (CollectionUtils.isEmpty(rolesList)) {
			return null;
		}
		Set<String> roles = new HashSet<>();
		for (Map<String, String> map : rolesList) {
			roles.addAll(map.values());
		}
		return roles;
	}

	@Override
	public List<SysRole> selectAll() {
		return this.roleMapper.selectAll();
	}

	@Override
	public int getCountByName(String name) {
		return this.roleMapper.selectCountByName(name);
	}

	@Override
	public int getCountByRole(String role) {
		return this.roleMapper.selectCountByRole(role);
	}

	@Override
	@Transactional
	public void saveRole(SysRole role) {

		boolean flag = false;
		if (role.getId() == null) {
			flag = this.insertRole(role);
		} else {
			flag = this.updateRole(role);
		}

		if (flag) {
			// 清理所有角色列表缓存
			SystemUtils.removeAllRoleList();
			// 清理所有权限缓存
			if (role.getId() != null) {
				// 先删除之前的角色菜单
				deleteMenuIdsByRoleId(role.getId());
				//修改角色后清理权限集合
				removePermissionByRole(role);
			}
			// 添加新的角色菜单
			addRoleMenuList(role);
			//清理用户导航菜单
			SystemUtils.clearNavigate();
		}

	}
	
	/**
	 * 修改角色后清理权限集合
	 * @param role
	 */
	private void removePermissionByRole(SysRole role){
		Set<String> ids = this.findUserIdsByRoleId(role.getId());
		for (String id : ids) {
			SystemUtils.removePermissionsCache(Integer.parseInt(id));
		}
	}

	@Override
	public void addRoleMenuList(SysRole role) {
		if (CollectionUtils.isNotEmpty(role.getMenuList())) {
			this.menuMapper.insertRoleMenu(role);
		}
	}

	@Override
	public void deleteMenuIdsByRoleId(Integer roleId) {
		this.menuMapper.deleteMenuIdByRoleId(roleId);
	}

	@Override
	public boolean insertRole(SysRole role) {
		return this.roleMapper.insertSelective(role) > 0 ? true : false;
	}

	@Override
	public boolean updateRole(SysRole role) {
		return this.roleMapper.updateByPrimaryKeySelective(role) > 0 ? true : false;
	}

	@Override
	public void deleteRole(Integer roleId) {
		deleteMenuIdsByRoleId(roleId);
		deleteUserRoleByRoleId(roleId);
		this.roleMapper.deleteByPrimaryKey(roleId);
		SystemUtils.removeAllRoleList();
		SystemUtils.removeAllMenuList();
		SystemUtils.removeMenuList();
	}

	@Override
	public boolean deleteUserRoleByRoleId(Integer roleId) {
		return this.roleMapper.deleteUserRoleByRoleId(roleId) > 0 ? true : false;
	}

	@Override
	public Set<String> findNamesByUserId(Integer userId) {
		List<Map<String, String>> namesList = roleMapper.selectNamesByUserId(userId);
		if (CollectionUtils.isEmpty(namesList)) {
			return null;
		}
		Set<String> roles = new HashSet<>();
		for (Map<String, String> map : namesList) {
			roles.addAll(map.values());
		}
		return roles;
	}

	@Override
	public List<SysRole> selectRoleListByUserId(Integer userId) {
		return this.roleMapper.selectRoleListByUserId(userId);
	}

	@Override
	public Set<String> findUserIdsByRoleId(Integer roleId) {
		List<Map<String, String>> idsList = roleMapper.selectUserIdsByRoleId(roleId);
		if (CollectionUtils.isEmpty(idsList)) {
			return null;
		}
		Set<String> ids = new HashSet<>();
		for (Map<String, String> map : idsList) {
			ids.addAll(map.values());
		}
		return ids;
	}

}
