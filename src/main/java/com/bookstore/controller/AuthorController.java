package com.bookstore.controller;

import com.bookstore.author.AuthorDAO;
import com.bookstore.author.AuthorsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    private AuthorsRepository authorsRepository;

    public AuthorController(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    @GetMapping("authors")
    public List<AuthorDAO> authors() {
        return authorsRepository.findAll();
    }

    @GetMapping("authors/{id}")
    public AuthorDAO getAuthor(@PathVariable("id") String id) {
        long authorId = Long.parseLong(id);
        return authorsRepository.findAuthorDAOByAuthorId(authorId);
    }

    @GetMapping("authors/{lastName}")
    public List<AuthorDAO> getAuthorsByLastName(@PathVariable("lastName") String lastName) {
        return authorsRepository.findAuthorDAOSBySurname(lastName);
    }

    @RequestMapping(value = "/authors/{name}/{surname}", method = RequestMethod.GET)
    public AuthorDAO getAuthorByName(@PathVariable("name") String name, @PathVariable("surname") String surname) {
        return authorsRepository.findAuthorDAOByNameAndSurname(name, surname);
    }
}
