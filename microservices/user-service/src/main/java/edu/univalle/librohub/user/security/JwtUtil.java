package edu.univalle.librohub.user.security;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

@Component
public class JwtUtil {
	@Value("${jwt.secret:ReplaceWithSecretKey123456789012345}")
	private String jwtSecret;

	@Value("${jwt.expirationMs:3600000}")
	private int jwtExpirationMs;

	public String generateToken(String subject) {
		return Jwts.builder()
			.setSubject(subject)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
			.signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
			.compact();
	}

	public String getSubject(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build()
				.parseClaimsJws(token).getBody().getSubject();
		} catch (JwtException e) {
			return null;
		}
	}

	public boolean validateToken(String token) {
		return getSubject(token) != null;
	}
}
