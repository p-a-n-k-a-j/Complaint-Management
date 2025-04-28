package com.factory;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.*;


public class JwtUtil {

	    // 🔒 Use a constant base64 encoded string as secret
	private static final String SECRET_STRING = "mX9$wB#2t@Y8^rP!sL0zQeC@kN7d%uH&fV1xG$ZlT#bA3cM^oJ6sWqE!iU9vLrNy";


	    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

	    private static final long EXPIRATION_TIME_MS = 30L * 24 * 60 * 60 * 1000; // 30 days

	    // ✅ Generate token
	    public static String generateToken(String username) {
	        return Jwts.builder()
	                .setSubject(username)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
	                .signWith(SECRET_KEY)
	                .compact();
	    }

	    // ✅ Validate token
	    public static boolean isValid(String token) {
	        try {
	            Jwts.parserBuilder()
	                .setSigningKey(SECRET_KEY)
	                .build()
	                .parseClaimsJws(token);
	            return true;
	        } catch (JwtException e) {
	            System.err.println("Token validation failed: " + e.getMessage());
	            return false;
	        }
	    }

	    // ✅ Extract username from token
	    public static String getUsername(String token) {
	        try {
	            return Jwts.parserBuilder()
	                    .setSigningKey(SECRET_KEY)
	                    .build()
	                    .parseClaimsJws(token)
	                    .getBody()
	                    .getSubject();
	        } catch (JwtException e) {
	            System.err.println("Failed to extract username: " + e.getMessage());
	            return null;
	        }
	    }
	}



