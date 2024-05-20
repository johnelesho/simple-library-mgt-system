package tech.elsoft.maidcclibrarymanagementsystem.services.interfaces;

import org.springframework.data.domain.Pageable;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.ApiResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.CreatePatronDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.PatronResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.BookQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.PatronQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Patron;

public interface PatronService extends  BaseService<CreatePatronDTO, PatronQueryFilter, PatronResponse> {

}
