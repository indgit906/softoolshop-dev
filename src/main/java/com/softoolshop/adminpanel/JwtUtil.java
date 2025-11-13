package com.softoolshop.adminpanel;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private String secret = "softoolskeymySuperSecretKeyThatIsLongEnough12345"
    		+ "softoolskeymySuperSecretKey"; // In production, use a secure key from config
    private int jwtExpirationInMs = 3600000; // 1 hour
    Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        //claims.put("userName", userDetails.getUsername());
        return doGenerateToken(claims, userDetails.getUsername());
    }
    
    private String doGenerateToken(Map<String, Object> claims, String subject) {

    	return Jwts.builder()
    	    .setClaims(claims)
    	    .setSubject(subject)
    	    .setIssuedAt(new Date())
    	    .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
    	    .signWith(key, SignatureAlgorithm.HS256)
    	    .compact();
    }

	public String extractUsername(String jwt) {
		 return Jwts.parserBuilder().setSigningKey(key).build()
                 .parseClaimsJws(jwt).getBody().getSubject();
	}
    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }

	
}
