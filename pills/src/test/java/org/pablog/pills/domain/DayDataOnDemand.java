package org.pablog.pills.domain;
import java.util.ArrayList;
import java.util.List;

import org.pablog.pills.repositories.ProductTakenRepository;
import org.pablog.pills.service.DayService;
import org.pablog.pills.service.ProductService;
import org.pablog.pills.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class DayDataOnDemand {
//	private Random rnd = new SecureRandom();

	private Day day;
	private User user;
	private List<Product> products;

	@Autowired DayService dayService;
	@Autowired UserService userService;
	@Autowired ProductService productService;
	@Autowired ProductTakenRepository productTakenRepo;
	
	public User getTestUser() {
		return user;
	}
	
	public Day getTestDay() {
		return day;
	}
	
	public void init() {
		User user = new User();
		user.setName("testUser");
		user.setPassword("testPassword");
		user = userService.save(user);
		
		products = new ArrayList<>();
		products.add(new Product("Domperidona 10 mg",1,1,1));
		products.add(new Product("Plenur 400 mg",0.5,0,1));
		products.add(new Product("Labileno 100 mg",1,0,1));
		products.add(new Product("Lansoprazon 30 mg",1, 0,1));
		products.add(new Product("Rivotril 0,5 mg",1,1,2));
		products.add(new Product("Zyprexa 10 mg",1,0,1));
		products.add(new Product("Noctamid 1 mg",0,0,1));
		products.add(new Product("Mysoline  250 mg",0,0,1));
		user.setProducts(products);
		
		day = dayService.createDay(user);
    }
	
	public void destroy() {
		for(ProductTaken pt : day.getProductTaken())
			productTakenRepo.delete(pt);
		
		dayService.delete(day);
		
		for(Product p : products)
			productService.delete(p);
		
		userService.delete(user);
	}
}
