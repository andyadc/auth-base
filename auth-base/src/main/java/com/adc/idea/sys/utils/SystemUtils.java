package com.adc.idea.sys.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adc.idea.common.utils.cache.EhCacheUtils;
import com.adc.idea.common.utils.spring.SpringContextHolder;
import com.adc.idea.sys.entity.SysMenu;
import com.adc.idea.sys.entity.SysOrganization;
import com.adc.idea.sys.entity.SysRole;
import com.adc.idea.sys.entity.SysUser;
import com.adc.idea.sys.security.SystemAuthorizingRealm.ShiroUser;
import com.adc.idea.sys.service.impl.SystemService;

@SuppressWarnings("unchecked")
public class SystemUtils {

	private static Logger logger = LoggerFactory.getLogger(SystemUtils.class);

	private static SystemService systemService = SpringContextHolder.getBean(SystemService.class);

	private static final String MENU_CACHE_PRE = "menuList_";
	private static final String PERMISSIONS_CACHE_PRE = "permissions_";
	private static final String ROLES_CACHE_PRE = "roles_";
	private static final String ALL_MENUS_CACHE = "all_menu_list";
	private static final String ALL_ROLES_CACHE = "all_role_list";
	private static final String ALL_ORG_CACHE = "all_org_list";
	private static final String NAVIGATE_MENU_PRE = "navigate_menu_";

	/**
	 * 获取所有组织结构
	 * 
	 * @return
	 */
	public static List<SysOrganization> getAllOrgList() {
		List<SysOrganization> allOrgList = (List<SysOrganization>) getCache(ALL_ORG_CACHE);
		if (allOrgList == null) {
			logger.info("can not load all orgList cache from cache, now loading from db");
			allOrgList = systemService.selectAllOrgList();
			if (CollectionUtils.isEmpty(allOrgList))
				return null;
			put(ALL_ORG_CACHE, allOrgList);
		}
		return allOrgList;
	}

	/**
	 * 清理组织机构缓存
	 */
	public static void removeAllOrgListCache() {
		List<SysOrganization> allOrgList = (List<SysOrganization>) getCache(ALL_ORG_CACHE);
		if (allOrgList != null)
			remove(ALL_ORG_CACHE);
	}

	/**
	 * 获取所有菜单
	 * 
	 * @return
	 */
	public static List<SysMenu> getAllMenuList() {
		List<SysMenu> allMenuList = (List<SysMenu>) getCache(ALL_MENUS_CACHE);
		if (allMenuList == null) {
			logger.info("can not load all menuList cache from cache, now loading from db");
			allMenuList = systemService.selectAllMenuList();
			if (CollectionUtils.isEmpty(allMenuList))
				return null;
			put(ALL_MENUS_CACHE, allMenuList);
		}
		return allMenuList;
	}

	/**
	 * 获取用户导航菜单
	 * 
	 * @return
	 */
	public static List<SysMenu> getNavigate() {
		Integer usrId = getShiroUser().getId();
		List<SysMenu> menuList = (List<SysMenu>) getCache(NAVIGATE_MENU_PRE + usrId);
		if (menuList == null) {
			logger.info("can not load all menuList cache from cache, now loading from db");
			menuList = systemService.selectNavigate(usrId);
			if (CollectionUtils.isEmpty(menuList))
				return null;
			put(NAVIGATE_MENU_PRE + usrId, menuList);
		}
		return menuList;
	}
	
	/**
	 * 清空用户导航菜单
	 */
	public static void clearNavigate(){
		Integer usrId = getShiroUser().getId();
		List<SysMenu> menuList = (List<SysMenu>) getCache(NAVIGATE_MENU_PRE + usrId);
		if (menuList != null) {
			remove(NAVIGATE_MENU_PRE + usrId);
		}
	}

	/**
	 * 从缓存中清除所有菜单列表
	 */
	public static void removeAllMenuList() {
		List<SysMenu> allMenuList = (List<SysMenu>) getCache(ALL_MENUS_CACHE);
		if (allMenuList != null) {
			remove(ALL_MENUS_CACHE);
		}
	}

	/**
	 * 得到所有角色列表
	 * 
	 * @return
	 */
	public static List<SysRole> getAllRoleList() {
		List<SysRole> allRoleList = (List<SysRole>) getCache(ALL_ROLES_CACHE);
		if (allRoleList == null) {
			logger.info("can not load all roleList cache from cache, now loading from db");
			allRoleList = systemService.selectAllRoleList();
			if (CollectionUtils.isEmpty(allRoleList))
				return null;
			put(ALL_ROLES_CACHE, allRoleList);
		}
		return allRoleList;
	}

