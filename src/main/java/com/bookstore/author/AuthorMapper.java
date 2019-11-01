package com.bookstore.author;

import org.mapstruct.Mapper;

@Mapper
public interface AuthorMapper {

    AuthorDAO createAuthor(Author author);
}
