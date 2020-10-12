package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.Planning;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanningRepository extends PagingAndSortingRepository<Planning, Long>, QuerydslPredicateExecutor<Planning> {
    Planning findByDayEquals(String dia);

    List<Planning> findAllByWeekEqualsOrderByIdAsc(Integer week);

}
