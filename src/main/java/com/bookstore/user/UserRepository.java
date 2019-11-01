package com.bookstore.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDAO, Integer> {
    UserDAO findByUsername(String username);
}
