package org.pablog.pills.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.pablog.pills.domain.Product;

public interface ProductService {

	List<Product> findAll();

	Product findById(ObjectId id);

	Product save(Product product);

	void delete(Product product);

}