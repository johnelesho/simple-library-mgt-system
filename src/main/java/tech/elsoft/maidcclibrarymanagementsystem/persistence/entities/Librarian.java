package tech.elsoft.maidcclibrarymanagementsystem.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.usertype.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.enums.LibraryUserType;

import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "lms_librarians_tbl")
public class Librarian extends AbstractAuditingEntity implements UserDetails {


        @Column(nullable = false)
        private String fullName;

        @Column(unique = true, length = 100, nullable = false)
        private String email;

        @Column(unique = true, length = 100, nullable = false)
        private String username;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false, columnDefinition = "VARCHAR(16)")
        @Enumerated(EnumType.STRING)
        private LibraryUserType libraryUserType = LibraryUserType.STAFF;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}