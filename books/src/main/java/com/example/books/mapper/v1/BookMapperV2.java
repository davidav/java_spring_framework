package com.example.books.mapper.v1;

import com.example.books.dto.BookListResponse;
import com.example.books.dto.BookResponse;
import com.example.books.dto.UpsertBookRequest;
import com.example.books.model.Book;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;


@DecoratedWith(BookMapperV2Delegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapperV2 {

    default BookListResponse bookListToBookListResponse(List<Book> books){
        BookListResponse response = new BookListResponse();
        response.setBooks(books.stream().map(this::bookToResponse).collect(Collectors.toList()));
        return response;
    }


    BookResponse bookToResponse(Book book);

    Book requestToBook(UpsertBookRequest request);
}
