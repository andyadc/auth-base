package com.adc.idea.sys.web.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.adc.idea.sys.dto.PasswordDTO;
import com.adc.idea.sys.entity.SysUser;
import com.adc.idea.sys.service.SysRoleService;
import com.adc.idea.sys.service.SysUserService;
import com.adc.idea.sys.service.impl.TestAsyncTaskService;
import com.adc.idea.sys.utils.SystemUtils;

@RequestMapping("${adminPath}/sys/user")
@Controller
public class SysUserController extends BaseController {

	@Autowired
	private SysUserService userService;
	@Autowired
	private SysRoleService roleService;
	@Autowired
	private TestAsyncTaskService testService;
	
	@ResponseBody
	@RequestMapping("test")
	public String test(){
		testService.sleep();
		System.out.println("djksdsfdf");
		return "success";
	}

	@RequestMapping({ "", "/", "index" })
	public String index() {
		return "sys/user/userIndex";
	}

	@RequestMapping("list")
	public String list(Model model, HttpServletRequest request) {
		Map<String, String> reqParams = this.getRequestParams(request);
		PageModel page = userService.selectUserPage(reqParams);
		model.addAttribute("page", page);
		model.addAttribute("params", reqParams);
		return "sys/user/userList";
	}
	
	@RequiresPermissions("sys:user:delete")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam(required  = true) Integer id, RedirectAttributes redirectAttributes){
		userService.deleteUserLogic(id);
		addMessage(redirectAttributes, "删除用户成功");
		return redirectToUrl("list");
	}

	@RequestMapping("add")
	public String add(Model model) {
		model.addAttribute("roleList", roleService.selectAll());
		return "sys/user/userAdd";
	}

	@RequestMapping("edit")
	public String edit(@RequestParam(required = true) Integer id, Model model) {
		SysUser user = userService.selectByPk(id);
		model.addAttribute("user", user);
		return "sys/user/userEdit";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(SysUser user, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (!beanValidator(model, user)) {
			return add(model);
		}

		if (!"true".equalsIgnoreCase(this.checkAccount(user.getOldAccount(), user.getAccount()))) {
			addMessage(redirectAttributes, "保存用户" + user.getAccount() + "失败, 账户已存在!");
			return add(model);
		}

		if (user.getId() == null) {
			user.setAddDate(new Date());
			user.setAddIp(this.getIp(request));
		}
		
		if (!checkPwd(request, user)) {
			addMessage(redirectAttributes, "保存用户" + user.getAccount() + "失败, 密码错误!");
			return add(model);
		}

		userService.saveUser(user);

		addMessage(redirectAttributes, "保存用户" + user.getAccount() + "成功!");
		return redirectToUrl("list");
	}

	private boolean checkPwd(HttpServletRequest request, SysUser user) {
		String newPwd = request.getParameter("newPassword");
		String confirmPwd = request.getParameter("confirmNewPassword");
		if (StringUtils.isNotBlank(confirmPwd) && StringUtils.isNotBlank(newPwd)) {
			if (!newPwd.equalsIgnoreCase(confirmPwd)) {
				return false;
			}
			user.setPassword(confirmPwd);
		}
		
		return true;
	}

	@ResponseBody
	@RequestMapping("checkAccount")
	public String checkAccount(String oldAccount, String account) {
		if (StringUtils.isNotBlank(oldAccount) && oldAccount.equalsIgnoreCase(account)) {
			return "true";
		} else if (StringUtils.isNotBlank(account) && userService.selectByAccount(account) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 个人信息
	 */
	@RequestMapping(value = "info", method = RequestMethod.GET)
	public String info(Model model) {
		Integer userId = SystemUtils.getShiroUser().getId();
		SysUser user = userService.selectByPk(userId);
		user.setRoleNames(roleService.findNamesByUserId(userId));

		model.addAttribute("user", user);
		return "sys/userInfo";
	}

	@RequestMapping(value = "info", method = RequestMethod.POST)
	public String info(SysUser user, RedirectAttributes redirectAttributes) {
		Integer userId = SystemUtils.getShiroUser().getId();
		user.setId(userId);
		userService.saveUser(user);

		addMessage(redirectAttributes, "保存成功");
		return redirectToUrl("info");
	}

	/**
	 * 修改个人密码
	 */
	@RequestMapping(value = "userModifyPwd", method = RequestMethod.GET)
	public String modifyPwd() {
		return "sys/userModifyPwd";
	}

	@RequestMapping(value = "userModifyPwd", method = RequestMethod.POST)
	public String userModifyPwd(PasswordDTO dto, RedirectAttributes redirectAttributes) {
		if (!dto.checkPwd()) {
			addMessage(redirectAttributes, "信息有误");
			return redirectToUrl("userModifyPwd");
		}

		Integer userId = SystemUtils.getShiroUser().getId();
		SysUser user = userService.selectByPk(userId);

		boolean flag = userService.verifyPassword(user, dto.getOldPassword());
		if (!flag) {
			addMessage(redirectAttributes, "修改失败,原始密码错误");
			return redirectToUrl("userModifyPwd");
		}

		if (dto.getOldPassword().equals(dto.getNewPassword())) {
			addMessage(redirectAttributes, "修改失败,新密码与原始密码相同");
			return redirectToUrl("userModifyPwd");
		}

		user.setPassword(dto.getNewPassword());
		flag = userService.updatePassword(user);
		if (flag) {
			addMessage(redirectAttributes, "保存成功");
		} else {
			addMessage(redirectAttributes, "修改失败");
		}

		return redirectToUrl("userModifyPwd");
	}

}
