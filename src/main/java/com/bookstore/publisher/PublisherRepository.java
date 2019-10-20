package com.bookstore.publisher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<PublisherDAO, Integer> {
    PublisherDAO findByName(String name);
}
