package cu.uci.cegel.onei.sigipipc.repository;


import cu.uci.cegel.onei.sigipipc.persistence.State;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends PagingAndSortingRepository<State, Long>, QuerydslPredicateExecutor<State> {

    State findByDescripcionEquals(String description);

}
