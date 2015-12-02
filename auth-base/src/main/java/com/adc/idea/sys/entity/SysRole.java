package com.adc.idea.sys.entity;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.adc.idea.common.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

public class SysRole extends BaseEntity {

	private static final long serialVersionUID = -1964719075308443360L;

	private Integer id;

	@NotEmpty
	private String name;

	@NotEmpty
	private String role;

	private String usable;

	private String remarks;

	private String deleted;

	@JsonIgnore
	private String menuIds;

	@JsonIgnore
	private List<SysMenu> menuList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsable() {
		return usable;
	}

	public void setUsable(String usable) {
		this.usable = usable;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	public List<String> getMenuIdList() {
		String[] ids = StringUtils.split(menuIds, ",");
		return Lists.newArrayList(ids);
	}

	public void setMenuIdList(List<String> menuIdList) {
		this.menuIds = StringUtils.join(menuIdList, ",");
	}

	public List<SysMenu> getMenuList() {
		menuList = Lists.newArrayList();
		for (String id : getMenuIdList()) {
			SysMenu menu = new SysMenu();
			menu.setId(Integer.parseInt(id));
			menuList.add(menu);
		}
		return menuList;
	}

	public void setMenuList(List<SysMenu> menuList) {
		List<String> ids = Lists.newArrayList();
		for (SysMenu menu : menuList) {
			ids.add(menu.getId().toString());
		}
		setMenuIdList(ids);
	}

}