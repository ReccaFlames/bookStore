package com.bookstore.controller;

import com.bookstore.author.Author;
import com.bookstore.author.AuthorDAO;
import com.bookstore.author.AuthorMapper;
import com.bookstore.author.AuthorsRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class AuthorController {

    private static final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);
    private final AuthorsRepository authorsRepository;

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

    @GetMapping(value = "/authors/{name}/{surname}")
    public AuthorDAO getAuthorByName(@PathVariable("name") String name, @PathVariable("surname") String surname) {
        return authorsRepository.findAuthorDAOByNameAndSurname(name, surname);
    }

    @PatchMapping(value = "authors")
    public AuthorDAO patchAuthorByName(@RequestBody Author author) {
        AuthorDAO existingAuthor = authorsRepository.findAuthorDAOByNameAndSurname(author.getName(), author.getSurname());
        if (Objects.isNull(existingAuthor)) {
            return null;
        }
        AuthorDAO authorDAO = authorMapper.createAuthor(author);
        authorDAO.setAuthorId(existingAuthor.getAuthorId());
        return authorsRepository.save(authorDAO);
    }
}
