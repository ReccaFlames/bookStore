package com.bookstore.controller;

import com.bookstore.book.Book;
import com.bookstore.publisher.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class BookPublisherController {

    private BookPublishersRepository bookPublishersRepository;
    private PublisherRepository publisherRepository;

    public BookPublisherController(BookPublishersRepository bookPublishersRepository, PublisherRepository publisherRepository) {
        this.bookPublishersRepository = bookPublishersRepository;
        this.publisherRepository = publisherRepository;
    }

    @GetMapping("bookPublisher")
    public List<BookPublisherDAO> bookPublishers() {
        return bookPublishersRepository.findAll();
    }

    @GetMapping("bookPublisher/{id}")
    public BookPublisherDAO bookPublisher(@PathVariable String id) {
        return bookPublishersRepository.findBookPublisherDAOByBookPublisherId(Long.parseLong(id));
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
        PublisherDAO existingPublisher = publisherRepository.findByName(name);
        PublisherDAO publisher = existingPublisher != null ? existingPublisher : new PublisherDAO(name);
        return new BookPublisherDAO(publisher);
    }
}
