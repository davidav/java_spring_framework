package com.example.news.dto.news;


import com.example.news.validation.UserFilterValid;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@UserFilterValid
public class NewsResponse {


    private String title;

    private String text;

    private Integer countComments;


}
