package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.MeasurementUnitType;
import cu.uci.cegel.onei.sigipipc.persistence.Planning;
import cu.uci.cegel.onei.sigipipc.persistence.PlanningDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlanningDateRepository extends PagingAndSortingRepository<PlanningDate, Long>, QuerydslPredicateExecutor<MeasurementUnitType> {

    @Query(value = "SELECT DISTINCT EXTRACT(YEAR from fecha) from dplanificacion_fecha", nativeQuery = true)
    List<Double> obtenerAnnosPlanificados();

    @Query("SELECT DISTINCT (f) FROM PlanningDate p INNER JOIN p.planning f WHERE p.date BETWEEN :inicio AND :fin ORDER BY f.id ASC")
    List<Planning> diasPlanificados(LocalDate inicio, LocalDate fin);
}
