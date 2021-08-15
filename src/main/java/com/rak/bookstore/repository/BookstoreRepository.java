package com.rak.bookstore.repository;

import com.rak.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

@Repository
public interface BookstoreRepository extends JpaRepository<Book, Long> {

    @Transactional
    default void updateBookDetails(EntityManager entityManager, Long id, String authorName, Double price, String classification) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Book> update = cb.createCriteriaUpdate(Book.class);

        Root e = update.from(Book.class);
        if (StringUtils.hasText(authorName)) {
            update.set("authorName", authorName);
        }
        if (StringUtils.hasText(classification)) {
            update.set("classification", classification);
        }
        if (price != null) {
            update.set("price", price);
        }
        update.where(cb.equal(e.get("id"), id));
        entityManager.createQuery(update).executeUpdate();
    }
}
