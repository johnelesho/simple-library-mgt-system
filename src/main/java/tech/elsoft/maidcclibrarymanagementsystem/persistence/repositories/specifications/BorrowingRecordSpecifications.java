package tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.specifications;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.BorrowingRecordQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.BorrowingRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BorrowingRecordSpecifications {
    public static Specification<BorrowingRecord> search(BorrowingRecordQueryFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (StringUtils.isNotBlank(filter.getSearch())) {
                String search = "%" + StringUtils.lowerCase(filter.getSearch()) + "%";
                Predicate searchCreated = cb.like(cb.lower(root.get("createdBy")), search);
                predicates.add(searchCreated);
            }

            if (StringUtils.isNotBlank(filter.getCreatedBy())) {
                String search = "%" + StringUtils.lowerCase(filter.getCreatedBy()) + "%";
                Predicate createdBy = cb.like(cb.lower(root.get("createdBy")), search);
                predicates.add(createdBy);
            }
            if (filter.getCreatedDateFrom() != null && filter.getCreatedDateTo() != null) {
                predicates.add(cb.between(root.get("createdDate"), filter.getCreatedDateFrom(), filter.getCreatedDateTo()));
            }
            else if (filter.getCreatedDateFrom() != null) {
                predicates.add(cb.between(root.get("createdDate"), filter.getCreatedDateFrom(), LocalDateTime.now()));
            }
            if (filter.getBorrowingDateFrom() != null && filter.getBorrowingDateTo() != null) {
                predicates.add(cb.between(root.get("borrowingDate"), filter.getBorrowingDateFrom(), filter.getBorrowingDateTo()));
            }
            else if (filter.getBorrowingDateFrom() != null) {
                predicates.add(cb.between(root.get("borrowingDate"), filter.getBorrowingDateFrom(), LocalDateTime.now()));
            }
            if (filter.getReturnDateFrom() != null && filter.getReturnDateTo() != null) {
                predicates.add(cb.between(root.get("returnDate"), filter.getReturnDateFrom(), filter.getReturnDateTo()));
            }
            else if (filter.getReturnDateFrom() != null) {
                predicates.add(cb.between(root.get("returnDate"), filter.getReturnDateFrom(), LocalDateTime.now()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));

        };
    }

    public static Specification<BorrowingRecord> findDuplicates(@Nonnull BorrowingRecord borrowingRecord) {
        return (Root<BorrowingRecord> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

                Predicate predicateBook = criteriaBuilder.equal(root.get("book"), borrowingRecord.getBook());

                Predicate predicateBorrowingDate = criteriaBuilder.equal(root.get("borrowingDate"), borrowingRecord.getBorrowingDate());

                Predicate predicateReturnDate = criteriaBuilder.equal(root.get("returnDate"), borrowingRecord.getReturnDate());
                Predicate predicatePatron = criteriaBuilder.equal(root.get("patron"), borrowingRecord.getPatron());


            return criteriaBuilder.and(predicateBook,predicateBorrowingDate,predicateReturnDate, predicatePatron);
        };
    }
}
