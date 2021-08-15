package com.rak.bookstore.mapper;

import com.rak.bookstore.entity.Book;
import com.rak.bookstore.payload.BookInfo;

import java.util.List;

public interface BookstoreMapper {

    Book mapToModel (BookInfo dto);
    BookInfo mapToPayload (Book model);
    List<BookInfo> mapAll (List<Book> books);
}
