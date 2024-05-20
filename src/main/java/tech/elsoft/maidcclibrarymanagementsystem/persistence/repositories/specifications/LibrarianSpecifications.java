package tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.BorrowingRecordQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.LibrarianQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.BorrowingRecord;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Librarian;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Patron;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LibrarianSpecifications {
    public static Specification<Librarian> search(LibrarianQueryFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filter.getSearch())) {
                String search = "%" + StringUtils.lowerCase(filter.getSearch()) + "%";
                Predicate searchUsername = cb.like(cb.lower(root.get("username")), search);
                Predicate searchEmail = cb.like(cb.lower(root.get("email")), search);
                Predicate searchFullName = cb.like(cb.lower(root.get("fullName")), search);
                predicates.add(cb.or(searchUsername, searchEmail, searchFullName));
            }


            if (StringUtils.isNotBlank(filter.getUsername())) {
                String search = "%" + StringUtils.lowerCase(filter.getUsername()) + "%";
                Predicate author = cb.like(cb.lower(root.get("username")), search);
                predicates.add(author);
            }
            if (StringUtils.isNotBlank(filter.getEmail())) {
                String search = "%" + StringUtils.lowerCase(filter.getEmail()) + "%";
                Predicate title = cb.like(cb.lower(root.get("email")), search);
                predicates.add(title);
            }
            if (StringUtils.isNotBlank(filter.getFullName())) {
                String search = "%" + StringUtils.lowerCase(filter.getFullName()) + "%";
                Predicate publisher = cb.like(cb.lower(root.get("fullName")), search);
                cb.and(publisher);
            }
            if (filter.getLibraryUserType() != null) {
                String search = "%" + StringUtils.lowerCase(String.valueOf(filter.getLibraryUserType())) + "%";
                Predicate publisher = cb.like(cb.lower(root.get("libraryUserType")), search);
                cb.and(publisher);
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



            return cb.and(predicates.toArray(new Predicate[0]));

        };
    }

    public static Specification<Librarian> findDuplicates(Librarian librarian) {
        return (Root<Librarian> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if(!Strings.isEmpty(librarian.getEmail())) {
                Predicate predicateTitle = criteriaBuilder.equal(criteriaBuilder.lower(root.get("email")), librarian.getEmail().toLowerCase());
                criteriaBuilder.and(predicateTitle);
            }
            if(!Strings.isEmpty(librarian.getUsername())) {
                Predicate predicateAuthor = criteriaBuilder.equal(criteriaBuilder.lower(root.get("username")), librarian.getUsername().toLowerCase());
                criteriaBuilder.and(predicateAuthor);
            }
            if(!Strings.isEmpty(librarian.getFullName())) {
                Predicate predicateISBN = criteriaBuilder.equal(criteriaBuilder.lower(root.get("fullName")), librarian.getFullName().toLowerCase());
                criteriaBuilder.and(predicateISBN);
            }
            return criteriaBuilder.and();
        };
    }
}
