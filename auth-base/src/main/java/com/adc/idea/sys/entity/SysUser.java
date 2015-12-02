package com.adc.idea.sys.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

import com.adc.idea.common.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

public class SysUser extends BaseEntity {

	private static final long serialVersionUID = 4633817925628435984L;

	// ===============================================

	/** 锁定 */
	public static final String USER_LOCKED = "1";
	/** 未锁定 */
	public static final String USER_UNLOCKED = "0";
	/** 删除状态 */
	public static final String USER_DELETED = "1";
	/** 未删除状态 */
	public static final String USER_UNDELETED = "0";

	// ===============================================

	private Integer id;

	@NotEmpty(message = "用户名称不能为空")
	private String name;

	@NotEmpty(message = "账户不能为空")
	private String account;

	private String password;

	private String salt;

	private String email;

	private String phone;

	private String status;

	private String locked;

	private String deleted;

	private Date addDate;

	private String addIp;

	private Set<String> roleNames;

	private List<SysRole> roles = Lists.newArrayList();

	private String oldAccount;

	public SysUser() {
	}

	public SysUser(Integer id) {
		this.id = id;
	}

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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp == null ? null : addIp.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	@JsonIgnore
	public String getOldAccount() {
		return oldAccount;
	}

	public void setOldAccount(String oldAccount) {
		this.oldAccount = oldAccount;
	}

	@JsonIgnore
	public String getCredentialsSalt() {
		return account + salt;
	}

	@JsonIgnore
	public Set<String> getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(Set<String> roleNames) {
		this.roleNames = roleNames;
	}

	@JsonIgnore
	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

}