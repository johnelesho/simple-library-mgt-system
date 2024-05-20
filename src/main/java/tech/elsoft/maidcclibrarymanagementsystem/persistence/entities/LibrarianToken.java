package tech.elsoft.maidcclibrarymanagementsystem.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SoftDelete;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.enums.TokenType;

@Getter
@Setter
@Entity
@Table(name = "librarian_token")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibrarianToken extends AbstractAuditingEntity {



    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column( columnDefinition = "VARCHAR(16)")

    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "librarian_id")
    @NotFound( action = NotFoundAction.IGNORE )
    public Librarian librarian;

}