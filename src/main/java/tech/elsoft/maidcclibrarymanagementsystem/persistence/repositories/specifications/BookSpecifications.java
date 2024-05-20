package tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import tech.elsoft.maidcclibrarymanagementsystem.DTOs.searchfilters.BookQueryFilter;
import tech.elsoft.maidcclibrarymanagementsystem.persistence.entities.Book;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookSpecifications{

    public static Specification<Book> findDuplicates(Book book) {
        return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicateTitle = criteriaBuilder.equal(criteriaBuilder.lower(root.get("title")), book.getTitle() == null ? null : book.getTitle().toLowerCase());
            Predicate predicateAuthor = criteriaBuilder.equal(criteriaBuilder.lower(root.get("author")), book.getAuthor() == null ? null : book.getAuthor().toLowerCase());
            Predicate predicateISBN = criteriaBuilder.equal(criteriaBuilder.lower(root.get("ISBN")), book.getISBN() == null ? null : book.getISBN().toLowerCase());
            return criteriaBuilder.and(predicateTitle, predicateAuthor, predicateISBN);
        };
    }
    public static Specification<Book> search(BookQueryFilter filter) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filter.getSearch())) {
                String search = "%" + StringUtils.lowerCase(filter.getSearch()) + "%";
                Predicate searchTitle = cb.like(cb.lower(root.get("title")), search);
                Predicate searchAuthor = cb.like(cb.lower(root.get("author")), search);
                Predicate searchPublisher = cb.like(cb.lower(root.get("publisher")), search);
                Predicate searchIsbn = cb.like(cb.lower(root.get("isbn")), search);
                predicates.add(cb.or(searchTitle, searchAuthor, searchPublisher, searchIsbn));
            }

            if (StringUtils.isNotBlank(filter.getIsbn())) {
                String search = "%" + StringUtils.lowerCase(filter.getIsbn()) + "%";
                Predicate isbn = cb.like(cb.lower(root.get("isbn")), search);
                predicates.add(isbn);

            }
            if (StringUtils.isNotBlank(filter.getAuthor())) {
                String search = "%" + StringUtils.lowerCase(filter.getAuthor()) + "%";
                Predicate author = cb.like(cb.lower(root.get("author")), search);
                predicates.add(author);
            }
            if (StringUtils.isNotBlank(filter.getTitle())) {
                String search = "%" + StringUtils.lowerCase(filter.getTitle()) + "%";
                Predicate title = cb.like(cb.lower(root.get("title")), search);
                predicates.add(title);
            }
            if (StringUtils.isNotBlank(filter.getCreatedBy())) {
                String search = "%" + StringUtils.lowerCase(filter.getCreatedBy()) + "%";
                Predicate createdBy = cb.like(cb.lower(root.get("createdBy")), search);
                predicates.add(createdBy);
            }
            if (StringUtils.isNotBlank(filter.getPublisher())) {
                String search = "%" + StringUtils.lowerCase(filter.getPublisher()) + "%";
                Predicate publisher = cb.like(cb.lower(root.get("publisher")), search);
                cb.and(publisher);
            }
            if (filter.getCreatedDateFrom() != null && filter.getCreatedDateTo() != null) {
                predicates.add(cb.between(root.get("createdDate"), filter.getCreatedDateFrom(), filter.getCreatedDateTo()));
            }
            else if (filter.getCreatedDateFrom() != null) {
                predicates.add(cb.between(root.get("createdDate"), filter.getCreatedDateFrom(), LocalDateTime.now()));
            }

            if (filter.getDatePublishedFrom() != null && filter.getDatePublishedTo() != null) {
                predicates.add(cb.between(root.get("datePublished"), filter.getDatePublishedFrom(), filter.getDatePublishedTo()));
            }
            else if (filter.getDatePublishedFrom() != null) {
                predicates.add(cb.between(root.get("datePublished"), filter.getDatePublishedFrom(), LocalDate.now()));
            }

//            Predicate favorite = cb.equal(root.get("favourite"), filter.getFavorite());
//            predicates.add(favorite);

            return cb.and(predicates.toArray(new Predicate[0]));

        };
    }
}
