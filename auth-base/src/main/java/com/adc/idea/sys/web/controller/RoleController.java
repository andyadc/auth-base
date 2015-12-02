package com.adc.idea.sys.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adc.idea.common.web.BaseController;
import com.adc.idea.sys.entity.SysGroup;
import com.adc.idea.sys.entity.SysRole;
import com.adc.idea.sys.service.SysGroupService;
import com.adc.idea.sys.service.SysMenuService;
import com.adc.idea.sys.service.SysRoleService;
import com.adc.idea.sys.utils.SystemUtils;

@RequestMapping("${adminPath}/sys/role")
@Controller
public class RoleController extends BaseController {

	@Autowired
	private SysRoleService roleService;
	@Autowired
	private SysMenuService menuService;
	@Autowired
	private SysGroupService groupService;

	@RequestMapping({ "", "/", "list" })
	public String list(Model model) {
		List<SysRole> roleList = SystemUtils.getAllRoleList();
		model.addAttribute("roleList", roleList);
		return "sys/role/roleList";
	}
	
	/**
	 * 角色分配
	 */
	@RequestMapping(value = "assign")
	public String assign(@RequestParam(required = true) Integer id, Model model){
		SysRole role = roleService.selectByPrimaryKey(id);
		List<SysGroup> groups = groupService.selectGroupsByRoleId(id);
		
		model.addAttribute("role", role);
		model.addAttribute("groups", groups);
		return "sys/role/roleAssign";
	}

	@RequestMapping("add")
	public String add(Model model) {
		model.addAttribute("menuList", SystemUtils.getAllMenuList());
		return "sys/role/roleAdd";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(SysRole role, Model model, RedirectAttributes redirectAttributes) {
		
		if (!beanValidator(model, role)) {
			return add(model);
		}
		roleService.saveRole(role);
		
		addMessage(redirectAttributes, "保存角色" + role.getName() + "成功");
		return redirectToUrl("list");
	}
	
	@RequestMapping(value = "edit")
	public String form(@RequestParam(required  = true) Integer id, Model model){
		SysRole role = roleService.selectByPrimaryKey(id);
		List<String> menuIdList = menuService.getMenuIdByRoleId(role.getId());
		role.setMenuIdList(menuIdList);
		
		model.addAttribute("menuList", SystemUtils.getAllMenuList());
		model.addAttribute("role", role);
		return "sys/role/roleEdit";
	}
	
	@RequestMapping(value = "delete")
	public String delete(@RequestParam(required  = true) Integer id, RedirectAttributes redirectAttributes){
		roleService.deleteRole(id);
		addMessage(redirectAttributes, "删除角色成功");
		return redirectToUrl("list");
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping("checkNameExist")
	public String checkNameExist(String oldName, String name) {
		if (StringUtils.isNotBlank(name) && name.equals(oldName)) {
			return "true";
		} else if (StringUtils.isNotBlank(name) && this.roleService.getCountByName(name) == 0) {
			return "true";
		}
		return "false";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping("checkRoleExist")
	public String checkRoleExist(String oldRole, String role) {
		if (StringUtils.isNotBlank(role) && role.equals(oldRole)) {
			return "true";
		} else if (StringUtils.isNotBlank(role) && this.roleService.getCountByRole(role) == 0) {
			return "true";
		}
		return "false";
	}

}
