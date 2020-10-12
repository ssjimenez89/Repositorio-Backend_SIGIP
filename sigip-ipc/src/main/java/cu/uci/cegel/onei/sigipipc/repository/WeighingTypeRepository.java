package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.WeighingType;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeighingTypeRepository extends PagingAndSortingRepository<WeighingType, Long>, QuerydslPredicateExecutor<WeighingType> {
}
