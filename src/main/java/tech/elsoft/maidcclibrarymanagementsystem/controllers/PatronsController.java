package tech.elsoft.maidcclibrarymanagementsystem.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.ApiResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.CreatePatronDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.PatronResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.PatronQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.services.interfaces.PatronService;

@RequestMapping("api/patrons")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "lms")
@Tag(name = "Patron Management Module")
public class PatronsController implements BaseController<CreatePatronDTO, PatronQueryFilter> {


     final PatronService patronService;


     @Override
     public ResponseEntity<ApiResponse> getAll(PatronQueryFilter queryFilter, Pageable pageable) {
          Page<PatronResponse> patronResponses = patronService.search(queryFilter, pageable);
          ApiResponse response = new ApiResponse(patronResponses);
          return ResponseEntity.ok(response);
     }

     @Override
     public ResponseEntity<ApiResponse> getById(Long id) {
          PatronResponse patronResponse = patronService.getById(id);
          ApiResponse response = new ApiResponse(patronResponse);
          return ResponseEntity.ok(response);
     }

     @Override
     public ResponseEntity<ApiResponse> create(CreatePatronDTO request) {
          PatronResponse patronResponse = patronService.createNew(request);
          ApiResponse response = new ApiResponse(patronResponse);
          return ResponseEntity.status(HttpStatus.CREATED).body(response);
     }

     @Override
     public ResponseEntity<ApiResponse> update(Long id, CreatePatronDTO request) {
          PatronResponse patronResponse = patronService.update(id, request);
          ApiResponse response = new ApiResponse(patronResponse);
          return ResponseEntity.ok(response);
     }

     @Override
     public ResponseEntity<ApiResponse> delete(Long id) {
          patronService.deleteById(id);
          return ResponseEntity.ok(new ApiResponse());
     }

     @Override
     public ResponseEntity<ApiResponse> getAllDeleted(PatronQueryFilter queryFilter, Pageable pageable) {
          Page<PatronResponse> patronResponses = patronService.getAllDeleted(queryFilter, pageable);
          ApiResponse response = new ApiResponse(patronResponses);
          return ResponseEntity.ok(response);
     }
}
