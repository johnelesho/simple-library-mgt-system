package tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories;

import org.springframework.data.jpa.repository.Query;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.LibrarianToken;

import java.util.List;
import java.util.Optional;

public interface LibrarianTokenRepository extends BaseRepository<LibrarianToken> {
    Optional<LibrarianToken> findByToken(String jwt);

    @Query(value = """
            select t from LibrarianToken t inner join Librarian u\s
            on t.librarian.id = u.id\s
            where u.id = :id and (t.expired = false or t.revoked = false)\s
            """)
    List<LibrarianToken> findAllValidTokenByUser(Long id);

}