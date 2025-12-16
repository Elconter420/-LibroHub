package edu.univalle.librohub.reservation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int capacity;

    @Column(name = "has_projector")
    private boolean hasProjector;

    @Column(name = "is_active")
    private boolean isActive = true;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public boolean isHasProjector() { return hasProjector; }
    public void setHasProjector(boolean hasProjector) { this.hasProjector = hasProjector; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
