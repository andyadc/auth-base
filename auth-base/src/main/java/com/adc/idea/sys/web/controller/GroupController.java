package com.adc.idea.sys.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adc.idea.common.page.PageModel;
import com.adc.idea.common.web.BaseController;
import com.adc.idea.sys.entity.SysGroup;
import com.adc.idea.sys.service.SysGroupService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RequestMapping("${adminPath}/sys/group")
@Controller
public class GroupController extends BaseController {

	@Autowired
	private SysGroupService groupService;

	@RequestMapping({ "", "/", "index" })
	public String index(Model model) {
		return "sys/group/groupIndex";
	}

	@RequestMapping("list")
	public String list(Model model, HttpServletRequest request) {
		Map<String, String> reqParams = this.getRequestParams(request);
		PageModel page = groupService.selectGroupPage(reqParams);

		model.addAttribute("page", page);
		model.addAttribute("params", reqParams);
		return "sys/group/groupList";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Model model) {
		return "sys/group/groupAdd";
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = true) Integer id, Model model, RedirectAttributes attributes) {
		SysGroup group = groupService.getParentById(id);
		if (group != null) {
			model.addAttribute("group", group);
			return "sys/group/groupEdit";
		}
		addMessage(attributes, "用户组不存在");
		return redirectToUrl("list");
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(SysGroup group, Model model, RedirectAttributes attributes) {
		if (!beanValidator(model, group)) {
			return this.add(model);
		}

		groupService.save(group);
		addMessage(attributes, "保存用户组 " + group.getName() + " 成功");
		return redirectToUrl("list");
	}

	@RequestMapping(value = "delete")
	public String delete(@RequestParam(required = true) Integer id, RedirectAttributes attributes) {
		groupService.deleteByLogic(id);
		addMessage(attributes, "删除用户组成功");
		return redirectToUrl("list");
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping("treeData")
	public List<Map<String, Object>> treeData() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<SysGroup> groups = groupService.selectList();
		for (SysGroup group : groups) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", group.getId());
			map.put("pId", group.getParentId());
			map.put("name", group.getName());

			mapList.add(map);
		}
		return mapList;
	}

}
