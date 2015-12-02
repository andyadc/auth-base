package com.adc.idea.sys.web.controller;

import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adc.idea.common.web.BaseController;

@RequestMapping("sessions")
@Controller
public class SessionController extends BaseController {

	@Autowired
	private SessionDAO sessionDAO;

	@RequestMapping("list")
	public String list(Model model) {
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		model.addAttribute("sessions", sessions);
		model.addAttribute("sessionCount", sessions.size());
		return "sys/session/sessionList";
	}

	@RequestMapping("{sessionId}/forceLogout")
	public String forceLogout(@PathVariable("sessionId") String sessionId, RedirectAttributes redirectAttributes) {
		Session session = sessionDAO.readSession(sessionId);
		if (session != null) {
			
		}
		return redirectToUrl("list");
	}
}
