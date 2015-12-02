package com.adc.idea.sys.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.adc.idea.common.web.BaseController;
import com.adc.idea.sys.security.SystemAuthorizingRealm.ShiroUser;
import com.adc.idea.sys.utils.SystemUtils;

/**
 * 负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 真正登录的POST请求由Filter完成
 * 
 * @author andaicheng
 *
 */
@Controller
public class LoginController extends BaseController {

	@RequestMapping("testLogin")
	public String login2() {
		return "sys/login";
	}

	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login() {
		ShiroUser shiroUser = SystemUtils.getShiroUser();
		if (shiroUser != null) {
			return "redirect:../admin";
		}
		return "sys/login";
	}

	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String fail(HttpServletRequest req, Model model) {
		String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
		String error = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			error = "用户名错误";
		} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
			error = "密码错误!!!";
		} else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
			error = "此账户已被锁定!";
		} else if (exceptionClassName != null) {
			error = "其他错误：" + exceptionClassName;
		}
		model.addAttribute("message", error);
		return "sys/login";
	}

	@RequestMapping("${adminPath}")
	public String index() {
		return "sys/index";
	}

	@RequestMapping("${adminPath}/sys/menuTree")
	public String leftTree() {
		return "sys/menuTree";
	}

}
