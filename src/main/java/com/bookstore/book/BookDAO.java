package com.bookstore.book;

import com.bookstore.author.AuthorDAO;
import com.bookstore.publisher.BookPublisherDAO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"authors", "bookPublishers"})
@Table(name = "book")
public class BookDAO implements Serializable {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    @JsonIgnoreProperties("books")
    Set<AuthorDAO> authors = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private long bookId;
    @Column(nullable = false)
    private String title;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "page_count")
    private String pageCount;
    @OneToMany(mappedBy = "book", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties("book")
    private Set<BookPublisherDAO> bookPublishers = new HashSet<>();

    public BookDAO(String title) {
        this.title = title;
    }

    public BookDAO(String title, Set<AuthorDAO> authors) {
        this.title = title;
        this.authors = authors;
        this.authors.forEach(author -> author.getBooks().add(this));
    }

    public BookDAO(String title, Set<AuthorDAO> authors, Set<BookPublisherDAO> bookPublishers) {
        this.authors = authors;
        this.title = title;
        this.authors.forEach(author -> author.getBooks().add(this));
        this.bookPublishers = bookPublishers;
        this.bookPublishers.forEach(bookPublisher->bookPublisher.setBook(this));
    }
}


