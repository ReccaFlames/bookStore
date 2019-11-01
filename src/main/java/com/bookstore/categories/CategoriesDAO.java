package com.bookstore.categories;

import com.bookstore.book.BookDAO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode()
@Table(name = "categories", uniqueConstraints = {@UniqueConstraint(columnNames = {"category"})})
public class CategoriesDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long id;
    private String category;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private Set<BookDAO> books = new HashSet<>();

    public CategoriesDAO(String category) {
        this.category = category;
    }
}
