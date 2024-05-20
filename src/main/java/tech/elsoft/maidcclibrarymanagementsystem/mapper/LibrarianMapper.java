package tech.elsoft.maidcclibrarymanagementsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.RegisterLibrarianDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.LibrarianResponse;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Librarian;


@Mapper(componentModel = "spring")
public interface LibrarianMapper extends EntityMapper<RegisterLibrarianDTO, LibrarianResponse, Librarian> {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Override
    Librarian toEntity(RegisterLibrarianDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Override
    void updateEntityFromDTO(RegisterLibrarianDTO dto, @MappingTarget Librarian librarian);
}