package tech.elsoft.maidcclibrarymanagementsystem.persistence.repositories.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
public class RangeSpecification<T> 
{

	public Specification<T> withInRange(ZonedDateTimeRange range) 
	{
		List<Predicate> predicates = new ArrayList<>();
		
	    return (root,  query, builder) -> {
	    	
	    	// range shared in not properly formed 
	    	if(!range.isValid())
			{
	    		log.warn("ranges shared are not valid");
				return builder.conjunction();
			}
	    	
	    	buildRangePredicate(range, root, builder)
	    		.ifPresent(predicate -> predicates.add(predicate));
	    	
	    	return predicates.isEmpty() ? builder.conjunction() : builder.and(predicates.toArray(new Predicate[0]));
	    };
	 }

	private Optional<Predicate> buildRangePredicate(
		ZonedDateTimeRange range, 
		Root<T> root,
		CriteriaBuilder builder
	) 
	{
		if (range.isBetween()) {
			Predicate from =  builder.greaterThanOrEqualTo(
					root.get(range.getField()), 
					range.getFrom());
			Predicate to =  builder.lessThanOrEqualTo(
					root.get(range.getField()), 
					range.getTo());
                return Optional.of(builder.and(to, from));
			
		} else if (range.isFromSet()) {
			return Optional.of(builder.greaterThanOrEqualTo(
					root.get(range.getField()), 
					range.getFrom()));
			
		} else if (range.isToSet()) {
			return Optional.of(builder.lessThanOrEqualTo(
					root.get(range.getField()), 
					range.getTo()));
		}

		return Optional.empty();
	}
}
