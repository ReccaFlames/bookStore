package com.bookstore.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BookController {

    @Autowired
    BooksRepository booksRepository;

    @GetMapping("books")
    public List<Book> book() {
        return booksRepository.findAll();
    }

    @PostMapping("books")
    public Book create(@RequestBody Map<String, String> body) {
        String title = body.get("title");
        return booksRepository.save(new Book(title));
    }

    @DeleteMapping("books/{id}")
    public boolean delete(@PathVariable String id){
        int bookId = Integer.parseInt(id);
        booksRepository.deleteById(bookId);
        return true;
    }
}
