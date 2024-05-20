package tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Librarian;

import java.util.Optional;

public interface LibrarianRepository extends BaseRepository<Librarian> {

    @Query("select L from Librarian L where lower(L.email) = lower(:email) or lower(L.username) = lower(:email)")
    Optional<Librarian> findByEmailOrUsername(String email);
    @Query(value = "select b.* from lms_librarian_tbl b where b.deleted='T'", nativeQuery = true)
    Page<Librarian> findAllByDeletedEquals(Specification<Librarian> spec, Pageable pageable);

}