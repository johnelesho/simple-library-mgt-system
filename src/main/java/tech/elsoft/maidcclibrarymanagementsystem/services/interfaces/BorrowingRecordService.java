package tech.elsoft.maidcclibrarymanagementsystem.services.interfaces;

import tech.elsoft.maidcclibrarymanagementsystem.DTOs.requests.CreateBorrowingRecordDTO;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.responses.BorrowingRecordResponse;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.BorrowingRecordQueryFilter;

public interface BorrowingRecordService extends BaseService<CreateBorrowingRecordDTO, BorrowingRecordQueryFilter, BorrowingRecordResponse> {

    BorrowingRecordResponse borrowBook(Long bookId, Long patronId);

    public BorrowingRecordResponse returnBook(Long bookId, Long patronId);
}
