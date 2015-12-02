package com.adc.idea.sys.web.controller;

import java.util.List;
import java.util.Map;

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
import com.adc.idea.sys.entity.SysMenu;
import com.adc.idea.sys.service.SysMenuService;
import com.adc.idea.sys.utils.SystemUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RequestMapping("${adminPath}/sys/menu")
@Controller
public class MenuController extends BaseController {

	@Autowired
	private SysMenuService menuService;

	@RequiresPermissions("sys:menu:view")
	@RequestMapping({ "", "/", "list" })
	public String list(Model model) {
		List<SysMenu> menus = Lists.newArrayList();
		List<SysMenu> menuList = SystemUtils.getAllMenuList();
		SysMenu.sortList(menus, menuList, SysMenu.getRootId(), true);
		model.addAttribute("menuList", menus);
		return "sys/menu/menuList";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Model model) {
		return "sys/menu/menuAdd";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(SysMenu menu, Model model, RedirectAttributes redirectAttributes) {
		
		if (!beanValidator(model, menu)) {
			return this.add(model);
		}

		menuService.saveMenu(menu);
		addMessage(redirectAttributes, "保存菜单 " + menu.getName() + " 成功");
		return redirectToUrl("list");
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String form(@RequestParam(required = true) Integer id, Model model){
		SysMenu menu = menuService.selectMenuParent(id);
		
		model.addAttribute("menu", menu);
		return "sys/menu/menuEdit";
	}
	
	@RequestMapping(value = "addSubMenu", method = RequestMethod.GET)
	public String form(SysMenu menu, Model model){
		menu.setParent(this.menuService.selectByPk(menu.getParent().getId()));
		model.addAttribute("menu", menu);
		return "sys/menu/menuEdit";
	}
	
	@RequestMapping(value = "delete")
	public String delete(@RequestParam(required = true) Integer id, RedirectAttributes redirectAttributes){
		this.menuService.delete(id);
		addMessage(redirectAttributes, "删除菜单成功");
		return redirectToUrl("list");
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping("treeData")
	public List<Map<String, Object>> treeData() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<SysMenu> menus = SystemUtils.getAllMenuList();
		for (SysMenu menu : menus) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", menu.getId());
			map.put("pId", menu.getParentId());
			map.put("name", menu.getName());

			mapList.add(map);
		}
		return mapList;
	}

}
