package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.Catchment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CatchmentRepository extends PagingAndSortingRepository<Catchment, Long>, QuerydslPredicateExecutor<Catchment> {


    @Query(value = "SELECT DISTINCT EXTRACT(year from dcaptacion.fecha) FROM dcaptacion", nativeQuery = true)
    List<Integer> getYears();

    Integer countCatchmentByEstablishmentIdAndDateBetween(Long idEstablishment, Date start, Date end);

    List<Catchment> findAllByEstablishmentIdAndDateBetween(Long idEstablishment, Date start, Date end);

    List<Catchment> findAllByPriceEquals(Long cant);

    @Query("SELECT c FROM Catchment c WHERE c.id IN :cathments")
    List<Catchment> getCatchmentIn(@Param("cathments") List<Long> cathments);

    @Query("Select c from Catchment c where c.establishment.id = :idEst and c.idVariedad = :idVar and c.date between :fechaIni and :fechaFin")
    List<Catchment> captacionAnterior(@Param("idEst") Long ideEst, @Param("idVar") Long idVar, @Param("fechaIni") Date fechaIni, @Param("fechaFin") Date fechaFin);

    Page<Catchment> findAllByFueraRangoIsTrue(Pageable pageable);

    List<Catchment> getCatchmentsByIdVariedadAndDateBetween(Long idVariedad, Date start, Date end);

    @Query("select c from Catchment c where c.fueraRango = false and c.price <> 0 and c.date between :fechaIni and :fechaFin")
    List<Catchment> captacionesDelMes(@Param("fechaIni") Date fechaIni, @Param("fechaFin") Date fechaFin);


    Optional<Catchment> findById(Long id);
}
