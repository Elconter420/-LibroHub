package edu.univalle.librohub.user.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import edu.univalle.librohub.user.repository.UserRepository;
import edu.univalle.librohub.user.entity.User;

@Service
public class UserService {
	private final UserRepository repo;
	public UserService(UserRepository repo) { this.repo = repo; }
	public Optional<User> findByEmail(String email) { return repo.findByEmail(email); }
}
