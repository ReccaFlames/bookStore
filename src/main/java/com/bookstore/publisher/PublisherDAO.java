package com.bookstore.publisher;

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
@EqualsAndHashCode(exclude = "bookPublishers")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class PublisherDAO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private long id;

    private String name;

    @OneToMany(mappedBy = "publisher", orphanRemoval = true)
    @JsonIgnoreProperties("publisher")
    private Set<BookPublisherDAO> bookPublishers = new HashSet<>();

    public PublisherDAO(String name) {
        this.name = name;
    }
}
