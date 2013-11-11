package org.pablog.pills.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.pablog.pills.domain.User;
import org.pablog.pills.repositories.UserRepository;
import org.pablog.pills.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;

@RequestMapping("/users")
@Controller
public class UserController {
	@Autowired UserRepository userRepository;
	@Autowired StandardPasswordEncoder pwdEncoder;
	@Autowired MessageSource messageSource;
	/*
	@RequestMapping(value = "/new/{username}/{password}/{role}", produces = "text/html")
	public String create(@PathVariable("username") String username, @PathVariable("password") String password, @PathVariable("role") String role, Model uiModel) {
		User user = new User();
		user.setPassword(pwdEncoder.encode(password));
		user.setUsername(username);
		user.setRole(role);
		userRepository.save(user);
        return "redirect:/days/current";
    }
	*/
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid User user,  BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if(userRepository.findByUsername(user.getUsername()) != null){
    		bindingResult.addError(new FieldError("user", "username", messageSource.getMessage("error_user_exists", null, httpServletRequest.getLocale())));
    	}
		/*
		@RequestParam(required=false) String confirmPassword,
		if(!user.getPassword().equals(confirmPassword)){
    		bindingResult.addError(new FieldError("user", "password", messageSource.getMessage("error_user_passwordConfirm", null, httpServletRequest.getLocale())));
    	}
		*/
		if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, user);
            return "/login";
        }
		
        uiModel.asMap().clear();
        user.setRole(Role.USER.toString());
        String pwd = user.getPassword();
        user.setPassword(pwdEncoder.encode(pwd));
        userRepository.save(user);
        
        return "redirect:/days/current";
    }
	
	@RequestMapping(value = "/validUsername", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> validUsername(@RequestParam("username") String username, HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
        Map<String,String> result = new HashMap<String, String>();
        
        if(userRepository.findByUsername(username) != null){
        	result.put("result", messageSource.getMessage("error_user_exists", null, httpServletRequest.getLocale()));
        } else {
        	result.put("result", "");
        }
        
        return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").serialize(result), headers, HttpStatus.OK);
    }
	
//	@PreAuthorize("hasRole('AMMIN')")
	
	void populateEditForm(Model uiModel, User user) {
        uiModel.addAttribute("user", user);
    }
}
