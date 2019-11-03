package com.bookstore.controller;

import com.bookstore.author.Author;
import com.bookstore.author.AuthorDAO;
import com.bookstore.author.AuthorMapper;
import com.bookstore.author.AuthorsRepository;
import com.bookstore.book.Book;
import com.bookstore.book.BookDAO;
import com.bookstore.book.BookMapper;
import com.bookstore.book.BooksRepository;
import com.bookstore.categories.CategoriesDAO;
import com.bookstore.categories.CategoriesRepository;
import com.bookstore.publisher.BookPublisherDAO;
import com.bookstore.publisher.PublisherDAO;
import com.bookstore.publisher.PublisherRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private static final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);
    private static final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);
    private final BooksRepository booksRepository;
    private final AuthorsRepository authorsRepository;
    private final PublisherRepository publisherRepository;
    private final CategoriesRepository categoriesRepository;

    public BookController(BooksRepository booksRepository, AuthorsRepository authorsRepository,
                          PublisherRepository publisherRepository, CategoriesRepository categoriesRepository) {
        this.booksRepository = booksRepository;
        this.authorsRepository = authorsRepository;
        this.publisherRepository = publisherRepository;
        this.categoriesRepository = categoriesRepository;
    }

    @DeleteMapping("books/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        booksRepository.deleteByBookId(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("books")
    public List<BookDAO> book() {
        return booksRepository.findAll();
    }

    @PostMapping("books")
    public BookDAO create(@RequestBody Book body) {
        BookDAO bookDAO = createBook(body);

        return booksRepository.save(bookDAO);
    }

    private BookDAO createBook(Book book) {
        Set<AuthorDAO> authors = book.getAuthors().stream()
                .map(this::createAuthor)
                .collect(Collectors.toCollection(HashSet::new));

        Set<BookPublisherDAO> publishers = book.getPublishers().stream()
                .map(this::createBookPublisher)
                .collect(Collectors.toCollection(HashSet::new));

        Set<CategoriesDAO> categories = book.getCategories().stream()
                .map(this::createCategory)
                .collect(Collectors.toCollection(HashSet::new));

        BookDAO bookDAO = bookMapper.mapBook(book);
        bookDAO.setAuthors(authors);
        bookDAO.setBookPublishers(publishers);
        bookDAO.setCategories(categories);

        return bookDAO;
    }

    private CategoriesDAO createCategory(String name) {
        CategoriesDAO existingCategory = categoriesRepository.findCategoriesDAOByCategory(name);
        return existingCategory != null ? existingCategory : new CategoriesDAO(name);
    }

    private BookPublisherDAO createBookPublisher(String name) {
        PublisherDAO existingPublisher = publisherRepository.findByName(name);
        PublisherDAO publisher = existingPublisher != null ? existingPublisher : new PublisherDAO(name);

        return new BookPublisherDAO(publisher);
    }

    private AuthorDAO createAuthor(Author author) {
        AuthorDAO existingAuthor = authorsRepository.findAuthorDAOByNameAndSurname(author.getName(), author.getSurname());
        return Objects.isNull(existingAuthor) ? authorMapper.createAuthor(author) : existingAuthor;
    }
}
