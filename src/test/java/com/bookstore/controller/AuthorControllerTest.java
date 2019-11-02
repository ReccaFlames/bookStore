package com.bookstore.controller;

import com.bookstore.author.Author;
import com.bookstore.author.AuthorDAO;
import com.bookstore.author.AuthorsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorControllerTest {

    private static final String LAST_NAME = "lastName";
    private static final String FIRST_NAME = "Name";
    private static final String SHORT_BIO = "new short bio";
    private static final Long AUTHOR_ID = 1L;

    @Mock
    private AuthorsRepository authorsRepository;

    @Mock
    private AuthorDAO mockedAuthorDAO;

    @Mock
    private List<AuthorDAO> mockedAuthors;

    private AuthorController authorController;

    @Before
    public void setUp() {
        authorController = new AuthorController(authorsRepository);
    }

    @Test
    public void returnAuthor() {
        //given
        when(authorsRepository.findAuthorDAOByAuthorId(anyLong())).thenReturn(mockedAuthorDAO);
        //when
        AuthorDAO author = authorController.getAuthor(AUTHOR_ID);
        //then
        assertThat(author).isEqualTo(mockedAuthorDAO);
    }

    @Test
    public void returnListOfAuthorsWithTheSameSurname() {
        //given
        when(authorsRepository.findAuthorDAOSBySurname(LAST_NAME)).thenReturn(mockedAuthors);
        //when
        List<AuthorDAO> authors = authorController.getAuthorsByLastName(LAST_NAME);
        //then
        assertThat(authors).isEqualTo(mockedAuthors);
    }

    @Test
    public void returnAuthorByName() {
        //given
        when(authorsRepository.findAuthorDAOByNameAndSurname(FIRST_NAME, LAST_NAME)).thenReturn(mockedAuthorDAO);
        //when
        AuthorDAO author = authorController.getAuthorByName(FIRST_NAME, LAST_NAME);
        //then
        assertThat(author).isEqualTo(mockedAuthorDAO);
    }

    @Test
    public void returnPatchedAuthor() {
        //given
        when(authorsRepository.findAuthorDAOByNameAndSurname(FIRST_NAME, LAST_NAME)).thenReturn(mockedAuthorDAO);
        when(authorsRepository.save(any())).thenReturn(mockedAuthorDAO);
        //when
        AuthorDAO authorDAO = authorController.patchAuthorByName(createAuthor());
        //then
        verify(authorsRepository).save(any());
        verify(authorsRepository).findAuthorDAOByNameAndSurname(FIRST_NAME, LAST_NAME);
        assertThat(authorDAO).isEqualTo(mockedAuthorDAO);
    }

    @Test
    public void patchReturnEmptyResponse() {
        //given
        when(authorsRepository.findAuthorDAOByNameAndSurname(FIRST_NAME, LAST_NAME)).thenReturn(null);
        //when
        AuthorDAO authorDAO = authorController.patchAuthorByName(createAuthor());
        //then
        verify(authorsRepository).findAuthorDAOByNameAndSurname(FIRST_NAME, LAST_NAME);
        assertThat(authorDAO).isNull();
    }

    private Author createAuthor() {
        Author author = new Author();
        author.setName(FIRST_NAME);
        author.setSurname(LAST_NAME);
        author.setShortBio(SHORT_BIO);
        return author;
    }
}
