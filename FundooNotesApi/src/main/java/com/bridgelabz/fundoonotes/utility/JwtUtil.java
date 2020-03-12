/**
 * @author Nayan Kumar G
 * purpose : To generate jwt and parse the token
 * date    :26-02-2020
 */

package com.bridgelabz.fundoonotes.utility;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {


	private String secretKey = "secret";


	public String generateToken(long userId)
	{

		Claims claims = Jwts.claims().setSubject(Long.toString(userId));

		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}

	public long parseToken(String token)
	{
		try
		{
			Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
			return Long.parseLong(body.getSubject());
		} catch (Exception e) {
			throw new InvalidTokenException("Invalid token" , HttpStatus.BAD_REQUEST);

		}

	}

}
