package edu.univalle.librohub.catalog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.univalle.librohub.catalog.entity.Book;
import edu.univalle.librohub.catalog.repository.BookRepository;

@RestController
@RequestMapping("/api/catalog/books")
public class BookController {
	private final BookRepository repo;
	public BookController(BookRepository repo) { this.repo = repo; }

	@GetMapping
	public List<Book> list() { return repo.findAll(); }

	@GetMapping("/{id}")
	public ResponseEntity<Book> get(@PathVariable Long id) {
		return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Book create(@RequestBody Book b) { return repo.save(b); }

	@PutMapping("/{id}")
	public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book b) {
		Optional<Book> ob = repo.findById(id);
		if (ob.isEmpty()) return ResponseEntity.notFound().build();
		Book ex = ob.get();
		ex.setTitle(b.getTitle());
		ex.setAuthor(b.getAuthor());
		ex.setIsbn(b.getIsbn());
		ex.setAvailable(b.getAvailable());
		return ResponseEntity.ok(repo.save(ex));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if (!repo.existsById(id)) return ResponseEntity.notFound().build();
		repo.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
