package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.Establishment;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
    public interface EstablishmentRepository extends PagingAndSortingRepository<Establishment, Long>, QuerydslPredicateExecutor<Establishment> {

    int countByActiveIsTrue();
    List<Establishment> findAllByOrderById();

}
