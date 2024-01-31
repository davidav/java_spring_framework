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
@Table(name = "unavailabledates")
public class UnavailableDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant fromDate;
    private Instant toDate;

    @ManyToMany(mappedBy = "unavailableDates")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    private List<Room> rooms = new ArrayList<>();


    public void addRoom(Room room){
        if (rooms == null){
            rooms = new ArrayList<>();
        }
        rooms.add(room);
    }


}
