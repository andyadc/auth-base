package com.adc.idea.sys.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import com.adc.idea.sys.entity.SysUser;

@Service
public class PasswordHelper {

	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

	private String algorithmName = "md5";
	private int hashIterations = 2;

	public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
		this.randomNumberGenerator = randomNumberGenerator;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}

	/**
	 * 用户密码加密
	 * 
	 * @param user
	 */
	public void encryptPassword(SysUser user) {

		user.setSalt(randomNumberGenerator.nextBytes().toHex());

		String newPassword = new SimpleHash(algorithmName, user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()), hashIterations).toHex();

		user.setPassword(newPassword);
	}

	/**
	 * 验证密码是否一致
	 * 
	 * @param user
	 * @param inputPassword
	 * @return
	 */
	public boolean verifyPassword(SysUser user, String inputPassword) {
		try {
			HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(algorithmName);
			matcher.setHashIterations(hashIterations);

			AuthenticationToken token = new UsernamePasswordToken(user.getAccount(), inputPassword);

			AuthenticationInfo info = new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(),
					ByteSource.Util.bytes(user.getCredentialsSalt()), this.getClass().getName());

			return matcher.doCredentialsMatch(token, info);
		} catch (Exception e) {
		}
		return false;
	}

}
