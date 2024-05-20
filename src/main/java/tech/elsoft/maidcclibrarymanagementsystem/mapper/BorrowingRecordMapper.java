package tech.elsoft.maidcclibrarymanagementsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.CreateBorrowingRecordDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.CreatePatronDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.BorrowingRecordResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.PatronResponse;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.BorrowingRecord;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Patron;


@Mapper(componentModel = "spring")
public interface BorrowingRecordMapper extends EntityMapper<CreateBorrowingRecordDTO, BorrowingRecordResponse, BorrowingRecord> {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "patron", ignore = true)
    @Override
    BorrowingRecord toEntity(CreateBorrowingRecordDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Override
    void updateEntityFromDTO(CreateBorrowingRecordDTO dto, @MappingTarget BorrowingRecord borrowingRecord);
}