package com.exemple.integration_client.entity;

import com.exemple.integration_client.model.EntityModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "entities")
public class DataBaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private Instant date;

    public static DataBaseEntity from(EntityModel model){
        return new DataBaseEntity(model.getId(), model.getName(), model.getDate());
    }


}
