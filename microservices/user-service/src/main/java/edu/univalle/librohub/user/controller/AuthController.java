package edu.univalle.librohub.user.controller;

import java.util.Map;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import edu.univalle.librohub.user.entity.User;
import edu.univalle.librohub.user.repository.UserRepository;
import edu.univalle.librohub.user.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public AuthController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
		String email = body.get("email");
		String pw = body.get("password");
		String role = body.getOrDefault("role", "ESTUDIANTE");
		if (email == null || pw == null) return ResponseEntity.badRequest().body("email and password required");
		if (userRepository.findByEmail(email).isPresent()) return ResponseEntity.badRequest().body("email exists");
		User u = new User();
		u.setEmail(email);
		u.setPassword(passwordEncoder.encode(pw));
		u.setRole(role);
		userRepository.save(u);
		String token = jwtUtil.generateToken(email);
		return ResponseEntity.ok(Map.of("message","registered", "token", token));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
		String email = body.get("email");
		String pw = body.get("password");
		Optional<User> ou = userRepository.findByEmail(email);
		if (ou.isEmpty()) return ResponseEntity.status(401).body("invalid");
		User u = ou.get();
		if (!passwordEncoder.matches(pw, u.getPassword())) return ResponseEntity.status(401).body("invalid");
		String token = jwtUtil.generateToken(u.getEmail());
		return ResponseEntity.ok(Map.of("token", token, "role", u.getRole(), "email", u.getEmail()));
	}
}
