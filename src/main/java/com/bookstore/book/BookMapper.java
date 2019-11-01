package com.bookstore.book;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface BookMapper {

    @Mappings({
            @Mapping(ignore = true, target = "bookPublishers"),
            @Mapping(ignore = true, target = "authors"),
            @Mapping(ignore = true, target = "categories")
    })
    BookDAO mapBook(Book book);
}
