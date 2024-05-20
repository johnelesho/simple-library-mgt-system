package tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookResponse implements Serializable  {
        private Long id;
        private String title;
        private String author;
        private Collection<String> categories;
        private String publisher;

        private LocalDate createdDate;
        private String createdBy;

        private String lastModifiedBy;
        private LocalDate lastModifiedDate;

        private Year publicationYear;
        private String ISBN;
        private boolean available;


}
