package com.github.dantonio808.fileChest.api.security.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.github.dantonio808.fileChest.api.security.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 *  Classe respons&aacute;vel por fazer as opera&ccedil;&otilde;es
 *  de extra&ccedil;&atilde;o de informa&ccedil;&otilde;es de um 
 *  token v&aacute;lido.
 *  
 * @author Diego.Ferreira
 * @since 29/07/2019
 *
 */
@Component
public class JwtTokenUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String CLAIM_KEY_USERNAME = "sub";
	public static final String CLAIM_KEY_CREATED = "created";
	public static final String CLAIM_KEY_EXPIRED = "exp";
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private long expiration;
	
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
	
	private boolean isTokenExpired(String token) {
		Date dataExpiracao = this.getExpirationDateFromToken(token);
		
		return dataExpiracao.before(new Date());
	}
	
	public String generateToken(UserDetails details) {
		Map<String, Object> claims = new HashMap<>();
		
		claims.put(CLAIM_KEY_USERNAME, details.getUsername());
		claims.put(CLAIM_KEY_CREATED, new Date());
		
		return doGenerateToken(claims);
	}

	private String doGenerateToken(Map<String, Object> claims) {
		final Date criacao = (Date) claims.get(CLAIM_KEY_CREATED);
		final Date dataExpiracao = new Date(criacao.getTime() + (expiration * 1000));
		
		return Jwts.builder().setClaims(claims)
							.setExpiration(dataExpiracao)
							.signWith(SignatureAlgorithm.HS512, secret)
							.compact();
	}
	
	public boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token));
	}
	
	public String refreshToken(String token) {
		String refreshedToken = "";
		
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = doGenerateToken(claims);
			
		}catch (Exception e) {
			e.printStackTrace();
			refreshedToken = null;
		}
		
		return refreshedToken;
	}
	
	public boolean isTokenValid(String token, UserDetails details) {
		JwtUser user = (JwtUser) details;
		final String userName = this.getUserNameFromToken(token);
		
		return (user.getUsername().equalsIgnoreCase(userName) && !isTokenExpired(token));
	}
}
