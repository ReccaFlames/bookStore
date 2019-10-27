package com.bookstore.controller;

import com.bookstore.categories.CategoriesDAO;
import com.bookstore.categories.CategoriesRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriesController {

    private CategoriesRepository categoriesRepository;

    public CategoriesController(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @GetMapping("categories")
    public List<CategoriesDAO> categories() {
        return categoriesRepository.findAll();
    }
}
