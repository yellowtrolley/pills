package org.pablog.pills.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pablog.pills.repositories.DayRepository;
import org.pablog.pills.repositories.ProductRepository;
import org.pablog.pills.repositories.ProductTakenRepository;
import org.pablog.pills.repositories.UserRepository;
import org.pablog.pills.scheduler.DayCreatorScheduler;
import org.pablog.pills.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Configurable
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
public class UserTest {
	/*
	@Autowired UserRepository userRepository;
	@Autowired DayRepository dayRepository;
	@Autowired ProductRepository productRepository;
	@Autowired ProductTakenRepository productTakenRepository;
	@Autowired StandardPasswordEncoder pwdEncoder;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	@Test
	public void testSave() {
		try {
			dayRepository.deleteAll();
			userRepository.deleteAll();
			productRepository.deleteAll();
			productTakenRepository.deleteAll();
			
			User user = new User();
			user.setPassword(pwdEncoder.encode("foo"));
			user.setUsername("foo");
			user.setRole(Role.AMMIN.toString());
			
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
            
			user = userRepository.save(user);
			
			Assert.assertNotNull("Expected 'User' identifier to no longer be null", user.getId());
			Assert.assertEquals("Find entries method for 'Product' returned an incorrect number of entries", user.getProducts().size(), data.size());
			
			createDay();
			
			Day day = dayRepository.findByTheDateAndUser(formatter.format(new Date()), user);
			Assert.assertNotNull("findByTheDateAndUser method for 'Day' illegally returned null", day);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
	}

	private void createDay() {
		for(User u : userRepository.findAll()) {
			Day day = new Day();
			day.setTheDate(formatter.format(new Date()));
			day.setProductTaken(new ArrayList<ProductTaken>());
			day.setUser(u);
			
			for(Product p : u.getProducts()) {
				ProductTaken pt = new ProductTaken();
				pt.setProduct(p);
				day.getProductTaken().add(pt);
			}
			
			dayRepository.save(day);
		}
	}
	*/
}
