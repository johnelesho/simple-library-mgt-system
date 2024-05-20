package tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.Year;
import java.util.Objects;
import java.util.Set;

@Builder
public record CreateBookDTO(
        @NotBlank
        String title,

@NotNull
        @NotEmpty
        Set<String> categories,
        @NotBlank

         String author,

                @NotBlank

 String ISBN,

@NotBlank

 String publisher,

        boolean available,
 Boolean favourite,
        @PastOrPresent
        Year publicationYear
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateBookDTO that)) return false;
        return StringUtils.equalsIgnoreCase(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
