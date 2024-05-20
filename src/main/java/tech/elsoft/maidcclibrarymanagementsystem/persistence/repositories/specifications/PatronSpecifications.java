package tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.specifications;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.PatronQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Book;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Patron;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PatronSpecifications {
    public static Specification<Patron> search(PatronQueryFilter filter) {
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
                Predicate username = cb.like(cb.lower(root.get("username")), search);
                predicates.add(username);
            }
            if (StringUtils.isNotBlank(filter.getEmail())) {
                String search = "%" + StringUtils.lowerCase(filter.getEmail()) + "%";
                Predicate email = cb.like(cb.lower(root.get("email")), search);
                predicates.add(email);
            }
            if (StringUtils.isNotBlank(filter.getFullName())) {
                String search = "%" + StringUtils.lowerCase(filter.getFullName()) + "%";
                Predicate fullName = cb.like(cb.lower(root.get("fullName")), search);
                cb.and(fullName);
            }
            if (filter.getCity() != null) {
                String search = "%" + StringUtils.lowerCase(filter.getCity()) + "%";
                Predicate city = cb.like(cb.lower(root.get("city")), search);
                cb.and(city);
            }
            if (filter.getCountry() != null) {
                String search = "%" + StringUtils.lowerCase(filter.getCity()) + "%";
                Predicate country = cb.like(cb.lower(root.get("country")), search);
                cb.and(country);
            } if (filter.getPostalCode() != null) {
                String search = "%" + StringUtils.lowerCase(filter.getCity()) + "%";
                Predicate postalCode = cb.like(cb.lower(root.get("postalCode")), search);
                cb.and(postalCode);
            }
            if (filter.getStateOrProvince() != null) {
                String search = "%" + StringUtils.lowerCase(filter.getCity()) + "%";
                Predicate stateOrProvince = cb.like(cb.lower(root.get("stateOrProvince")), search);
                cb.and(stateOrProvince);
            }
            if (filter.getStreetAddress() != null) {
                String search = "%" + StringUtils.lowerCase(filter.getCity()) + "%";
                Predicate streetAddress = cb.like(cb.lower(root.get("streetAddress")), search);
                cb.and(streetAddress);
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

    public static Specification<Patron> findDuplicate(@Nonnull Patron patron) {
        return (Root<Patron> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if(!Strings.isEmpty(patron.getEmail())) {
                Predicate predicateTitle = criteriaBuilder.equal(criteriaBuilder.lower(root.get("email")), patron.getEmail().toLowerCase());
                criteriaBuilder.and(predicateTitle);
            }
            if(!Strings.isEmpty(patron.getUsername())) {
                Predicate predicateAuthor = criteriaBuilder.equal(criteriaBuilder.lower(root.get("username")), patron.getUsername().toLowerCase());
                criteriaBuilder.and(predicateAuthor);
            }
            if(!Strings.isEmpty(patron.getFullName())) {
                Predicate predicateISBN = criteriaBuilder.equal(criteriaBuilder.lower(root.get("fullName")), patron.getFullName().toLowerCase());
                criteriaBuilder.and(predicateISBN);
            }
            return criteriaBuilder.and();
        };
    }
}
