package org.pablog.pills.domain;
/*
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.pablog.pills.repositories.UserRepository;
import org.pablog.pills.service.DayService;
import org.pablog.pills.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
*/
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath*:/META-INF/spring/applicationContext*.xml"})
public class DayControllerTest {
/*
	private MockMvc mockMvc;
	
	@ReplaceWithMock
	@Autowired
	private DayService dayService;
	
	@ReplaceWithMock
	@Autowired 
	UserRepository userRepository;
	
	@ReplaceWithMock
	@Autowired
    private User user;
	
	@Autowired 
	PasswordEncoder pwdEncoder;
	
	@Autowired
    private WebApplicationContext webappContext;
	
    @Autowired
    private FilterChainProxy springSecurityFilter;

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	@Before
    public void before() throws Exception {
             mockMvc = webAppContextSetup(this.webappContext).build();
    }  
	
	@Test
	public void testDayCreation() throws Exception {
		user = new User();
		user.setPassword(pwdEncoder.encode("foo"));
		user.setUsername("foo");
		List<String> roles = new ArrayList<>();
		roles.add(Role.ROLE_USER.toString());
		user.setRoles(roles);
		
        List<Product> data = new ArrayList<Product>();
        data.add(new Product("Domperidona 10 mg",1,1,1));
        data.add(new Product("Plenur 400 mg",0.5,0,1));
        data.add(new Product("Labileno 100 mg",1,0,1));
        data.add(new Product("Lansoprazon 30 mg",1, 0,1));
        data.add(new Product("Rivotril 0,5 mg",1,1,2));
        data.add(new Product("Zyprexa 10 mg",1,0,1));
        data.add(new Product("Noctamid 1 mg",0,0,1));
        data.add(new Product("Mysoline  250 mg",0,0,1));
        user.setProducts(data);
        
        
        // authenticate user
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, 
        		AuthorityUtils.createAuthorityList(user.getRoles().toArray(new String[user.getRoles().size()])));
        
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

		mockMvc.perform(get("/days/current").session(session))
        .andExpect(status().isOk())
        .andExpect(view().name("days/show"))
        .andExpect(forwardedUrl("/WEB-INF/views/days/show.jspx"))
        .andExpect(model().attribute("day", hasProperty("theDate", is(formatter.format(new Date())))))
        ;

		verify(dayService, times(1)).createDay(user);
	}
*/
}
