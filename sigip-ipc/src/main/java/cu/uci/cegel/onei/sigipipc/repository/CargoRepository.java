package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.Cargo;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends PagingAndSortingRepository<Cargo, Long>, QuerydslPredicateExecutor<Cargo> {

    List<Cargo> findCargoByDescripcion(String descripcion);
}
