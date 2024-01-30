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
@Entity(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String title;
    private String city;
    private String address;
    private Long fromCentre;
    private Integer rating;
    private Integer numberOfRatings;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Room> rooms = new ArrayList<>();

}
