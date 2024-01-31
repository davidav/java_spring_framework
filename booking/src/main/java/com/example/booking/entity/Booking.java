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

    private Instant arrivalDate;
    private Instant departureDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    @ToString.Exclude
    @Builder.Default
    private List<Room> room = new ArrayList<>();

    @OneToOne
    private User user;

}
