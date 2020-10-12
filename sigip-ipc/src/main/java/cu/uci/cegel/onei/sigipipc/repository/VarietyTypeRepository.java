package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.VarietyType;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VarietyTypeRepository extends PagingAndSortingRepository<VarietyType, Long>, QuerydslPredicateExecutor<VarietyType> {
}