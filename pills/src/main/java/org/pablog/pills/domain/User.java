package org.pablog.pills.domain;

import java.util.List;

import org.bson.types.ObjectId;
import org.pablog.pills.mongo.CascadeSave;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

	@Id
    private ObjectId id;
	private String username;
	private String password;
	private String name;
	private String role;
	private String email;
	@DBRef
	@CascadeSave
	List<Product> products;
	
	
	public User(){};
	
	public User(String username, String password, String name, String role,
			String email, List<Product> products) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = role;
		this.email = email;
		this.products = products;
	}

	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Product> getProducts() {
		return products;
	}
	/*public List<Product> getProducts(Pageable pageable) {
		int fromIndex = pageable.getPageNumber() * pageable.getPageSize();
	    int toIndex = fromIndex + pageable.getPageSize() - 1;
		return products.subList(fromIndex, toIndex);
	}*/
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
