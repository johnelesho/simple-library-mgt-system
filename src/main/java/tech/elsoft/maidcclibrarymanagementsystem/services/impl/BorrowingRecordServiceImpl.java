package tech.elsoft.maidcclibrarymanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.CreateBorrowingRecordDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.BorrowingRecordResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.BorrowingRecordQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.exceptions.ApiBadRequestException;
import tech.elsoft.maidcclibrarymanagementsystem.exceptions.ApiNotFoundException;
import tech.elsoft.maidcclibrarymanagementsystem.mapper.BorrowingRecordMapper;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Book;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.BorrowingRecord;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Patron;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.BookRepository;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.BorrowingRecordRepository;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.PatronRepository;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.specifications.BorrowingRecordSpecifications;
import tech.elsoft.maidcclibrarymanagementsystem.services.interfaces.BorrowingRecordService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "borrowingRecord")
@Transactional
public class BorrowingRecordServiceImpl  implements BorrowingRecordService {


    final BorrowingRecordRepository borrowingRecordRepository;
    final PatronRepository patronRepository;
    final BookRepository bookRepository;
    final BorrowingRecordMapper borrowingRecordMapper;

    @Override
    public Page<BorrowingRecordResponse> search(BorrowingRecordQueryFilter queryFilter, Pageable pageable) {
        Specification<BorrowingRecord> searchSpecification = BorrowingRecordSpecifications.search(queryFilter);
        Page<BorrowingRecordResponse> response = borrowingRecordRepository.findAll(searchSpecification, pageable).map(borrowingRecordMapper::toResponse);
        if(!response.hasContent())
            throw new ApiNotFoundException("No record found");
        return response;
    }

    @Override
    @Cacheable(key = "#id")
    public BorrowingRecordResponse getById(Long id) {
       BorrowingRecord response = fetchById(id);
        return borrowingRecordMapper.toResponse(response);
    }

    private BorrowingRecord fetchById(Long id) {
        return borrowingRecordRepository.findById(id).orElseThrow(() -> new ApiNotFoundException("Book not found with Id " + id));

    }
    @Override
    public BorrowingRecordResponse createNew(CreateBorrowingRecordDTO request) {
        BorrowingRecord borrowingRecord= borrowingRecordMapper.toEntity(request);
        Specification<BorrowingRecord> specification = BorrowingRecordSpecifications.findDuplicates(borrowingRecord);
        boolean duplicateExists = borrowingRecordRepository.exists(specification);
        if(duplicateExists){
            throw new ApiBadRequestException("Duplicate Borrowing Record exists");
        }
        borrowingRecord = borrowingRecordRepository.save(borrowingRecord);
        BorrowingRecordResponse response = borrowingRecordMapper.toResponse(borrowingRecord);
        return response;
    }

    @Override
    public BorrowingRecordResponse update(Long id, CreateBorrowingRecordDTO request) {
        BorrowingRecord borrowingRecord = fetchById(id);
        borrowingRecordMapper.updateEntityFromDTO(request,borrowingRecord);
        BorrowingRecordResponse response = borrowingRecordMapper.toResponse(borrowingRecord);
        return response;
    }

    @Override
    public void deleteById(Long id) {
        borrowingRecordRepository.deleteById(id);
    }

    @Override
    public Page<BorrowingRecordResponse> getAllDeleted(BorrowingRecordQueryFilter queryFilter, Pageable pageable) {
        Specification<BorrowingRecord> spec = BorrowingRecordSpecifications.search(queryFilter);
        Page<BorrowingRecordResponse> deleted = borrowingRecordRepository.findAllByDeletedEquals(spec, pageable).map(borrowingRecordMapper::toResponse);
        return deleted;
    }

    @Override
    public BorrowingRecordResponse borrowBook(Long bookId, Long patronId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ApiNotFoundException("Book not found with ID: " + bookId));

        if (!book.isAvailable()) {
            throw new ApiBadRequestException("Book with ID " + bookId + " is not available for borrowing");
        }
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ApiNotFoundException("Patron not found with ID: " + patronId));
    // Other check on the patron, active subscription, have many unborrowed books, etc
        if(!patron.isActiveSubscription()){
            throw new ApiBadRequestException("Patron with ID " + patronId + " does not have an active subscription");

        }

        // Create a borrowing record
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now());


        // Save the borrowing record
         borrowingRecord = borrowingRecordRepository.save(borrowingRecord);

        // Update book availability status
        book.setAvailable(false);
        bookRepository.save(book);
        return borrowingRecordMapper.toResponse(borrowingRecord);
    }

    @Override
    public BorrowingRecordResponse returnBook(Long bookId, Long patronId) {
        // Retrieve the book by its ID
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ApiNotFoundException("Book not found with ID: " + bookId));

        // Retrieve the patron by their ID
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ApiNotFoundException("Patron not found with ID: " + patronId));

        // Find the borrowing record for the given book and patron
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron)
                .orElseThrow(() -> new ApiNotFoundException("Borrowing record not found for book ID: " + bookId + " and patron ID: " + patronId));

        // Update the return date of the borrowing record
        borrowingRecord.setReturnDate(LocalDate.now());

        // Save the updated borrowing record
      borrowingRecord=  borrowingRecordRepository.save(borrowingRecord);

        // Update the availability status of the book
        book.setAvailable(true);

        bookRepository.save(book);
        return borrowingRecordMapper.toResponse(borrowingRecord);
    }
}

