package org.pablog.pills.web;

import org.pablog.pills.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	
	private final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model uiModel) {
		uiModel.addAttribute("user", new User());
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		
		return "redirect:/resources/j_spring_security_logout";
	}
	
	
}
