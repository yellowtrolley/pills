package org.pablog.pills.web;

import org.pablog.pills.domain.PUser;
import org.pablog.pills.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
@Controller
public class UserController {
	@Autowired UserRepository userRepository;
	@Autowired StandardPasswordEncoder pwdEncoder;
	
	@RequestMapping(value = "/new", produces = "text/html")
	public String create(Model uiModel) {
		PUser pUser = new PUser();
		pUser.setPassword("fooUser");
		pUser.setUsername("fooUser");
		userRepository.save(pUser);
		System.out.println("Username: " + pUser.getUsername());
		System.out.println("Password raw: " + pUser.getPassword());
		System.out.println("Password encoded: " + pwdEncoder.encode("solobeira"));    
        return "/";
    }
}
