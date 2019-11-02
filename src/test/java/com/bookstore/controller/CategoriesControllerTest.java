package com.bookstore.controller;

import com.bookstore.categories.CategoriesDAO;
import com.bookstore.categories.CategoriesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoriesControllerTest {

    @Mock
    List<CategoriesDAO> mockedCategories;

    @Mock
    private CategoriesRepository categoriesRepository;

    private CategoriesController categoriesController;

    @Before
    public void setUp() {
        categoriesController = new CategoriesController(categoriesRepository);
    }

    @Test
    public void returnCategoriesList() {
        //given
        when(categoriesRepository.findAll()).thenReturn(mockedCategories);
        //when
        List<CategoriesDAO> categories = categoriesController.categories();
        //then
        verify(categoriesRepository).findAll();
        assertThat(categories).isEqualTo(mockedCategories);
    }
}
