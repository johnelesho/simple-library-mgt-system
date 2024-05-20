package tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Book;

import java.util.Optional;

public interface BookRepository extends BaseRepository<Book> {


    @Query(value = "select b.* from lms_books_tbl b where b.deleted='T'", nativeQuery = true)
    Page<Book> findAllByDeletedEquals(Specification spec, Pageable pageable);
    @Cacheable(value = "book")

    Optional<Book> findFirstByTitle(String dune);
}