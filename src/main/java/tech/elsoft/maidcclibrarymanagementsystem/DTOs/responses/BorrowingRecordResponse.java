package tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Book;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Patron;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowingRecordResponse implements Serializable {
    private Long id;
    private PatronResponse patron;
    private BookResponse book;
    private LocalDate borrowingDate;
    private LocalDate returnDate;

}
