package org.pablog.pills.web;

import org.pablog.pills.domain.User;
import org.pablog.pills.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
@Controller
public class UserController {
	@Autowired UserRepository userRepository;
	@Autowired StandardPasswordEncoder pwdEncoder;
	
	@RequestMapping(value = "/new/{username}/{password}/{role}", produces = "text/html")
	public String create(@PathVariable("username") String username, @PathVariable("password") String password, @PathVariable("role") String role, Model uiModel) {
		User user = new User();
		user.setPassword(pwdEncoder.encode(password));
		user.setUsername(username);
		user.setRole(role);
		userRepository.save(user);
        return "/days/current";
    }
}
