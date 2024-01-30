package com.example.booking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;
    private Integer number;
    private Double price;
    private Integer capacity;

    @ManyToMany
    @JoinTable(
            name = "room_unavailable_date",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "unavailable_date_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    private List<UnavailableDate> unavailableDates = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Hotel hotel;


}
