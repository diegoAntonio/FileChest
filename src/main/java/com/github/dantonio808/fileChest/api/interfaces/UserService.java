package com.github.dantonio808.fileChest.api.interfaces;

import com.github.dantonio808.fileChest.api.model.User;

/**
 *  Interface que expoe os metodos de persistencia
 *  relativos a classe {@link User}.
 *  
 * @author Diego
 *
 */
public interface UserService {
	
	/**
	 *  Adiciona um {@link User}
	 * @param user
	 */
	public void addUser(User user);
	
	
	/** 
	 * Retorna todas as instancias de {@link User}
	 * no banco.
	 * 
	 * @return
	 */
	public Iterable<User> findAll();
	
	/**
	 * Procura um {@link User} pelo Id.
	 * 
	 * @param id
	 * @return {@link User} ou <code>null</code> caso nao haja dados
	 * para retornar.
	 */
	public User findById(Long id);
	
	/**
	 * Procura um {@link User} pelo Login.
	 * 
	 * @param userLogin
	 * @return {@link User} ou <code>null</code> caso nao haja dados
	 * para retornar.
	 */
	public User findByLogin(String userLogin);
}
