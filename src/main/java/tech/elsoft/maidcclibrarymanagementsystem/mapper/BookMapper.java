package tech.elsoft.maidcclibrarymanagementsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.CreateBookDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.BookResponse;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Book;


@Mapper(componentModel = "spring")
//@Mapper(uses = { CarMapper.class, BusMapper.class })

public interface BookMapper extends EntityMapper<CreateBookDTO, BookResponse, Book> {
    @Override

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Book toEntity(CreateBookDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Override
    void updateEntityFromDTO(CreateBookDTO dto, @MappingTarget Book book);
}