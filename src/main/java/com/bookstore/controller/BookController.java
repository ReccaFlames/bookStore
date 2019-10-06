package com.bookstore.controller;

import com.bookstore.author.AuthorDAO;
import com.bookstore.author.AuthorsRepository;
import com.bookstore.book.Book;
import com.bookstore.book.BookDAO;
import com.bookstore.book.BooksRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class BookController {

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    AuthorsRepository authorsRepository;

    @GetMapping("books")
    public List<BookDAO> book() {
        return booksRepository.findAll();
    }

    @PostMapping("books")
    public BookDAO create(@RequestBody Book body) {
        String title = body.getTitle();
        Set<AuthorDAO> authors = body.getAuthors().stream()
                .map(this::createAuthor)
                .collect(Collectors.toCollection(HashSet::new));

        BookDAO book = new BookDAO(title, authors);

        return booksRepository.save(book);
    }

    private AuthorDAO createAuthor(String author) {
        String[] nameAndSurname = StringUtils.split(author, " ");
        String name = nameAndSurname[0];
        String surname = nameAndSurname[1];
        AuthorDAO existingAuthor = authorsRepository.findAuthorDAOByNameAndSurname(name, surname);
        return existingAuthor != null ? existingAuthor : new AuthorDAO(name, surname);
    }

    @DeleteMapping("books/{id}")
    public boolean delete(@PathVariable String id) {
        long bookId = Long.parseLong(id);
        booksRepository.deleteByBookId(bookId);
        return true;
    }
}
