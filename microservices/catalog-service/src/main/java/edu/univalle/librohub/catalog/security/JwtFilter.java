package edu.univalle.librohub.catalog.security;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.*;

@Component
public class JwtFilter extends OncePerRequestFilter {
	private final JwtConfig config;
	public JwtFilter(JwtConfig config) { this.config = config; }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			try {
				Jwts.parserBuilder().setSigningKey(config.jwtSecret.getBytes()).build().parseClaimsJws(token);
				// token válido -> continuar
			} catch (JwtException e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Invalid token");
				return;
			}
		} else {
			// no header -> unauthorized for protected endpoints (except health/open)
			if (!request.getRequestURI().startsWith("/actuator") && !request.getRequestURI().startsWith("/api/catalog/books")) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Missing token");
				return;
			}
		}
		filterChain.doFilter(request, response);
	}
}
