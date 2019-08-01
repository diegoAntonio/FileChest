package com.github.dantonio808.fileChest.api.security.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.github.dantonio808.fileChest.api.model.User;
import com.github.dantonio808.fileChest.api.model.security.UserAuthorithies;
import com.github.dantonio808.fileChest.api.security.model.JwtUser;

public class JwtUserFactory {

	private JwtUserFactory() {
		super();
	}
	
	public static JwtUser createFromUser(User user) {
		return new JwtUser(user.getId(), user.getLogin(), user.getPasswd(), mapToGrantAutorities(user.getAuthorities()));
	}


	private static Collection <GrantedAuthority> mapToGrantAutorities(List<UserAuthorithies> authorities) {
		List<GrantedAuthority> authoritiesList = new ArrayList<GrantedAuthority>();
		
		for (UserAuthorithies userAuthorithies : authorities) {
			authoritiesList.add(new SimpleGrantedAuthority(userAuthorithies.getRole().name()));
		}

		return authoritiesList;
	}
	
	

	
	

}
