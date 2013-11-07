package org.pablog.pills.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String toCurrent(Model uiModel) {
		return "redirect:/days/current";
	}
}
