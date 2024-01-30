package com.example.booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity(name = "unavailable_dates")
public class UnavailableDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant from;
    private Instant to;

    @ManyToMany(mappedBy = "unavailableDates")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    private List<Room> rooms = new ArrayList<>();

}
