package com.rak.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rak.bookstore.BookstoreApplication;
import com.rak.bookstore.payload.BookInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes=BookstoreApplication.class)
public class BookstoreIntegrationTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testWithNullRequiredData () throws Exception {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setAuthorEmailAddress("user@example.com");
        bookInfo.setAuthorName("Test Name");
        bookInfo.setCategory("Test");
        bookInfo.setDescription("Test");
        headers.add("Content-Type", "application/json");
        HttpEntity<BookInfo> entity = new HttpEntity<BookInfo>(bookInfo, headers);
        ResponseEntity<String> responseEnty = restTemplate.exchange(
                createURLWithPort("/books"), HttpMethod.POST, entity,
                String.class);
        assertTrue (responseEnty.getStatusCode().name().equals(HttpStatus.BAD_REQUEST.name()));
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
        headers.add("Content-Type", "application/json");
        HttpEntity<BookInfo> entity = new HttpEntity<BookInfo>(bookInfo, headers);
        ResponseEntity<String> responseEnty = restTemplate.exchange(
                createURLWithPort("/books"), HttpMethod.POST, entity,
                String.class);
        assertTrue (responseEnty.getStatusCode().name().equals(HttpStatus.BAD_REQUEST.name()));
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
        bookInfo.setName("Test");
        bookInfo.setIsbn("Test isbn");
        bookInfo.setClassification(BookInfo.ClassificationEnum.ADVENTURE);
        headers.add("Content-Type", "application/json");
        HttpEntity<BookInfo> entity = new HttpEntity<BookInfo>(bookInfo, headers);
        ResponseEntity<String> responseEnty = restTemplate.exchange(
                createURLWithPort("/books"), HttpMethod.POST, entity,
                String.class);
        String expected = new ObjectMapper().writeValueAsString(bookInfo);
        JSONAssert.assertEquals(expected, responseEnty.getBody(), false);

    }

    @Test
    public void testGetBooks () throws Exception {

        headers.add("Content-Type", "application/json");
        HttpEntity<BookInfo> entity = new HttpEntity<BookInfo>(null, headers);
        ResponseEntity<String> responseEnty = restTemplate.exchange(
                createURLWithPort("/books/1"), HttpMethod.GET, entity,
                String.class);
        assertTrue(responseEnty.getBody().contains("No matching data found for id 1"));

    }



    @Test
    public void testUpdateBooks () throws Exception {

        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(1l);
        bookInfo.setAuthorEmailAddress("user@example");
        bookInfo.setAuthorName("Test Name");
        bookInfo.setCategory("Test");
        bookInfo.setDescription("Test");
        bookInfo.setPrice(10d);
        bookInfo.setName("Test");
        bookInfo.setIsbn("Test isbn");
        bookInfo.setClassification(BookInfo.ClassificationEnum.ADVENTURE);

        headers.add("Content-Type", "application/json");
        HttpEntity<BookInfo> entity = new HttpEntity<BookInfo>(null, headers);
        ResponseEntity<String> responseEnty = restTemplate.exchange(
                createURLWithPort("/books/1"), HttpMethod.PUT, entity,
                String.class);
       assertTrue(responseEnty.getStatusCodeValue() == 400);

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/bookstore/1.0.0"+ uri;
    }
}
