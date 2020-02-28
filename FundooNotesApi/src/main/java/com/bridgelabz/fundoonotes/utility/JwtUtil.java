

package com.bridgelabz.fundoonotes.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.dto.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	//@Value("${jwt.secret}")
	private String secretKey = "secret";
	
	@Autowired
	private UserDto userDto;
	
	public String generateToken(UserDto userDto)
	{
		
		Claims claims = Jwts.claims().setSubject(userDto.getEmail());
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secretKey).compact();
		
	}
	
	public UserDto parseToken(String token)
	{
		try
		{
			Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
			userDto.setEmail((String)body.get("email"));
			return userDto;
		} catch (JwtException | ClassCastException e) {
            return null;
		
		}
		
	}
}
