package com.example.booking.dto.hotel;

import com.example.booking.validation.PagesFilterValid;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@PagesFilterValid
public class HotelListResponse {

    private List<HotelResponse> hotels;

}
