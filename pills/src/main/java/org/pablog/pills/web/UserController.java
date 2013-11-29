package org.pablog.pills.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.pablog.pills.domain.Product;
import org.pablog.pills.domain.User;
import org.pablog.pills.security.DefaultUserDetailsService;
import org.pablog.pills.service.DayService;
import org.pablog.pills.service.UserService;
import org.pablog.pills.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	@Autowired UserService userService;
	@Autowired DayService dayService;
	@Autowired StandardPasswordEncoder pwdEncoder;
	@Autowired MessageSource messageSource;
	@Autowired MailSender mailSender;
	@Autowired SimpleMailMessage templateMessage;
	@Autowired AuthenticationManager authenticationManager;
	@Autowired DefaultUserDetailsService defaultUserDetailsService;
	
	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid User user, @RequestParam String confirmPassword,  BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if(StringUtils.isBlank(user.getUsername())){
    		bindingResult.addError(new FieldError("user", "username", messageSource.getMessage("field_required", null, httpServletRequest.getLocale())));
    	}
		else if(userService.findByUsername(user.getUsername()) != null){
    		bindingResult.addError(new FieldError("user", "username", messageSource.getMessage("error_user_exists", null, httpServletRequest.getLocale())));
    	}
		if(StringUtils.isBlank(user.getPassword())){
    		bindingResult.addError(new FieldError("user", "password", messageSource.getMessage("field_required", null, httpServletRequest.getLocale())));
    	}
		else if(!user.getPassword().equals(confirmPassword)){
    		bindingResult.addError(new FieldError("user", "password", messageSource.getMessage("error_user_passwordConfirm", null, httpServletRequest.getLocale())));
    	}
		if(StringUtils.isBlank(user.getEmail())){
    		bindingResult.addError(new FieldError("user", "email", messageSource.getMessage("field_required", null, httpServletRequest.getLocale())));
    	}
		if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, user);
            uiModel.addAttribute("register_error", bindingResult.getAllErrors());
            return "login";
        }
		
        templateMessage.setSubject(messageSource.getMessage("mail_new_user_subject", null, httpServletRequest.getLocale()));
        templateMessage.setText(messageSource.getMessage("mail_new_user_text", new String[]{user.getUsername(),user.getPassword()}, httpServletRequest.getLocale()));
        templateMessage.setTo(user.getEmail());
		try{
			mailSender.send(templateMessage);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
        user.setRoles(new ArrayList<String>());
        user.getRoles().add(Role.ROLE_USER.toString());
        user.setPassword(pwdEncoder.encode(user.getPassword()));
        user.setProducts(new ArrayList<Product>());
        userService.save(user);
       
        // Authenticate user and set session
        try {
        	login(user, httpServletRequest);
        } catch (Exception e) {
          e.printStackTrace();
          SecurityContextHolder.getContext().setAuthentication(null);
          return "login";
        }
        
        return "redirect:/days/current";
    }
	
	/*
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new User());
        return "products/create";
    }
	*/
	@RequestMapping(value = "/validUsername", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> validUsername(@RequestParam("username") String username, HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
        Map<String,String> result = new HashMap<String, String>();
        
        if(userService.findByUsername(username) != null){
        	result.put("result", messageSource.getMessage("error_user_exists", null, httpServletRequest.getLocale()));
        } else {
        	result.put("result", "");
        }
        
        return new ResponseEntity<String>(new JSONSerializer().exclude("*.class").serialize(result), headers, HttpStatus.OK);
    }
	
//	@PreAuthorize("hasRole('ROLE_AMMIN')")
	
	void populateEditForm(Model uiModel, User user) {
        uiModel.addAttribute("user", user);
    }
	
	private void login(User user, HttpServletRequest request) throws Exception {
		UserDetails userDetails = defaultUserDetailsService.loadUserByUsername(user.getUsername());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
