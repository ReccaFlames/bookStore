package com.bookstore.book;

import com.bookstore.author.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Book implements Serializable {
    private String title;
    private List<Author> authors;
    private List<String> publishers;
    private List<String> categories;
}
