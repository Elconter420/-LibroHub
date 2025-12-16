package edu.univalle.librohub.reservation.service;

import edu.univalle.librohub.reservation.entity.Reservation;
import edu.univalle.librohub.reservation.entity.Room;
import edu.univalle.librohub.reservation.repository.ReservationRepository;
import edu.univalle.librohub.reservation.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findByIsActiveTrue();
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public Reservation createReservation(Long userId, Long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Here you should check for conflicts/overlaps
        
        Reservation reservation = new Reservation();
        reservation.setUserId(userId);
        reservation.setRoom(room);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setStatus("CONFIRMED");

        return reservationRepository.save(reservation);
    }
}
