package org.pablog.pills.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.pablog.pills.domain.User;
import org.pablog.pills.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserDetailsService  implements UserDetailsService {
	@Autowired UserRepository userRepository;
	@Autowired StandardPasswordEncoder pwdEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		User user = null;
		try{
			user = userRepository.findByUsername(username);
		} catch (Exception e) {
			throw new UsernameNotFoundException("Problem retrieving User from DB", e);
		}
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		for(String role : user.getRoles())
			authorities.add(new SimpleGrantedAuthority(role));
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
	}
	
}
