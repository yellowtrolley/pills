package org.pablog.pills.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.pablog.pills.domain.Day;
import org.pablog.pills.domain.Product;
import org.pablog.pills.domain.ProductTaken;
import org.pablog.pills.domain.User;
import org.pablog.pills.repositories.DayRepository;
import org.pablog.pills.repositories.ProductRepository;
import org.pablog.pills.repositories.ProductTakenRepository;
import org.pablog.pills.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired ProductRepository productRepository;
	@Autowired ProductTakenRepository productTakenRepository;
	@Autowired DayRepository dayRepository;
	@Autowired UserRepository userRepository;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}
	
	@Override
	public Product findById(ObjectId id) {
    	return productRepository.findById(id);
    }
    
	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	@Override
	public void delete(Product product, User user) {
		// delete ProductTaken from current day
		Day today = dayRepository.findByTheDateAndUser(formatter.format(new Date()), user);
		ProductTaken pt = productTakenRepository.findByProduct(product);
		today.getProductTaken().remove(pt);
		dayRepository.save(today);
		productTakenRepository.delete(pt);

		// delete product
		if(user.getProducts().contains(product)) {
			user.getProducts().remove(product);
			userRepository.save(user);
			productRepository.delete(product);
		}
	}
}
