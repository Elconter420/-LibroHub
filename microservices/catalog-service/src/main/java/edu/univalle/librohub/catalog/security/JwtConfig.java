package edu.univalle.librohub.catalog.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {
	@Value("${jwt.secret:ReplaceWithSecretKey123456789012345}")
	public String jwtSecret;
}
