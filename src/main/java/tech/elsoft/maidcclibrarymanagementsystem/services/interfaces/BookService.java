package tech.elsoft.maidcclibrarymanagementsystem.services.interfaces;

import org.springframework.data.domain.Pageable;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.ApiResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.BookResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.BookQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.CreateBookDTO;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Book;

public interface BookService extends BaseService<CreateBookDTO, BookQueryFilter, BookResponse>{


}
