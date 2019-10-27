package com.bookstore.controller;

import com.bookstore.author.Author;
import com.bookstore.author.AuthorDAO;
import com.bookstore.author.AuthorsRepository;
import com.bookstore.book.Book;
import com.bookstore.book.BookDAO;
import com.bookstore.book.BooksRepository;
import com.bookstore.categories.CategoriesDAO;
import com.bookstore.categories.CategoriesRepository;
import com.bookstore.publisher.BookPublisherDAO;
import com.bookstore.publisher.PublisherDAO;
import com.bookstore.publisher.PublisherRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private BooksRepository booksRepository;
    private AuthorsRepository authorsRepository;
    private PublisherRepository publisherRepository;
    private CategoriesRepository categoriesRepository;

    public BookController(BooksRepository booksRepository, AuthorsRepository authorsRepository,
                          PublisherRepository publisherRepository, CategoriesRepository categoriesRepository) {
        this.booksRepository = booksRepository;
        this.authorsRepository = authorsRepository;
        this.publisherRepository = publisherRepository;
        this.categoriesRepository = categoriesRepository;
    }

    @GetMapping("books")
    public List<BookDAO> book() {
        return booksRepository.findAll();
    }

    @PostMapping("books")
    public BookDAO create(@RequestBody Book body) {
        String title = body.getTitle();
        Set<AuthorDAO> authors = body.getAuthors().stream()
                .map(this::createAuthor)
                .collect(Collectors.toCollection(HashSet::new));

        Set<BookPublisherDAO> publishers = body.getPublishers().stream()
                .map(this::createBookPublisher)
                .collect(Collectors.toCollection(HashSet::new));

        Set<CategoriesDAO> categories = body.getCategories().stream()
                .map(this::createCategory)
                .collect(Collectors.toCollection(HashSet::new));

        BookDAO book = new BookDAO(title, authors, publishers, categories);

        return booksRepository.save(book);
    }

    private CategoriesDAO createCategory(String name) {
        CategoriesDAO existingCategory = categoriesRepository.findCategoriesDAOByCategory(name);
        return existingCategory != null ? existingCategory : new CategoriesDAO(name);
    }

    private BookPublisherDAO createBookPublisher(String name) {
        PublisherDAO existingPublisher = publisherRepository.findByName(name);
        PublisherDAO publisher = existingPublisher != null ? existingPublisher : new PublisherDAO(name);
        BookPublisherDAO bookPublisherDAO = new BookPublisherDAO(publisher);

        return bookPublisherDAO;
    }

    private AuthorDAO createAuthor(Author author) {
        String name = author.getName();
        String surname = author.getSurname();
        AuthorDAO existingAuthor = authorsRepository.findAuthorDAOByNameAndSurname(name, surname);
        return existingAuthor != null ? existingAuthor : new AuthorDAO(name, surname, author.getCountry(), author.getDateOfBirth());
    }

    @DeleteMapping("books/{id}")
    public boolean delete(@PathVariable String id) {
        long bookId = Long.parseLong(id);
        booksRepository.deleteByBookId(bookId);
        return true;
    }
}
