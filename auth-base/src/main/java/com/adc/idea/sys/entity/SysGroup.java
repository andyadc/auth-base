package com.adc.idea.sys.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.adc.idea.common.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * 用户组
 * 
 * @author andaicheng
 *
 */
public class SysGroup extends BaseEntity {

	private static final long serialVersionUID = -6130700961831302226L;
	
	/** 删除 */
	public static final String GROUP_DELETED = "1";
	/** 未删除 */
	public static final String GROUP_UNDELETED = "0";

	private Integer id;

	private Integer parentId;

	private String parentIds;

	@NotEmpty(message = "用户组名称不能为空")
	private String name;

	private Integer sort;

	private String usable;

	private String deleted;

	private String remark;

	private SysGroup parent;

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

	public String getUsable() {
		return usable;
	}

	public void setUsable(String usable) {
		this.usable = usable == null ? null : usable.trim();
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted == null ? null : deleted.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	@JsonBackReference
	@NotNull(message = "没有上级用户组")
	public SysGroup getParent() {
		return parent;
	}

	public void setParent(SysGroup parent) {
		this.parent = parent;
	}

}