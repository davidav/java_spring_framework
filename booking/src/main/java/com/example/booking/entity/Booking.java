package com.example.booking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant arrival;
    private Instant departure;

    @ManyToMany(mappedBy = "bookings")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    private List<Room> rooms = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

}
