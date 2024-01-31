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

    @ManyToMany
    @JoinTable(
            name = "room_unavailabledate",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "unavailabledate_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    private List<UnavailableDate> unavailableDates = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Booking booking;



    public void addUnavailableDates(List<UnavailableDate> addedDates){
        if (unavailableDates == null){
            unavailableDates = new ArrayList<>();
        }
        unavailableDates.addAll(addedDates);
    }
}
