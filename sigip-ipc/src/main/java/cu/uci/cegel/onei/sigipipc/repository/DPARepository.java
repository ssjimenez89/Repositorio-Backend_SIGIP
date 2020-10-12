package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.DPA;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DPARepository extends PagingAndSortingRepository<DPA, Long>, QuerydslPredicateExecutor<DPA> {

    List<DPA> findAllByParentEquals(DPA padre);
    List<DPA> findAllByActiveTrueOrderByDescriptionAsc();
    List<DPA> findByActiveIsTrueOrderByDescriptionAsc();
}