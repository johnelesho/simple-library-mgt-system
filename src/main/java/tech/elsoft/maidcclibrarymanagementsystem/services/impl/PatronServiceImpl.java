package tech.elsoft.maidcclibrarymanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.CreatePatronDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.PatronResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.PatronQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.exceptions.ApiBadRequestException;
import tech.elsoft.maidcclibrarymanagementsystem.exceptions.ApiNotFoundException;
import tech.elsoft.maidcclibrarymanagementsystem.mapper.PatronMapper;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Patron;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.PatronRepository;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.specifications.PatronSpecifications;
import tech.elsoft.maidcclibrarymanagementsystem.services.interfaces.PatronService;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@CacheConfig(cacheNames = "patron")
public class PatronServiceImpl implements PatronService {


    final PatronRepository patronRepository;
    final PatronMapper patronMapper;

    @Override
    public Page<PatronResponse> search(PatronQueryFilter queryFilter, Pageable pageable) {
        Specification<Patron> searchSpecification = PatronSpecifications.search(queryFilter);
        Page<PatronResponse> response = patronRepository.findAll(searchSpecification, pageable).map(patronMapper::toResponse);
        if(!response.hasContent())
            throw new ApiNotFoundException("No record found");
        return response;
    }


    private Patron fetchById(Long id) {
        return patronRepository.findById(id).orElseThrow(() -> new ApiNotFoundException("Patron not found with Id " + id));

    }
    @Override
    public PatronResponse getById(Long id) {
        Patron patron = fetchById(id);
        return patronMapper.toResponse(patron);
    }

    @Override
    public PatronResponse createNew(CreatePatronDTO request) {
       Patron patron = patronMapper.toEntity(request);
        Specification<Patron> specification = PatronSpecifications.findDuplicate(patron);
        boolean duplicateExists = patronRepository.exists(specification);
        if(duplicateExists){
            throw new ApiBadRequestException("Duplicate Patron exists");
        }
        patron = patronRepository.save(patron);
        return patronMapper.toResponse(patron);

    }

    @Override
    public PatronResponse update(Long id, CreatePatronDTO request) {
        Patron patron = fetchById(id);
        patronMapper.updateEntityFromDTO(request,patron);
        return patronMapper.toResponse(patron);

    }

    @Override
    public void deleteById(Long id) {
        patronRepository.deleteById(id);
    }

    @Override
    public Page<PatronResponse> getAllDeleted(PatronQueryFilter queryFilter, Pageable pageable) {
        Specification<Patron> spec = PatronSpecifications.search(queryFilter);
        Page<PatronResponse> deleted = patronRepository.findAllByDeletedEquals(spec, pageable).map(patronMapper::toResponse);
        return deleted;
    }
}

