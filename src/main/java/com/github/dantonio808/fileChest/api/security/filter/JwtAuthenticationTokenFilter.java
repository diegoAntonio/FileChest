package com.github.dantonio808.fileChest.api.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.dantonio808.fileChest.api.security.jwt.JwtTokenUtil;
import com.github.dantonio808.fileChest.api.services.JwtServiceImpl;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtServiceImpl service;
	
	@Autowired
	private JwtTokenUtil tokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest arg0, HttpServletResponse arg1, FilterChain arg2)
			throws ServletException, IOException {
		String authToken;

	}

}
