package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.Region;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends PagingAndSortingRepository<Region, Long>, QuerydslPredicateExecutor<Region> {

    List<Region> findRegionByDescripcion(String descripcion);
}
