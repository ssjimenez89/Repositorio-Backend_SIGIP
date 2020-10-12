package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.MeasurementUnitType;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MeasurementUnitTypeRepository extends PagingAndSortingRepository<MeasurementUnitType, Long>, QuerydslPredicateExecutor<MeasurementUnitType> {

    MeasurementUnitType findByDescriptionEquals(String description);

    List<MeasurementUnitType> findByActiveIsTrue();

}
