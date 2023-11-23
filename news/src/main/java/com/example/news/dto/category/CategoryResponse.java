package com.example.news.dto.category;

import com.example.news.model.News;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    private String name;

    private List<News> newses;

}
