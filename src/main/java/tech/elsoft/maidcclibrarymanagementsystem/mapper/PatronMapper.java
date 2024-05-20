package tech.elsoft.maidcclibrarymanagementsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.CreatePatronDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.PatronResponse;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Patron;


@Mapper(componentModel = "spring")
public interface PatronMapper extends EntityMapper<CreatePatronDTO, PatronResponse, Patron> {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdBy", ignore = true)
//    @Mapping(target = "createdDate", ignore = true)
//    @Mapping(target = "lastModifiedBy", ignore = true)
//    @Mapping(target = "lastModifiedDate", ignore = true)
//    @Override
//    PatronResponse toResponse(Patron entity);
@Mapping(target = "id", ignore = true)
@Mapping(target = "createdBy", ignore = true)
@Mapping(target = "createdDate", ignore = true)
@Mapping(target = "lastModifiedBy", ignore = true)
@Mapping(target = "lastModifiedDate", ignore = true)
    @Override
    Patron toEntity(CreatePatronDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Override
    void updateEntityFromDTO(CreatePatronDTO dto, @MappingTarget Patron patron);
}