package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.IndiceArticulo;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface IndiceArticuloRepository extends QuerydslPredicateExecutor<IndiceArticulo>, PagingAndSortingRepository<IndiceArticulo, Long> {
}
