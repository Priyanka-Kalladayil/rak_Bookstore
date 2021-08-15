package com.rak.bookstore.controller;


import com.rak.bookstore.exception.NotFoundException;
import com.rak.bookstore.payload.BookInfo;
import com.rak.bookstore.payload.BookPatch;
import com.rak.bookstore.service.BookstoreService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@Api(value = "Books API Controller")
@RequestMapping(value = "/bookstore/1.0.0")
@RestController
public class BooksApiController{

    private static final Logger log = LoggerFactory.getLogger(BooksApiController.class);

    @Autowired
    private BookstoreService service;

    /**
     * To save book information into database. It will store the data from book info payload and map the same
     * values into entity object using mapper class to store the values in H2 Database
     *
     * rest controller method to store the data into repository.Based on the requested data,
     * it will call service method to validate, convert it as BOOK
     * data and save the same into database table, All repository calls will go
     * through Builder class which is implemented by builder design pattern. Its
     * separates the logic and object creation from the actual service calls.
     *
     * @param body
     * @return
     */

    @ApiOperation(value = "Create a new book")
    @PostMapping(value = "/books")
    public ResponseEntity<BookInfo> booksPost(@ApiParam(value = "Create new book" ,required=true )  @Valid @RequestBody BookInfo body) {
        return ResponseEntity.ok(service.saveBook(body));
    }

    /**
     * To find  all the stored book info from database. Return the if id is matching with store details
     * else it will return empty
     *
     * controller method to Retrieve all BOOKs data which stored already in the
     * database.it will fetch all stored BOOK  entities, if any .All repository calls will go
     * through Builder class which is implemented by builder design pattern. Its
     * separates the logic and object creation from the actual service calls.
     *
     * @return
     */

    @ApiOperation(value = "find all books")
    @GetMapping(value = "/books")
    public ResponseEntity<List<BookInfo>> booksGet() {
        log.info("find all the records from database");
        return  ResponseEntity.ok(service.findAll());
    }

    /**
     * Delete store book from databased if the id is matching
     *
     * controller method to delete BOOKs data which stored already in the
     * database by give book id.All repository calls will go
     * through Builder class which is implemented by builder design pattern. Its
     * separates the logic and object creation from the actual service calls.
     * @param id
     */

    @ApiOperation(value = "Delete a book")
    @DeleteMapping(value = "/books/{id}")
    public ResponseEntity<Void> booksIdDelete(@ApiParam(value = "book id",required=true)
                                              @PathVariable("id") Long id) {
        log.info("Delete record from database by id {}", id);
        service.deleteBook(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    /**
     * To find the stored book info from database by book id. Return the if id is matching with store details
     * else it will return empty
     *
     * controller method to Retrieve BOOK data which stored already in the
     * database. based on the given id, it will fetch the matched BOOK entity .All repository calls will go
     * through Builder class which is implemented by builder design pattern. Its
     * separates the logic and object creation from the actual service calls.
     *
     * @param id
     * @return
     * @throws NotFoundException
     */

    @ApiOperation(value = "Get book details using id")
    @GetMapping(value = "/books/{id}")
    public ResponseEntity<BookInfo> booksIdGet(@ApiParam(value = "book id",required=true)
                                               @PathVariable("id") Long id) throws NotFoundException {
        log.info("find a record from database by id {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * To update patch info by book id.
     * @param id
     * @param body
     */

    @ApiOperation(value = "Update author/classification/price of a book")
    @PatchMapping(value = "/books/{id}")
    public ResponseEntity<Void> booksIdPatch(@ApiParam(value = "book id",required=true)
                                             @PathVariable("id") Long id,
                                             @ApiParam(value = "Create new book" ,required=true )
                                             @Valid  BookPatch body) throws NotFoundException {
        log.info("update the patch record from database by id {}", id);
        service.updateBook(id, body);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * update book details by id. if the book id is not stored in database, then book record will be created.
     * @param id
     * @param body
     * @return
     */

    @ApiOperation(value = "Update a book")
    @PutMapping(value = "/books/{id}")
    public ResponseEntity<Void> booksIdPut(@ApiParam(value = "book id",required=true)
                                           @PathVariable("id") Long id,
                                           @ApiParam(value = "Create new book" ,required=true )
                                           @Valid @RequestBody BookInfo body) {
        log.info("update the  record from database by id {}", id);
        service.updateBook(id, body);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
