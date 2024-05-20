package tech.elsoft.maidcclibrarymanagementsystem.services.interfaces;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<C, F, R> {
    Page<R> search(F queryFilter, Pageable pageable);

    @Cacheable( key = "#id")
    R getById(Long id);

    R createNew(C request);

    @CachePut(key = "#id")
    R update(Long id, C request);

    @CacheEvict(key = "#id", beforeInvocation = true)
    void deleteById(Long id);

    Page<R> getAllDeleted(F queryFilter, Pageable pageable);
}
