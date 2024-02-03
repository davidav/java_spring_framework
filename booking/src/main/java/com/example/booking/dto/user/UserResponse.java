package com.example.booking.dto.user;

import com.example.booking.dto.booking.BookingResponse;
import com.example.booking.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private RoleType roles;
    private List<BookingResponse> bookings;

}
