package com.example.booking.dto;



import com.example.booking.validation.PagesFilterValid;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@PagesFilterValid
public class PagesRequest {

    private Integer pageSize;
    private Integer pageNumber;

}
