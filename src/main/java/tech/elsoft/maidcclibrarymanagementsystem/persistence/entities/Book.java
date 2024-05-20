package tech.elsoft.maidcclibrarymanagementsystem.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
@Entity
@Table(name = "lms_books_tbl")
@AllArgsConstructor
@NoArgsConstructor
public class Book extends AbstractAuditingEntity {


    private String title;
    private String author;
    private Year publicationYear;
    private String ISBN;
    private String publisher;
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean available = Boolean.TRUE;

    @ElementCollection
    @CollectionTable(name = "BookCategories", joinColumns = @JoinColumn(name = "bookId"))
    @SoftDelete
    private Collection<String> categories = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Book book = (Book) o;

        if (!getId().equals(book.getId())) return false;
        if (!getTitle().equals(book.getTitle())) return false;
        if (!getAuthor().equals(book.getAuthor())) return false;
        if (!getPublicationYear().equals(book.getPublicationYear())) return false;
        if (!getISBN().equals(book.getISBN())) return false;
        return getPublisher().equals(book.getPublisher());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getAuthor().hashCode();
        result = 31 * result + getPublicationYear().hashCode();
        result = 31 * result + getISBN().hashCode();
        result = 31 * result + getPublisher().hashCode();
        return result;
    }
}

