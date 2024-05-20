package tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Book;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.BorrowingRecord;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Patron;

import java.util.Optional;

public interface BorrowingRecordRepository extends BaseRepository<BorrowingRecord> {

    @Cacheable(value = "borrowingRecord")

    @Query(value = "select b.* from lms_borrowing_record_tbl b where b.deleted='T'", nativeQuery = true)
    Page<BorrowingRecord> findAllByDeletedEquals(Specification<BorrowingRecord> spec, Pageable pageable);

    Optional<BorrowingRecord> findByBookAndPatronAndReturnDateIsNull(Book book, Patron patron);
}