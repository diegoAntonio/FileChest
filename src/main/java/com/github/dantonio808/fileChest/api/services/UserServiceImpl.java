package com.github.dantonio808.fileChest.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.dantonio808.fileChest.api.interfaces.*;
import com.github.dantonio808.fileChest.api.interfaces.UserService;
import com.github.dantonio808.fileChest.api.model.User;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository repository;

	@Autowired
	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public void addUser(User user) {
		this.repository.save(user);
	}

	@Override
	@Transactional(readOnly=true)
	public Iterable<User> findAll() {
		return this.repository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public User findById(Long id) {
		return this.repository.findOne(id);
	}
	
	@Override
	@Transactional(readOnly=true)
	public User findByLogin(String userLogin) {
		return this.repository.findByLogin(userLogin);
	}
}