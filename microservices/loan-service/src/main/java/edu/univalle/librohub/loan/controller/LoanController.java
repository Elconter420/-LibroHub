package edu.univalle.librohub.loan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
	@PostMapping("/borrow")
	public ResponseEntity<?> borrow(@RequestBody java.util.Map<String,Object> body) {
		// body: { userId, bookId }
		return ResponseEntity.ok(java.util.Map.of("status","requested"));
	}

	@PostMapping("/return/{id}")
	public ResponseEntity<?> returnLoan(@PathVariable Long id) {
		return ResponseEntity.ok(java.util.Map.of("status","returned"));
	}
}
