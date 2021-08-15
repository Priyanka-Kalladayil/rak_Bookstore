package com.rak.bookstore.mapper;

import com.rak.bookstore.entity.Book;
import com.rak.bookstore.payload.BookInfo;
import com.rak.bookstore.payload.BookInfo.ClassificationEnum;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BookstoreMapperImpl implements BookstoreMapper {

    @Override
    public Book mapToModel(BookInfo dto) {
        if ( dto == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( dto.getId() );
        book.setName( dto.getName() );
        book.setCategory( dto.getCategory() );
        book.setDescription( dto.getDescription() );
        book.setAuthorName( dto.getAuthorName() );
        book.setAuthorEmailAddress( dto.getAuthorEmailAddress() );
        book.setPrice( dto.getPrice() );
        book.setIsbn( dto.getIsbn() );
        if ( dto.getClassification() != null ) {
            book.setClassification( dto.getClassification().name() );
        }

        return book;
    }

    @Override
    public BookInfo mapToPayload(Book model) {
        if ( model == null ) {
            return null;
        }

        BookInfo bookInfo = new BookInfo();

        bookInfo.setId( model.getId() );
        bookInfo.setName( model.getName() );
        bookInfo.setCategory( model.getCategory() );
        bookInfo.setDescription( model.getDescription() );
        bookInfo.setAuthorName( model.getAuthorName() );
        bookInfo.setAuthorEmailAddress( model.getAuthorEmailAddress() );
        bookInfo.setPrice( model.getPrice() );
        bookInfo.setIsbn( model.getIsbn() );
        if ( model.getClassification() != null ) {
            bookInfo.setClassification( Enum.valueOf( ClassificationEnum.class, model.getClassification() ) );
        }

        return bookInfo;
    }

    @Override
    public List<BookInfo> mapAll(List<Book> books) {
        if ( books == null ) {
            return null;
        }

        List<BookInfo> list = new ArrayList<BookInfo>( books.size() );
        for ( Book book : books ) {
            list.add( mapToPayload( book ) );
        }

        return list;
    }
}
