package org.pablog.pills.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.pablog.pills.domain.User;
import org.pablog.pills.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired UserRepository userRepository;
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@Override
	public User findByUsername(String username) {
    	return userRepository.findByUsername(username);
    }
    
	@Override
	public User findById(ObjectId id) {
    	return userRepository.findById(id);
    }
    
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}
}
