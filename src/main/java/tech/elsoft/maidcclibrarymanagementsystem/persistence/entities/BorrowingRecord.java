package tech.elsoft.maidcclibrarymanagementsystem.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "lms_borrowing_record_tbl")
public class BorrowingRecord extends AbstractAuditingEntity {


    @ManyToOne
    private Book book;

    @ManyToOne
    private Patron patron;

    private LocalDate borrowingDate;
    private LocalDate returnDate;
}