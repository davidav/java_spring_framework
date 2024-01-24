package com.example.news.dto.mapper;


import com.example.news.dto.news.NewsListResponse;
import com.example.news.dto.news.NewsWithoutContactsResponse;
import com.example.news.dto.news.NewsResponse;
import com.example.news.dto.news.UpsertNewsRequest;
import com.example.news.model.News;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


import java.util.List;
import java.util.stream.Collectors;
@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {

    default NewsListResponse newsListToNewsListResponse(List<News> newses){
        NewsListResponse response = new NewsListResponse();
        response.setNewses(newses.stream().map(this::newsWithoutContactsToResponse).collect(Collectors.toList()));
        return response;
    }

    NewsWithoutContactsResponse newsWithoutContactsToResponse(News news);

    News requestToNews(UpsertNewsRequest request);

    News requestToNews(Long id, UpsertNewsRequest request);

    NewsResponse newsToResponse(News news);
}
