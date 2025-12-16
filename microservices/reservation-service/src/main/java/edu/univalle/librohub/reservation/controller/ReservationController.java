package edu.univalle.librohub.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
	@PostMapping
	public ResponseEntity<?> create(@RequestBody java.util.Map<String,Object> body) {
		return ResponseEntity.ok(java.util.Map.of("status","reserved"));
	}

	@GetMapping("/rooms")
	public ResponseEntity<?> rooms() {
		return ResponseEntity.ok(java.util.List.of(java.util.Map.of("id",1,"name","Sala A","available",true)));
	}
}
