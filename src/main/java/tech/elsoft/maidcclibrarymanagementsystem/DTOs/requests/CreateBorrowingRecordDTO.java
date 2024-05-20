package tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBorrowingRecordDTO {
    private Long bookId;
    private Long patronId;
    @FutureOrPresent
    private LocalDate borrowingDate = LocalDate.now();
    @FutureOrPresent
    private LocalDate returnDate;

}
