package org.pablog.pills.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.pablog.pills.domain.User;

public interface UserService {

	List<User> findAll();

	User findByUsername(String username);

	User findById(ObjectId id);

	User save(User user);

	void delete(User user);

}