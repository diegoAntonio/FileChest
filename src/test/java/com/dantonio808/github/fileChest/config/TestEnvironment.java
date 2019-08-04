package com.dantonio808.github.fileChest.config;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Classe que configura todo o ambiente de testes para as sessoes de teste.
 * 
 * @author Diego
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
public abstract class TestEnvironment {
}
