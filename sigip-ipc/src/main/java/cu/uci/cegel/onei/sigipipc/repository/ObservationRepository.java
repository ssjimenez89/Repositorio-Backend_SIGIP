package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.Observation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationRepository extends PagingAndSortingRepository<Observation, Long>, QuerydslPredicateExecutor<Observation> {

    Observation findByDescriptionEquals(String description);

    Observation findByAcronymEquals(String acronym);

}
