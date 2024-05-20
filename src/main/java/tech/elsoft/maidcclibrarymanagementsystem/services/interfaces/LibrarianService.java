package tech.elsoft.maidcclibrarymanagementsystem.services.interfaces;

import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.RegisterLibrarianDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.LibrarianResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.LibrarianQueryFilter;

public interface LibrarianService extends BaseService<RegisterLibrarianDTO, LibrarianQueryFilter, LibrarianResponse>{


}
