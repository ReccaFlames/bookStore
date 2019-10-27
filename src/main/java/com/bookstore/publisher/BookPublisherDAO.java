package com.bookstore.publisher;

import com.bookstore.book.BookDAO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "book_publisher")
public class BookPublisherDAO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_publisher_id", updatable = false, nullable = false)
    private long bookPublisherId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties("bookPublishers")
    private BookDAO book;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "bookPublishers", "id"})
    private PublisherDAO publisher;

    private LocalDate publishedDate;

    public BookPublisherDAO(PublisherDAO publisher) {
        this.publisher = publisher;
        this.publisher.getBookPublishers().add(this);
    }
}
