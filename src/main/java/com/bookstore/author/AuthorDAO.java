package com.bookstore.author;

import com.bookstore.book.BookDAO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "books")
@Table(name = "author", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname"})})
public class AuthorDAO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", nullable = false)
    private long authorId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    private String country;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "short_bio")
    private String shortBio;
    @ManyToMany(mappedBy = "authors")
    @JsonIgnoreProperties("authors")
    private Set<BookDAO> books = new HashSet<>();


    public AuthorDAO(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}

