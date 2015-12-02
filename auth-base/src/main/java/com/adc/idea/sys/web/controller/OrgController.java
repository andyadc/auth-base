package com.adc.idea.sys.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adc.idea.common.web.BaseController;
import com.adc.idea.sys.entity.SysOrganization;
import com.adc.idea.sys.utils.SystemUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RequestMapping("${adminPath}/sys/org")
@Controller
public class OrgController extends BaseController {

	@RequestMapping({ "", "/", "index" })
	public String index(Model model) {
		return "sys/org/orgIndex";
	}

	@RequiresPermissions("user")
	@RequestMapping("treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<SysOrganization> orgList = SystemUtils.getAllOrgList();
		for (SysOrganization org : orgList) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", org.getId());
			map.put("pId", org.getParentId());
			map.put("name", org.getName());
			
			mapList.add(map);
		}
		return mapList;
	}

}
