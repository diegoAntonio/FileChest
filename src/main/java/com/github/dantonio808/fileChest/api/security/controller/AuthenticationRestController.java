package com.github.dantonio808.fileChest.api.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.dantonio808.fileChest.api.interfaces.UserService;
import com.github.dantonio808.fileChest.api.model.User;
import com.github.dantonio808.fileChest.api.security.jwt.JwtTokenUtil;
import com.github.dantonio808.fileChest.api.security.model.CurrentUser;
import com.github.dantonio808.fileChest.api.security.request.JwtAuthenticationRequest;

@RestController
@CrossOrigin(origins = "*", allowedHeaders= "*")
public class AuthenticationRestController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil tokenUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;

	@PostMapping("/fileChest/sec/auth")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
		final Authentication authentication = this.authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getLogin(),
						authenticationRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getLogin());
		final String token = this.tokenUtil.generateToken(userDetails);
		final User user = this.userService.findByLogin(authenticationRequest.getLogin());
		user.setPasswd(null);
		
		return ResponseEntity.ok(new CurrentUser(token, user));
	}
	
	
	@PostMapping("/fileChest/sec/refreshToken")
	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader("Authentication");
		String userName = this.tokenUtil.getUserNameFromToken(token);
		final User user = this.userService.findByLogin(userName);
		
		if(tokenUtil.canTokenBeRefreshed(token)) {
			String refreshedToken = this.tokenUtil.refreshToken(token);
			return ResponseEntity.ok(new CurrentUser(refreshedToken, user));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}
}
