package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.Incidence;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenceRepository extends PagingAndSortingRepository<Incidence, Long>, QuerydslPredicateExecutor<Incidence> {

    Incidence findByAcronymEquals(String acronym);
    List<Incidence> findAllByActiveTrue();

}
