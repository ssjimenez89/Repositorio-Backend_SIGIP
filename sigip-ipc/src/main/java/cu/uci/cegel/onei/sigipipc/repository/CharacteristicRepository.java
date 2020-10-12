package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.Characteristic;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacteristicRepository extends PagingAndSortingRepository<Characteristic, Long>, JpaSpecificationExecutor<Characteristic> {
    Characteristic findByDescriptionEquals(String description);
    List<Characteristic> findByDescriptionContaining(String description);
}