	/**
	 * 从缓存中清除所有角色列表
	 */
	public static void removeAllRoleList() {
		List<SysRole> allRoleList = (List<SysRole>) getCache(ALL_ROLES_CACHE);
		if (allRoleList != null) {
			remove(ALL_ROLES_CACHE);
		}
	}

	/**
	 * 得到当前登录用户菜单列表
	 * 
	 * @return
	 */
	public static List<SysMenu> getMenuList() {
		Integer usrId = getShiroUser().getId();
		List<SysMenu> menuList = (List<SysMenu>) getCache(MENU_CACHE_PRE + usrId);
		if (menuList == null) {
			logger.info("can not load user 【" + usrId + "】 menuList cache from cache, now loading from db");
			menuList = systemService.selectMenuListByUserId(usrId);
			if (CollectionUtils.isEmpty(menuList))
				return null;
			put(MENU_CACHE_PRE + usrId, menuList);
		}
		return menuList;
	}

	/**
	 * 清理当前登录用户菜单列表
	 */
	public static void removeMenuList() {
		Integer usrId = getShiroUser().getId();
		List<SysMenu> menuList = (List<SysMenu>) getCache(MENU_CACHE_PRE + usrId);
		if (menuList != null) {
			remove(MENU_CACHE_PRE + usrId);
		}
	}

	/**
	 * 获取用户权限集合
	 * 
	 * @param userId
	 * @return
	 */
	public static Set<String> getPermissionsByUserId(Integer userId) {
		Set<String> permissions = (Set<String>) getCache(PERMISSIONS_CACHE_PRE + userId);
		if (permissions == null) {
			logger.info("can not load user 【" + userId + "】 permissions cache from cache, now loading from db");
			permissions = systemService.findPermissionsByUserId(userId);
			if (CollectionUtils.isEmpty(permissions))
				return new HashSet<>();
			permissions.add("user");
			put(PERMISSIONS_CACHE_PRE + userId, permissions);
		}
		return permissions;
	}

	/**
	 * 清理指定用户权限缓存
	 * 
	 * @param userId
	 */
	public static void removePermissionsCache(Integer userId) {
		Set<String> permissions = (Set<String>) getCache(PERMISSIONS_CACHE_PRE + userId);
		if (permissions != null) {
			remove(PERMISSIONS_CACHE_PRE + userId);
		}
	}

	/**
	 * 清理当前登录用户权限缓存
	 */
	public static void removePermissionsCache() {
		removePermissionsCache(getSysUser().getId());
	}

	/**
	 * 获取用户角色集合
	 * 
	 * @param userId
	 * @return
	 */
	public static Set<String> getRolesByUserId(Integer userId) {
		Set<String> roles = (Set<String>) getCache(ROLES_CACHE_PRE + userId);
		if (roles == null) {
			logger.info("can not load user roles cache from ehcache, now loading from db");
			roles = systemService.findRolesByUserId(userId);
			if (CollectionUtils.isEmpty(roles))
				return null;
			put(ROLES_CACHE_PRE + userId, roles);
		}
		return roles;
	}

	/**
	 * 清理指定用户角色缓存
	 * 
	 * @param userId
	 */
	public static void removeRolesCache(Integer userId) {
		Set<String> roles = (Set<String>) getCache(ROLES_CACHE_PRE + userId);
		if (roles != null) {
			remove(ROLES_CACHE_PRE + userId);
		}
	}

	/**
	 * 清理当前登录用户角色缓存
	 */
	public static void removeRolesCache() {
		removeRolesCache(getSysUser().getId());
	}

	public static SysUser get(Integer id) {
		return systemService.getSysUserByPk(id);
	}

	/**
	 * 获取当前用户
	 */
	public static SysUser getSysUser() {
		ShiroUser shiroUser = getShiroUser();
		if (shiroUser != null) {
			SysUser user = get(shiroUser.getId());
			if (user != null)
				return user;
		}
		return new SysUser();
	}

	/**
	 * 获取当前登录者对象
	 */
	public static ShiroUser getShiroUser() {
		Subject subject = getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		if (shiroUser != null)
			return shiroUser;
		return null;
	}

	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static Session getSession() {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(false);
		if (session == null)
			session = subject.getSession();
		if (session != null)
			return session;
		return null;
	}

	// ===============Sys Cache================

	public static Object getCache(String key) {
		return EhCacheUtils.get(key);
	}

	public static void put(String key, Object object) {
		EhCacheUtils.put(key, object);
	}

	public static void remove(String key) {
		EhCacheUtils.remove(key);
	}
}
