package com.example.books.dto;

import com.example.books.model.Book;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@DecoratedWith(BookMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

//    Book requestToBook(UpsertBookRequest request);

    BookResponse bookToResponse(Book book);
}
