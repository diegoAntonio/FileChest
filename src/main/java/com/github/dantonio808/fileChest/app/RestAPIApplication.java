package com.github.dantonio808.fileChest.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.github.dantonio808.fileChest.api")
public class RestAPIApplication {

	/**
	 * Executa a aplicacao
	 * 
	 * @param args
	 *            argumentos
	 */
	public static void main(String[] args) {
		SpringApplication.run(RestAPIApplication.class, args);
	}
}
