package com.bookstore.categories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<CategoriesDAO, Integer> {
    CategoriesDAO findCategoriesDAOByCategory(String category);
}
