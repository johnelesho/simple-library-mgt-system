package tech.elsoft.maidcclibrarymanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.RegisterLibrarianDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.LibrarianResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.LibrarianQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.exceptions.ApiBadRequestException;
import tech.elsoft.maidcclibrarymanagementsystem.exceptions.ApiNotFoundException;
import tech.elsoft.maidcclibrarymanagementsystem.mapper.LibrarianMapper;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Librarian;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.LibrarianRepository;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.specifications.LibrarianSpecifications;
import tech.elsoft.maidcclibrarymanagementsystem.services.interfaces.LibrarianService;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@CacheConfig(cacheNames = "librarian")
public class LibrarianServiceImpl implements LibrarianService {

    final LibrarianRepository librarianRepository;
    final LibrarianMapper librarianMapper;

    final PasswordEncoder passwordEncoder;

    @Override
    public Page<LibrarianResponse> search(LibrarianQueryFilter queryFilter, Pageable pageable) {
        Specification<Librarian> searchSpecification = LibrarianSpecifications.search(queryFilter);
        Page<LibrarianResponse> response = librarianRepository.findAll(searchSpecification, pageable).map(librarianMapper::toResponse);
        if(!response.hasContent())
            throw new ApiNotFoundException("No record found");
        return response;
    }

    @Override
    public LibrarianResponse getById(Long id) {
        Librarian librarian = fetchById(id);
        return librarianMapper.toResponse(librarian);
    }

    @Override
    public LibrarianResponse createNew(RegisterLibrarianDTO request) {
        Librarian librarian = librarianMapper.toEntity(request);
        Specification<Librarian> specification = LibrarianSpecifications.findDuplicates(librarian);
        boolean duplicateExists = librarianRepository.exists(specification);
        if(duplicateExists){
            throw new ApiBadRequestException("Librarian exist with similar details");
        }
        librarian.setPassword(passwordEncoder.encode(request.password()));
        librarian = librarianRepository.save(librarian);
        LibrarianResponse response = librarianMapper.toResponse(librarian);
        return response;
    }


    private Librarian fetchById(Long id) {
        return librarianRepository.findById(id).orElseThrow(() -> new ApiNotFoundException("Library not found with Id " + id));

    }



    @Override
    public LibrarianResponse update(Long id, RegisterLibrarianDTO request) {
        Librarian librarian = fetchById(id);
        librarianMapper.updateEntityFromDTO(request, librarian);
        LibrarianResponse response = librarianMapper.toResponse(librarian);
        return response;
    }

    @Override
    public void deleteById(Long id) {
        librarianRepository.deleteById(id);

    }

    @Override
    public Page<LibrarianResponse> getAllDeleted(LibrarianQueryFilter queryFilter, Pageable pageable) {
        Specification<Librarian> spec = LibrarianSpecifications.search(queryFilter);
        Page<LibrarianResponse> deletedLibrarian = librarianRepository.findAllByDeletedEquals(spec, pageable).map(librarianMapper::toResponse);
        return deletedLibrarian;
    }
}
