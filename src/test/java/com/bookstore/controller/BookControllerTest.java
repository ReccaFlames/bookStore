package com.bookstore.controller;

import com.bookstore.author.Author;
import com.bookstore.author.AuthorDAO;
import com.bookstore.author.AuthorsRepository;
import com.bookstore.book.Book;
import com.bookstore.book.BookDAO;
import com.bookstore.book.BooksRepository;
import com.bookstore.categories.CategoriesDAO;
import com.bookstore.categories.CategoriesRepository;
import com.bookstore.publisher.PublisherDAO;
import com.bookstore.publisher.PublisherRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    private final Book bookToSave = createBook();

    @Mock
    private BookDAO mockBookDAO;

    @Mock
    private List<BookDAO> bookDAOS;

    @Mock
    private BooksRepository booksRepository;

    @Mock
    private AuthorsRepository  authorsRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private CategoriesRepository categoriesRepository;

    private BookController bookController;

    @Before
    public void setUp() {
        when(booksRepository.save(any(BookDAO.class))).thenReturn(mockBookDAO);
        bookController = new BookController(
                booksRepository,
                authorsRepository,
                publisherRepository,
                categoriesRepository
        );
    }

    @Test
    public void saveBookIntoRepository() {
        //given
        when(categoriesRepository.findCategoriesDAOByCategory(anyString())).thenReturn(null);
        when(publisherRepository.findByName(anyString())).thenReturn(null);
        when(authorsRepository.findAuthorDAOByNameAndSurname(anyString(), anyString())).thenReturn(null);
        //when
        bookController.create(bookToSave);
        //then
        verifyBookCreation();
    }

    @Test
    public void saveBookIntoRepositoryWithExistingElements() {
        //given
        when(categoriesRepository.findCategoriesDAOByCategory(anyString())).thenReturn(mock(CategoriesDAO.class));
        when(publisherRepository.findByName(anyString())).thenReturn(mock(PublisherDAO.class));
        when(authorsRepository.findAuthorDAOByNameAndSurname(anyString(), anyString())).thenReturn(mock(AuthorDAO.class));
        //when
        bookController.create(bookToSave);
        //then
        verifyBookCreation();
    }

    @Test
    public void bookRemovedFromRepository() {
        //given
        //when
        ResponseEntity response = bookController.delete(1L);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(true);
    }

    @Test
    public void getAllBooks() {
        //given
        when(booksRepository.findAll()).thenReturn(bookDAOS);
        //when
        List<BookDAO> books = bookController.book();
        //then
        assertThat(books).isNotNull();
    }

    private void verifyBookCreation() {
        verify(booksRepository).save(any(BookDAO.class));
        verify(categoriesRepository, times(2)).findCategoriesDAOByCategory(anyString());
        verify(publisherRepository, times(2)).findByName(anyString());
        verify(authorsRepository, times(2)).findAuthorDAOByNameAndSurname(anyString(), anyString());
    }

    private Book createBook() {
        Book book = new Book();
        book.setTitle("Title");
        book.setAuthors(createAuthors());
        book.setPublishers(Arrays.asList("PWN", "PKN"));
        book.setCategories(Arrays.asList("Sci-Fun", "Document"));
        book.setPageCount(200);
        book.setShortDescription("Really short book");
        return book;
    }

    private List<Author> createAuthors() {
        List<Author> authors = new ArrayList<>();
        Author author1 = new Author("Joe", "Doe", "USA", LocalDate.of(2019, 8, 10), "Short Bio about Joe");
        Author author2 = new Author("Boban", "Marjanovic", "Serbia", LocalDate.of(2009, 8, 10), "Short Bio about Boban");
        authors.add(author1);
        authors.add(author2);
        return authors;
    }


}
