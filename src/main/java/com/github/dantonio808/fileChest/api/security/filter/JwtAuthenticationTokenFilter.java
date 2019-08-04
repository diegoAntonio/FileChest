package com.github.dantonio808.fileChest.api.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.dantonio808.fileChest.api.security.jwt.JwtTokenUtil;
import com.github.dantonio808.fileChest.api.services.JwtServiceImpl;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtServiceImpl service;

	@Autowired
	private JwtTokenUtil tokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter)
			throws ServletException, IOException {
		String authToken = request.getHeader("Authorization");
		String username = tokenUtil.getUserNameFromToken(authToken);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails details = this.service.loadUserByUsername(username);
			if (this.tokenUtil.isTokenValid(authToken, details)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(details,
						null, details.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		
		filter.doFilter(request, response);
	}
}
