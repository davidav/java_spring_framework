package com.example.listcontacts.model;
import lombok.*;
import lombok.experimental.FieldNameConstants;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldNameConstants
public class Contact {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long phone;

}
