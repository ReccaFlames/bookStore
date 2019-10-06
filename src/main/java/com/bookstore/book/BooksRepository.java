package com.bookstore.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<BookDAO, Integer> {
    List<BookDAO> findAllByTitleContaining(String text);

    @Transactional
    void deleteByBookId(long id);
}
