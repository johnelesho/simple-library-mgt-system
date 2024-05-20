package tech.elsoft.maidcclibrarymanagementsystem.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.ApiResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.BookQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.CreateBookDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.BookResponse;
import tech.elsoft.maidcclibrarymanagementsystem.exceptions.ApiBadRequestException;
import tech.elsoft.maidcclibrarymanagementsystem.exceptions.ApiNotFoundException;
import tech.elsoft.maidcclibrarymanagementsystem.mapper.BookMapper;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Book;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.BookRepository;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.specifications.BookSpecifications;
import tech.elsoft.maidcclibrarymanagementsystem.services.interfaces.BookService;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@CacheConfig(cacheNames = "book")
public class BookServiceImpl implements BookService{

    final BookRepository bookRepository;
    final BookMapper bookMapper;



    @Override
    public Page<BookResponse> search(BookQueryFilter queryFilter, Pageable pageable) {
        Specification<Book> searchSpecification = BookSpecifications.search(queryFilter);
        Page<BookResponse> response = bookRepository.findAll(searchSpecification, pageable).map(bookMapper::toResponse);
        if(!response.hasContent())
            throw new ApiNotFoundException("No record found");
        return response;
    }

    @Override
    @Cacheable( key = "#id")
    public BookResponse getById(Long id) {
        Book book = fetchById(id);
        return bookMapper.toResponse(book);
    }


    private Book fetchById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ApiNotFoundException("Book not found with Id " + id));

    }

    @Override
    public BookResponse createNew(CreateBookDTO request) {
//       you should not have same title and author
        Book book = bookMapper.toEntity(request);
        Specification<Book> specification = BookSpecifications.findDuplicates(book);
        boolean duplicateExists = bookRepository.exists(specification);
        if(duplicateExists){
            throw new ApiBadRequestException("Duplicate Book exists");
        }
        book = bookRepository.save(book);
        BookResponse response = bookMapper.toResponse(book);
        return response;
    }

    @Override
    @CachePut(key = "#id")
    public BookResponse update(Long id, CreateBookDTO request) {
        Book book = fetchById(id);
        bookMapper.updateEntityFromDTO(request,book);
        BookResponse response = bookMapper.toResponse(book);
        return response;
    }

    @Override
    @CacheEvict(key = "#id", beforeInvocation = true)

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookResponse> getAllDeleted(BookQueryFilter queryFilter, Pageable pageable) {
        Specification<Book> spec = BookSpecifications.search(queryFilter);
        Page<BookResponse> deletedBooks = bookRepository.findAllByDeletedEquals(spec, pageable).map(bookMapper::toResponse);
        return deletedBooks;
    }
}
