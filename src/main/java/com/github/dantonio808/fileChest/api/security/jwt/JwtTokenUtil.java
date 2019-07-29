package com.github.dantonio808.fileChest.api.security.jwt;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 *  Classe respons&aacute;vel por fazer as opera&ccedil;&otilde;es
 *  de extra&ccedil;&atilde;o de informa&ccedil;&otilde;es de um 
 *  token v&aacute;lido.
 *  
 * @author Diego.Ferreira
 * @since 29/07/2019
 *
 */
public class JwtTokenUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String CLAIM_KEY_USERNAME = "sub";
	public static final String CLAIM_KEY_CREATED = "created";
	public static final String CLAIM_KEY_EXPIRED = "exp";
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private String expiration;
	
	/**
	 *  Retorna o login do usuario obitdo de um token
	 *  v&aacute;lido.
	 *  
	 * @param token - Token de onde sera extraido o login.
	 * @return Login do ususario como {@link String}.
	 */
	public String getUserNameFromToken(String token) {
		String username = "";
		try{
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		}catch(Exception e) {
			username = null;
		}

		return username;
		
	}
	
	/**
	 *  Retorna a data de expira&ccedil;&atilde;o do token
	 *   usuario obitdo de um token v&aacute;lido.
	 *  
	 * @param token - Token de onde ser&aacute; extra&iacute;da a data.
	 * @return Data de expira&ccedil;&atilde;o do token do usu&aacute;rio como {@link Date}.
	 */
	public Date getExpirationDateFromToken(String token) {
		Date expiration = null;
		final Claims claims = getClaimsFromToken(token);
		
		expiration = claims.getExpiration();
		
		return expiration;
	}
	
	/**
	 *  Recupera o {@link Claims} de um token v&aacute;lido..
	 * @param token - Token de onde ser&aacute; extra&iacute;do o {@link Claims}..
	 * @return {@link Claims} extra&iacute;do do Token.
	 */
	private Claims getClaimsFromToken(String token) {
		Claims claims = null;
		
		try {
			claims = Jwts.parser().
					setSigningKey(secret).
					parseClaimsJws(token).getBody();

		}catch(Exception e) {
			claims = null;
		}
		
		return claims;
	}
}
