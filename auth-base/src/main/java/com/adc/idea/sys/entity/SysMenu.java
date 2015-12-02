package com.adc.idea.sys.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.adc.idea.common.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 菜单权限
 * 
 * @author andaicheng
 *
 */
public class SysMenu extends BaseEntity {

	private static final long serialVersionUID = -1017389026782083897L;

	private Integer id;

	private Integer parentId;

	private String parentIds;

	@NotEmpty(message = "菜单名称不能为空")
	private String name;

	private Integer sort;

	private String href;

	private String target;

	private String icon;

	private String permission;

	private String isShow;

	private String type;

	private SysMenu parent;

	public SysMenu() {
	}

	public SysMenu(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds == null ? null : parentIds.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href == null ? null : href.trim();
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target == null ? null : target.trim();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission == null ? null : permission.trim();
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow == null ? null : isShow.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	public static void sortList(List<SysMenu> list, List<SysMenu> sourcelist, Integer parentId, boolean cascade) {
		for (int i = 0; i < sourcelist.size(); i++) {
			SysMenu sm = sourcelist.get(i);
			if (sm.getParentId() != null && sm.getId() != null && sm.getParentId().equals(parentId)) {
				list.add(sm);
				if (cascade) {
					for (int j = 0; j < sourcelist.size(); j++) {
						SysMenu child = sourcelist.get(j);
						if (child.getParentId() != null && child.getId() != null
								&& child.getParentId().equals(sm.getId())) {
							sortList(list, sourcelist, sm.getId(), true);
							break;
						}
					}
				}
			}
		}
	}

	@JsonIgnore
	public static Integer getRootId() {
		return 1;
	}

	@JsonBackReference
	@NotNull(message = "没有上级菜单")
	public SysMenu getParent() {
		return parent;
	}

	public void setParent(SysMenu parent) {
		this.parent = parent;
	}

}