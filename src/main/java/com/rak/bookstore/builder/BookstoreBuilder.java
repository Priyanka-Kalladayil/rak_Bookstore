package com.rak.bookstore.builder;

import com.rak.bookstore.entity.Book;
import com.rak.bookstore.exception.NotFoundException;
import com.rak.bookstore.mapper.BookstoreMapper;
import com.rak.bookstore.payload.BookInfo;
import com.rak.bookstore.repository.BookstoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Component
@Scope ("prototype")
public class BookstoreBuilder {

    private static final Logger logger = LoggerFactory.getLogger(BookstoreBuilder.class);

    private BookstoreRepository repository;
    private BookstoreMapper mapper;
    private EntityManager entityManager;


    private Long id;
    private BookInfo bookInfo;
    private String authorName;
    private Double amount;
    private String classification;

    @Autowired
    public BookstoreBuilder(BookstoreRepository repository, BookstoreMapper mapper, EntityManager entityManager) {
        this.repository = repository;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    /**
     *
     * @param id
     * @return
     */
    public BookstoreBuilder with (Long id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @param bookInfo
     * @return
     */
    public BookstoreBuilder with (BookInfo bookInfo) {
        this.bookInfo = bookInfo;
        return this;
    }

    /**
     *
     * @param id
     * @param bookInfo
     * @return
     */
    public BookstoreBuilder with (Long id, BookInfo bookInfo) {
        this.bookInfo = bookInfo;
        this.id = id;
        return this;
    }

    /**
     *
     * @param id
     * @param authorName
     * @param amount
     * @param classification
     * @return
     */
    public BookstoreBuilder with (Long id, String authorName, Double amount, BookInfo.ClassificationEnum classification) {
        this.id = id;
        this.authorName = authorName;
        this.amount = amount;
        if (classification != null) {
            this.classification = classification.name();
        }
        return this;
    }

    /**
     *
     * @return
     */
    public BookInfo save () {
        logger.info("map payload data into entity");
        Book book = mapper.mapToModel(this.bookInfo);
        Book savedItem = repository.save(book);
        logger.info("Book details are saved successfully.");
        return mapper.mapToPayload(savedItem);
    }

    /**
     *
     * @return
     */
    public List<BookInfo> findAll  () {
        logger.info("executing query to find all stored data.");
        return mapper.mapAll(repository.findAll());
    }


    /**
     *
     * @return
     * @throws NotFoundException
     */
    public BookInfo find  () throws NotFoundException {
        logger.info("executing query to find  matching book details.");
        Optional<Book> book =  repository.findById(id) ;
        if (!book.isPresent())
            throw  new NotFoundException(HttpStatus.NOT_FOUND.value(), "No matching data found for id " + id);
        logger.info("data found.");
        return mapper.mapToPayload(book.get());
    }

    /**
     *
     */
    public void delete () {
        repository.deleteById(id);
    }

    /**
     *
     * @return
     */
    public BookInfo update () {
      this.bookInfo.setId(this.id);
      return save();
    }

    /**
     *
     */
   public void patch () throws NotFoundException {
       logger.info("Data received for patch update id {} authorName {} price {} classification {}", id, authorName,
               amount, classification);
       find();
       repository.updateBookDetails(entityManager, id, authorName, amount, classification);
   }


}
