package com.adc.idea.sys.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adc.idea.common.web.BaseController;

@RequestMapping("${adminPath}/sys")
@Controller
public class TagController extends BaseController {

	@RequiresPermissions("user")
	@RequestMapping("iconSelect")
	public String iconSelect(HttpServletRequest request, Model model) {
		model.addAttribute("value", request.getAttribute("value"));
		return "sys/tagIconSelect";
	}
	
	@RequiresPermissions("user")
	@RequestMapping("treeSelect")
	public String treeSelect(HttpServletRequest request, Model model){
		model.addAttribute("url", request.getParameter("url")); 	// 树结构数据URL
		model.addAttribute("extId", request.getParameter("extId")); // 排除的编号ID
		model.addAttribute("checked", request.getParameter("checked")); // 是否可复选
		model.addAttribute("selectIds", request.getParameter("selectIds")); // 指定默认选中的ID
		model.addAttribute("isAll", request.getParameter("isAll")); 	// 是否读取全部数据，不进行权限过滤
		model.addAttribute("module", request.getParameter("module"));	// 过滤栏目模型（仅针对CMS的Category树）
		return "sys/tagTreeSelect";
	}
	
}
