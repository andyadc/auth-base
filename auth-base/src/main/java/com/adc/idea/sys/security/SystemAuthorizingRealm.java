package com.adc.idea.sys.security;

import java.io.Serializable;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.adc.idea.common.utils.LogUtils;
import com.adc.idea.sys.entity.SysUser;
import com.adc.idea.sys.service.impl.SystemService;
import com.adc.idea.sys.utils.SystemUtils;
import com.google.common.base.Objects;

/**
 * 
 * @author andaicheng
 *
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {

	protected SystemService systemService;

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;
		String account = token.getUsername();
		
		SysUser user = systemService.selectSysUserByAccount(account);
		LogUtils.logLogin(user, token.getHost());
		
		if (user == null) {
			throw new UnknownAccountException();
		}
		if (SysUser.USER_LOCKED.equals(user.getLocked())) {
			throw new LockedAccountException();
		}

		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				new ShiroUser(user.getId(), user.getAccount()), user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()), 
				getName()
		);

		return authenticationInfo;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(SystemUtils.getRolesByUserId(shiroUser.getId()));
		info.addStringPermissions(SystemUtils.getPermissionsByUserId(shiroUser.getId()));
		
		return info;
	}

	@Override
	protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	protected void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public static class ShiroUser implements Serializable {

		private static final long serialVersionUID = -6567544640961195754L;
		private Integer id;
		private String account;

		public ShiroUser(Integer id, String account) {
			this.id = id;
			this.account = account;
		}

		public Integer getId() {
			return id;
		}

		public String getAccount() {
			return account;
		}

		@Override
		public String toString() {
			return account;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(account);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (account == null) {
				if (other.account != null) {
					return false;
				}
			} else if (!account.equals(other.account)) {
				return false;
			}
			return true;
		}

	}

}
