package com.bookstore.author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorsRepository extends JpaRepository<AuthorDAO, Integer> {
    AuthorDAO findAuthorDAOByNameAndSurname(String name, String surname);

    List<AuthorDAO> findAuthorDAOSBySurname(String surname);

    AuthorDAO findAuthorDAOByAuthorId(long id);
}
