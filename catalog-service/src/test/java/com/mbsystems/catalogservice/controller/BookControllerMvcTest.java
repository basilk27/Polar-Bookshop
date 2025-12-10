package com.mbsystems.catalogservice.controller;


import com.mbsystems.catalogservice.exception.BookNotFoundException;
import com.mbsystems.catalogservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import org.mockito.Mockito;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "7373731394";

        given(bookService.viewBookDetails(isbn))
                .willThrow(BookNotFoundException.class);

        mockMvc
                .perform(get("/books/" + isbn))
                .andExpect(status().isNotFound());
    }
//
//    @Test
//    void whenGetBooksThenStatusOk() throws Exception {
//        given(bookService.viewBookList()).willReturn(List.of());
//
//        mockMvc.perform(get("/books"))
//                .andExpect(status().isOk());
//    }
}