package com.example.booking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reserves")
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant fromDateReserved;
    private Instant toDateReserved;

    @ManyToMany(mappedBy = "reserves")
    @ToString.Exclude
    @Builder.Default
    private List<Room> rooms = new ArrayList<>();

}
