package com.example.booking.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.ToOne;

import java.time.Instant;


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

    @ManyToOne
    @JoinColumn(name = "room_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

}
