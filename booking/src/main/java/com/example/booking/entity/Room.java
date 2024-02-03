package com.example.booking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer number;
    private Double price;
    private Integer capacity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    @ToString.Exclude
    private Hotel hotel;

    @ManyToMany
    @JoinTable(
            name = "room_reserve",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "reserve_id"))
    @Builder.Default
    @ToString.Exclude
    private List<Reserve> reserves = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "rooms_bookings",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "bookings_id"))
    @Builder.Default
    @ToString.Exclude
    private List<Booking> bookings = new ArrayList<>();

    public void addBooking(Booking booking){
        if (bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
    }

}
