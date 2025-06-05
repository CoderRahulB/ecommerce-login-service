package com.project.ecom.service;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Service
public class JwtService {

	private final String SECRET_KEY = "1614871ef0f0860827679c72794693bee421e8f711d37529d58a660571c77d26bb817c804aac63c671ca2dcfb0f3ede678c5dac4ccdb39ecbd781394e5c97c104e67ce6d3804ed09e5f809dffa347560500683041fd32f44d86de985c8c6ce72e3fde8ddd1ee39b1fa84c2b9a085c11fd8f4a324e4057768eee56bb8c1f32c484be9525b3f8b63f01dad510832db3ab3583b7159ab75e562905ff81ebce4d6137fe7c540db874e18c672a169ffcd07eb3f566b3d2f8879fd2c375a140eae6e820f31a7f30d4349f9933d23b940850c4337ba390f2a8d4314fe6e206aa8aa0e2950cedf552f19ca49ce4407ed861fed84c7907a51a2ff618f2be0289b66881b3e";
	
	public String extendsUsername(String token) {
		return extractClaims(token,Claims::getSubject);
	}

	public <T> T extractClaims(String token , Function<Claims,T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private SecretKey getSignKey() { 
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSignKey()) // Changed from setSigningKey()
				.build().parseSignedClaims(token) // Changed from parseClaimsJws()
				.getPayload(); // Changed from getBody()

	}
	
	public String generateToken(String username) {
	    Map<String, Object> claims = new HashMap<>();
	    return buildToken(claims, username);
	}

	private String buildToken(Map<String, Object> claims, String subject) {
	    long expirationTime = 1000 * 60 * 60; // 1 hour

	    return Jwts.builder()
	            .claims(claims)
	            .subject(subject)
	            .issuedAt(new Date(System.currentTimeMillis()))
	            .expiration(new Date(System.currentTimeMillis() + expirationTime))
	            .signWith(getSignKey()) 
	            .compact();
	}
	
	public boolean isTokenExpired(String token) {
	    return extractClaims(token, Claims::getExpiration).before(new Date());
	}
}
