package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.ClassifierType;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassifierTypeRepository extends PagingAndSortingRepository<ClassifierType, Long>, QuerydslPredicateExecutor<ClassifierType> {
}
