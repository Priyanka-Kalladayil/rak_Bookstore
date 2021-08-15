package com.rak.bookstore.service;

import com.rak.bookstore.builder.BookstoreBuilder;
import com.rak.bookstore.exception.NotFoundException;
import com.rak.bookstore.payload.BookInfo;
import com.rak.bookstore.payload.BookPatch;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookstoreService {


    private ObjectFactory<BookstoreBuilder> builder;

    @Autowired
    public BookstoreService(ObjectFactory<BookstoreBuilder> builder) {
        this.builder = builder;
    }

    /**
     * To save book information into database. It will store the data from book info payload and map the same
     * values into entity object using mapper class to store the values in H2 Database.
     * @param bookInfo
     * @return
     */
    public BookInfo saveBook (BookInfo bookInfo) {
        BookstoreBuilder bookstoreBuilder = builder.getObject();
        return bookstoreBuilder.with(bookInfo).save();
    }

    /**
     * To find the stored book info from database by book id. Return the if id is matching with store details
     * else it will return empty.
     * @param id
     * @return
     * @throws NotFoundException
     */
    public BookInfo findById (Long id) throws NotFoundException {
        BookstoreBuilder bookstoreBuilder = builder.getObject();
        return bookstoreBuilder.with(id).find();
    }
    /**
     * To find  all the stored book info from database. Return the if id is matching with store details
     * else it will return empty
     * @return
     */
    public List<BookInfo> findAll () {
        BookstoreBuilder bookstoreBuilder = builder.getObject();
        return bookstoreBuilder.findAll();
    }

    /**
     * Delete store book from databased if the id is matching
     * @param id
     */
    public void deleteBook (Long id) {
        BookstoreBuilder bookstoreBuilder = builder.getObject();
        bookstoreBuilder.with(id).delete();
    }

    /**
     * update book details by id. if the book id is not stored in database, then book record will be created.
     * @param id
     * @param bookInfo
     * @return
     */
    public BookInfo updateBook (Long id, BookInfo bookInfo) {
        BookstoreBuilder bookstoreBuilder = builder.getObject();
        return bookstoreBuilder.with(id, bookInfo).update();
    }

    /**
     * To update patch info by book id.
     * @param id
     * @param pathInfo
     */
    public void updateBook (Long id, BookPatch pathInfo) throws NotFoundException {
        BookstoreBuilder bookstoreBuilder = builder.getObject();
        bookstoreBuilder.with(id, pathInfo.getAuthor(), pathInfo.getPrice(), pathInfo.getClassification()).patch();
    }


}
