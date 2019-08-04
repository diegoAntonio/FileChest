package com.github.dantonio808.fileChest.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.dantonio808.fileChest.api.interfaces.AuthoritiesRepository;
import com.github.dantonio808.fileChest.api.interfaces.AuthoritiesService;
import com.github.dantonio808.fileChest.api.model.security.UserAuthorithies;

@Service
public class UserAuthorithiesServiceImpl implements AuthoritiesService {
	@Autowired
	private AuthoritiesRepository repository;
	
	@Autowired
	public UserAuthorithiesServiceImpl(AuthoritiesRepository repository) {
		super();
		this.repository = repository;
	}

	@Transactional
	public void addUserAuthority(UserAuthorithies authority) {
		this.repository.save(authority);
	}

	@Transactional(readOnly=true)
	public Iterable<UserAuthorithies> findAll() {
		return this.repository.findAll();
	}

	@Transactional
	public UserAuthorithies findById(Long id) {
		return this.repository.findById(id).get();
	}
}
