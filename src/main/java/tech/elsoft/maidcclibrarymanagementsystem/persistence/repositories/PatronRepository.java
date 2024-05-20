package tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Patron;

public interface PatronRepository extends BaseRepository<Patron> {
    @Query(value = "select b.* from lms_patron_tbl b where b.deleted='T'", nativeQuery = true)
    Page<Patron> findAllByDeletedEquals(Specification<Patron> spec, Pageable pageable);

}