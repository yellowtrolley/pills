package org.pablog.pills.domain;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.pablog.pills.service.DayService;
import org.pablog.pills.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = "classpath*:/META-INF/spring/applicationContext*.xml")
public class DayIntegrationTest {
	@ReplaceWithMock
	@Autowired
    private User user;
	
	@ReplaceWithMock
	@Autowired
	private Day day;
	
	@ReplaceWithMock
	@Autowired
	private DayService dayService;
	
	@Autowired 
	PasswordEncoder pwdEncoder;
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	@Before
	public void setUp() throws Exception {
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
	}
	
	@Test
	public void testDayCreation() {
		// given
		day = new Day(user, formatter.format(new Date()), new ArrayList<ProductTaken>());
		
		// when
		dayService.createDay(user);
		
		// then
		verify(dayService).createDay(user);
		assertEquals(formatter.format(new Date()), day.getTheDate());
		assertNotNull(day.getUser());
		assertNotNull(day.getProductTaken());
		for(ProductTaken pt : day.getProductTaken()) {
			assertFalse(pt.isMorning());
			assertFalse(pt.isMidday());
			assertFalse(pt.isNight());
		}
	}
	
}
