package cu.uci.cegel.onei.sigipipc.repository;


import cu.uci.cegel.onei.sigipipc.persistence.Characteristic;
import cu.uci.cegel.onei.sigipipc.persistence.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecificationRepository extends PagingAndSortingRepository<Specification, Long> {

    Specification findByDescriptionEquals(String description);

    List<Specification> findByCharacteristicEquals(Characteristic characteristic);

    List<Specification> findAllByMeasurementUnitType_Id(Long tipoUM);

    List<Specification> findAllByActiveTrueAndCharacteristic_Id( Long characteristicId );

    List<Specification> findAllByActiveTrueAndCharacteristic_Description(String description);

    Integer countSpecificationsByCharacteristic_Id(Long id);


}
