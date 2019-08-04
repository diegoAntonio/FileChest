package com.github.dantonio808.fileChest.api.security.request;

import java.io.Serializable;

public class JwtAuthenticationRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String login;
	private String password;
	
	public JwtAuthenticationRequest() {
		super();
	}

	public JwtAuthenticationRequest(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}		
}
