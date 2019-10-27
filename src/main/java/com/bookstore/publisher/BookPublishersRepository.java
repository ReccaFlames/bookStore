package com.bookstore.publisher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPublishersRepository extends JpaRepository<BookPublisherDAO, Integer> {
    BookPublisherDAO findBookPublisherDAOByBookPublisherId(Long integer);
}
