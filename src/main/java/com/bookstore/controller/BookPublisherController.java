package com.bookstore.controller;

import com.bookstore.book.Book;
import com.bookstore.publisher.BookPublisherDAO;
import com.bookstore.publisher.BookPublishersRepository;
import com.bookstore.publisher.PublisherDAO;
import com.bookstore.publisher.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class BookPublisherController {

    @Autowired
    private BookPublishersRepository bookPublishersRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping("bookPublisher")
    public List<BookPublisherDAO> bookPublishers() {
        return bookPublishersRepository.findAll();
    }

    @GetMapping("bookPublisher/{id}")
    public BookPublisherDAO bookPublisher(@PathVariable String id) {
        return bookPublishersRepository.findBookPublisherDAOById(Long.parseLong(id));
    }

    @PostMapping("bookPublisher")
    public List<BookPublisherDAO> create(@RequestBody Book body) {
        Set<BookPublisherDAO> publishers = body.getPublishers().stream()
                .map(this::createBookPublisher)
                .collect(Collectors.toSet());
        return bookPublishersRepository.saveAll(publishers);
    }

    @GetMapping("publisher")
    public List<PublisherDAO> publishers() {
        return publisherRepository.findAll();
    }

    private BookPublisherDAO createBookPublisher(String name) {
        return new BookPublisherDAO(new PublisherDAO(name));
    }
}
