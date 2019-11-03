package com.bookstore.controller;

import com.bookstore.book.Book;
import com.bookstore.publisher.BookPublisherDAO;
import com.bookstore.publisher.BookPublishersRepository;
import com.bookstore.publisher.PublisherDAO;
import com.bookstore.publisher.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookPublisherControllerTest {

    @Mock
    private PublisherDAO mockPublisherDAO;

    @Mock
    private BookPublisherDAO mockBookPublisherDAO;

    @Mock
    private List<BookPublisherDAO> mockBookPublisherDAOList;

    @Mock
    private List<PublisherDAO> mockPublisherDAOList;

    @Mock
    private BookPublishersRepository bookPublishersRepository;

    @Mock
    private PublisherRepository publisherRepository;

    private BookPublisherController bookPublisherController;

    @BeforeEach
    public void setUp() {
        bookPublisherController = new BookPublisherController(bookPublishersRepository,
                publisherRepository
        );
    }

    @Test
    void returnAllBookPublishers() {
        //given
        when(bookPublishersRepository.findAll()).thenReturn(mockBookPublisherDAOList);
        //when
        bookPublisherController.bookPublishers();
        //then
        verify(bookPublishersRepository).findAll();
    }

    @Test
    void returnBookPublisherDAOForGivenID() {
        //given
        when(bookPublishersRepository.findBookPublisherDAOByBookPublisherId(anyLong())).thenReturn(mockBookPublisherDAO);
        //when
        bookPublisherController.bookPublisher("1");
        //then
        verify(bookPublishersRepository).findBookPublisherDAOByBookPublisherId(anyLong());
    }

    @Test
    void returnAllPublishers() {
        //given
        when(publisherRepository.findAll()).thenReturn(mockPublisherDAOList);
        //when
        bookPublisherController.publishers();
        //then
        verify(publisherRepository).findAll();
    }

    @Test
    public void createBookPublishers() {
        //given
        when(publisherRepository.findByName(anyString())).thenReturn(mockPublisherDAO);
        //when
        bookPublisherController.create(createBookWithPublishers());
        //then
        verify(publisherRepository, times(2)).findByName(anyString());
        verify(bookPublishersRepository).saveAll(any(Set.class));
    }

    private Book createBookWithPublishers() {
        Book book = new Book();
        List<String> publishers = new ArrayList<>(
                Arrays.asList("A", "B")
        );
        book.setPublishers(publishers);
        return book;
    }
}
