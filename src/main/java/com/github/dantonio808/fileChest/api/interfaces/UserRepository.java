package com.github.dantonio808.fileChest.api.interfaces;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.github.dantonio808.fileChest.api.model.User;

/**
 *  Interface que representa o repositorio relativo a operacoes 
 *  da classe {@link User}.
 *  
 * @author Diego
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {
	
	@Query(value = "SELECT * FROM USER WHERE LOGIN = ?1", nativeQuery = true)
	public User findByLogin(String login);
}
