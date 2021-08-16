package com.rak.bookstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rak.bookstore.BookstoreApplication;
import com.rak.bookstore.builder.BookstoreBuilder;
import com.rak.bookstore.payload.BookInfo;
import com.rak.bookstore.service.BookstoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookstoreController.class)
@ContextConfiguration(classes = { BookstoreApplication.class })
public class BookstoreController {

    @Autowired
    private MockMvc mvc;
    @MockBean
    BookstoreService mockService;
    @MockBean
    BookstoreBuilder mockBuilder;

    @Test
    public void testWithNullRequiredData () throws Exception {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setAuthorEmailAddress("user@example.com");
        bookInfo.setAuthorName("Test Name");
        bookInfo.setCategory("Test");
        bookInfo.setDescription("Test");
        when(mockService.saveBook(bookInfo)).thenReturn(null);
        mvc.perform(post("/bookstore/1.0.0/books").contentType("application/json").content(toJson(bookInfo)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testWithInvalidEmailId () throws Exception {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setAuthorEmailAddress("user@example");
        bookInfo.setAuthorName("Test Name");
        bookInfo.setCategory("Test");
        bookInfo.setDescription("Test");
        bookInfo.setPrice(10d);
        bookInfo.setClassification(BookInfo.ClassificationEnum.ADVENTURE);
        when(mockService.saveBook(bookInfo)).thenReturn(null);
        mvc.perform(post("/bookstore/1.0.0/books").contentType("application/json").content(toJson(bookInfo)))
                .andExpect(status().isNotFound());
    }

    private byte[] toJson(BookInfo bookInfo) throws JsonProcessingException {
        return new ObjectMapper ().writeValueAsBytes(bookInfo);
    }

    @Test
    public void testWithValidData () throws Exception {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(1l);
        bookInfo.setAuthorEmailAddress("user@example");
        bookInfo.setAuthorName("Test Name");
        bookInfo.setCategory("Test");
        bookInfo.setDescription("Test");
        bookInfo.setPrice(10d);
        bookInfo.setClassification(BookInfo.ClassificationEnum.ADVENTURE);
        when(mockService.saveBook(bookInfo)).thenReturn(bookInfo);
        mvc.perform(post("/bookstore/1.0.0/books").contentType("application/json").content(toJson(bookInfo)));

    }

   @Test
    public void testGetData () throws Exception {
       when(mockService.findById(1L)).thenReturn(testPayload());
        mvc.perform(get("/bookstore/1.0.0/books/1").contentType("application/json").content(toJson(testPayload())));
    }


    @Test
    public void testDeleteData () throws Exception {
        mockService.deleteBook(1L);
        mvc.perform(delete("/bookstore/1.0.0/books/1").contentType("application/json"));
    }

    @Test
    public void testUpdateData () throws Exception {
       BookInfo payload = testPayload();
        when(mockService.updateBook(1L, payload)).thenReturn(testPayload());
        mvc.perform(put("/bookstore/1.0.0/books/1").contentType("application/json").content(toJson(testPayload())));
    }

    @Test
    public void testPatchData () throws Exception {
        BookInfo payload = testPayload();
        when(mockService.updateBook(1L, payload)).thenReturn(testPayload());
        mvc.perform(put("/bookstore/1.0.0/books/1").contentType("application/json").content(toJson(testPayload())));
    }

    private BookInfo testPayload () {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(1l);
        bookInfo.setAuthorEmailAddress("user@example");
        bookInfo.setAuthorName("Test Name");
        bookInfo.setCategory("Test");
        bookInfo.setDescription("Test");
        bookInfo.setPrice(10d);
        bookInfo.setClassification(BookInfo.ClassificationEnum.ADVENTURE);
        return bookInfo;
    }
}
