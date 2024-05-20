package tech.elsoft.maidcclibrarymanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.ApiResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.CreateBorrowingRecordDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.BorrowingRecordResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.BorrowingRecordQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.services.interfaces.BorrowingRecordService;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "lms")
@Tag(name = "Borrowing Management Module")
public class BorrowingRecordController implements BaseController<CreateBorrowingRecordDTO, BorrowingRecordQueryFilter> {

    final BorrowingRecordService borrowingRecordService;

    @Override
    @GetMapping("borrow")
    public ResponseEntity<ApiResponse> getAll(BorrowingRecordQueryFilter queryFilter, Pageable pageable) {
        Page<BorrowingRecordResponse> recordResponses = borrowingRecordService.search(queryFilter, pageable);
        ApiResponse response = new ApiResponse(recordResponses);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("borrow/{id}")
    public ResponseEntity<ApiResponse> getById(Long id) {
        BorrowingRecordResponse recordResponse = borrowingRecordService.getById(id);
        ApiResponse response = new ApiResponse(recordResponse);
        return ResponseEntity.ok(response);
    }

    @Override
    @Hidden
    public ResponseEntity<ApiResponse> create(CreateBorrowingRecordDTO request) {
        return ResponseEntity.notFound().build();
    }
    @PostMapping("borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse> create(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecordResponse recordResponse = borrowingRecordService.borrowBook(bookId, patronId);
        ApiResponse response = new ApiResponse(recordResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @Hidden
    public ResponseEntity<ApiResponse> update(Long id, CreateBorrowingRecordDTO request) {
        return ResponseEntity.notFound().build();
    }
    @PutMapping("return/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecordResponse recordResponse = borrowingRecordService.returnBook(bookId, patronId);
        ApiResponse response =  new ApiResponse(recordResponse);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("borrow/{id}")
    public ResponseEntity<ApiResponse> delete(Long id) {
         borrowingRecordService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse());
    }

    @Override
    @DeleteMapping("borrow")
    public ResponseEntity<ApiResponse> getAllDeleted(BorrowingRecordQueryFilter queryFilter, Pageable pageable) {
        Page<BorrowingRecordResponse> recordResponses = borrowingRecordService.getAllDeleted(queryFilter, pageable);
        ApiResponse response = new ApiResponse(recordResponses);
        return ResponseEntity.ok(response);
    }
}
