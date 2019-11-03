package com.bookstore.controller;

import com.bookstore.categories.CategoriesDAO;
import com.bookstore.categories.CategoriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriesControllerTest {

    @Mock
    List<CategoriesDAO> mockedCategories;

    @Mock
    private CategoriesRepository categoriesRepository;

    private CategoriesController categoriesController;

    @BeforeEach
    void setUp() {
        categoriesController = new CategoriesController(categoriesRepository);
    }

    @Test
    void returnCategoriesList() {
        //given
        when(categoriesRepository.findAll()).thenReturn(mockedCategories);
        //when
        List<CategoriesDAO> categories = categoriesController.categories();
        //then
        verify(categoriesRepository).findAll();
        assertThat(categories).isEqualTo(mockedCategories);
    }
}
