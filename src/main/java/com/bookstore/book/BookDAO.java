package com.bookstore.book;

import com.bookstore.author.AuthorDAO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "authors")
@Table(name = "book")
public class BookDAO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private long bookId;
    @Column(nullable = false)
    private String title;
    private String publisher;
    @Column(name = "published_day")
    private String publishedDay;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "page_count")
    private String pageCount;
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = { @JoinColumn(name = "book_id") },
            inverseJoinColumns = { @JoinColumn(name = "author_id") }
    )
    @JsonIgnoreProperties("books")
    Set<AuthorDAO> authors = new HashSet<>();

    public BookDAO(String title) {
        this.title = title;
    }

    public BookDAO(String title, Set<AuthorDAO> authors) {
        this.title = title;
        this.authors = authors;
        this.authors.forEach(author->author.getBooks().add(this));
    }

}


