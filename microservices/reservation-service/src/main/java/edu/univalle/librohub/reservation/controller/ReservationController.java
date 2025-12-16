package edu.univalle.librohub.reservation.controller;

import edu.univalle.librohub.reservation.entity.Reservation;
import edu.univalle.librohub.reservation.entity.Room;
import edu.univalle.librohub.reservation.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return reservationService.getAllRooms();
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationsByUser(@PathVariable Long userId) {
        return reservationService.getReservationsByUser(userId);
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        Long roomId = Long.valueOf(body.get("roomId").toString());
        LocalDateTime startTime = LocalDateTime.parse(body.get("startTime").toString());
        LocalDateTime endTime = LocalDateTime.parse(body.get("endTime").toString());

        Reservation reservation = reservationService.createReservation(userId, roomId, startTime, endTime);
        return ResponseEntity.ok(reservation);
    }
}
