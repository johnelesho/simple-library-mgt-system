package tech.elsoft.maidcclibrarymanagementsystem.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.BookResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.BookQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.CreateBookDTO;
import tech.elsoft.maidcclibrarymanagementsystem.services.interfaces.BookService;

@RequestMapping("api/books")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "lms")
@Tag(name = "Book Management Module")
public class BooksController implements BaseController<CreateBookDTO, BookQueryFilter> {


     final BookService bookService;


     @Override
     public ResponseEntity<ApiResponse> getAll(BookQueryFilter queryFilter, Pageable pageable) {
          Page<BookResponse> bookResponses = bookService.search(queryFilter, pageable);
          ApiResponse response = new ApiResponse(bookResponses);
          return ResponseEntity.ok(response);
     }

     @Override
     public ResponseEntity<ApiResponse> getById(Long id) {
          BookResponse bookResponse = bookService.getById(id);
          ApiResponse response = new ApiResponse(bookResponse);
          return ResponseEntity.ok(response);
     }

     @Override
     public ResponseEntity<ApiResponse> create(@Valid CreateBookDTO request) {
          BookResponse bookResponse = bookService.createNew(request);
          ApiResponse response = new ApiResponse(bookResponse);
          return ResponseEntity.status(HttpStatus.CREATED).body(response);
     }

     @Override
     public ResponseEntity<ApiResponse> update(Long id, @Valid CreateBookDTO request) {
          BookResponse bookResponse = bookService.update(id, request);
          ApiResponse response = new ApiResponse(bookResponse);
          return ResponseEntity.ok(response);
     }

     @Override

     public ResponseEntity<ApiResponse> delete(Long id) {
          bookService.deleteById(id);
          return ResponseEntity.ok(new ApiResponse());
     }

     @Override
     public ResponseEntity<ApiResponse> getAllDeleted(BookQueryFilter queryFilter, Pageable pageable) {
          Page<BookResponse> bookResponses = bookService.getAllDeleted(queryFilter, pageable);
          ApiResponse response =new ApiResponse( bookResponses);
          return ResponseEntity.ok(response);
     }
}
