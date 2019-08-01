package com.github.dantonio808.fileChest.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.dantonio808.fileChest.api.interfaces.UserService;
import com.github.dantonio808.fileChest.api.model.User;
import com.github.dantonio808.fileChest.api.security.factory.JwtUserFactory;
import com.github.dantonio808.fileChest.api.security.model.JwtUser;

@Service
public class JwtServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
		User user = userService.findByLogin(userLogin);
		JwtUser userJwt = null;
		
		if(user != null) {
			userJwt = JwtUserFactory.createFromUser(user);
		} else {
			throw new UsernameNotFoundException("User: " + userLogin + " was not found!");
		}
		
		return userJwt;
	}

}
