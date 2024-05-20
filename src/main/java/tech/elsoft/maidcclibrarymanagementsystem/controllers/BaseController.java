package tech.elsoft.maidcclibrarymanagementsystem.controllers;

import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.ApiResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.QueryFilters;

public interface BaseController<B, T extends QueryFilters> {
    @GetMapping()
    ResponseEntity<ApiResponse> getAll(@ParameterObject T queryFilter, @ParameterObject Pageable pageable);

    @GetMapping("{id}")

    ResponseEntity<ApiResponse> getById(@PathVariable Long id);

    @PostMapping()
    ResponseEntity<ApiResponse> create(@Valid @RequestBody B request);

    @PutMapping("{id}")
    ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody B request);

    @DeleteMapping("{id}")
    ResponseEntity<ApiResponse> delete(@PathVariable Long id);

    @GetMapping("deleted")
//    @PreAuthorize()
    ResponseEntity<ApiResponse> getAllDeleted(@ParameterObject T queryFilter, @ParameterObject Pageable pageable);

}
