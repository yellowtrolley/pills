package org.pablog.pills.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.pablog.pills.domain.Product;
import org.pablog.pills.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired ProductRepository productRepository;
	
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
	public void delete(Product product) {
		productRepository.delete(product);
	}
}
