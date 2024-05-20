package tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters;

import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookQueryFilter extends QueryFilters{
    private String title;
   
    private String category;

    private String author;


    private String isbn;


    private String publisher;

    private Boolean favorite = Boolean.FALSE;
    @PastOrPresent
    private LocalDate datePublishedFrom;
    @PastOrPresent
    private LocalDate datePublishedTo;
}
