package com.bookstore.controller;

import com.bookstore.author.AuthorDAO;
import com.bookstore.author.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    @Autowired
    AuthorsRepository authorsRepository;

    @GetMapping("authors")
    public List<AuthorDAO> authors() {
        return authorsRepository.findAll();
    }

    @RequestMapping(value = "/authors/{name}/{surname}", method = RequestMethod.GET)
    public AuthorDAO getAuthorByName(@PathVariable("name") String name, @PathVariable("surname") String surname) {
        return authorsRepository.findAuthorDAOByNameAndSurname(name, surname);
    }
}